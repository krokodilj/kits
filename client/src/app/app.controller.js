(function(){
	angular.
		module('kits')
		.controller('AppController',appController);

	function appController($rootScope, authService, sessionService){
		$rootScope.isAuthorised=authService.isAuthorised
		$rootScope.isAuthenticated=authService.isAuthenticated
		
		var vm = this;
		
		vm.goto = function (page){
			window.location = page;
		}
		
		vm.logout = function(){
			authService.logout()
			window.location = "/";
		}
		
		vm.hasRole = function(role) {
			return authService.isAuthorised([role]);
		}
	}

})();