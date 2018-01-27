(function(){
	angular
		.module('kits.create_building',['model.service.building'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/create_building',{
					templateUrl:"./app/main/create_building/create_building.html",
					controller: "CreateBuildingController",
					controllerAs: "vm"
				})
		}])
		.controller('CreateBuildingController',['buildingService',function(buildingService){

			var vm = this;

			vm.building={
				city:"",
				address:"",
				country:"",
				apartmentCount:0,
				description:""
			}

			vm.create = createBuilding

			function createBuilding(building){
				alert(JSON.stringify(building))
				//buildingService.create(building)	
			}

		}])

})();