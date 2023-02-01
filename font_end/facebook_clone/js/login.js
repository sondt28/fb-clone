const LOGIN_URL = 'http://localhost:8080/api/v1/auth/login'

$("#submit").click(function (e) {
    e.preventDefault();

    let email = $("#email").val();
    let password = $("#password").val();

    myData = {
        email: email,
        password: password
    }

    $.ajax({
        url: LOGIN_URL,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(myData),
        complete: function (xhr, textStatus) {
            if (xhr.status == 400) {
                if (xhr.responseJSON == null) {
                    $('#msg').html(xhr.responseText);
                } else {
                    $('#msg').html(xhr.responseJSON);
                }

            } else {
                sessionStorage.setItem('token', xhr.responseText);

                alert("Đăng nhập thành công!")

                window.location.href = "index.html";
            }
        }
    })
})