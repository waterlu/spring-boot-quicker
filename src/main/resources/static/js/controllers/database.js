'use strict';

angular.module('app').controller('DatabaseController', ['$scope', '$http', '$state', function($scope, $http, $state) {

    console.log('DatabaseController 1.0.0');

    $scope.dbType = 'MySQL';
    $scope.dbIP = '10.10.10.4';
    $scope.dbPort = '3306';
    $scope.dbName = 'kingold';
    $scope.dbUsername = 'zj_admin';
    $scope.dbPassword = '123456';

    $scope.initDefaultDependency = function() {


    };

    $scope.initDefaultDependency();

    $scope.testDBConnection = function () {
        var param = {
            'dbType': null,
            'dbIP': null,
            'dbPort': null,
            'dbName': null,
            'dbUsername': null,
            'dbPassword': null
        };

        param.dbType = $scope.dbType;
        param.dbIP = $scope.dbIP;
        param.dbPort = $scope.dbPort;
        param.dbName = $scope.dbName;
        param.dbUsername = $scope.dbUsername;
        param.dbPassword = $scope.dbPassword;

        var jsonString = JSON.stringify(param);
        console.log(jsonString);

        $http.post("/api/database/connect", jsonString).success(function(data) {
            console.log('code=' + data.code);
            console.log('data=' + data.data);
            if (data.code == 200) {
                window.alert("成功！")
            } else {
                window.alert(data.message)
            }
        }).error(function (data) {
            console.log('data=' + data);
        });
    };

}]);