'use strict';

angular.module('app').controller('ProjectController', ['$scope', '$http', '$state', function($scope, $http, $state) {

    console.log('ProjectController 1.0.2');

    $scope.groupId = 'cn.zjhf.kingold';
    $scope.artifactId = 'service_demo';
    $scope.version = '1.0.0-SNAPSHOT';
    $scope.name = 'service_demo';
    $scope.description = 'Service demo project for Spring Boot';
    $scope.javaVersion = '1.8';
    $scope.springBootVersion = '1.5.6.RELEASE';
    $scope.dependencies = [];

    $scope.initDefaultDependency = function() {

        var fastjson = [];
        fastjson.selected = true;
        fastjson.name = 'fastjson';
        fastjson.groupId = 'com.alibaba';
        fastjson.artifactId = 'fastjson';
        fastjson.version = '1.2.32';

        var mybatis = [];
        mybatis.selected = true;
        mybatis.name = 'mybatis';
        mybatis.groupId = 'org.mybatis.spring.boot';
        mybatis.artifactId = 'mybatis-spring-boot-starter';
        mybatis.version = '1.3.0';

        var mysql = [];
        mysql.selected = true;
        mysql.name = 'mysql';
        mysql.groupId = 'mysql';
        mysql.artifactId = 'mysql-connector-java';
        mysql.version = '5.1.40';

        var druid = [];
        druid.selected = true;
        druid.name = 'druid';
        druid.groupId = 'com.alibaba';
        druid.artifactId = 'druid';
        druid.version = '1.0.18';

        var common = [];
        common.selected = false;
        common.name = 'common';
        common.groupId = 'cn.zjhf.kingold';
        common.artifactId = 'common';
        common.version = '1.1.3';

        var rocket_mq = [];
        rocket_mq.selected = false;
        rocket_mq.name = 'rocketmq';
        rocket_mq.groupId = 'cn.zjhf.kingold';
        rocket_mq.artifactId = 'spring-boot-starter-rocketmq';
        rocket_mq.version = '1.0.2';

        var service_consumer = [];
        service_consumer.selected = false;
        service_consumer.name = 'service_consumer';
        service_consumer.groupId = 'cn.zjhf.kingold';
        service_consumer.artifactId = 'service-consumer';
        service_consumer.version = '1.1';

        $scope.dependencies.push(fastjson);
        $scope.dependencies.push(mybatis);
        $scope.dependencies.push(mysql);
        $scope.dependencies.push(druid);
        $scope.dependencies.push(common);
        $scope.dependencies.push(service_consumer);
        $scope.dependencies.push(rocket_mq);
    };

    $scope.initDefaultDependency();

    $scope.preview = function () {
        $scope.dependencyList = new Array();
        angular.forEach ($scope.dependencies, function (dependency, index) {
            if (dependency.selected) {
                var item = {
                    'name': null,
                    'groupId': null,
                    'artifactId': null,
                    "version":null
                };
                item.name = dependency.name;
                item.groupId = dependency.groupId;
                item.artifactId = dependency.artifactId;
                item.version = dependency.version;
                $scope.dependencyList.push(item);
            }
        });

        var param = {
            'groupId': null,
            'artifactId': null,
            'version': null,
            'name': null,
            'description': null,
            'javaVersion': null,
            'springBootVersion': null,
            'dependencies': null
        };

        param.groupId = $scope.groupId;
        param.artifactId = $scope.artifactId;
        param.version = $scope.version;
        param.name = $scope.name;
        param.description = $scope.description;
        param.javaVersion = $scope.javaVersion;
        param.springBootVersion = $scope.springBootVersion;
        param.dependencies = $scope.dependencyList;

        var jsonString = JSON.stringify(param);
        console.log(jsonString);

        $http.post("/api/project/preview", jsonString).success(function (data) {
            console.log('code=' + data.code);
            if (data.code == 200) {
                window.alert(data.data);
            } else {
                window.alert(data.message);
            }
        }).error(function (data) {
            console.log('code=' + data.code);
            console.log('data=' + data.data);
        });
    };

    $scope.save = function () {
        $scope.dependencyList = new Array();
        angular.forEach ($scope.dependencies, function (dependency, index) {
            if (dependency.selected) {
                var item = {
                    'name': null,
                    'groupId': null,
                    'artifactId': null,
                    "version":null
                };
                item.name = dependency.name;
                item.groupId = dependency.groupId;
                item.artifactId = dependency.artifactId;
                item.version = dependency.version;
                $scope.dependencyList.push(item);
            }
        });

        var param = {
            'groupId': null,
            'artifactId': null,
            'version': null,
            'name': null,
            'description': null,
            'javaVersion': null,
            'springBootVersion': null,
            'dependencies': null
        };

        param.groupId = $scope.groupId;
        param.artifactId = $scope.artifactId;
        param.version = $scope.version;
        param.name = $scope.name;
        param.description = $scope.description;
        param.javaVersion = $scope.javaVersion;
        param.springBootVersion = $scope.springBootVersion;
        param.dependencies = $scope.dependencyList;

        var jsonString = JSON.stringify(param);
        console.log(jsonString);

        $http.post("/api/project/save", jsonString).success(function(data) {
            console.log('code=' + data.code);
            console.log('data=' + data.data);
        }).error(function (data) {
            console.log('data=' + data);
        });
    };

}]);