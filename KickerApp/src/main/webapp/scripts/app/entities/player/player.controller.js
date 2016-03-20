'use strict';

angular.module('kickerappApp')
    .controller('PlayerController', function ($scope, $state, Player, PlayerSearch) {

        $scope.players = [];
        $scope.loadAll = function() {
            Player.query(function(result) {
               $scope.players = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            PlayerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.players = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.player = {
                name: null,
                wins: null,
                losses: null,
                ties: null,
                mmr: null,
                avg_duration: null,
                id: null
            };
        };
    });
