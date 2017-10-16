angular.module("conpag")
.directive('comboboxMunicipios', function(){
	return {
		template: 'modulos/template/municipio.lista.html'
	}
})
.directive('tileControlToggle', function () {
	return {
		restrict: 'A',
		link: function postLink(scope, element) {
			var tile = element.parents('.tile');

			element.on('click', function(){
				tile.toggleClass('collapsed');
				tile.children().not('.tile-header').slideToggle(150);
			});
		}
    };
})
.directive('rodapePaginado', function() {
	return {
		templateUrl: "modulos/template/paginacao.rodape.html"
	};
})
.filter("formatNumber",function(){
	return function(valor){
		return valor < 0 ? "--" : valor;
	}
})
.filter("validade",function(){
	return function(dataVencimento){
		var resultado;
		var dataAtual = new Date();
		var dtVencimento = new Date(dataVencimento);
		var dif;
		dif = ( (dataVencimento - dataAtual) / (1000 * 3600 * 24) );
		if(dif < 30 && dif >= 0){
			resultado = 1;
		} else if(dif < 0){
			resultado = 2;
		}
		return resultado;
	}
})
;

