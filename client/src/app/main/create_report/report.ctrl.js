(function () {
    angular
        .module('kits.report', ['model.service.report'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/report', {
                    templateUrl: "./app/main/create_report/report.html",
                    controller: "ReportController",
                    controllerAs: "vm"
                })
        }])
        .controller('ReportController', ['reportService','$scope','toastr',
                                         '$http', 
                                         function (reportService,$scope, toastr, 
                                        		 $http) {
            var vm = this;
            $scope.files = [];
            vm.photos = [];
            vm.description = "";
            vm.building = 0;
            
            reportService.getBuildings()
			.then(function(response){
				if(response.error){
					toastr.error("Error fetching buildings")
				}else{
					vm.buildings=response.data
				}
			})

            $scope.$watch('files.length', function (newVal, oldVal) {
                vm.photos = [];
                
                angular.forEach($scope.files, function (obj) {
                    var reader = new FileReader();

                    reader.onloadend = function (evt) {
                        $scope.$apply(function(){
                            vm.photos.push(evt.target.result);
                        });
                    };

                    if (!obj.isRemote) {
                        reader.readAsDataURL(obj.lfFile); 
                    }
                });
            });
            
            function haveInputError(){
    			var haveError = false;
    			if(!vm.building || !vm.description){
    				haveError = true;
    			}
    			return haveError;
    		}
            
            vm.sendReport = function(){
    			if(haveInputError()){
    				toastr.error("Building and description must be filled!", "Fields error");
    			}else{
   
    				var data =  { 
    					"building": vm.building,
    					"description": vm.description,
    					"photos": vm.photos
    				};
    			    
    				reportService.sendReport(data)
    				.then(function(response){
    					if(response.error){
    						toastr.error("Error sending report")
    					}else{
    						toastr.success("Report is succesfully sent.");
    						vm.photos = [];
    			            vm.description = "";
    			            $scope.files = [];
    						//redirect
    					}
    				})
    			}
    		}



        }])
})();

