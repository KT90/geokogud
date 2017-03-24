'use strict';

//routes
angular.module('main', []).config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('main', {
        url: "/info",
        templateUrl: "app/main/info.html"
    });
});

//routes
angular.module('news', []).config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('news', {
        url: "/news",
        templateUrl: "app/main/news.html"
    });
});

//routes
angular.module('map', []).config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('map', {
        url: "/map",
        templateUrl: "app/main/map.html"
    });
});
//routes
angular.module('usingCollection', []).config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('usingCollection', {
                url: "/usingCollection",
        templateUrl: "app/main/using_collection.html"
      });
});
-//routes
angular.module('database', []).config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('database', {
            url: "/database",
            templateUrl: "app/main/database.html"
   });
});

angular.module('main').controller('MainInfoController', function(){
    console.log("MainInfoController");
}).controller('MainNewsController', function(){
    console.log("MainNewsController");
}).controller('MainMapController', function(){
    console.log("MainMapController");
})