app.controller('character_controller', function($rootScope, $http, $location, $routeParams, TokenFactory) {

    var self = this;

    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json"
        }
    };

    console.log('showing character');
    // $http.get('/users/' + $rootScope.username + '/characters/' + $routeParams.id, config).then(function (response) {
    //     console.log("Got character details");
    //
    //     self.character = response.data ;
    // });

    $http.get('/characters/' + $routeParams.id, config).then(function (response) {
        console.log("Got character details");

        self.character = response.data ;
    });
});