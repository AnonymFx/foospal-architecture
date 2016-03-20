'use strict';

angular.module('kickerappApp')
    .factory('GameSearch', function ($resource) {
        return $resource('api/_search/games/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
