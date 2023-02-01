const REGISTER_URL = 'http://localhost:8080/api/v1/auth/register'

$("#submit").click(function(e) {
    e.preventDefault();
    
    let surname = $("#surname").val();
    let givenName = $("#givenName").val();
    let dateOfBirth = $("#dateOfBirth").val();
    let email = $("#email").val();
    let password = $("#password").val();

    myData = {
        surname: surname,
        givenName: givenName,
        dateOfBirth: dateOfBirth,
        email: email,
        password: password
    }

    $.ajax({
        url: REGISTER_URL,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(myData),
        complete: function(xhr, textStatus) {
            if(xhr.status == 400) {
                var bodyString = '';
                $.each(xhr.responseJSON, function(message) {
                    bodyString += ('<div>' + xhr.responseJSON[message] + '</div>');
                });

                $('#msg').html(bodyString);
                
            } else {
                alert("Đăng ký thành công!")
                window.location.href = "login.html";
            }
        } 
    })
})