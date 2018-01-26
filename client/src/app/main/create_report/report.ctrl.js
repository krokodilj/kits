(function () {
    angular
        .module('kits.report', ['service.auth'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/report', {
                    templateUrl: "./app/main/create_report/report.html",
                    controller: "ReportController",
                    controllerAs: "vm"
                })
        }])
        .controller('ReportController', ['$scope','toastr','$http', function ($scope, toastr, $http) {
            var vm = this;
            $scope.files = [];
            vm.photos = [];
            vm.description = "";
            vm.building = 0;

            $scope.$watch('files.length', function (newVal, oldVal) {
                vm.photos = [];
                
                angular.forEach($scope.files, function (obj) {
                    var reader = new FileReader();

                    reader.onloadend = function (evt) {
                        $scope.$apply(function(){
                        	alert(evt.target.result);
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
    					"description": vm.building,
    					"photos": vm.photos
    				};
    			    
    				$http.post('http://localhost:8080/api/auth/login', {
    					  "password": "ivan",
    					  "username": "ivan"
    					}).then(function(response) {

    					}, function(response) {
    						toastr.error(response.data, 'Error');
    				});	
    			}
    		}



        }])
})();

