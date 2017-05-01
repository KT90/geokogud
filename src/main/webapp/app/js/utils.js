var module = angular.module('geoApp');

var constructor = function ($http,$location, configuration) {

    var service = {};

    service.isFunction = function (testable) {
        return typeof testable == "function";
    };

    service.httpGet = function (url, data, successCb, errorCb, headers) {
        var config = {
            "params": data,
            "headers": headers ? headers : {},
            "method": "GET",
            "url": url
        };

        service.httpRequest(url, config, successCb, errorCb)
    };

    service.httpPost = function (url, data, successCb, errorCb, headers, composeSearchUrl) {
        if(composeSearchUrl) service.composeUrl(data);
        var config = {
            "data": data,
            "headers": headers ? headers : {},
            "method": "POST",
            "url": url
            //"params": params ? params : ""
        };

        service.httpRequest(url, config, successCb, errorCb)
    };

    service.composeUrl = function(data) {
        var url = "", currentTable = $location.$$path.split('/')[1];
        angular.forEach(Object.keys(data), function(attr){
            if(attr != 'sortField' && attr != 'dbs') {

               if(configuration.urlHelper[currentTable]) {
                   var fieldName = configuration.urlHelper[$location.$$path.split('/')[1]].fields[attr];

                   if(fieldName)
                       url += fieldName + "_1=" + configuration.urlHelper['lookUpType'][data[attr].lookUpType] +"&"+fieldName+"="+(data[attr].name ? data[attr].name : "") +"&";
               }
            }
        });
        if(url != "") {
            url +="&currentTable="+currentTable.trim();
            //todo: add dbs and sortFields
        }
        url == "" ? $location.path($location.$$path).search() : $location.path($location.$$path).search(url);
        $location.replace();

    };

    service.preRequestCallbacks = [];

    service.httpRequest = function (url, config, successCb, errorCb) {

        for (var i in service.preRequestCallbacks) {
            if (service.isFunction(service.preRequestCallbacks[i])) {
                service.preRequestCallbacks[i]();
            }
        }

        $http(config).then(successCallback, errorCallback);

        function successCallback(response, status, headers, config) {
            service.isFunction(successCb) && successCb(response, status, headers, config);
        }

        function errorCallback(err, status, headers, config) {
            service.isFunction(errorCb) && errorCb(err, status, headers, config);
        }

    };

    service.inArray = function (elem, arr) {
        return arr.indexOf(elem) >= 0;
    };

    service.toggleInArray = function (elem, arr) {
        var inArr = service.inArray(elem, arr);
        if (inArr) {
            arr.splice(arr.indexOf(elem), 1);
        }
        else {
            arr.push(elem);
        }
    };

    return service;
};

constructor.$inject = ['$http', '$location', 'configuration'];

module.service('utils', constructor);

module.filter('split', function() {
    return function(input, splitChar, splitIndex) {
        return input.split(splitChar)[splitIndex];
    }
});

module.factory("SearchFactory", ['$http', 'configuration', function($http, configuration) {
    return {
        autocompleteSearch: function (table, term, searchField) {
            return $http.get(configuration.autocompleteUrl, {params:{table: table, term: term, searchField: searchField}})
                .then(function (response) {
                    return response.data.results;
                });
        }
    };
}]).factory("WebPagesFactory", ['$http', 'configuration', function($http, configuration) {

    var getData = function(id) {
        return $http({method:"GET", url: configuration.webPagesUrl + "/" + id}).then(function(result){
            return result.data.results[0];
        });
    };
    return { getData: getData };
}]).factory("GlobalSearchFactory", ['$http',function($http){
    return {
        searchGlobally: function(query, successfulCallback) {
            return $http.get('/search/global/'+query).then(successfulCallback);
        }
    };
}]);