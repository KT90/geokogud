var module = angular.module("geoApp");

var constructor = function ($scope, $stateParams, configuration, $http, applicationService,$window,$translate) {
    var vm = this;
    vm.service = applicationService;
    var search = vm.service.getTranslationRoot($stateParams.type);

    $scope.isIdentifierFieldsCollapsed = false;
    $scope.isLocationFieldsCollapsed = true;
    $scope.isAnalysesFieldsCollapsed = true;
    $scope.isInstitutionsCollapsed = true;

    function onSearchData(result){
        $scope.pageSize = 100;
        $scope.totalItems = result.data.count;
        $scope.windowWidth = "innerWidth" in window ? window.innerWidth : document.documentElement.offsetWidth;
        if ($scope.windowWidth > 400) {
            $scope.searchParameters.maxSize = 5;
        } else {
            $scope.searchParameters.maxSize = 2;
        }
        // Window resize event
        var w = angular.element($window);
        w.bind('resize', function () {
            $scope.windowWidth = "innerWidth" in window ? window.innerWidth : document.documentElement.offsetWidth;
            if ($scope.windowWidth > 400) {
                $scope.searchParameters.maxSize = 5;
            } else {
                $scope.searchParameters.maxSize = 2;
            }
            $scope.$apply();
        });
        $scope.response = result.data;
        if ($scope.isMapHidden) {
            $scope.getLocalities($scope.response.data);
        }
    }

    $scope.search = function () {
        console.log($scope.searchParameters)
        applicationService.getList($stateParams.type,$scope.searchParameters,onSearchData)
    };

    $scope.searchDefault = function () {
        $scope.searchParameters = {sortField: {}, dbs: []};
        $scope.searchParameters.sortField.sortBy = "id";
        $scope.sortByAsc = true;
        vm.service.toggle("GIT", $scope.searchParameters.dbs);
        $scope.search();
    };

    $scope.searchDefault();

    $scope.entitySelected = function (item) {
        $scope.sampleSearch.locality.name = item.locality;
    };
    $scope.order = function (predicate) {
        $scope.sortByAsc = ($scope.searchParameters.sortField.sortBy === predicate ? !$scope.sortByAsc : true);
        $scope.searchParameters.sortField.sortBy = predicate;
        !$scope.sortByAsc ? $scope.searchParameters.sortField.order = "ASCENDING" : $scope.searchParameters.sortField.order = "DESCENDING";
        $scope.search();
    };

    $scope.showHint = function () {
        $scope.isHintHidden = !$scope.isHintHidden;
        $scope.getLocalities($scope.response.result);
    };

    $scope.showMap = function () {
        $scope.isMapHidden = !$scope.isMapHidden;
        $scope.getLocalities($scope.response.result);
    };
    $scope.getLocalities = function (list) {
        $scope.localities = [];
        if (list) {
            angular.forEach(list, function (el) {
                if (el.localityLatitude != null && el.localityLongitude != null && el.localityEn != null && el.locality != null && el.localityId != null)
                    $scope.localities.push({
                        latitude: el.localityLatitude,
                        longitude: el.localityLongitude,
                        localityEng: el.localityEn,
                        localityEt: el.locality,
                        fid: el.localityId
                    })
            })
        }
        return $scope.localities;
    };


    function loadHintText() {
        var hintRoot = search+'.HELP_POPUP.';
        $translate([hintRoot+'HEADING',hintRoot+'PARAGRAPH_ONE',hintRoot+'PARAGRAPH_TWO',hintRoot+'PARAGRAPH_THREE',hintRoot+'PARAGRAPH_FOUR']).then(function (translations) {
            $scope.popupHeading = translations[hintRoot+'HEADING'];
            $scope.paragraphOne = translations[hintRoot+'PARAGRAPH_ONE'];
            $scope.paragraphTwo = translations[hintRoot+'PARAGRAPH_TWO'];
            $scope.paragraphThree = translations[hintRoot+'PARAGRAPH_THREE'];
            $scope.paragraphFour = translations[hintRoot+'PARAGRAPH_FOUR'];
        });
    }
    loadHintText();


};

constructor.$inject = ["$scope", "$stateParams", "configuration", "$http", 'ApplicationService', '$window','$translate'];

module.controller("SearchController", constructor);