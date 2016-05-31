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

    self.currentCharacter = {};

    $rootScope.characters = {};

    var getEvent = function () {
        console.log("getting one event");
        console.log($routeParams.id);
        $http.get('/events/' + $routeParams.id).then(function(response) {
            console.log("Got event");
            console.log(response.data);
            self.event_info = response.data;

        });
    };


    var getCharacters = function () {
        console.log('showing characters');
        $http.get('/users/'+$rootScope.username+'/characters', {}).then(function (response) {
            console.log("characters: ");
            $rootScope.characters = response.data;
            console.log(response.data);
        });
    };

    var set_character = function () {
        console.log("setting current character");
        $rootScope.characterId = self.currentCharacter.id;
        console.log(self.currentCharacter);
        console.log("Current character set");

    };

    self.setCharacter = function (id) {
        console.log("setting current character in setcharacter");
        $rootScope.characterId = id;
        console.log(id);
        console.log("Current character set");
    };

    getCharacters();
    getEvent();

    set_character();

    self.joinEvent = function () {

        console.log("joining event");
        $http.post('events/' + $routeParams.id + '/join/' +$rootScope.characterId, {})
            .then( function (response) {
                console.log("Response from joining");
                console.log(response.data);
                $location.path("/events");
            }, function onError (response) {
                console.log("Error in joining event");
                console.log(response.data);
                $location.path("/events");
            })
    }

});