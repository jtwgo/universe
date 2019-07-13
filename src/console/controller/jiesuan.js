myapp.controller("jsCtrl",function ($scope,myservice) {
    //调用服务
    $scope.dd=myservice.dd;

    //进行全选
    $scope.qx=false;
    $scope.xz=function () {
        if( $scope.qx==true){

            for(var i=0;i<$scope.dd.length;i++){

                $scope.dd[i].done=true;

            }
        } else if( $scope.qx==false){

            for(var i=0;i<$scope.dd.length;i++){

                $scope.dd[i].done=false;
            }
        }
    }

    //进行反选
    $scope.fx=function () {
        $scope.n=0;
        for(var i=0;i<$scope.dd.length;i++){

            if($scope.dd[i].done==true){

                $scope.n++;
            }
        }
        if( $scope.n==$scope.dd.length){
            $scope.qx=true;
        }else{
            $scope.qx=false;
        }
    }




    //点击删除的事件
    $scope.del=function (index) {

        //判断数量如果数量大于等于2时让数量减减，否则就从数组中删除
        if($scope.dd[index].done==true) {

            if ($scope.dd[index].num >= 2) {

                $scope.dd[index].num--;

            } else {

                $scope.dd.splice(index, 1);
            }
        }
    }
    //批量删除

    $scope.delAll=function () {
        for(var i=0;i<$scope.dd.length;i++){
            if($scope.dd[i].done==true){
                $scope.dd.splice(i,1);
                i--;
            }

        }
    }

    //计算总价
    $scope.zongjia=function () {
        var zj=0;
        for(var i=0;i<$scope.dd.length;i++){
            zj+=$scope.dd[i].num*$scope.dd[i].price;
        }
        return zj;
    }

});
