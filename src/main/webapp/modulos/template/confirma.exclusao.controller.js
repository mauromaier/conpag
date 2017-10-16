angular.module("conpag").controller("ModalConfirmaExclusaoController", function($scope, $uibModalInstance, mensagem, id ){
	
	$scope.mensagem = mensagem;
	
	$scope.cancelar = function(){
		$uibModalInstance.dismiss();
	}
	
	$scope.confirmar = function(){
		$uibModalInstance.close( id );
	}
	
});