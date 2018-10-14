var RouteAppModule = angular.module('RouteAppModule', []);

RouteAppModule.config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider.state('index', {
        url: '/index',
        templateUrl: '/resources/app/template/system/info.html',
    }).state('system_info', {
        url: '/system/info',
        templateUrl: '/resources/app/template/system/info.html',
    }).state('about', {
        url: '/about/about',
        templateUrl: '/resources/app/template/about/about.html',
    }).state('log', {
        url: '/log/info',
        templateUrl: '/resources/app/template/log/info.html',
    }).state('devices', {
        url: '/devices/info',
        templateUrl: '/resources/app/template/devices/info.html',
    }).state('server', {
        url: '/config/server',
        templateUrl: '/resources/app/template/config/server.html',
    });
    $urlRouterProvider.when('/sss', {
        templateUrl: '/resources/app/template/other/container.html',
        //controller: 'HomeController'

    }).when('/xxx', {
        templateUrl: '/resources/app/template/other/container.html',
        //controller: 'HomeController'

    }).otherwise('/index');
})

RouteAppModule.run(function ($rootScope, $state, $stateParams) {
    window.getState = function () {
        return $state;
    };
    window.getStateParams = function () {
        return $stateParams;
    };
});