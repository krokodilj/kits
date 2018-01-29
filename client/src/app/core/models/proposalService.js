(function(){
	angular
		.module('model.service.proposal', [])
		.factory('proposalService', ['$http', proposalService])

	function proposalService($http){

		return {
			create: create,
			getAllForBuilding: getAllForBuilding,
			vote: vote,
			getVotes: getVotes
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
		
		function vote(proposalVote) {
			var promise = $http
			.post('/api/proposal_votes', proposalVote).then(
				function(res) {
					return {data: res.data};
				},
				function(err){
					return {error: true, data: err.data};
				})
			return promise;
		}
		
		function getVotes(proposalId) {
			var promise = $http
			.get('/api/proposal_votes/' + proposalId).then(
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
