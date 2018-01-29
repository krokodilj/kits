(function(){
	angular
		.module('model.service.report',[])
		.factory('reportService',['$http','sessionService','authService',reportService])

	function reportService($http, sessionService, authService){

		return {
			getBuildings : getBuildings,
			sendReport : sendReport
		}

		function getBuildings(){
			
			var url = null;
			if(authService.isAuthorised(['COMPANY']))
				url = '/api/buildings/';
			else
				url = '/api/buildings/getUserBuildings/'+sessionService.userId;
			
			var promise = $http
						.get(url)
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise
		}
		
		function sendReport(report){
			var promise= $http
					.post('/api/reports/', report)
					.then(function(res){
						return {data:res.data}
					},function(err){
						return {error:true,data:err.data}
					})
			return promise
		}

	}

})();