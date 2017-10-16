angular.module("conpag").controller('NavbarController', 
		function ($scope, api, $uibModal, toastr) {
    $scope.oneAtATime = false;

    $scope.status = {
      isFirstOpen: true,
      isSecondOpen: true,
      isThirdOpen: true
    };

	$scope.isPermissaoConPag = false;
	
	api.usuario.getUserOnline().then( function(response) {
		$scope.usuarioOnline = response.data;
		$scope.getPermissoes();
	});
    
    $scope.getPermissoes = function(){
		for (i = 0; i < $scope.usuarioOnline.roles.length; i++) { 
			var role = $scope.usuarioOnline.roles[i];

			if( role.modulo.nome == "conpag" ){
				$scope.isPermissaoConPag = true;
				
			}
			
			
		}
		
		$scope.roles = $scope.usuarioOnline.roles;
	}
    
 });
