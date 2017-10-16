angular.module("conpag").controller("ModalMensagemDialogController", function($scope, $uibModalInstance, mensagem, titulo ){

	$scope.mensagem = mensagem;
	$scope.titulo = titulo;
	
	if($scope.titulo == null){
		$scope.titulo = "Aviso";
	}
	
	$scope.fechar = function(){
		$uibModalInstance.close();
	}
	
});