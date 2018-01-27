(function(){
	angular
		.module('model.service.building',[])
		.factory('buildingService',['$http',buildingService])

	function buildingService($http){

		return{
			create : create
		}

		function create(building){

			var promise= $http
					.post('/api/buildings/',building)
					.then(function(res){
						return {data:res.data}
					},function(err){
						return {error:true,data:err.data}
					})
			return promise
		}

	}

})();
