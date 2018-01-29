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
			
			vm.username = sessionService.userId;
			
			vm.parseDateTime = parseDateTime;
			vm.proposalUpvote = proposalUpvote;
			vm.proposalDownvote = proposalDownvote;
			
			getProposals(vm.username, getUserBuildings);
			
			
			function getProposals(username, callback) {
				callback(username, buildingsToBuildingIds, getProposalsForBuildings);
			}

			function getUserBuildings(username, callback_1, callback_2) {
				buildingService.getAllForUser(username).then(function(response) {
					if(response.error) {
						toastr.error("Unable to fetch buildings")
					}
					else {
						var buildings = response.data;
						getProposalsForBuildings(buildings, getProposalsForBuilding);
					}
				})
			}
			
			function buildingsToBuildingIds(buildings) {
				return buildings.map(function(building) {return building["id"]});
			}
			
			function getProposalsForBuildings(buildings, callback) {
				buildings.forEach(function(building) {
					callback(building);
				})
			}
			
			function getProposalsForBuilding(building) {
				proposalService.getAllForBuilding(building["id"]).then(function(response){
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
						console.log(vm.proposals);
					}
				})
			}
			
			function proposalUpvote(proposalId) {
				var proposalVote = {proposalId: proposalId, value: "FOR"};
				proposalService.vote(proposalVote).then(function(response){ 
					if(response.error) {
						toastr.error("You already casted your vote for this Proposal.")
					}
					else {
						toastr.success("Your Vote has been recorded.")
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