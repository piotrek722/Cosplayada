angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home',
        controllerAs: 'controller'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation',

    function($rootScope, $http, $location, $route) {

        var self = this;
        
        self.tab = function(route) {
            return $route.current && route === $route.current.controller;
        };

        var authenticate = function(credentials, callback) {

            var headers = credentials ? {
                authorization : "Basic "
                + btoa(credentials.username + ":"
                    + credentials.password)
            } : {};

            // $http.get('user', {
            //     headers : headers
            // }).then(function(response) {
            //     if (response.data.name) {
            //         $rootScope.authenticated = true;
            //     } else {
            //         $rootScope.authenticated = false;
            //     }
            //     callback && callback($rootScope.authenticated);
            // }, function() {
            //     $rootScope.authenticated = false;
            //     callback && callback(false);
            // });

            var params = {
                username : credentials.username,
                password : credentials.password
            }
            var user = {
                params: params
            }
            $http.get('auth', user).then(function (response) {
                console.log(response.data);
                $rootScope.authenticated = response.data;
                callback && callback($rootScope.authenticated);
                $rootScope.username = credentials.username;
            })

        }

     //   authenticate();

        self.credentials = {};
        self.login = function() {
            authenticate(self.credentials, function(authenticated) {
                if (authenticated) {
                    console.log("Login succeeded");
                    $location.path("/");
                    self.error = false;
                    $rootScope.authenticated = true;
                } else {
                    console.log("Login failed");
                    $location.path("/login");
                    self.error = true;
                    $rootScope.authenticated = false;
                }
            })
        };

        self.logout = function() {
            $http.post('logout', {}).finally(function() {
            //    console.log(response.data);
                $rootScope.authenticated = false;
             //   callback && callback(false);
                console.log("Logout succeeded");
                $location.path("/");
            });
        }

    }).controller('home', function($http) {
    var self = this;
    $http.get('/resource/').then(function(response) {
        self.greeting = response.data;
    })
});