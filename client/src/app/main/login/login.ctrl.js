(function(){
	angular
		.module('kits.login',['service.auth'])
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
					(function(response){
						if(response.error){
							vm.loginError.message=response.data
							vm.loginError.show = true
						}else{
							//ovde href	
							console.log(response)	
						}						
					})
			}

		}])
})();