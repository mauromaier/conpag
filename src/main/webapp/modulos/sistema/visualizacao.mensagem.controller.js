angular.module("conpag").controller("ModalVisualizaMensagemController", function($scope, $uibModalInstance, mensagem, api){

	$scope.mensagem = mensagem;
	
	$scope.fechar = function(){
		$uibModalInstance.close( $scope.mensagem.id );
	}
	
//	$scope.salvar = function(){
//		if($scope.form.$valid){
//			
//			$scope.alerta.dtCriacao = new Date();
//			$scope.alerta.usuario = $scope.usuarioOnLine;
//			
//			$uibModalInstance.close(alerta);
//		}
//	}
});