(function(){
	angular
		.module('kits.manager_buildings', ['model.service.building'])
		.config(['$routeProvider', function($routeProvider){
			$routeProvider
				.when('/manager_buildings',{
					templateUrl:'./app/main/manager_buildings/manager_buildings.html',
					controller: 'ManagerBuildingsController',
					controllerAs: 'vm'
				})
		}])
		.controller('ManagerBuildingsController',['buildingService', 'sessionService', 'toastr',
			function(buildingService, sessionService, toastr) {

			var vm = this;
			var username = sessionService.userId;
			
			buildingService.getAllForManager(username).then(function(response){
				if(response.error) {
					toastr.error("Unable to fetch buildings")
				}
				else {
					vm.buildings = response.data;
				}
			})
			

		}])
		
})()