(function(){
	angular
		.module('service.session',[])
		.service('sessionService',['$cookies','$http',sessionService])

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
					self.userId=cookie.userId;
					self.userRole=cookie.userRole;
				}
			}

			function createSession(data){
				$cookies.putObject('kitscookie',data);
				self.userId=data.userId;
				self.userRole=data.userRole;
			}

			function destroySession(){
				$cookies.remove('kitscookie');
				self.userId=null;
				self.userRole=null;
			}

		}
})();