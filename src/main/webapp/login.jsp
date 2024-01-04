<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Login</title>

	<!-- Font Icon -->
	<link rel="stylesheet"
		  href="fonts/material-icon/css/material-design-iconic-font.min.css">

	<!-- Main css -->
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
<input type="hidden" id="status" value="<%=request.getAttribute("status")%>">
<input type="hidden" id="message" value="<% if(request.getAttribute("status") == "error"){
	out.print(request.getAttribute("error"));
}%>">
<div class="main">

	<!-- Sing in  Form -->
	<section class="sign-in">
		<div class="container">
			<div class="signin-content">
				<div class="signin-image">
					<figure>
						<img src="images/signin-image.jpg" alt="sing up image">
					</figure>
					<a href="registration.jsp" class="signup-image-link">Create an
						account</a>
				</div>

				<div class="signin-form">
					<h2 class="form-title">Login</h2>
					<form method="post" action="login" class="register-form"
						  id="login-form">
						<div class="form-group">
							<label for="username"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
								type="text" name="username" id="username"
								placeholder="Your Username" />
						</div>
						<div class="form-group">
							<label for="password"><i class="zmdi zmdi-lock"></i></label> <input
								type="password" name="password" id="password"
								placeholder="Password" />
						</div>

						<div class="form-group form-button">
							<input type="submit" name="signin" id="signin"
								   class="form-submit" value="Log in" />
						</div>
					</form>

				</div>
			</div>
		</div>
	</section>

</div>

<!-- JS -->
<script defer src="js/main.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.min.css">
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
	let status = document.getElementById("status").value;
	let message = document.getElementById("message").value;
	if (status == "error") {
		Swal.fire({
			position: 'center',
			icon: 'error',
			title: message,
			showConfirmButton: false,
			timer: 1500
		})
	}
</script>
</body>
</html>