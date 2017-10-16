angular.module("conpag").controller('HttpError403Controller', function ($scope, $location) {
	
	$scope.home = function(){
		$location.path("/home");
	}
	
});