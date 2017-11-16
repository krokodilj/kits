(function(){
	angular.module('kits.login',['service.auth'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
			.when('/login',{
					templateUrl:"./app/login/login.html",
					controller:"loginController",
					controllerAs:"ctrl"								
				})
		}])
		.controller('loginController',['authService',function(authService){
			var vm = this;

			vm.user={
				username:"",
				password:""
			}

			vm.loginError={
				show:false,
				message:""
			}

			vm.login=authService

		}])
})();