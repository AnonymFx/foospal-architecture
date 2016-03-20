'use strict';

angular.module('kickerappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('player', {
                parent: 'entity',
                url: '/players',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Players'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/player/players.html',
                        controller: 'PlayerController'
                    }
                },
                resolve: {
                }
            })
            .state('player.detail', {
                parent: 'entity',
                url: '/player/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Player'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/player/player-detail.html',
                        controller: 'PlayerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Player', function($stateParams, Player) {
                        return Player.get({id : $stateParams.id});
                    }]
                }
            })
            .state('player.new', {
                parent: 'player',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/player/player-dialog.html',
                        controller: 'PlayerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    wins: null,
                                    losses: null,
                                    ties: null,
                                    mmr: null,
                                    avg_duration: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('player', null, { reload: true });
                    }, function() {
                        $state.go('player');
                    })
                }]
            })
            .state('player.edit', {
                parent: 'player',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/player/player-dialog.html',
                        controller: 'PlayerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Player', function(Player) {
                                return Player.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('player', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('player.delete', {
                parent: 'player',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/player/player-delete-dialog.html',
                        controller: 'PlayerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Player', function(Player) {
                                return Player.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('player', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
