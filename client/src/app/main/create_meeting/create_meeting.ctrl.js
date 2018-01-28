(function(){
	angular
		.module('kits.create_meeting', ['model.service.meeting'])
		.config(['$routeProvider', function($routeProvider){
			$routeProvider
				.when('/create_meeting',{
					templateUrl:"./app/main/create_meeting/create_meeting.html",
					controller: "CreateMeetingController",
					controllerAs: "vm"
				})
		}])
		.controller('CreateMeetingController', ['meetingService', 'buildingService', 'sessionService', 'toastr',
			function(meetingService, buildingService, sessionService, toastr) {

			var vm = this;
			vm.building = null;
			vm.date = null;
			vm.hour = null;
			vm.minute = null;
			vm.location = null;
			vm.buildings = [];
			
			vm.minDate = new Date();
			vm.minDate.setDate(vm.minDate.getDate() + 1);
			
			vm.hours = ["07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"];
			vm.minutes = ["5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"];
			
			vm.createMeeting = createMeeting;
			vm.range = range;
			
			getAllForManager(sessionService.userId);
			
			function createMeeting() {
				vm.date.setHours(vm.hour);
				vm.date.setMinutes(vm.minute);
				
				var meeting = {'buildingId': vm.building, 'startsAt': vm.date, 'location': vm.location};
				
				meetingService.create(meeting).then(function(response) {
					if(response.error) {
						console.log(response);
					}
					else {
						toastr.success("Meeting successfully created");
					}
				});
			}
			
			function getAllForManager(username) {
				buildingService.getAllForManager(username).then(function(response){
					if(response.error) {
						toastr.error("Unable to fetch buildings")
					}
					else {
						vm.buildings = response.data;
					}
				})
			}
			
			function range(start, edge, step) {
				// If only one number was passed in make it the edge and 0 the start.
				if (arguments.length == 1) {
					edge = start;
				    start = 0;
				}
				 
				// Validate the edge and step numbers.
				edge = edge || 0;
				step = step || 1;
				 
				// Create the array of numbers, stopping befor the edge.
				for (var ret = []; (edge - start) * step > 0; start += step) {
				    ret.push(start);
				}
					return ret;
			}
			
		}])

})();