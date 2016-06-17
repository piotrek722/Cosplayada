app.controller('events_controller', function($rootScope, $http, $location, $route, TokenFactory) {

    var self = this;

    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json"
        }
    };

    $rootScope.evenstArray = {};

    console.log('showing events');
    $http.get('/events', config).then(function (response) {
        $rootScope.eventsArray = response.data;
        console.log(response.data);
    });

});

app.controller('add_events_controller', function($rootScope, $http, $location, $route, TokenFactory) {

    var self = this;

    self.event = {};
    var check_event = function (event, callback) {

        var event_info = {
            name : event.name,
            city : event.city
        };
        
        console.log(event_info);

        var config = {
            headers:{
                "AUTH_TOKEN" : TokenFactory.getValue(),
                "Accept" : "application/json"
            }
        };

        $http.post('/events/add', event_info, config).then(function(response) {
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

app.controller('event_controller', function($http, $routeParams, $rootScope, $location, TokenFactory) {

    var self = this;
    self.error = false;
     
    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json"
        }
    };

    self.currentCharacter = {};

    self.event = {};
    
    $http.get('/events/' + $routeParams.id, config)
        .then( function(response) {
            console.log(response.data);
            self.event.name = response.data.name;
            self.event.city = response.data.city;
            console.log(self.event);
        }, function onError (response) {
            console.log("Error in joining event");
            console.log(response.data);

        });
    

    self.joinEvent = function () {

        console.log("joining event");
        console.log(self.currentCharacter);
        $http.post('events/' + $routeParams.id + '/join/' +self.currentCharacter.id, {}, config)
            .then( function (response) {
                console.log("Response from joining");
                if (response.data.status) {
                    console.log(response.data);
                    $location.path("/events");
                } else {
                    self.error = response.data.message;
                    console.log(response.data);
                }
            }, function onError (response) {
                console.log("Error in joining event");
                console.log(response.data);
                $location.path("/events");
            })
    }

});