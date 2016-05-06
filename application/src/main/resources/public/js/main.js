$(".nav a").on("click", function(){
    $(".nav").find(".active").removeClass("active");
    $(this).parent().addClass("active");
});

var app = angular.module('app',[]);

app.controller('nav', function($scope,$http){

    $scope.site='main.html';

    $scope.changeSite = function(name){
       $http.get(name).then(function(response){
           $scope.content = response.data;
           $scope.site = name;
       });
    };

    $scope.getContent = function(){
        return $scope.site;
    }

});