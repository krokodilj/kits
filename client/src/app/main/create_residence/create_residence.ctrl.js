(function(){
	angular
		.module('kits.create_residence',['model.service.residence','model.service.building'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/create_residence',{
					templateUrl:'./app/main/create_residence/create_residence.html',
					controller: 'CreateResidenceController',
					controllerAs : 'vm'
				})
		}])
		.controller('CreateResidenceController',['residenceService','buildingService','toastr','$location',
			function(residenceService,buildingService,toastr,$location){

				var vm = this

				vm.residence={}

				vm.create = create
				buildingService.getAll()
					.then(function(response){
						if(response.error){
							toastr.error("Unable to fetch buildings")
						}else{
							vm.buildings=response.data
						}
					})

				function create(residence){
					residenceService.create(residence)
					.then(function(response){
						if(response.error){
							toastr.error("Error creating residence")
						}else{
							vm.residence={}
							toastr.success("Residence successfully created")
							$location.path('/residence/'+response.data)
						}
					})
				}
		}])
})();