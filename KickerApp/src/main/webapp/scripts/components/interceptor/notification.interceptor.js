 'use strict';

angular.module('kickerappApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-kickerappApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-kickerappApp-params')});
                }
                return response;
            }
        };
    });
