"use strict";

$(document).ready(function () {
    $(function () {
        $.get("/api/author/").done(function (authors) {
            $("tbody").empty();
            authors.forEach(function (author) {
                $("tbody").append(`
                    <tr>
                        <th scope="row">${author.id}</th>
                        <td>${author.name}</td>
                        <td>
                            <button data-field="${author.id}" id="show" type="button" class="btn btn-warning">Показать книги</button>
                        </td>
                    </tr>
                `);
            });
        });
    });

    $("#authorTable").on("click", "#show", function (e) {
        e.preventDefault();

        const id = $(this).data("field");

        $.cookie("authorId", id);
        window.location = "authorBooks.html";
    });

    $("#authorsBookPage").after(function () {
        $.get("/api/author/" + $.cookie("authorId") + "/book")
            .done(function (books) {
                $("tbody").empty();
                books.forEach(function (book) {
                    $("tbody").append(`
                    <tr>
                        <th scope="row">${book.id}</th>
                        <td>${book.name}</td>
                        <td>${book.author.name}</td>
                        <td>${book.genre.name}</td>
                        <td>${book.publishDate}</td>
                    </tr>
                `);
                });
            });
    });
});