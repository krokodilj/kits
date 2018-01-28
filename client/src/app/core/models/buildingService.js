(function(){
	angular
		.module('model.service.building',[])
		.factory('buildingService',['$http', buildingService])

	function buildingService($http){

		return {
			create : create,
			getAllForManager: getAllForManager,
			getOne : getOne,
			getAllForUser: getAllForUser
		}

		function create(building){

			var promise = $http
					.post('/api/buildings/',building)
					.then(function(res){
						return {data:res.data}
					},function(err){
						return {error:true,data:err.data}
					})
			return promise
		}
		
		function getAllForManager(username) {
			var promise = $http
				.get('/api/buildings/getManagerBuildings/' + username)
				.then(function(res) {
					return {data: res.data};
				}, function(err) {
					return {error: true, data: err.data};
				})
			return promise;
		}
		
		function getAllForUser(username) {
			var promise = $http
				.get('/api/buildings/getUserBuildings/' + username)
				.then(function(res) {
					return {data: res.data};
				}, function(err) {
					return {error: true, data: err.data};
				})
			return promise;
		}
		
		function getAll(){
			var promise = $http
				.get('/api/buildings/')
				.then(function(res){
					return {data:res.data}
				},function(err){
					return {error : true , data : err.data }
				})
			return promise;
		}

		function getOne(building_id){
			var promise = $http
							.get('/api/buildings/'+building_id)
							.then(function(res){
								return {data:res.data}
							},function(err){
								return {error : true , data : err.data }
							})

			return promise
		}

	}

})();
