angular.module("conpag").factory("api", function($http, $window, $filter, $websocket){

	return{
//=============================================================================================//		
//		USUARIO														                           //
//=============================================================================================//
		websocket: function(path) {
		    var protocolPrefix = (window.location.protocol === 'https:') ? 'wss:' : 'ws:';
		    return $websocket(protocolPrefix + '//' 
		    				+ location.host + '/'
		    				+ location.pathname + path);
		},
		usuario:{
			insert: function(user){
				return $http.post("./rest/usuario", user);
			},
			update: function(user){
				return $http.put("./rest/usuario", user);
			},
			get: function(id){
				return $http.get("./rest/usuario/"+id);
			},
			getUserOnline: function(){
				return $http.get("./rest/usuario/useronline");
			},
			isUserInrole: function( role ){
				return $http.get("./rest/usuario/isuserinrole/"+ role );
			},
			redirectOld: function(){
				$window.open("./rest/usuario/redirect-old", "_blank");
			},
			getDestinatarios: function(){
				return $http.get("./rest/usuario/destinatarios");
			}
		},
//=============================================================================================//		
//		MENSAGEM														                           //
//=============================================================================================//
		mensagem:{
			insert: function( msgn ){
				return $http.post("./rest/mensagem", msgn );
			},
			update: function( msgn ){
				return $http.put("./rest/mensagem", msgn );
			},
			excluir: function( id ){
				return $http.delete("./rest/mensagem/"+ id );
			},
			get: function(id){
				return $http.get("./rest/mensagem/"+ id);
			},
			getNaoLidas: function(idUser){
				return $http.get("./rest/mensagem/naolidas/"+ idUser );
			},
			getRecebidas: function( filtro ){
				return $http.post("./rest/mensagem/recebidas", filtro );
			},
			getEnviadas: function( filtro ){
				return $http.post("./rest/mensagem/enviadas", filtro );
			},
			updateLida: function( id ){
				return $http.put("./rest/mensagem/update-lida/"+ id );
			}
		},
//=============================================================================================//		
//		SISTEMA														                           //
//=============================================================================================//
		sistema: {
			getParametro: function(key){
				return $http.get("./rest/sistema/"+ key );
			},
			setParametro: function(key, value){
				return $http.post("./rest/sistema/"+ key, value );
			}
		},
//=============================================================================================//		
//		MUNICIPIO														                           //
//=============================================================================================//
		municipio:{
			insert: function(municipio){
				return $http.post("./rest/municipio", municipio);
			},
			update: function(municipio){
				return $http.put("./rest/municipio", municipio);
			},
			get: function(id){
				return $http.get("./rest/municipio/"+id);
			},
			getAll:function(){
				return $http.get("./rest/municipio");
			},
            getByUF:function(uf){
                return $http.get("./rest/municipio/municipios/"+uf);
            }
		},

//=============================================================================================//		
//		SEMANA 																				   //
//=============================================================================================//		
		semana:{
			getAll:function(ano){
				return $http.get("./rest/semana/"+ ano);
			},
			getSemanaAtual:function(dtAtual){
				return $http.post("./rest/semana", dtAtual);
			}
		},
//=============================================================================================//		
//		CONPAG														                           //
//=============================================================================================//	
        fornecedor:{
        	insert: function( fornecedor ){
        		return $http.post("./rest/fornecedores", fornecedor );
        	},
        	update: function( fornecedor ){
        		return $http.put("./rest/fornecedores", fornecedor );
        	},
        	excluir: function(id){
        		return $http.delete("./rest/fornecedores/"+ id);
        	},
        	getById: function(id){
        		return $http.get("./rest/fornecedores/by-id/" + id);
        	}, 
        	getBairrosByMunicipio: function(municipio){
        		return $http.post("./rest/fornecedores/bairros", municipio );
        	},
        	getEnderecosByBairro: function( bairro ){
        		return $http.post("./rest/fornecedores/enderecos", bairro );
        	},
        	getBancos: function(){
        		return $http.get("./rest/fornecedores/bancos");
        	},
        	getFornecedorByParams: function(cnpjCpf){
        		return $http.get("./rest/fornecedores/fornecedor-existente?cnpjCpf="+cnpjCpf);
        	}, 
        	getFornecedores: function(filtro){
        		return $http.post("./rest/fornecedores/lista-dto", filtro);
        	},

        },
        contapagar:{
        	insert: function( contapagar ){
        		return $http.post("./rest/contapagar", contapagar );
        	},
        	update: function( contapagar ){
        		return $http.put("./rest/contapagar", contapagar );
        	},
        	excluir: function(id){
        		return $http.delete("./rest/contapagar/"+ id);
        	},
        	getById: function(id){
        		return $http.get("./rest/contapagar/by-id/" + id);
        	}, 
        	getListaFornecedores: function(){
        		return $http.get("./rest/contapagar/lista-fornecedores");
        	},
        	getGrupos: function(){
        		return $http.get("./rest/contapagar/grupos");
        	},
        	getEmpresas: function(){
        		return $http.get("./rest/contapagar/empresas");
        	},
        	getContasPagar: function(filtro){
        		return $http.post("./rest/contapagar/lista-contas-pagar", filtro);
        	},
        	insertQuitacao: function( parcelaPaga ){
        		return $http.post("./rest/contapagar/quitacao", parcelaPaga );
        	},

        },


	}
});