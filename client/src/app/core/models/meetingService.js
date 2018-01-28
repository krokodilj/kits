(function(){
	angular
		.module('model.service.meeting', [])
		.factory('meetingService', ['$http', meetingService])

	function meetingService($http){

		return {
			create: create,
			getAll: getAll,
			getAllForManager: getAllForManager
		}

		function create(meeting) {
			var promise = $http
				.post('/api/meetings/', meeting).then(
					function(res) {
						return {data: res.data};
					},
					function(err) {
						return {error: true, data: err.data};
					})
			return promise;
		}

		function getAll(){
			var promise = $http
				.get('/api/meetings/').then(
					function(res){
						return {data: res.data};
					},
					function(err){
						return {error: true, data: err.data};
					})
			return promise;
		}
		
		function getAllForManager(username) {
			console.log("SENDIN");
			var promise = $http
			.get('/api/meetings?manager_username=' + username).then(
				function(res){
					console.log("succ");
					return {data: res.data};
				},
				function(err){
					console.log("err");
					return {error: true, data: err.data};
				})
				return promise;
		}

	}

})();
