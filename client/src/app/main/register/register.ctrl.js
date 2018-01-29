(function(){
	angular
		.module('kits.register',['model.service.user'])
		.config(['$routeProvider',function($routeProvider){
			$routeProvider
			.when('/register',{
					templateUrl:"./app/main/register/register.html",
					controller:"RegisterController",
					controllerAs:"vm"								
				})
		}])
		.controller('RegisterController',['userService','toastr','$scope',
			function(userService,toastr,$scope){
			var vm = this;

			vm.user={}
			

			vm.register=register

			$scope.$watch('files.length', function (newVal, oldVal) {
                vm.user.pictures = [];
                
                angular.forEach($scope.files, function (obj) {
                    var reader = new FileReader();

                    reader.onloadend = function (evt) {
                        $scope.$apply(function(){
                            vm.user.pictures.push(evt.target.result);
                        });
                    };

                    if (!obj.isRemote) {
                        reader.readAsDataURL(obj.lfFile); 
                    }
                });
            });

			function register(user,type){
				userService.create(user,type)
					.then(function(response){
						if(response.error){
							toastr.error("Unable to register user : "+response.data)
						}else{
							toastr.success("User registered ")
							vm.user={}
							vm.type=undefined
						}
					})
			}

		}])
})();

