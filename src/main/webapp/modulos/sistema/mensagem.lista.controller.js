angular.module("conpag").controller("MensagemListaController", 
		function($scope, $rootScope, api, utils, $uibModal, toastr, $filter, $location){
	
	$scope.opcao = "recebidas";
	
    $scope.paginaAtual = 1;
    $scope.limite = 10;
	
	$scope.isOpen = false;
	
	$scope.openMenu = function(){
		$scope.isOpen = true;
	}
	
	api.usuario.getUserOnline().then( function(response) {
		$scope.usuarioOnline = response.data;
		$scope.roles = response.data.roles;
		
		$scope.getPermissoes();
	});
	
	$scope.getPermissoes = function(){
		$scope.mensagemFiltro = {};
		$scope.mensagemFiltro.inicio = utils.dateUtils.getInicioMes();
		$scope.mensagemFiltro.fim = utils.dateUtils.getInicioProxMes();	
		
		$scope.pesquisar();		
	}
	
	api.regional.getAll().then(function(response){
		$scope.regionais = response.data;
	});
	
	
	$scope.pesquisar = function(){
		$scope.mensagemFiltro.idUser = $scope.usuarioOnline.id;
		
		$scope.mensagemFiltro.limite = $scope.limite;
		$scope.mensagemFiltro.pagina = $scope.paginaAtual;
		
		if( $scope.opcao == "recebidas" ){
			api.mensagem.getRecebidas( $scope.mensagemFiltro ).then( function(response){
				$scope.recebidas = response.data;
				$scope.total = response.headers("x-count");
			});	
		}
		else if( $scope.opcao == "enviadas" ){
			api.mensagem.getEnviadas( $scope.mensagemFiltro ).then( function(response){
				$scope.enviadas = response.data;
				$scope.total = response.headers("x-count");
			});	
		}
	}
	
	$scope.visualizarRecebida = function( mensagem ){
		
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/sistema/visualizacao.mensagem.html", 
			controller: "ModalVisualizaMensagemController",
			
			backdrop: 'static', 
			keyboard: false,
			
			resolve: { 
		    	mensagem: function () {
		    		return mensagem;
		        }
			}
		});
		
		modalInstance.result.then(function (id){
			api.mensagem.updateLida( id ).then( function(response){
//				$rootScope.ws.send( $scope.usuarioOnline.id );
				$scope.pesquisar();
			});
		});
	}
	
	$scope.visualizarEnviadas = function( mensagem ){
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/sistema/visualizacao.mensagem.html", 
			controller: "ModalVisualizaMensagemController",
			
			backdrop: 'static', 
			keyboard: false,
			
			resolve: { 
		    	mensagem: function () {
		    		return mensagem;
		        }
			}
		});
	}
	
	$scope.responder = function( id ){
		
		api.mensagem.updateLida( id ).then( function(response){
			$scope.pesquisar();
		});
		
		$location.path("/mensagens/"+ id );
	}
	
	$scope.remover = function(id){
		api.mensagem.excluir(id).success(function(data){
			$scope.pesquisar();
			toastr.success('Mensagem removido com sucesso');
		});
	}
	
	$scope.confirmaExclusao = function( selecionado ){
		var mensagem = "Confirma exclusão da mensagem com assunto "+ selecionado.assunto +" com data: "+ $filter('date')( selecionado.data , 'dd/MM/yyyy') +"?";
		
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/template/confirma.exclusao.html", 
			controller: "ModalConfirmaExclusaoController",
			resolve: { 
		    	mensagem: function () {
		    		return mensagem;
		        },
		        id: function(){
		        	return selecionado.id;
		        }
		      }
		    });
		
		modalInstance.result.then(function (id){
			$scope.remover( id );
		});
	}
	
});