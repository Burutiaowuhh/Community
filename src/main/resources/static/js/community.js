/**
 * 提交一级评论内容
 */

function post() {
    var questionId = $("#questionId").val();
    var commentcontent = $("#commentcontent").val();
    console.log(questionId);
    target(questionId, 1, commentcontent);
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var replaycontent = $("#replay-" + commentId).val();
    console.log(commentId);
    target(commentId, 2, replaycontent);
}

function target(targetId, type, commentcontent) {

    if (!commentcontent) {
        alert("提交内容不能为空");
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": commentcontent,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                // $("#replaysection").hide();    //回复部分隐藏
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        //打开一个新页面
                        window.open("https://github.com/login/oauth/authorize?client_id=3daee8956728aea3117c&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}


/**
 * 展开二级评论
 * @param e
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {//折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {

        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");

        } else {
            console.log(subCommentContainer);
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data.data.reverse(), function (index, comment) {

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h4/>",{
                        "class":"media-heading font-icon-style",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu1"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('HH:mm YYYY-MM-DD')
                    })));

                    var imgElement = $("<img/>", {
                        "class": "media-object img-circle",
                        "src":comment.user.avatarUrl
                    });

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append(imgElement);

                    var mediaElement = $("<div/>", {
                        "class": "media border"
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    subCommentContainer.prepend(mediaElement);
                });
            });

            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }

    }
    console.log(id);
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {

    var tagvalue = e.getAttribute("data-selectTag");

    var previous = $("#tag").val();
    if(previous.indexOf(tagvalue)==-1) {
        if (previous) {
            $("#tag").val(previous + ',' + tagvalue);
        } else {
            $("#tag").val(tagvalue);
        }
    }
}