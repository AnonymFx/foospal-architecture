'use strict';

angular.module('kickerappApp')
    .factory('Player', function ($resource, DateUtils) {
        return $resource('api/players/:id', {}, {
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
