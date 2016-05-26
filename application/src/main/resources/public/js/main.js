var app = angular.module('main', [ 'ngRoute' ]);


app.config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl: 'home.html',
        controller: 'home',
        controllerAs: 'controller'
    }).when('/login', {
        templateUrl: 'login.html',
        controller: 'navigation',
        controllerAs: 'controller'
    }).when('/signup', {
        templateUrl: 'signup.html',
        controller: 'signup',
        controllerAs: 'controller'
    }).when('/logout', {
        templateUrl: 'logout.html',
        controller: 'navigation',
        controllerAs: 'controller'
    }).when('/events', {
        templateUrl: 'eventsView.html',
        controller: 'events_controller',
        controllerAs: 'controller'
    }).when('/events/add', {
        templateUrl: 'addEvent.html',
        controller: 'add_events_controller',
        controllerAs: 'controller'
    }).when('/events/:id', {
        templateUrl: 'event.html',
        controller: 'events_controller',
        controllerAs: 'controller'
    }).otherwise('/');

    //$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});

app.controller('home', function($http) {

    var self = this;

    $http.get('/resource').then(function(response) {
        self.greeting = response.data;
    });

});


app.controller('navigation', function($rootScope, $http, $location, $route) {

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
                $rootScope.authenticated = false;
                console.log("Logout succeeded");
                $location.path("/logout");
            });
        };

});

app.controller('signup', function($rootScope, $http, $location, $route) {

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
        };
    });


app.controller('events_controller', function($rootScope, $http, $location, $route) {

    var self = this;
    $rootScope.evenstArray = {};

    console.log('showing events');
    $http.get('/events', {}).then(function (response) {
        $rootScope.eventsArray = response.data;
        console.log(response.data);
    });

});


app.controller('add_events_controller', function($rootScope, $http, $location, $route) {

    var self = this;
    self.event = {};
    var check_event = function (event, callback) {

                var params = {
                    name : event.name,
                    city : event.city
                };

        var event_info = {
            params : params
        };

                $http.get('/events/add', event_info).then(function(response) {
                    console.log("Adding new event");
                    console.log(response.data);
                });
                callback && callback($rootScope.checked);
            };

    self.addEvent = function() {
        check_event(self.event, function(checked) {
            if (checked) {
                console.log("added new event");
                $location.path("/events");
            } else {
                console.log("error adding event");
            }
        });
    }
});