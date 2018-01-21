(function(){
	angular
		.module('kits.register',[])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
			.when('/register',{
					templateUrl:"./app/main/register/register.html",
					controller:"RegisterController",
					controllerAs:"vm"								
				})
		}])
		.controller('RegisterController',[function(){
			var vm = this;

			vm.user={
				username:"",
				password:""
			}

			vm.registerError={
				show:false,
				message:""
			}

		}])
})();

