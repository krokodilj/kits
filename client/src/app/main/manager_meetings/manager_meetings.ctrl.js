(function(){
	angular
		.module('kits.manager_meetings', ['model.service.meeting'])
		.config(['$routeProvider', function($routeProvider){
			$routeProvider
				.when('/manager_meetings',{
					templateUrl:'./app/main/manager_meetings/manager_meetings.html',
					controller: 'ManagerMeetingsController',
					controllerAs: 'vm'
				})
		}])
		.controller('ManagerMeetingsController', ['meetingService', 'sessionService', 'toastr',
			function(meetingService, sessionService, toastr) {

			var vm = this;
			vm.meetings = null;
			
			var username = sessionService.userId;
			
			vm.parseDateTime = parseDateTime;
			
			meetingService.getAllForManager(username).then(
				function(response){
					if(response.error){
						toastr.error("Unable to fetch buildings")
					}
					else {
						console.log(response.data);
						vm.meetings = response.data
					}
			})
			
			function parseDateTime(array) {
				let date = array[2] + "." +  array[1] +  "." + array[0];
				let time = " " + array[3] + ":" + array[4];
				return date + time;
			}
		}])
})()