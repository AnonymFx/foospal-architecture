'use strict';

angular.module('kickerappApp')
    .controller('PlayerDetailController', function ($scope, $rootScope, $stateParams, entity, Player, User, Game) {
        $scope.player = entity;
        $scope.load = function (id) {
            Player.get({id: id}, function(result) {
                $scope.player = result;
            });
        };
        var unsubscribe = $rootScope.$on('kickerappApp:playerUpdate', function(event, result) {
            $scope.player = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
