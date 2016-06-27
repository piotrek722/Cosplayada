app.controller('updateUser_controller', function($rootScope, $http, $location, $routeParams, TokenFactory) {

    var self = this;

    var config = {
        headers:{
            "AUTH_TOKEN" : TokenFactory.getValue(),
            "Accept" : "application/json"
        }
    };

    self.user = {};

    var check_user = function (user, callback) {
        $rootScope.checkedCh = true;

        var user_info = {
            user : $rootScope.username,
            photo : user.photo
        };

        console.log(user_info);

        $http.post('/characters/add', user_info, config).then(function(response) {
            console.log("Adding new character");
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