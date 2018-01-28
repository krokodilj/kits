(function(){ 
	angular
		.module('kits.display_report',[
			'model.service.displayReport',
			'model.service.buildingReport'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
				.when('/display_report/:reportId',{
					templateUrl:'./app/main/display_report/displayReport.html',
					controller: 'DisplayReportController',
					controllerAs : 'vm'
				})
		}])
		.controller('DisplayReportController',['$scope',
		                                       '$routeParams',
		                                       'displayReportService',
		                                       'buildingReportService',
		                                       'toastr',
			function($scope,$routeParams,displayReportService,buildingReportService,
					toastr){

				var vm = this;
				
				displayReportService.getReport($routeParams.reportId)
				.then(function(response){
					if(response.error){
						toastr.error("Error fetching report")
					}else{
						vm.report=response.data
					}
				})

		}])
})();