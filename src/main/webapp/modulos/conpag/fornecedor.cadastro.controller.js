angular.module("conpag").controller("FornecedorCadastroController", function($scope, $location, api, $routeParams, toastr, $uibModal){
	
	// $location.path("/error403");
	
	var id = $routeParams.id;
	
	$scope.isPermissaoAdmin = false;
		
	$scope.hoje = new Date();
	
	
	if(id && id != "new"){
		api.fornecedor.getById(id).then(function(response){
			$scope.fornecedor = response.data;
		
		});
	} 
	else {
		$scope.fornecedor = {};
	}
	
	api.usuario.getUserOnline().then(function(response){
		$scope.usuarioOnLine = response.data;
		
	});
	
	api.municipio.getAll().then(function(response){
		$scope.municipios = response.data;
	});
	
	api.fornecedor.getBancos().then(function(response){
		$scope.bancos = response.data;
	});
	
	$scope.getBairrosByMunicipio = function(){
		
		if($scope.municipio != null){
			api.fornecedor.getBairrosByMunicipio($scope.municipio).then(function(response){
				$scope.bairros= response.data;
			});
		}else{
			$scope.bairros = [];
		}
	}
	
	$scope.getEnderecosByBairro = function(){
		
		if($scope.bairro != null){
			api.fornecedor.getEnderecosByBairro($scope.bairro).then(function(response){
				$scope.enderecos= response.data;
			});
		}else{
			$scope.enderecos = [];
		}
	}
	
	$scope.salvar = function(){
		$scope.fornecedor.dtCriacao = $scope.hoje;
		$scope.fornecedor.usuario = $scope.usuarioOnLine;
		
		if($scope.form.$valid){
			api.fornecedor.getFornecedorByParams($scope.fornecedor.cnpjCpf).then(function(response){
					
					if($scope.fornecedor.id > 0){
						api.fornecedor.update($scope.fornecedor).then(function(response){
							$location.path("/conpag/fornecedores");
							toastr.success("Fornecedor alterado com sucesso");
						});
					}else{
						if(response.data == 'true'){
							
							var mensagem = "Já existe um fornecedor cadastrado com esse CNPJ/CPF.";
							
							var modalInstance = $uibModal.open({ 
								templateUrl: "modulos/template/dialog.mensagem.html", 
								controller: "ModalMensagemDialogController",
								resolve: { 
							    	mensagem: function () {
							    		return mensagem;
							        },
							        titulo: function () {
							    		return null;
							        }
							
							      }
							});
							
						}else{
							
							api.fornecedor.insert($scope.fornecedor).then(function(response){
								$location.path("/conpag/fornecedores");
								toastr.success("Fornecedor salvo com sucesso");
							});
						}
					}
			});
		}
		else{
			toastr.error('Preencha os campos');
		}
	}
	
	$scope.cadastraBanco = function() {
		var mensagem = "Informe o nome do novo Banco:";

		var modalInstance = $uibModal
				.open({
					templateUrl : "modulos/template/cadastra.objeto.html",
					controller : "ModalCadastraObjetoController",
					resolve : {
						mensagem : function() {
							return mensagem;
						}
					}
				});

		modalInstance.result.then(function(nome) {
			var banco = {};
			banco.nome = nome;

			$scope.bancos.push(banco);
			$scope.fornecedor.banco = banco;
		});
	}
	
	$scope.cadastraEndereco = function() {
		var mensagem = "Informe o nome do novo Endereço:";

		var modalInstance = $uibModal
				.open({
					templateUrl : "modulos/template/cadastra.objeto.html",
					controller : "ModalCadastraObjetoController",
					resolve : {
						mensagem : function() {
							return mensagem;
						}
					}
				});

		modalInstance.result.then(function(nome) {
			var endereco = {};
			
			endereco.bairro = $scope.bairro;
			endereco.nome = nome;
			
			if( ! $scope.enderecos ){
				$scope.enderecos = [];
			}
			
			$scope.enderecos.push( endereco );
			$scope.fornecedor.endereco = endereco;
		});
	}
	

	$scope.cadastraBairro = function() {
		var mensagem = "Informe o nome do novo Bairro:";

		var modalInstance = $uibModal
				.open({
					templateUrl : "modulos/template/cadastra.objeto.html",
					controller : "ModalCadastraObjetoController",
					resolve : {
						mensagem : function() {
							return mensagem;
						}
					}
				});

		modalInstance.result.then(function(nome) {
			var bairro = {};
			bairro.nome = nome;
			bairro.municipio = $scope.municipio;
			$scope.bairros.push(bairro);
			$scope.bairro = bairro;
		});
	}


	$scope.validaTelefone = function (telefone){
		
		if(telefone.length >= 1 && telefone.length < 13){
			var mensagem = "Verifique se o campo 'Telefone' está completo.";
			
			var modalInstance = $uibModal.open({ 
				templateUrl: "modulos/template/dialog.mensagem.html", 
				controller: "ModalMensagemDialogController",
				resolve: { 
			    	mensagem: function () {
			    		return mensagem;
			        },
			        titulo: function () {
			    		return null;
			        }
			      }
			});
		}
	}
	
	
});
	