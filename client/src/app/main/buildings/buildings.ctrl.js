(function(){
	angular
		.module('kits.buildings',['model.service.building'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/buildings',{
					templateUrl:'./app/main/buildings/buildings.html',
					controller: 'BuildingsController',
					controllerAs: 'vm'
				})
		}])
		.controller('BuildingsController',['buildingService','toastr',
			function(buildingService,toastr){

			var vm = this

			buildingService.getAll().then(
				function(response){
					if(response.error){
						toastr.error("Unable to fetch buildings")
					}else{
						vm.buildings=response.data
					}
				})


		}])
})()