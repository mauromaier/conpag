angular.module("conpag").controller("ModalOpcaoRelatorioController", function($scope, $uibModalInstance){
	
	$scope.fechar = function(){
		$uibModalInstance.dismiss();
	}
	
	$scope.onClickPdf = function(){
		$uibModalInstance.close( "pdf" );
	}
	
	$scope.onClickXls = function(){
		$uibModalInstance.close( "xls" );
	}

});