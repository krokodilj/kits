(function () {
    angular
        .module('kits.building_reports', ['model.service.report','model.service.buildingReport'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/building_reports', {
                    templateUrl: "./app/main/building_reports/buildingReports.html",
                    controller: "BuildingReportController",
                    controllerAs: "vm"
                })
        }])
        .controller('BuildingReportController', ['reportService', 'buildingReportService','toastr', 
                                         function (reportService, buildingReportService, toastr) {
            var vm = this;
            
            
            reportService.getBuildings()
			.then(function(response){
				if(response.error){
					toastr.error("Error fetching buildings")
				}else{
					vm.buildings=response.data
				}
			})
			
			vm.getRep = function(buildingId){
    			buildingReportService.getReports(buildingId)
    				.then(function(response){
    					if(response.error){
    						toastr.error("Error fetching reports")
    					}else{
    						vm.reports=response.data
    					}
    				})
    		}
            

        }])
})();

