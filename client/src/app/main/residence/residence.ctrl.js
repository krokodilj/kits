(function(){
	angular
		.module('kits.residence',[
			'model.service.residence',
			'model.service.building',
			'model.service.user'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/residence/:id',{
					templateUrl:'./app/main/residence/residence.html',
					controller:'ResidenceController',
					controllerAs:'vm'
				})
		}])
		.controller('ResidenceController',['residenceService','buildingService','userService'
			,'toastr','$routeParams',
			function(residenceService,buildingService,userService,toastr,$routeParams){

				var vm = this

				residenceService.getOne($routeParams.id)
					.then(function(response){
						if(response.error){
							toastr.error("Unable to fetch residence")
						}else{
							vm.residence=response.data
							getBuilding(vm.residence.building)
							getResidents(vm.residence.building)
							getOwner(vm.residence.appartmentOwner)
						}
					})
				getAllResidents()

				vm.addResident=function(r){
					if(!r) toastr.error("u have to select smthng")

					residenceService.addResident($routeParams.id,r.id)
						.then(function(response){
							if(response.error){
								toastr.error(response.data)
							}else{
								vm.new_resident=undefined
								vm.residence.residents.push(r)
							}
						})
				}

				vm.addOwner=function(r){
					if(!r) toastr.error("u have to select smthng")
					residenceService.addOwner($routeParams.id,r.id)
						.then(function(response){
							if(response.error){
								toastr.error(response.data)
							}else{
								vm.new_owner=undefined
								vm.residence.appartmentOwner=r
							}
						})
				}

				function getBuilding(building_id){
					buildingService.getOne(building_id)
						.then(function(response){
							if(response.error){
								toastr.error("Unable to fetch building")
							}else{
								vm.residence.building=response.data
							}
						})
				}

				function getResidents(building_id){
					userService.getAllByBuilding(building_id)
						.then(function(response){
							if(response.error){
								toastr.error("Unable to fetch residents")
							}else{
								vm.residence.residentss=response.data

							}
						})
				}

				function getOwner(owner_id){
					userService.getOne(owner_id)
						.then(function(response){
								if(response.error){
									toastr.error("Unable to fetch owner")
								}else{
									vm.residence.appartmentOwner=response.data

								}
							})
				}

				function getAllResidents(){
					userService.getResidents()
						.then(function(response){
							if(response.error){
								toastr.error("Unable to fetch residents")
							}else{
								vm.residents=response.data

							}
						})
				}

		}])
})()