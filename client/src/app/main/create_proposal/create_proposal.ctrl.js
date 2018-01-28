(function(){
	angular
		.module('kits.create_proposal', ['model.service.proposal'])
		.config(['$routeProvider', function($routeProvider){
			$routeProvider
				.when('/create_proposal',{
					templateUrl:"./app/main/create_proposal/create_proposal.html",
					controller: "CreateProposalController",
					controllerAs: "vm"
				})
		}])
		.controller('CreateProposalController', ['proposalService', 'buildingService', 'buildingReportService', 'sessionService', 'toastr',
			function(proposalService, buildingService, buildingReportService, sessionService, toastr) {

			var vm = this;
			vm.buildings = [];
			vm.buildingId = null;
			vm.reports = [];
			vm.reportId = null;
			
			vm.getReports = getReports;
			vm.createProposal = createProposal;
			
			getUserBuildings(sessionService.userId);
			
			
			function getUserBuildings(username) {
				buildingService.getAllForUser(username).then(function(response) {
					if(response.error) {
						toastr.error("Unable to fetch buildings")
					}
					else {
						vm.buildings = response.data;
					}
				})
			}
			
			function getReports(buildingId) {
				buildingReportService.getReports(buildingId).then(function(response) {
					if(response.error) {
						toastr.error("Unable to fetch reports");
					}
					else {
						vm.reports = response.data;
					}
				})
			}
			
			function createProposal() {
				var proposal = {buildingId: vm.buildingId, attachedReport: vm.reportId, content: vm.content, suggestedAt: new Date()};
				proposalService.create(proposal).then(function(response) {
					if(response.error) {
						toastr.error("Unable to fetch reports");
					}
					else {
						toastr.success("Proposal successfully created");
					}
				})
			}
			
		}])

})();