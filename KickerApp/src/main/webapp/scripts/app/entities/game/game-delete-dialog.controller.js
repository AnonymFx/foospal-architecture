'use strict';

angular.module('kickerappApp')
	.controller('GameDeleteController', function($scope, $uibModalInstance, entity, Game) {

        $scope.game = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Game.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
