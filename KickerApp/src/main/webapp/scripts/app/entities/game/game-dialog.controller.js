'use strict';

angular.module('kickerappApp').controller('GameDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Game', 'Player',
        function($scope, $stateParams, $uibModalInstance, entity, Game, Player) {

        $scope.game = entity;
        $scope.players = Player.query();
        $scope.load = function(id) {
            Game.get({id : id}, function(result) {
                $scope.game = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('kickerappApp:gameUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.game.id != null) {
                Game.update($scope.game, onSaveSuccess, onSaveError);
            } else {
                Game.save($scope.game, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
