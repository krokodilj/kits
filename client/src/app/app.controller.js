(function(){
	angular.
		module('kits')
		.controller('AppController',appController);

	function appController($rootScope,authService){
		$rootScope.isAuthorised=authService.isAuthorised
		$rootScope.isAuthenticated=authService.isAuthenticated
	}

})();