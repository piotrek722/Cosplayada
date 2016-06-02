app.controller('character_controller', function($rootScope, $http, $location, $routeParams) {

    var self = this;

    console.log('showing character');
    $http.get('/users/' + $rootScope.username + '/characters/' + $routeParams.id, {}).then(function (response) {
        console.log("Got character details");

        self.character_info = {
            idd: response.data.id,
            name: response.data.name,
            description: response.data.description,
            photo: response.data.photo
        };
        console.log(self.character_info);
        console.log(response.data);
    });
});