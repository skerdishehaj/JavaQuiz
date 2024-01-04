<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>

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

    <!-- Sign up form -->
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title">Sign up</h2>

                    <form method="post" action="register" class="register-form"
                          id="register-form">
                        <div class="form-group">
                            <label for="username"><i
                                    class="zmdi zmdi-account material-icons-name"></i></label> <input
                                type="text" name="username" id="username" placeholder="Your Username" required/>
                        </div>
                        <div class="form-group">
                            <label for="email"><i class="zmdi zmdi-email"></i></label> <input
                                type="email" name="email" id="email" placeholder="Your Email" required/>
                        </div>
                        <div class="form-group">
                            <label for="password"><i class="zmdi zmdi-lock"></i></label> <input
                                type="password" name="password" id="password" placeholder="Password" required/>
                        </div>
                        <div class="form-group">
                            <label for="re_pass"><i class="zmdi zmdi-lock-outline"></i></label>
                            <input type="password" name="re_pass" id="re_pass"
                                   placeholder="Repeat your password" required/>
                        </div>

                        <div class="form-group form-button">
                            <input type="submit" name="signup" id="signup"
                                   class="form-submit" value="Register"/>
                        </div>
                    </form>
                </div>
                <div class="signup-image">
                    <figure>
                        <img src="images/signup-image.jpg" alt="sing up image">
                    </figure>
                    <a href="login.jsp" class="signup-image-link">I am already
                        member</a>
                </div>
            </div>
        </div>
    </section>


</div>
<!-- JS -->
<%--<script--%>
<%--		src="https://code.jquery.com/jquery-3.7.1.min.js"--%>
<%--		integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="--%>
<%--		crossorigin="anonymous"></script>--%>
<script src="js/main.js"></script>
<script type="text/javascript">
</script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.min.css">
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
	let status = document.getElementById("status").value;
	let message = document.getElementById("message").value;
	if (status == "success") {
		Swal.fire({
			position: 'center',
			icon: 'success',
			title: 'Register Success',
			showConfirmButton: false,
			timer: 1500
		})
	} else if (status == "error") {
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
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>