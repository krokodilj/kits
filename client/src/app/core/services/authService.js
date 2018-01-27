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

				var promise = $http
					.post('http://localhost:8080/api/auth/login',data)
					.then(function(res){
						sessionService.createSession(res.data)//200
						return {data:res.data}
					},function(err){
						return {error:true,data:err.data}
					});
				return promise
			}

			function logout(){
				sessionService.destroySession()
			}

			function isAuthenticated(){
				return sessionService.userId!=null
			}

			function isAuthorised(roles){
				var r=false
				roles.forEach(function(e){
					if (sessionService.userRoles.includes(e)) r=true;
				})				
				return r
			}
		}
})();