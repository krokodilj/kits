(function(){
	angular
		.module('model.service.building',[])
		.factory('buildingService',['$http',buildingService])

	function buildingService($http){

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
