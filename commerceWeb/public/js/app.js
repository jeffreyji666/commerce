var commerceApp = angular.module('commerceApp', [
    'ngRoute', 'ngAnimate', 'commerceCtrls', 'commerceFilters',
    'commerceServices', 'commerceDirectives'
]);

commerceApp.config(function($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl: 'tpls/home.html',
        controller: 'HomeCtrl'
    }).when('/login', {
        templateUrl: 'tpls/login.html',
        controller: 'LoginCtrl'
    }).otherwise({
        redirectTo: '/home'
    })
});