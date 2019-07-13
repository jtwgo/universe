myapp.controller("selectCtrl",function ($scope,$http) {

    $scope.sr="北京";
    $scope.cx=function (sr) {
        $http({
            url:"https://free-api.heweather.com/v5/weather?city="+sr+"&key=545d63e185fc48169a43cbabba6e74d2",
            method:"GET"
        }).then(function(data){

            $scope.data=data.data;
        })
    }

})