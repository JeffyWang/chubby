var poolsApp = angular.module("poolsApp", []);
var poolUrl = "http://localhost:9090/rest/service/proxy?uri=/v1/cos/pool";

poolsApp.controller('poolsController',
    function($scope,$http) {
        $http.get(poolUrl).
            success(function(data, status) {
                $scope.pools = data.data;
        }).error(function(data, status) {
            alert(data);
        });
})