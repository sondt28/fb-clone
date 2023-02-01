const LOAD_USER_ID_URL = 'http://localhost:8080/api/v1/auth/'
const LOAD_POST_BY_ID_URL = 'http://localhost:8080/api/v1/posts/'
const POST_URL = 'http://localhost:8080/api/v1/posts/'
const SEARCH_URL = 'http://localhost:8080/api/v1/profiles/';
const LIKE_URL = 'http://localhost:8080/api/v1/likes/';
const COMMENT_URL = 'http://localhost:8080/api/v1/comments/';
const PROFILE_URL = 'http://localhost:8080/api/v1/profiles/';

$('a[href="login.html"]').click(function () {
    sessionStorage.removeItem('token');
});

$('a[href="proflie-detail.html"').click(function () {

})

$(document).ready(function () {
    let token = sessionStorage.getItem('token');

    if (token === null) {
        alert("Yêu cầu đăng nhập!")
        window.location.href = "login.html";
    }
    getPosts();
});

$("#post").click(function (e) {
    let token = sessionStorage.getItem('token');

    e.preventDefault();
    let writternText = $("#writtenText").val();

    myData = {
        writtenText: writternText
    }

    $.ajax({
        url: POST_URL,
        method: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(myData),
        success: function (data) {
            console.log(data);
            alert("Create post successfully !")
            getPosts();
        },
        error: function (request, status, error) {
            console.log(request)
        }

    })
})

$("#like").click(function (e) {
    console.log("aasdasd");
})

$('#search').click(function () {
    let searching = $('#searching').val();
    sessionStorage.setItem('searching', searching);
    window.location = 'profiles.html';

    return false;
})

function getPosts() {
    let token = sessionStorage.getItem('token');
    $.ajax({
        url: POST_URL,
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data);
            let output = '';
            $.each(data, (index, post) => {
                output += `
                <div>
                    <p><strong>${post.givenName} ${post.surname}</strong></p>
                    <p>${post.createdDateTime}</p>
                    <p>${post.writtenText}</p>
                    <p>
                        <span class="mr-2">Thích: ${post.totalLike}</span>
                        <span>Bình luận: ${post.totalComment}</span>
                    </p>
                    <button class="mb-1" onclick="like(${post.id})"><i class="fa-solid fa-thumbs-up"></i></button>
                    <button class="ml-1 mb-2 px-3" onclick="showComment(${post.id})"><i class="fa-solid fa-comment"></i></button>
                    <div id="comments-${post.id}">
                    
                    `;

                $.each(post.postComments, (index, postComment) => {
                    output += `
                        <p class="mb-1 ml-5"><strong>${postComment.userProfile.givenName} ${postComment.userProfile.surname}: </strong> 
                        <span>${postComment.commentText}</span></p>
                    `
                })

                output += `</div><br><span><i class="fa-solid fa-user"></i>: <input type="text" id="commentText-${post.id}"/></span>
                <input type="submit" id="comment" value="Bình luận" onclick="comment(${post.id})"/>
                <hr>
            </div>`
            });
            $('.posts').html(output);
        }
    })
}

function showComment(postId) {
    $("#comments-" + postId).toggle();

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
            getPosts();
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
            getPosts();
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
            getPosts();
        },
        error: function (request, status, error) {
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
        },
        error: function (request, status, error) {
            console.log(request)
        }
    })
}

getInfor();