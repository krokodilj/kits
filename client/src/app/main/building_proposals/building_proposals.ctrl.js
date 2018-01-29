(function(){
	angular
		.module('kits.building_proposals', ['model.service.proposal', 'model.service.building','service.session'])
		.config(['$routeProvider', function($routeProvider){
			$routeProvider
				.when('/building_proposals',{
					templateUrl:'./app/main/building_proposals/building_proposals.html',
					controller: 'BuildingProposalsController',
					controllerAs: 'vm'
				})
		}])
		.controller('BuildingProposalsController', ['proposalService', 'buildingService', 'toastr', 'sessionService',
			function(proposalService, buildingService, toastr, sessionService) {

			var vm = this;
			vm.proposals = [];
			vm.userBuildings = null;
			vm.proposalStatus = "OPEN";
			
			vm.username = sessionService.userId;
			
			vm.parseDateTime = parseDateTime;
			vm.getUserBuildings = getUserBuildings;
			vm.getProposals = getProposals;
			vm.proposalUpvote = proposalUpvote;
			vm.proposalDownvote = proposalDownvote;
			vm.refreshVotes = refreshVotes;
			
			getProposals(vm.username, "OPEN");
			
			
			function getProposals(username, proposalStatus) {
				vm.proposals = [];
				vm.getUserBuildings(username, proposalStatus);
			}

			function getUserBuildings(username, proposalStatus) {
				buildingService.getAllForUser(username).then(function(response) {
					if(response.error) {
						toastr.error("Unable to fetch buildings")
					}
					else {
						var buildings = response.data;
						getProposalsForBuildings(buildings, proposalStatus, getProposalsForBuilding);
					}
				})
			}
			
			function getProposalsForBuildings(buildings, proposalStatus, callback) {
				buildings.forEach(function(building) {
					callback(building, proposalStatus);
				})
			}
			
			function getProposalsForBuilding(building, proposalStatus) {
				proposalService.getAllForBuilding(building["id"], proposalStatus).then(function(response){
					if(response.error) {
						toastr.error("Unable to fetch proposals")
					}
					else {
						var proposals = response.data.map(function(prop) {
							prop.building = building;
							
							//get votes for a proposal
							proposalService.getVotes(prop.id).then(function(response){
								var totalVotes = 0;
								response.data.map(function(response) {
									if(response.vote == "FOR")
										totalVotes += 1;
									else
										totalVotes -= 1;
								})
								prop.votes = totalVotes;
							})
							return prop;
						})
						vm.proposals = vm.proposals.concat(proposals);
					}
				})
			}
			
			function refreshVotes() {
				vm.proposals.map(function(prop) {
					
					//get votes for a proposal
					proposalService.getVotes(prop.id).then(function(response){
						var totalVotes = 0;
						response.data.map(function(response) {
							if(response.vote == "FOR")
								totalVotes += 1;
							else
								totalVotes -= 1;
						})
						prop.votes = totalVotes;
					})
				})
			}
			
			function proposalUpvote(proposalId) {
				var proposalVote = {proposalId: proposalId, value: "FOR"};
				proposalService.vote(proposalVote).then(function(response){ 
					if(response.error) {
						toastr.error("You already casted your vote for this Proposal.")
						vm.refreshVotes();
					}
					else {
						toastr.success("Your Vote has been recorded.")
						vm.refreshVotes();
					}
				})
			}
			
			function proposalDownvote(proposalId) {
				var proposalVote = {proposalId: proposalId, value: "AGAINST"};
				proposalService.vote(proposalVote).then(function(response){ 
					if(response.error) {
						toastr.error("You already casted your vote for this Proposal.")
					}
					else {
						toastr.success("Your Vote has been recorded.")
						vm.refreshVotes();
					}
				})
			}
			
			function parseDateTime(array) {
				let date = array[2] + "." +  array[1] +  "." + array[0];
				let time = " " + array[3] + ":" + array[4];
				return date + time;
			}
			
		}])
		
})()