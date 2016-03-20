'use strict';

describe('Controller Tests', function() {

    describe('Player Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPlayer, MockUser, MockGame;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockUser = jasmine.createSpy('MockUser');
            MockGame = jasmine.createSpy('MockGame');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Player': MockPlayer,
                'User': MockUser,
                'Game': MockGame
            };
            createController = function() {
                $injector.get('$controller')("PlayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kickerappApp:playerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
