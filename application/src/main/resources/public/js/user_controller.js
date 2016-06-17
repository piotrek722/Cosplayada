app.controller('user_controller', function($rootScope, $http, $location, $route, TokenFactory) {

    var self = this;

    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json"
        }
    };

    console.log('showing user');
    $http.get('/users/'+$rootScope.username, config).then(function (response) {
        console.log("Got user details");
        console.log(response.data.nickname);
        self.user_info = {
            userId : response.data.id,
            nickname : response.data.nickname
        };
        console.log(self.user_info);
        //console.log(response.data);
    });

    self.character = {};

    var check_character = function (character, callback) {
        $rootScope.checkedCh = true;

        var character_info = {
            user : $rootScope.username,
            name : character.name,
            description : character.description,
            photo : character.photo
        };

        console.log(character_info);

        $http.post('/characters/add', character_info, config).then(function(response) {
            console.log("Adding new character");
            console.log(response.data);
        });
        callback && callback($rootScope.checkedCh);
    };

    self.addCharacter = function() {
        self.character.photo = $rootScope.tmpImage;
        check_character(self.character, function(checkedCh) {
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
                self.character.photo = base64Image.replace(/data:image\/jpeg;base64,/g, '');
                $rootScope.tmpImage = base64Image.replace(/data:image\/jpeg;base64,/g, '');
            }
            reader.readAsDataURL(input.files[0]);
        }
    };

    $rootScope.characters = {};

    console.log('showing characters');
    $http.get('/users/'+$rootScope.username+'/characters', config).then(function (response) {
        console.log("characters: ");
        $rootScope.characters = response.data;
        console.log(response.data);
    });

});