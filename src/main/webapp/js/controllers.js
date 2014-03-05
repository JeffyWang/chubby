function HelloController($scope) {
    $scope.greeting = { text: 'Hello',test2:'sm' };
}

function personController($scope) {
    $scope.persons = [
        {name: 'jeffy', age: 2311111111, sex: "man",code: 2},
        {name: 'tom', age: 22111111111, sex: "man",code: 3},
        {name: 'amy', age: 23111111, sex: "woman",code: 2},
        {name: 'john', age: 27111111, sex: "man",code: 1},
    ]
    $scope.remove = function($index) {
        $scope.persons.splice($index, 1);
    }
}

var myAppMoudle = angular.module('myApp', []);
myAppMoudle.controller('TextController',
    function($scope) {
        var someText = {};
        someText.message = 'You hava start your journey.';
        $scope.someText = someText;
})

myAppMoudle.controller('SomeController',
    function($scope) {
        var check = {};
        check.youCheckedIt = true;
        $scope.check = check;

        $scope.checkIt = function() {
            console.log(check);
        }
})

myAppMoudle.controller('StartUpController',
    function($scope) {
        $scope.funding = {startingEstimate: 0};
        var computeNeeded = function () {
            $scope.funding.needed = $scope.funding.startingEstimate * 10;
        }
        $scope.$watch('funding.startingEstimate', computeNeeded);
})
