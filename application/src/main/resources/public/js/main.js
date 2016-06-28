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
    }).when('/user', {
        templateUrl: 'userView.html',
        controller: 'user_controller',
        controllerAs: 'controller'
    }).when('/characters/:id', {
        templateUrl: 'character.html',
        controller: 'character_controller',
        controllerAs: 'controller'
    }).when('/updateUser', {
        templateUrl: 'updateUser.html',
        controller: 'updateUser_controller',
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
    $rootScope.username = "";

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
                        $rootScope.username = self.credentials.username;
                        TokenFactory.setValue(self.credentials.username + ":" + self.credentials.password);
                    }else{
                        self.error = "Login failed. Please try again.";
                        $rootScope.authenticated = false;
                    }

                },
                function(response){
                    console.log("Login failed");
                    self.error = "Login failed. Please try again.";
                    $rootScope.authenticated = false;

                }
            )


    };

    self.logout = function() {
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

            $rootScope.checked = true;
            $http.post("/user/add", user)
                .then(function (response) {
                    console.log(response.data);
                    if (!response.data.status) {
                        self.error = response.data.message;
                    }
                    else {
                        $location.path("/login");
                        console.log("User " + user.username + " successfully added");
                    }

                },
                function(response){
                    self.error = response.data.message;
                    console.log(response.data);
                });

        } else {
           $rootScope.checked = false;
           self.error = "Passwords don't match";
        }

    };
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
app.controller('updateUser_controller', function($rootScope, $http, $location, $routeParams, TokenFactory) {

    var self = this;
    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json",
        }
    };

    
    self.user = {};

    var check_user = function (user, callback) {

        $rootScope.checkedCh = true;
        self.user.username = $rootScope.username;
        $http.post('/user/update', self.user, config).then(function(response) {
            console.log("Update character");
            console.log(response.data);
        });
        callback && callback($rootScope.checkedCh);
    };

    self.updateUser = function() {
        self.user.photo = $rootScope.tmpImage;
        check_user(self.user, function(checkedCh) {
            if (checkedCh) {
                console.log("added new character");
                self.photoViewCondition = false;
                $location.path("/");
            } else {
                console.log("error adding character");
            }
        });
    };

    $rootScope.uploadFile = function (input){
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {

                $('#photoView').attr('src', e.target.result);
                //Create a canvas and draw image on Client Side to get the byte[] equivalent
                var canvas = document.createElement("canvas");
                var imageElement = document.createElement("img");

                imageElement.setAttribute('src', e.target.result);
                canvas.width = imageElement.width;
                canvas.height = imageElement.height;
                var context = canvas.getContext("2d");
                context.drawImage(imageElement, 0, 0);
                var base64Image = canvas.toDataURL("image/jpeg");

//                 //Removes the Data Type Prefix
//                 //And set the view model to the new value
                self.user.photo = base64Image.replace(/data:image\/jpeg;base64,/g, '');
                $rootScope.tmpImage = base64Image.replace(/data:image\/jpeg;base64,/g, '');
            }
            reader.readAsDataURL(input.files[0]);
        }
    };

});