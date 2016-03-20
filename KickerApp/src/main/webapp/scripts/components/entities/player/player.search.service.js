'use strict';

angular.module('kickerappApp')
    .factory('PlayerSearch', function ($resource) {
        return $resource('api/_search/players/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
