(function(){ 
	angular
		.module('kits.building',[
			'model.service.building',
			'model.service.user',
			'model.service.residence'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/building/:building_id',{
					templateUrl:'./app/main/building/building.html',
					controller: 'BuildingController',
					controllerAs : 'vm'
				})
		}])
		.controller('BuildingController',['$routeParams','buildingService','userService',
			'residenceService','toastr',
			function($routeParams,buildingService,userService,residenceService,toastr){

				var vm = this

				buildingService.getOne($routeParams.building_id)
					.then(function(response){
						if(response.error){
							toastr.error("Unable to fetch building")
						}else{
							vm.building=response.data
							getManager(vm.building.manager)
							getResidences(vm.building.id)

						}
					})

				function getManager(managerId){
					userService.getOne(managerId)
						.then(function(response){
							if(response.error){
								toastr.error("Unable to fetch building manager")
							}else{
								vm.building.manager=response.data

							}
						})
				}

				function getResidences(building_id){
					residenceService.getByBuilding(building_id)
						.then(function(response){
							if(response.error){
								toastr.error("Unable to fetch residences")
							}else{
								vm.building.residences=response.data

							}
						})
				}

		}])
})();