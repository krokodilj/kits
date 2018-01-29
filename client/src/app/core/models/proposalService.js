(function(){
	angular
		.module('model.service.proposal', [])
		.factory('proposalService', ['$http', proposalService])

	function proposalService($http){

		return {
			create: create,
			getAllForBuilding: getAllForBuilding,
			vote: vote,
			getVotes: getVotes,
			acceptProposal: acceptProposal,
			rejectProposal: rejectProposal
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

		function getAllForBuilding(buildingId, proposalStatus) {
			var promise = $http
			.get('/api/proposals?building_id=' + buildingId + "&proposal_status=" + proposalStatus).then(
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
		
		function acceptProposal(proposalId) {
			var promise = $http
			.post('/api/proposals/' + proposalId + "/accept").then(
				function(res) {
					return {data: res.data};
				},
				function(err){
					return {error: true, data: err.data};
				})
			return promise;
		}
		
		function rejectProposal(proposalId) {
			var promise = $http
			.post('/api/proposals/' + proposalId + "/reject").then(
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
