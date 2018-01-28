(function(){
	angular
		.module('kits.building',['model.service.building','model.service.user'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/building/:building_id',{
					templateUrl:'./app/main/building/building.html',
					controller: 'BuildingController',
					controllerAs : 'vm'
				})
		}])
		.controller('BuildingController',['$routeParams','buildingService','userService','toastr',
			function($routeParams,buildingService,userService,toastr){

				var vm = this

				buildingService.getOne($routeParams.building_id)
					.then(function(response){
						if(response.error){
							toastr.error("Unable to fetch building")
						}else{
							vm.building=response.data
							getManager(vm.building.manager)

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

		}])
})();