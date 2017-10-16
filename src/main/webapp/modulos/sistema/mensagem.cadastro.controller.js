angular.module("conpag").controller("MensagemCadastroController", function($scope, api, $routeParams, toastr, $location){
	
	var id = $routeParams.id;
	
	if( id && id != "new" ){
		api.mensagem.get( id ).then( function( response ){
			
			var mensagem = response.data;
			$scope.mensagem = mensagem;
			$scope.mensagem.id = null;
			
			$scope.mensagem.idUserDestino = response.data.remetente.id;
			
			$scope.mensagem.assunto = "RE: "+ mensagem.assunto;
			$scope.mensagem.texto = "<br><br><blockquote><h6> "+ mensagem.texto +" </h6></blockquote>";
			$scope.mensagem.data = new Date();
		});
	}
	else{
		$scope.mensagem = {};
		$scope.mensagem.data = new Date();
	}
	
	api.usuario.getUserOnline().then( function(response){
		$scope.usuarioOnline = response.data;
	});
	
	api.usuario.getDestinatarios().then( function( response ){
		$scope.destinatarios = response.data;
	});
	
	$scope.salvar = function(){
		if($scope.form.$valid){
			$scope.mensagem.lida = false;
			$scope.mensagem.remetente = $scope.usuarioOnline;
			
			api.mensagem.insert( $scope.mensagem ).then( function( response ){
				toastr.success("Mensagem enviada com sucesso");
				$location.path("/mensagens");
			});
			
		}
	}
});