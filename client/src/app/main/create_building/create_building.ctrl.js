(function(){
	angular
		.module('kits.create_building',['model.service.building','model.service.user'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/create_building',{
					templateUrl:"./app/main/create_building/create_building.html",
					controller: "CreateBuildingController",
					controllerAs: "vm"
				})
		}])
		.controller('CreateBuildingController',['buildingService','userService','toastr'
				,function(buildingService,userService,toastr){

			var vm = this;

			vm.building={
				city:"",
				address:"",
				country:"",
				apartmentCount:0,
				description:"",
				managerId:undefined
			}

			vm.create = createBuilding

			userService.getManagers()
				.then(function(response){
					if(response.error){
						toastr.error("Error fetching managers")
					}else{
						vm.managers=response.data
					}
				})

			function createBuilding(building){
				
				buildingService.create(building)
					.then(function(response){
						if(response.error){
							toastr.error("Error creating building")
						}else{
							toastr.success("Building successfully created")
						}
					})
			}

		}])

})();