(function(){
	angular
		.module('model.service',[])
		.factory('userService',['$http','userService',userService])

	function userService($http){

		return{
			create : create,
			remove : remove
		}

		function create(user){

			return $http
					.post('/user',user)
					.then(function(res){
						return res.data
					})

		}

		function remove(user){

		}

	}

})();

