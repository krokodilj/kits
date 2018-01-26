(function(){
	angular
		.module('kits.report',['service.auth'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
			.when('/report',{
					templateUrl:"./app/main/create_report/report.html",
					controller:"ReportController",
					controllerAs:"vm"								
				})
		}])
		.controller('ReportController',['$scope',function($scope){
			var vm = this;
            $scope.files=[];
            
            $scope.$watch('files.length',function(newVal,oldVal){
                console.log($scope.files);
            });

		}])
})();

