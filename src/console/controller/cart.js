myapp.controller("cartCtrl",function ($scope,myservice) {
    $scope.data=myservice.hh;
    //实现添加的功能
    $scope.dd=myservice.dd;
    $scope.add=function (item) {

        $scope.has=false;   //判断性数组中的名字和要添加的物品名字是否相等，相等说明是同一件商品
        for(var i=0;i< $scope.dd.length;i++){
            //判断性数组中的名字和要添加的物品名字是否相等，相等说明是同一件商品
            if( $scope.dd[i].name==item.name){
                $scope.has=true;
                $scope.dd[i].num++;  //如果是同一件商品添加的时候就让数量加加
                break;
            }else{
                $scope.has=false;
            }
        }
        //判断$scope.has为false时说明是不一样的商品
        if($scope.has==false){
            $scope.dd.push({name:item.name,num:1,price:item.price,done: false});
        }

    };
    //价格区间进行排序
    $scope.ss="--请选择--";
    $scope.pricefilter=function (item) {
        $scope.ff=$scope.ss;
        if($scope.ss!="--请选择--"){
            var arr=$scope.ff.split("-");//以“-”拆分字符串，得到数组
            var min=arr[0];
            var max=arr[1];
            if(item.price<min||item.price>max){
                return false;
            }else{
                return true;
            }
        }else {
            return true;
        }
    }
    //  $scope.dj=function () {



});