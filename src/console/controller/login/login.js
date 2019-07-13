var app = angular.module("loginApp",[]);
app.controller("loginController",function ($scope, $http,$state) {
    $scope.login = function()
    {
    	var userName = $scope.userName;
    	var password = $scope.password;
		/*$http({ 
		 method: 'post', 
		 url: 'http://localhost:28114/unified/sec/login',
		 data:{"X-Auth-User":userName,"X-Auth-Password":password,"roleId":"1"}
		}).then(function(data,status,headers,config) { 
			console.log(status);
			console.log(headers);
			console.log(config);
		    console.log("成功:" + data["error"]);
		},function(data,status,headers,config) { 
		    console.log("失败:" + data);
		});*/
		$state.go('home.index');

    }

});