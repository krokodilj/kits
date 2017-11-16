(function(){
	angular.module('service.auth',['service.session'])
		.factory('authService',['$http','sessionService',function($http,sessionService){
			return {
				login:function(data){
					// return $http
					// 	.post('/login',data)
					// 	.then(function(res){
					// 			sessionService.createSession()
					// 		},function(error){

					// 		});
				},
				logout:function(){
				// 	sessionService.destroy()
				}
			}
		}])
})();