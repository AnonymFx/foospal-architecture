'use strict';

angular.module('kickerappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('game', {
                parent: 'entity',
                url: '/games',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Games'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/game/games.html',
                        controller: 'GameController'
                    }
                },
                resolve: {
                }
            })
            .state('game.detail', {
                parent: 'entity',
                url: '/game/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Game'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/game/game-detail.html',
                        controller: 'GameDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Game', function($stateParams, Game) {
                        return Game.get({id : $stateParams.id});
                    }]
                }
            })
            .state('game.new', {
                parent: 'game',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/game/game-dialog.html',
                        controller: 'GameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    team1_score: null,
                                    team2_score: null,
                                    duration: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('game', null, { reload: true });
                    }, function() {
                        $state.go('game');
                    })
                }]
            })
            .state('game.edit', {
                parent: 'game',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/game/game-dialog.html',
                        controller: 'GameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Game', function(Game) {
                                return Game.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('game', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('game.delete', {
                parent: 'game',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/game/game-delete-dialog.html',
                        controller: 'GameDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Game', function(Game) {
                                return Game.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('game', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
