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
            date : event.date,
            city : event.city,
            address : event.address,
            time: event.time
            photo : event.photo
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
        self.event.photo = $rootScope.tmpImage;

        check_event(self.event, function(checked) {
            if (checked) {
                console.log("added new event");
                $location.path("/events");
            } else {
                console.log("error adding event");
            }
        });
    }

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
                self.event.photo = base64Image.replace(/data:image\/jpeg;base64,/g, '');
                $rootScope.tmpImage = base64Image.replace(/data:image\/jpeg;base64,/g, '');
            }
            reader.readAsDataURL(input.files[0]);
        }
    };
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

    $rootScope.characterSet = {};

    $http.get('/events/' + $routeParams.id, config)
        .then( function(response) {
            console.log(response.data);
            self.event.name = response.data.name;
            self.event.date = response.data.date;
            self.event.time = response.data.time;
            self.event.address = response.data.address;
            self.event.city = response.data.city;
            self.event.photo = response.data.photo;
            $rootScope.characterSet = response.data.characterSet;
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