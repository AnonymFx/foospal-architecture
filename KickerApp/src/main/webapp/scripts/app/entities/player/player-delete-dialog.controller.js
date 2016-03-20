'use strict';

angular.module('kickerappApp')
	.controller('PlayerDeleteController', function($scope, $uibModalInstance, entity, Player) {

        $scope.player = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Player.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
