angular.module("conpag").controller("ContaPagarListaController", function($scope, api, $uibModal, toastr, $filter, $location){
	
	//$location.path("/error403");
	
	$scope.filtro = {};
    $scope.paginaAtual = 1;
    $scope.limite = 10;
   
	$scope.isOpen = false;
	
	$scope.openMenu = function(){
		$scope.isOpen = true;
	}
    
	api.contapagar.getEmpresas().then(function(response){
		var empresa = {};
		empresa.id = -1;
		empresa.descricao = "Todos...";
		
		$scope.empresas = [];
		$scope.empresas.push(empresa);

		for(i=0;i < response.data.length; i++){
			$scope.empresas.push( response.data[i] );
		}
	});
	
	$scope.remover = function(id){
		api.contapagar.excluir(id).then(function(response){
			$scope.pesquisar();
			toastr.success('Conta a Pagar removida com sucesso');
		});
	}
	
	$scope.confirmarRemover = function( conta ){
		
			
		var mensagem = "Confirma a exclusão da Conta a Pagar?";
		
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/template/confirma.exclusao.html", 
			controller: "ModalConfirmaExclusaoController",
			resolve: { 
				mensagem: function () {
					return mensagem;
				},
				id: function(){
					return conta.id;
				}
			}
		});
		
		modalInstance.result.then(function (id){
			$scope.remover( id );
		});
		
	}

	
	$scope.pesquisar = function(){
		
		$scope.filtro.idEmpresa = $scope.idEmpresa;
		$scope.filtro.limite = 10;
		$scope.filtro.pagina = $scope.paginaAtual;
		$scope.filtro.dtInicial = $scope.dtInicial;
		$scope.filtro.dtFinal = $scope.dtFinal;
		
    	api.contapagar.getContasPagar($scope.filtro).then(function(response){
        	$scope.lista = response.data;
        	
        	$scope.total = response.headers("x-total");
        });              	        	
	}
	
	$scope.pesquisar(); 
		
});