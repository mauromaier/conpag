angular.module("conpag").controller('HeaderController', function ($scope, $rootScope, api, toastr) {

	$scope.totalNaoLidas = 0;
	$scope.user = [];
	
	$scope.usuarioOnline = function(){
		
		api.usuario.getUserOnline().then(function(response){
			$scope.user = response.data;

	   		 api.mensagem.getNaoLidas( $scope.user.id ).then(function(result){
	   			$scope.totalNaoLidas = result.headers("x-naolidas");
			 });
			
		});
	}
	
	$scope.usuarioOnline();

});