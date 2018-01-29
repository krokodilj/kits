(function(){
	angular
		.module('kits.users',['model.service.user'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/users',{
					templateUrl:'./app/main/users/users.html',
					controller: 'UsersController',
					controllerAs: 'vm'
				})
		}])
		.controller('UsersController',['userService',function(userService){
			var vm = this

			getManagers()
			getAdmins()
			getResidents()
			getCompanies()

			function getAdmins(){
				userService.getAdmins()
					.then(function(response){
						if(response.error){
							toastr.error("Error fetching admins")
						}else{
							vm.admins=response.data
						}
					})
			}

			function getManagers(){
				userService.getManagers()
					.then(function(response){
						if(response.error){
							toastr.error("Error fetching managers")
						}else{
							vm.managers=response.data
						}
					})
			}

			function getResidents(){
				userService.getResidents()
					.then(function(response){
						if(response.error){
							toastr.error("Error fetching residents")
						}else{
							vm.residents=response.data
						}
					})
			}

			function getCompanies(){
				userService.getCompanies()
					.then(function(response){
						if(response.error){
							toastr.error("Error fetching managers")
						}else{
							vm.companies=response.data
						}
					})
			}

		}])
		
})()