$(".nav a").on("click", function(){
    $(".nav").find(".active").removeClass("active");
    $(this).parent().addClass("active");
});

var app = angular.module('app',[]);

app.controller('nav', function($scope){

    $scope.site='main.html';

    $scope.changeSite = function(name){
        $scope.site = name;
    };

    $scope.getContent = function(){
        return $scope.site;
    }

});

app.controller('userForm',function($scope,$http){4

    $scope.createUser = function(){
        $http.post("/users/add/?name=" + $scope.login);
        //password pewnie w hashu
    }

});