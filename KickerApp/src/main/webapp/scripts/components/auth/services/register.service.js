'use strict';

angular.module('kickerappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


