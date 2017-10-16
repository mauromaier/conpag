angular.module("conpag").controller("ParcelasModalController", function($scope, $uibModalInstance, api, $filter, parcela){

	$scope.parcela = parcela;
	
	api.usuario.getUserOnline().then(function(response){
		$scope.usuarioOnLine = response.data;
	});
	
	$scope.fechar = function(){
		$uibModalInstance.close(null);
	}
	
	$scope.salvar = function(){
		if($scope.form.$valid){	
			
			$uibModalInstance.close($scope.parcela);
		}
	}

});