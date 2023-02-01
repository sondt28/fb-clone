const POST_URL = 'http://localhost:8080/api/v1/posts/';
const PROFILE_URL = 'http://localhost:8080/api/v1/profiles/';
const COMMENT_URL = 'http://localhost:8080/api/v1/comments/';
const LIKE_URL = 'http://localhost:8080/api/v1/likes/';
const FRIENDSHIP_URL = 'http://localhost:8080/api/v1/friendships/'

$('a[href="index.html"]').click(function () {
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('givenName');
    sessionStorage.removeItem('surname');
    sessionStorage.removeItem('dateOfBirth');
    sessionStorage.removeItem('searching');
    sessionStorage.removeItem('profile-searching');
})

$('a[href="login.html"]').click(function () {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('givenName');
    sessionStorage.removeItem('surname');
    sessionStorage.removeItem('dateOfBirth');
    sessionStorage.removeItem('searching');
    sessionStorage.removeItem('profile-searching');
})

$('#search').click(function () {
    let searching = $('#searching').val();
    sessionStorage.setItem('searching', searching);
    sessionStorage.removeItem('givenName');
    sessionStorage.removeItem('surname');
    sessionStorage.removeItem('dateOfBirth');
    window.location = 'profiles.html';

    return false;
})

function getPostByOwnProfile() {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.getItem('profile-searching');
    $.ajax({
        url: POST_URL + "profile/" + profileSearching,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data);

            if (data.length == 0) {
                $('.posts').html(`<p>Không có bài viết nào</p>`);
            } else {

                let output = '';
                $.each(data, (index, post) => {
                    output += `
                <div>
                    <div class="d-flex justify-content-between">
                        <div><strong>${post.givenName} ${post.surname}</strong></div>
                    </div>
                    
                    <p class="text-secondary">${post.createdDateTime}</p>
                    <p class="postWrittenText postWrittenText-${post.id}">${post.writtenText}</p>
                    <p>
                        <span class="mr-2">Thích: ${post.totalLike}</span>
                        <span>Bình luận: ${post.totalComment}</span>
                    </p>
                    <button class="mb-1" onclick="like(${post.id})"><i class="fa-solid fa-thumbs-up"></i></button><br>
                    `;

                    $.each(post.postComments, (index, postComment) => {
                        output += `
                        <div class="d-flex justify-content-between">
                        <p class="postComment">
                            <strong>${postComment.userProfile.givenName} ${postComment.userProfile.surname}: </strong> 
                            <span>${postComment.commentText}</span>
                        </p>
                        </div>
                    `
                    })

                    output += `</div><br><span><i class="fa-solid fa-user"></i>: <input type="text" id="commentText-${post.id}"/></span>
                                <input type="submit" id="comment" value="Bình luận" onclick="comment(${post.id})"/>
                                <hr>
                            </div>`
                });
                $('.posts').html(output);
            }
        },
        error: function (request, status, error) {
            console.log(request)
        }
    })
}

function getInfor() {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.getItem('profile-searching');
    $.ajax({
        url: PROFILE_URL + profileSearching,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            let output = '';
            output += `<p class="mb-1"><strong>Họ tên</strong> : ${data.givenName} ${data.surname}</p>
                    <div class="d-flex justify-content-between">
                    <p class="mb-1"><strong>Sinh nhật</strong>: ${data.dateOfBirth} <i class="fa-solid fa-cake-candles"></i></p>
                    <div><button onclick="follow()" class="btn btn-outline-success">Theo dõi</button>
                        <button onclick="unfollow()" class="btn btn-outline-danger">Hủy theo dõi</button>
                    </div>
                    </div>    
                    `;

            $('#userInfor').html(output);
        },
        error: function (request, status, error) {
            console.log(request)
        }
    })
}

function getFriends() {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.getItem('profile-searching');

    $.ajax({
        url: PROFILE_URL + 'friends/' + profileSearching,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data)
            if (data.length == 0) {
                $('#friends').html(`<p>Không có bạn</p>`);
            } else {
                let output = ``;
                $.each(data, (index, friend) => {
                    output += `<p><button onclick="getFriendProfile(${friend.id})" type="button" class="btn btn-outline-secondary"><i class="fa-solid fa-user mr-1"></i>${friend.givenName} ${friend.surname}</button> </p>`;
                })
                $('#friends').html(output);
            }
        },
        error: function (request, status, error) {
            console.log(request);
        }
    })
}

function follow() {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.getItem('profile-searching');

    $.ajax({
        url: FRIENDSHIP_URL + "add-friend/" + profileSearching,
        method: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            alert("Follow successfully!");
            console.log(data);
        },
        error: function (request, status, error) {
            alert(request.responseJSON.error);
        }
    })
}

function unfollow() {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.getItem('profile-searching');

    $.ajax({
        url: FRIENDSHIP_URL + "unfriend/" + profileSearching,
        method: 'DELETE',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            alert("Unfollow successfully!");
            console.log(data);
        },
        error: function (request, status, error) {
            alert(request.responseJSON.error);
        }
    })
}

function getFriendProfile(userId) {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.setItem('profile-searching', userId);

    getPostByOwnProfile();
    getFriends();
    getInfor();
}

function comment(postId) {

    let token = sessionStorage.getItem('token');
    let commentText = $("#commentText-" + postId).val();
    console.log(commentText)
    myData = {
        commentText: commentText
    }

    $.ajax({
        url: COMMENT_URL + postId,
        method: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(myData),
        success: function (data) {
            getPostByOwnProfile();
        },
        error: function (request, status, error) {

            alert(request.responseJSON[0])
        }
    })
    return false;
}

function like(postId) {
    let token = sessionStorage.getItem('token');

    $.ajax({
        url: LIKE_URL + postId,
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            getPostByOwnProfile();
        },
        error: function (request, status, error) {
            if (request.status == 400) {
                unlike(postId);
            }
        }
    })
}

function unlike(postId) {
    let token = sessionStorage.getItem('token');
    $.ajax({
        url: LIKE_URL + postId,
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            getPostByOwnProfile();
        },
        error: function (request, status, error) {
            console.log(request)
        }
    })
}

getFriends();
getInfor();
getPostByOwnProfile();