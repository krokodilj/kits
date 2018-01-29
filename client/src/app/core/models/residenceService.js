(function(){
	angular
		.module('model.service.residence',[])
		.factory('residenceService',['$http',residenceService])

		function residenceService($http){

			return {
				getByBuilding : getByBuilding,
				create	: 	create
			}

			function getByBuilding(building_id){
				var promise = $http
								.get('/api/residences/by_building/'+building_id)
								.then(function(response){
									return {data:response.data}
								},function(error){
									return { error :true , data :error.data}
								})
				return promise
			}

			function create(residence){
				var promise = $http
								.post('/api/residences/',residence)
								.then(function(response){
									return {data:response.data}
								},function(error){
									return { error :true , data :error.data}
								})
				return promise
			}
		}
})()