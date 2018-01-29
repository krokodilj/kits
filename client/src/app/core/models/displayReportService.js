(function(){
	angular
		.module('model.service.displayReport',[])
		.factory('displayReportService',['$http',displayReportService])

	function displayReportService($http){

		return{
			getReport : getReport,
			getAllByBuilding: getAllByBuilding,
			forward: forward
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
		
		function getAllByBuilding(building_id){
			var promise = $http
							.get('/api/reports/by_building/'+building_id)
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise	
		}
		
		function forward(data){
			var promise = $http
							.post('/api/reports/forward/',data)
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise	
		}

	}

})();