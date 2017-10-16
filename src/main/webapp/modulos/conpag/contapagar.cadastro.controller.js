angular.module("conpag").controller("ContaPagarCadastroController", function($scope, $location, api, utils, $routeParams, toastr, $uibModal, $filter){

	//$location.path("/error403");
	
	var id = $routeParams.id;

	$scope.hoje = new Date();
	
	if(id && id != "new"){
		api.contapagar.getById(id).then(function(response){
			$scope.contapagar = response.data;
			
		});
		
	} 
	else {
		$scope.contapagar = {};
	}
	
	api.usuario.getUserOnline().then(function(response){
		$scope.usuarioOnLine = response.data;
		
	});
	
	api.contapagar.getListaFornecedores().then(function(response){
		$scope.fornecedores = response.data;
	});
	
	api.contapagar.getGrupos().then(function(response){
		$scope.grupos = response.data;
	});
	
	api.contapagar.getEmpresas().then(function(response){
		$scope.empresas = response.data;
	});
	
	$scope.onClickCadastrarParcela = function(){
		var parcela = {};
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/conpag/parcelas.modal.html", 
			controller: "ParcelasModalController",
			resolve: { 
				parcela: function () {
		    		return parcela;
		        }
		    }
		});
		
		modalInstance.result.then(function (parcela){
			if(!$scope.contapagar.listaParcelas){
				$scope.contapagar.listaParcelas = [];;
			}

			$scope.contapagar.listaParcelas.push( parcela );
			
		});
	}
	
	$scope.onClickAlterarParcela = function(parcela){
		
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/conpag/parcelas.modal.html", 
			controller: "ParcelasModalController",
			resolve: { 
				parcela: function () {
		    		return parcela;
		        }
		    }
		});
	}
	
	$scope.confirmarRemoverParcela = function(parcela){
		
		var mensagem = "Confirma a exclusão?";
		
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/template/confirma.exclusao.html", 
			controller: "ModalConfirmaExclusaoController",
			resolve: { 
				mensagem: function () {
					return mensagem;
				},
				id: function(){
					return parcela;
				}
			}
		});
		
		modalInstance.result.then(function (parcela){
			var index = $scope.contapagar.listaParcelas.indexOf(parcela);
			$scope.contapagar.listaParcelas.splice(index, 1);
			
		});
	}
	
	$scope.confirmarQuitarParcela = function(parcela){
		
		var mensagem = "Confirma a quitarção da Parcela?";
		
		var modalInstance = $uibModal.open({ 
			templateUrl: "modulos/template/confirma.exclusao.html", 
			controller: "ModalConfirmaExclusaoController",
			resolve: { 
				mensagem: function () {
					return mensagem;
				},
				id: function(){
					return parcela;
				}
			}
		});
		
		modalInstance.result.then(function (parcela){
			var parcelaPaga = {};
			parcelaPaga.valor = parcela.valor;
			parcelaPaga.idConta = $scope.contapagar.id;
			parcelaPaga.dtVencimento = parcela.dtVencimento;
			parcelaPaga.dtPagamento = parcela.dtPagamento;
			parcelaPaga.idParcela = parcela.id;
			
			api.contapagar.insertQuitacao(parcelaPaga).then(function(response){
				if(response.data){
					var index = $scope.contapagar.listaParcelas.indexOf(parcela);
					$scope.contapagar.listaParcelas.splice(index, 1);
					
				}
			});
			
		});
	}
	
	$scope.salvar = function(){
		
		$scope.contapagar.usuario = $scope.usuarioOnLine;
		
		if($scope.form.$valid){
						
		
			if($scope.contapagar.id > 0){
				api.contapagar.update($scope.contapagar).then(function(response){
					$location.path("/conpag/contas-pagar");
					toastr.success("Conta a Pagar alterado com sucesso");
				});
			}else{
				
				api.contapagar.insert($scope.contapagar).then(function(response){
					$location.path("/conpag/contas-pagar");
					toastr.success("Conta a Pagar salva com sucesso");
				});
			}
		}
		else{
			toastr.error('Preencha os campos');
		}
	}
	
});
	