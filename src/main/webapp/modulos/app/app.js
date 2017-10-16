
var app = angular.module("conpag",
		[ 'ngRoute', 'ngSanitize', 'ui.bootstrap', 'toastr', 
		  'picardy.fontawesome', 'mgcrea.ngStrap' , 'ngMap', 
		  'angularFileUpload', 'chart.js', 'dndLists', 
		  'ui.utils.masks', 'brasil.filters', 'AxelSoft', 
		  'ngTagsInput', 'textAngular', 'ngWebSocket']);

app.config(function($routeProvider, toastrConfig, $qProvider){
	
//	$qProvider.errorOnUnhandledRejections(false)
	
	angular.extend(toastrConfig, {
	    positionClass: 'toast-bottom-right',
	    target: 'body'
	});

	
   $routeProvider
//=============================================================================================//		
//	   SISTEMA														                           //
//=============================================================================================//
	   .when('/home', {
		   templateUrl: 'modulos/home/home.html', 
		   controller: 'HomeController'
	   })
	   .when('/about', {
           templateUrl: 'modulos/home/about.html', 
           controller: 'AboutController'
       })
       .when("/construcao",{
          	templateUrl: "default.desenvolvimento.html"
        })
       .when("/error403",{
          	templateUrl: "modulos/layout/http.error.403.html",
          	controller: "HttpError403Controller"
        })
       .when("/mensagens",{
          	templateUrl: "modulos/sistema/mensagem.lista.html",
          	controller: "MensagemListaController"
        })
        .when("/mensagens/:id",{
          	templateUrl: "modulos/sistema/mensagem.cadastro.html",
          	controller: "MensagemCadastroController"
        })
        .when("/no-session",{
          	templateUrl: "modulos/sistema/sessao.expirada.html",
          	controller: "NoSessionController"
        })
//=============================================================================================//		
//		ConPag														                          
//=============================================================================================//
         .when("/conpag/fornecedor/:id",{
        	templateUrl: "modulos/conpag/fornecedor.cadastro.html",
        	controller: "FornecedorCadastroController"
        })
         .when("/conpag/fornecedor",{
          	templateUrl: "modulos/sistema/default.desenvolvimento.html",
        	controller: "DefaulMensagemDesenvolvimento"
        })
          .when("/conpag/contas-pagar/:id",{
        	templateUrl: "modulos/conpag/contapagar.cadastro.html",
        	controller: "ContaPagarCadastroController"
        })
         .when("/conpag/contas-pagar",{
          	templateUrl: "modulos/conpag/contapagar.lista.html",
        	controller: "ContaPagarListaController"
        })
       
//===========================================================================================//
//		DEFAULT
//===========================================================================================//        
         .otherwise({redirectTo: '/home' })
});

app.run( function(uibPaginationConfig){
	uibPaginationConfig.firstText='<<';
	uibPaginationConfig.previousText='<';
	uibPaginationConfig.nextText='>';
	uibPaginationConfig.lastText='>>';
	
});

app.value('$strapConfig', {
	datepicker: {
		language: 'pt-BR',
		format: 'dd/MM/yyyy'
	}
});

