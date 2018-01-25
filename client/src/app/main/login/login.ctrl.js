(function(){
	angular
		.module('kits.login',['service.auth', 'ngMaterial'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
			.when('/login',{
					templateUrl:"./app/main/login/login.html",
					controller:"LoginController",
					controllerAs:"vm"								
				})
		}])
		.controller('LoginController',['authService',function(authService){
			var vm = this;

			vm.user={
				username:"",
				password:""
			}

			vm.loginError={
				show:false,
				message:""
			}

			vm.login=login;

				
			function login(user){
				authService.login(user).then
					(function(success){
						console.log("success")
					},function(error){
						console.log("error")
					})
			}

		}])
})();