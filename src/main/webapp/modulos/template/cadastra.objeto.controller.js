angular.module("conpag").controller("ModalCadastraObjetoController", function($scope, $uibModalInstance, mensagem ){
	
	$scope.mensagem = mensagem;
	
	$scope.nome = null;
	
	$scope.cancelar = function(){
		$uibModalInstance.dismiss();
	}
	
	$scope.salvar = function(){
		$uibModalInstance.close( $scope.nome );
	}
	
});