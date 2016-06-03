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
        controller: 'event_controller',
        controllerAs: 'controller'
    }).otherwise('/');

    //$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});

app.controller('home', function($http, TokenFactory) {

    var self = this;

    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json"
        }
    };

    $http.get('/resource',config).then(function(response) {
        self.greeting = response.data;
    });

});


app.controller('navigation', function($rootScope, $http, $location, $route, TokenFactory) {

    var self = this;
    self.error=false;
    self.credentials = {};

    self.login = function() {

        var config = {
            headers:{
                "AUTH_TOKEN" : self.credentials.username + ":" + self.credentials.password,
                "Accept" : "application/json"
            }
        };
        $http.get("/user",config)
            .then(
                function(response){
                    if(response.data.role === "ROLE_USER"){
                        self.error = false;
                        $location.path("/");
                        $rootScope.authenticated = true;
                        TokenFactory.setValue(self.credentials.username + ":" + self.credentials.password);
                    }else{
                        self.error = true; // todo show be msg about error
                        $rootScope.authenticated = false;
                    }

                },
                function(response){
                    console.log("Login failed");
                    self.error = true;
                    $rootScope.authenticated = false;

                }
            )


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
    self.error = "";
    self.credentials = {};

    self.signup = function() {

        var user = {
            username : self.credentials.username,
            password : self.credentials.password,
            role: self.credentials.role
        };

        if (self.credentials.repeat_password == self.credentials.password) {

            console.log("passwords match");
            $http.post("/user/add", user)
                .then(function (response) {
                    $location.path("/login");
                    console.log("User " + user.username + " successfully added");
                },
                function(response){
                    self.error = "Signup Failed";  // todo check response status
                });

        } else {
           self.error = "Passwords don't match";
        }

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

app.controller('event_controller', function($http, $routeParams, $rootScope, $location) {

    var self = this;
    console.log("getting one event");
    console.log($routeParams.id);
    $http.get('/events/' + $routeParams.id).then(function(response) {
        console.log("Got event");
        console.log(response.data);
        self.event_info = response.data;
    });

    self.joinEvent = function () {
        console.log("joining event");
        $http.get('events/' + $routeParams.id + '/join/' + $rootScope.username)
            .then( function (response) {
                console.log("Response from joining");
                console.log(response.data);
                $location.path('#/events');
        })
    }

});

app.constant('authToken', 'authToken');

app.factory('TokenFactory', function() {
    var authToken = {
        value: "null:null"
    };

    authToken.setValue = function(val) {
        this.value = val;
    };

    authToken.getValue = function() {
        return this.value;
    };

    return authToken;
});