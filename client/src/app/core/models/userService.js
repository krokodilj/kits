(function(){
	angular
		.module('model.service.user',[])
		.factory('userService',['$http',userService])

	function userService($http){

		return{
			create : create,
			remove : remove,
			getManagers	: getManagers,
			getOne : getOne,
			getAllByBuilding : getAllByBuilding
		}

		function create(user,type){

			var promise =  $http
					.post('/api/'+type+'/',user)
					.then(function(res){
						return {data:res.data}
					},function(error){
						return { error :true , data :error.data}
					})
			return promise
		}

		function remove(user){

		}

		function getManagers(){
			var promise = $http
							.get('/api/managers/')
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise
		}

		function getOne(user_id){
			var promise = $http
							.get('/api/managers/'+user_id)
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise
		}

		function getAllByBuilding(building_id){
			var promise = $http
							.get('/api/residents/by_building/'+building_id)
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise	
		}
	}
})();

