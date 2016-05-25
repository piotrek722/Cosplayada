angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl: 'home.html',
        controller: 'home',
        controllerAs: 'controller'
    }).when('/login', {
        templateUrl: 'login.html',
        controller: 'navi',
        controllerAs: 'controller'
    }).when('/signup', {
        templateUrl: 'signup.html',
        controller: 'signup',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navi',

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
            };
            var user = {
                params: params
            };
            $http.get('auth', user).then(function (response) {
                console.log(response.data);
                $rootScope.authenticated = response.data;
                callback && callback($rootScope.authenticated);
                $rootScope.username = credentials.username;
            })

        };

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
            console.log("trying to log out");
            $http.post('logout', {}).finally(function() {
            //    console.log(response.data);
                $rootScope.authenticated = false;
             //   callback && callback(false);
                console.log("Logout succeeded");
                $location.path("/");
            });
        };



    }).controller('home', function($http) {
    var self = this;
    $http.get('/resource/').then(function(response) {
        self.greeting = response.data;
    })

    }).controller('signup',

    function($rootScope, $http, $location, $route) {

        var self = this;
        self.credentials = {};

        var check_password = function(credentials, callback) {
            if (credentials.password == credentials.repeat_password) {
                $rootScope.checked = true;
                console.log("passwords match");

                var params = {
                    username : credentials.username,
                    password : credentials.password
                };
                var user = {
                    params: params
                };

                console.log("trying to add user");
                $http.get("/users/add/", user).then(function(response) {
                    console.log("added "+ credentials.username);
                    console.log(response.data);
                });

                callback && callback($rootScope.checked);
            } else {
                $rootScope.checked = false;
                console.log("passwords dont match");
            }
        };

        self.signup = function() {
            check_password(self.credentials, function(checked) {

                console.log("inside singup");

                if (checked) {
                    console.log("user successfully added");
                    $location.path("/");
                } else {
                    console.log("Signup failed");
                    $location.path("/signup");
                }
                
            });
        }
});