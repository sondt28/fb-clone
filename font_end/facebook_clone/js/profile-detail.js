const POST_URL = 'http://localhost:8080/api/v1/posts/';
const PROFILE_URL = 'http://localhost:8080/api/v1/profiles/';
const COMMENT_URL = 'http://localhost:8080/api/v1/comments/';
const LIKE_URL = 'http://localhost:8080/api/v1/likes/';

$('a[href="index.html"]').click(function () {
    sessionStorage.removeItem('givenName');
    sessionStorage.removeItem('surname');
    sessionStorage.removeItem('dateOfBirth');
    sessionStorage.removeItem('searching');
    sessionStorage.removeItem('userId');
})

$('a[href="login.html"]').click(function () {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('givenName');
    sessionStorage.removeItem('surname');
    sessionStorage.removeItem('dateOfBirth');
    sessionStorage.removeItem('searching');
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
    let ownProfileId = sessionStorage.getItem('ownProfileId');

    $.ajax({
        url: POST_URL + 'profile/' + ownProfileId,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            if (data.length == 0) {
                $('.posts').html(`<p>Không có bài viết nào</p>`);
            } else {

                let output = '';
                $.each(data, (index, post) => {
                    output += `
                <div>
                    <div class="d-flex justify-content-between">
                        <div><strong>${post.givenName} ${post.surname}</strong></div>
                        <div>
                            <button type="button" class="btn btn-outline-success" onclick="showUpdatePost(${post.id})"><i class="fa-solid fa-pencil"></i></button>
                            <button type="button" class="btn btn-outline-danger" onclick="deletePost(${post.id})"><i class="fa-sharp fa-solid fa-x"></i></button>
                        </div>
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
                        <button type="button" class="btn btn-outline-danger" onclick="deleteComment(${postComment.id})"><i class="fa-sharp fa-solid fa-x"></i></button>
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
        }
    })
}

function getFriends() {
    let token = sessionStorage.getItem('token');
    let ownProfileId = sessionStorage.getItem('ownProfileId');

    $.ajax({
        url: PROFILE_URL + 'friends/' + ownProfileId,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            if (data.length == 0) {
                $('#friends').html(`<p>Không có bạn</p>`);
            } else {
                let output = ``;
                $.each(data, (index, friend) => {
                    output += `<p><button onclick="getFriendProfile(${friend.id})" type="button" class="btn btn-outline-secondary">
                                    <i class="fa-solid fa-user mr-1"></i>${friend.givenName} ${friend.surname}</button> 
                                </p>`;
                })
                $('#friends').html(output);
            }
        },
        error: function (request, status, error) {
            ;
        }
    })
}

function getInfor() {
    let token = sessionStorage.getItem('token');

    $.ajax({
        url: PROFILE_URL,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {

            sessionStorage.setItem('ownProfileId', data.id);
            sessionStorage.setItem('givenName', data.givenName);
            sessionStorage.setItem('surname', data.surname);
            sessionStorage.setItem('dateOfBirth', data.dateOfBirth);

            console.log(data)
            let output = '';
            output += `<p class="mb-1"><strong>Họ tên</strong> : ${data.givenName} ${data.surname}</p>

                        <p class="mb-1"><strong>Sinh nhật</strong>: ${data.dateOfBirth}</p>`;
            $('#userInfor').html(output);
        }
    })
}

function updateUserShowForm() {
    let ownProfileId = sessionStorage.getItem('ownProfileId');
    let email = sessionStorage.getItem('email');
    let givenname = sessionStorage.getItem('givenName');
    let surname = sessionStorage.getItem('surname');
    let dateOfBirth = sessionStorage.getItem('dateOfBirth');

    $('.updateUser').css({ display: "block" });

    let output = '';
    output = `
            <label>Tên</label>
            <input type="text" id="givenName" value="${givenname}" />
            <br>
            <label>Họ</label>
            <input type="text" id="surname" value="${surname}" />
            <br>
            <label>Ngày sinh</label>
            <input type="date" id="dateOfBirth" value="${dateOfBirth}" />
            <br>
            <input type="submit" value="Cập nhật" onclick="updateUser(${ownProfileId})"/>
    `;

    $('.updateUser').html(output);
}

function updateUser(userId) {
    let token = sessionStorage.getItem('token');

    let givenName = $('#givenName').val();
    let surname = $('#surname').val();
    let dateOfBirth = $('#dateOfBirth').val();

    myData = {
        givenName: givenName,
        surname: surname,
        dateOfBirth: dateOfBirth
    }

    $.ajax({
        url: PROFILE_URL + userId,
        method: 'PUT',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(myData),
        success: function (data) {
            $('.updateUser').css({ display: "none" });
            getInfor();

        },
        error: function (request, status, error) {
            console.log(error);
        }
    })
}

function deletePost(postId) {
    if (confirm("Are you sure to delete this post?") == true) {
        let token = sessionStorage.getItem('token');

        $.ajax({
            url: POST_URL + postId,
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (data) {
                getPostByOwnProfile();
            },
            error: function (request, status, error) {
                console.log(error);
            }
        })
    }
}

function deleteComment(postCommentId) {
    if (confirm("Are you sure to delete this comment?") == true) {
        let token = sessionStorage.getItem('token');

        $.ajax({
            url: COMMENT_URL + postCommentId,
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function (data) {
                getPostByOwnProfile();
            },
            error: function (request, status, error) {
                console.log(error);
            }
        })
    }
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
        }
    })
}

function showUpdatePost(postId) {
    let text = $('.postWrittenText-' + postId).html();

    $('.postWrittenText-' + postId).replaceWith($(`<input type="text" id="updateText" value="${text}" />
                                        <button onclick="updatePost(${postId})">Chỉnh sửa</button>`));
}

function updatePost(postId) {
    let writternText = $('#updateText').val();
    let token = sessionStorage.getItem('token');

    myData = {
        writtenText: writternText
    }

    $.ajax({
        url: POST_URL + postId,
        method: 'PUT',
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

}

function getFriendProfile(userId) {
    let token = sessionStorage.getItem('token');
    let profileSearching = sessionStorage.setItem('profile-searching', userId);

    window.location.href = "profile-searching.html";

}

getInfor();
getFriends();
getPostByOwnProfile();