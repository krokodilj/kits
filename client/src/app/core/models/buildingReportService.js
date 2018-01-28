(function(){
	angular
		.module('model.service.buildingReport',[])
		.factory('buildingReportService',['$http','sessionService',buildingReportService])

	function buildingReportService($http, sessionService){

		return{
			getReports : getReports
		}

		function getReports(buildingId){
			var promise = 
				$http
					.get('/api/reports/getUserReports/'+buildingId)
						.then(function(response){
							return {data:response.data}
						},function(error){
							return { error :true , data :error.data}
						})
			return promise
		}

	}

})();