(function(){
	angular
		.module('kits',[
			'ngRoute',
			'ngCookies',
			'ngMaterial',
			'ngMessages',
			'lfNgMdFileInput',
			'toastr',
			'kits.login',
			'kits.register',
			'kits.report',
			'kits.create_building',
			'kits.buildings'
		])
})();