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

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <script>
        $(document).ready(function () {
            $("#register-form").submit(function (event) {
                // Prevent the form from submitting in the traditional way
                event.preventDefault();

                // Serialize form data
                var formData = $(this).serialize();

                // AJAX request
                $.ajax({
                    type: "POST",
                    url: "register",
                    data: formData,
                    success: function (response) {
                        // Process the response
                        if (response.status === "success") {
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: response.message,
                                showConfirmButton: false,
                                timer: 1500
                            });
                            // Clear form fields on error
                            $("#username").val("");
                            $("#email").val("");
                            $("#password").val("");
                            $("#re_pass").val("");
                            // Redirect to a success page if needed
                            // window.location.href = "login.jsp";
                        } else if (response.status === "error") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: response.message,
                                showConfirmButton: false,
                                timer: 1500
                            });
                            // Clear form fields on error
                            $("#username").val("");
                            $("#email").val("");
                            $("#password").val("");
                            $("#re_pass").val("");
                        }
                    },
                    error: function () {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Something went wrong',
                            showConfirmButton: false,
                            timer: 1500
                        });
                        // Clear form fields on error
                        $("#username").val("");
                        $("#email").val("");
                        $("#password").val("");
                        $("#re_pass").val("");
                    }
                });
            });
        });
    </script>
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
<script src="js/main.js"></script>
</body>
</html>