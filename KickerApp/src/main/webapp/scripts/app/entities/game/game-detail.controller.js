'use strict';

angular.module('kickerappApp')
    .controller('GameDetailController', function ($scope, $rootScope, $stateParams, entity, Game, Player) {
        $scope.game = entity;
        $scope.load = function (id) {
            Game.get({id: id}, function(result) {
                $scope.game = result;
            });
        };
        var unsubscribe = $rootScope.$on('kickerappApp:gameUpdate', function(event, result) {
            $scope.game = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
