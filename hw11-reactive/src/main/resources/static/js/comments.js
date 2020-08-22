"use strict";

$(document).ready(function () {
    $(function () {
        getComments("");
    });

    $("#inputButton").click(function () {
        const bookId = $(".form-control").val();
        getComments(bookId);
    });

    function getComments(bookId) {
        $.get("/api/comment/" + bookId).done(function (comments) {
            $("tbody").empty();
            comments.forEach(function (comment) {
                $("tbody").append(`
                    <tr>
                    <th scope="row">${comment.id}</th>
                        <td>${comment.text}</td>
                        <td>${comment.userName}</td>
                    </tr>
                `);
            });
        });
    }
});