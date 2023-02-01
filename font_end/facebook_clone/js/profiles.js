const PROFILE_URL = 'http://localhost:8080/api/v1/profiles/';

$('#search').click(function () {
    let searching = $('#searching').val();
    sessionStorage.setItem('searching', searching);
    window.location = 'profiles.html';

    return false;
})

function getResult() {
    let token = sessionStorage.getItem('token');
    let keyword = sessionStorage.getItem('searching');
    $.ajax({
        url: PROFILE_URL + 'search/' + keyword,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            if (data.length == 0) {
                $('.profiles').html(`<p>Không có kết quả nào</p>`);
            } else {
                let output = '';
                $.each(data, (index, profile) => {
                    output += `
                    <p class="d-flex justify-content-between">
                        <p><i class="fa-solid fa-user"></i> <strong>${profile.givenName} ${profile.surname}</strong> 
                        <button onclick="getProfileDetails(${profile.id})" type="button" class="btn btn-outline-secondary">Xem</button></p>
                    </p>`
                });

                $('.profiles').html(output);
            }
        },
        error: function (request, status, error) {
            console.log(request)
        }
    })
}

function getProfileDetails(userId) {
    sessionStorage.setItem('profile-searching', userId);
    window.location.href = "profile-searching.html";
}

getResult();