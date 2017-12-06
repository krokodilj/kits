(function(){
	angular
		.module('service.auth',['service.session'])
		.factory('authService',['$http','sessionService',authService])

		function authService($http,sessionService){
			
			return {

				login: login,
				logout:logout,
				isAuthenticated: isAuthenticated,
				isAuthorised : isAuthorised
				
			}

			function login (data){

				return $http
					.post('/login',data)
					.then(function(res){
						sessionService.createSession()//200
						return res.data
					},function(res){
						console.log(res)
						return res.data//405a
					});
			}

			function logout(){
				
			}

			function isAuthenticated(){

			}

			function isAuthorised(roles){
				
			}
		}
})();