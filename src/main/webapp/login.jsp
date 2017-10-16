<%@ page session="false" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="shortcut icon" href="assets/imagens/logo/favicon.ico" type="image/x-icon">   
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>ConPag</title>
	
	<link href="assets/css/bootstrap.css" rel="stylesheet">
	<link href="assets/css/font-awesome.min.css" rel="stylesheet">
	<link href="assets/css/main.css" rel="stylesheet">

	<script type="text/javascript">
		<%
			if( request.getParameter("msg") != null){
				out.print("alert('Usuario e/ou senha invalido!')");
			}
		%>
	</script>

</head>
<body>

	<div class="page page-core page-login">

		<div class="text-center">
			<h3 class="text-light text-white">
				<span class="text-lightred">Con</span>Pag
			</h3>
		</div>

		<div class="container w-420 p-15 bg-white mt-40 text-center">

			<h2 class="text-light text-greensea">Entrar</h2>

			<form name="form" class="form-validation mt-20" method="post" action="login.action">

				<div class="form-group">
					<input type="text" 
							name="login"
							class="form-control underline-input"
							placeholder="Usuário" required>
				</div>

				<div class="form-group">
					<input type="password" 
							name="senha"
							placeholder="Senha"
							class="form-control underline-input"
							required>
				</div>

				<div class="form-group text-left mt-20">
					<button type="submit" 
							class="btn btn-greensea b-0 br-2 mr-5">
						Entrar
					</button>
				</div>

				<hr class="b-3x" />
			</form>
			
			
		</div>

	</div>

</body>
</html>