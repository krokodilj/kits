(function(){
	angular
		.module('model.service.report',[])
		.factory('reportService',['$http','sessionService',reportService])

	function reportService($http, sessionService){

		return{
			getBuildings : getBuildings,
			sendReport : sendReport
		}

		function getBuildings(){
			var promise = $http
							.get('/api/buildings/getUserBuildings/'+sessionService.userId)
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