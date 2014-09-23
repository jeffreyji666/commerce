var commerceCtrls = angular.module('commerceCtrls', []);

commerceCtrls.controller('HomeCtrl', ['$scope',
    function($scope) {
        $scope.greeting = {
            text: 'Hello'
        };
    }
]);

commerceCtrls.controller('LoginCtrl', ['$scope',
        function($scope) {
            $scope.submit(function() {
                $scope.alerts = [];
                if ($scope.loginForm.userName.$invalid) {
                    $scope.alerts.push({
                        type: 'danger',
                        msg: '用户名不能为空'
                    });
                }
                if ($scope.loginForm.password.$invalid) {
                    $scope.alerts.push({
                        type: 'danger',
                        msg: '密码不能为空'
                    });
                }
                if ($scope.alerts.length) {
                    return;
                }
            })
            }]);