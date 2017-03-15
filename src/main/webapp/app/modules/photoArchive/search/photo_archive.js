angular.module('search').controller('SearchPhotoArchiveController', function($scope, SearchService,PhotoService){
    $scope.sortbyOptions = [
        {  name: 'ID', value: 'id' },
        {  name: 'Date', value: 'date' },
        {  name: 'Number', value: 'number' },
        {  name: 'Author', value: 'author' },
    ];
    $scope.departments = [
        {code:"GIT",label:"GIT"},
        {code:"TUG",label:"TUG"},
        {code:"ELM",label:"ELM"},
        {code:"TUGO",label:"TUGO"},
        {code:"MUMU",label:"MUMU"},
        {code:"EGK",label:"EGK"}];


    $scope.search = function() {
        PhotoService.search($scope.searchParameters).then(function(result) {
            $scope.totalItems = result.count;
            $scope.pageSize = 100;
            $scope.response = result;
            console.log(result);
        });
    };

    $scope.searchDefault = function() {
        $scope.searchParameters = {};
        $scope.search();
    };

    $scope.searchDefault();

}).factory("PhotoService", ['$http', function ($http) {
    return {
        search: function (searchParameters) {
            return $http.post('/search/photo-archive', searchParameters)
                .then(function (response) {
                    return response.data;
                });
        }
    };
}]).controller('PhotoArchiveController', function($scope, SearchService, $uibModal, $http,$stateParams){
    $scope.loadInfo = function() {
        $http.get('/search/photo-archive/' + $stateParams.id).then(successCallback, errorCallback);
        function successCallback(response){
            console.log(response);
            $scope.image = response.data.result[0];
        } function errorCallback(response){
            $scope.image = "ehm, an error..";
        }
    };
    $scope.loadInfo();
});