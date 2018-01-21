(function(){
	angular
		.module('kits',[
			'ngRoute',
			'ngCookies',
			'kits.login',
			'kits.register'
		])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/',{
					redirectTo:'/login'
				})
		}])
		.controller('AppController',[function(){
			
			
			
		}])
})();