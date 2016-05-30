app.controller('user_controller', function($rootScope, $http, $location, $route) {

    var self = this;

    console.log('showing user');
    $http.get('/users/'+$rootScope.username, {}).then(function (response) {
        self.user_info = response.data;
        console.log(response.data);
    });

    self.character = {};

    var check_character = function (character, callback) {
        $rootScope.checkedCh = true;

        var params = {
            user : $rootScope.username,
            name : character.name,
            description : character.description
        };

        var character_info = {
            params : params
        };

        console.log(character_info);

        $http.get('/characters/add', character_info).then(function(response) {
            console.log("Adding new character");
            console.log(response.data);
        });
        callback && callback($rootScope.checkedCh);
    };

    self.addCharacter = function() {
        check_character(self.character, function(checkedCh) {
            if (checkedCh) {
                console.log("added new character");
                $location.path("/");
            } else {
                console.log("error adding character");
            }
        });
    }

});