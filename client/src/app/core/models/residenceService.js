(function(){
	angular
		.module('model.service.residence',[])
		.factory('residenceService',['$http',residenceService])

		function residenceService($http){

			return {
				getOne : getOne,
				getByBuilding : getByBuilding,
				create	: 	create,
				addResident: addResident,
				addOwner : addOwner
			}

			function getOne(id){
				var promise = $http
								.get('/api/residences/'+id)
								.then(function(response){
									return {data:response.data}
								},function(error){
									return { error :true , data :error.data}
								})
				return promise
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
			function addResident(id,user_id){
				var promise = $http
								.put('/api/residents/'+user_id+'/add_to_residence/'+id)
								.then(function(response){
									return {data:response.data}
								},function(error){
									return { error :true , data :error.data}
								})
				return promise
			}

			function addOwner(id,user_id){
				var promise = $http
								.put('/api/residents/'+user_id+'/add_to_owner/'+id)
								.then(function(response){
									return {data:response.data}
								},function(error){
									return { error :true , data :error.data}
								})
				return promise
			}
		}
})()