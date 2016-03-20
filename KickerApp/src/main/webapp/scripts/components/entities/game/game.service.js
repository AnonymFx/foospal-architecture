'use strict';

angular.module('kickerappApp')
    .factory('Game', function ($resource, DateUtils) {
        return $resource('api/games/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
