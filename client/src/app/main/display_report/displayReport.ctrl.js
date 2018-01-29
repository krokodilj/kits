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
		                                       'toastr', 'sessionService', 
		                                       '$mdDialog',
			function($scope,$routeParams,displayReportService,buildingReportService,
					toastr, sessionService, $mdDialog){

				var vm = this;
				
				displayReportService.getReport($routeParams.reportId)
				.then(function(response){
					if(response.error){
						toastr.error("Error fetching report")
					}else{
						vm.report=response.data;
						
						displayReportService.getAllByBuilding(vm.report.location.id)
						.then(function(resp){
							if(resp.error){
								toastr.error("Error fetching report")
							}else{
								$scope.items = resp.data;
								$scope.d = 0;
							}
						})
						
					}
				})
				
				vm.isHolder = function(){
					if(sessionService.userId == vm.report.currentHolder.forwardedTo.username)
						return true;
					else
						return false;
				}
				
				vm.forwardReport = function ($event) {
					       var parentEl = angular.element(document.body);
					       $mdDialog.show({
					         parent: parentEl,
					         targetEvent: $event,
					         template:
					           '<md-dialog aria-label="List dialog">' +
					           '  <md-dialog-content>'+
					           '    <md-select ng-model="d" required>'+
					           '		<md-option value="{{i.id}}" '+
					           '			ng-repeat="i in items">{{i.username}}'+
					           '		</md-option>'+
					           '	</md-select>'+
					           '  </md-dialog-content>' +
					           '  <md-dialog-actions>' +
					           '    <md-button ng-click="forReport()" class="md-primary">' +
					           '      Forward' +
					           '    </md-button>' +
					           '    <md-button ng-click="closeDialog()" class="md-primary">' +
					           '      Close Dialog' +
					           '    </md-button>' +
					           '  </md-dialog-actions>' +
					           '</md-dialog>',
					         locals: {
					           items: $scope.items,
					           d: $scope.d
					         },
					         controller: DialogController
					      });
					       
					      function DialogController($scope, displayReportService, $mdDialog, items) {
					        $scope.items = items;
					        $scope.forReport = function() {
					          if($scope.d!=undefined){
					        	  var data =  { 
					    					"to": $scope.d,
					    					"report": vm.report.id
					    				};
					        	  displayReportService.forward(data)
									.then(function(resp){
										if(resp.error){
											toastr.error("Error forwarding report")
										}else{
											var h = "";
											for(var i=0; i < $scope.items.length; i++) {
												if($scope.d == $scope.items[i].id)
											    	h= $scope.items[i].username;
											}
											
											vm.report.currentHolder.forwardedTo.username = h;
											$mdDialog.hide();
										}
									})
					          }
					        }
					        $scope.closeDialog = function() {
						          $mdDialog.hide();
						        }
					      }
			    };

		}])
})();