(function(){
	angular
		.module('model.service.displayReport',[])
		.factory('displayReportService',['$http',displayReportService])

	function displayReportService($http){

		return{
			getReport : getReport
		}

		function getReport(reportId){
			var promise = 
				$http
					.get('/api/reports/getReport/'+reportId)
						.then(function(response){
							return {data:response.data}
						},function(error){
							return { error :true , data :error.data}
						})
			return promise
		}

	}

})();