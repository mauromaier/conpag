angular.module("conpag").controller("AboutController", function($scope){
    
	$scope.ano = {};
	
	$scope.getAno = function(){

		var data = new Date();
		var anoAtual = data.getFullYear();
		
		$scope.ano = anoAtual;
	}
	
	$scope.getAno();
	
});