(function(){
	angular
		.module('model.service.user',[])
		.factory('userService',['$http',userService])

	function userService($http){

		return{
			create : create,
			remove : remove,
			getManagers	: getManagers,
			getOne : getOne
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

		function getManagers(){
			var promise = $http
							.get('/api/managers/')
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise
		}

		function getOne(user_id){
			var promise = $http
							.get('/api/managers/'+user_id)
							.then(function(response){
								return {data:response.data}
							},function(error){
								return { error :true , data :error.data}
							})
			return promise
		}
	}

})();

