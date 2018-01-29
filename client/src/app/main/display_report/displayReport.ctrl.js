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
				vm.comment="";
				
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
				
				vm.parseDateTime = function (array) {
					let date = array[2] + "." +  array[1] +  "." + array[0];
					let time = " " + array[3] + ":" + array[4];
					return date + time;
				}
				
				vm.isHolder = function(){
					if(sessionService.userId == vm.report.currentHolder.forwardedTo.username)
						return true;
					else
						return false;
				}
				
				vm.postComment = function(){
					var data =  { 
	    				"data": vm.comment,
	    				"report": vm.report.id
	    			};
					
					displayReportService.postComment(data)
					.then(function(resp){
						if(resp.error){
							toastr.error("Comment isn't sent")
						}else{
							vm.report.comments.push(resp.data);
							toastr.success("Comment is succesfully posted.");
							vm.comment = "";
						}
					})
					
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
			    
			    vm.sendBid = function ($event) {
					var parentEl = angular.element(document.body);
						$mdDialog.show({
						  parent: parentEl,
					      targetEvent: $event,
					      template:
					           '<md-dialog aria-label="List dialog">' +
					           '  <md-dialog-content>'+
					           '    <md-input-container class="md-block" flex-gt-sm>'+
					           '		<label>Price</label>'+
					           ' 		<input ng-model="price">'+
					           '	</md-input-container>'+
					           '    <md-input-container class="md-block" flex-gt-sm>'+
					           '		<label>Description</label>'+
					           ' 		<input ng-model="description">'+
					           '	</md-input-container>'+
					           '  </md-dialog-content>' +
					           '  <md-dialog-actions>' +
					           '    <md-button ng-click="sendBid()" class="md-primary">' +
					           '      Send Bid' +
					           '    </md-button>' +
					           '    <md-button ng-click="closeDialog()" class="md-primary">' +
					           '      Close Dialog' +
					           '    </md-button>' +
					           '  </md-dialog-actions>' +
					           '</md-dialog>',
					      locals: {
					        price: $scope.price,
					        description: $scope.description
					      },
					         controller: DialogController
					      });
					       
					      function DialogController($scope, displayReportService, $mdDialog) {
					        $scope.sendBid = function() {
					          if($scope.price!=undefined && $scope.description!=undefined){
					        	  var data =  { 
					    					"price": $scope.price,
					    					"description": $scope.description,
					    					"report": vm.report.id
					    				};
					        	  alert(JSON.stringify(data))
					        	  displayReportService.sendBid(data)
									.then(function(resp){
										if(resp.error){
											toastr.error("Error while sending bid")
										}else{
											toastr.success("Bid is succesfully sent.");
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