(function(){
	angular
		.module('service.session',[])
		.service('sessionService',['$cookies','$http', '$window', sessionService])

		function sessionService($cookies,$http){

			var self = this;
			
			self.userId=null;
			self.userRole=null;

			self.createSession = createSession;
			self.destroySession = destroySession;

			activate();		

			function activate(){
				cookie = $cookies.getObject('kitscookie');
				if(cookie){
					var userData = parseJwt(cookie)
					self.userId=userData.sub;
					self.userRoles=userData.roles;
					$http.defaults.headers.common={'X-Auth-Token':cookie}
				}
			}

			function createSession(data){
				$cookies.putObject('kitscookie',data);
				var userData = parseJwt(data)
				self.userId=userData.sub;
				self.userRoles=userData.roles;
				$http.defaults.headers.common={'X-Auth-Token':data}
			}

			function destroySession(){
				$cookies.remove('kitscookie');
				self.userId=null;
				self.userRoles=null;
				$http.defaults.headers.common={'X-Auth-Token':undefined}
			}

			function parseJwt (token) {
			    var base64Url = token.split('.')[1];
			    var base64 = base64Url.replace('-', '+').replace('_', '/');
			    return JSON.parse(window.atob(base64));
			}

		}
})();