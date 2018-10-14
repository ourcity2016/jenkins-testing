var ControllerApp = angular.module('ControllerAppModule', [ 'ngSanitize','ui.router',
		'RouteAppModule', 'ControllerAppModuleController','ngAnimate' ]);
ControllerApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    var param = function(obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;

        for(name in obj) {
            value = obj[name];

            if(value instanceof Array) {
                for(i=0; i<value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name + '[' + i + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            }else if(value instanceof Object) {
                for(subName in value) {
                    subValue = value[subName];
                    fullSubName = name + '[' + subName + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            }else if(value !== undefined && value !== null)
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
            }

        return query.length ? query.substr(0, query.length - 1) : query;
    };

    $httpProvider.defaults.transformRequest = [function(data) {
        return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
    }];
}]);
ControllerApp.config(function($stateProvider, $urlRouterProvider,$locationProvider){
    $locationProvider.hashPrefix('');
});
ControllerApp.animation('.view-slide-in', function () {
    return {
        enter: function(element, done) {
            element.css({
                opacity: 0.5,
                position: "relative",
                top: "10px",
                left: "20px"
            })
                .animate({
                    top: 0,
                    left: 0,
                    opacity: 1
                }, 1000, done);
        }
    };
});
ControllerApp.animation('.repeat-animation', function () {
    return {
        enter : function(element, done) {
            console.log("entering...");
            var width = element.width();
            element.css({
                position: 'relative',
                left: -10,
                opacity: 0
            });
            element.animate({
                left: 0,
                opacity: 1
            }, done);
        },
        leave : function(element, done) {
            element.css({
                position: 'relative',
                left: 0,
                opacity: 1
            });
            element.animate({
                left: -10,
                opacity: 0
            }, done);
        },
        move : function(element, done) {
            element.css({
                left: "2px",
                opacity: 0.5
            });
            element.animate({
                left: "0px",
                opacity: 1
            }, done);
        }
    };
});