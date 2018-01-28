(function(){
	angular
		.module('model.service.proposal', [])
		.factory('proposalService', ['$http', proposalService])

	function proposalService($http){

		return {
			create: create,
			getAllForBuilding: getAllForBuilding
		}
		
		function create(proposal) {
			var promise = $http
				.post('/api/proposals', proposal).then(
					function(res) {
						return {data: res.data};
					},
					function(err) {
						return {error: true, data: err.data};
					})
			return promise;
		}

		function getAllForBuilding(buildingId) {
			var promise = $http
			.get('/api/proposals?building_id=' + buildingId).then(
				function(res) {
					return {data: res.data};
				},
				function(err){
					return {error: true, data: err.data};
				})
			return promise;
		}
		
	}

})();
