"use strict";

$(document).ready(function () {
    $("#mainBook").after(function () {
        loadAllBooks()
    });

    $("#inputButton").click(function () {
        const bookId = $(".form-control").val();

        if (bookId !== "") {
            $.get("/api/book/" + bookId).done(function (book) {
                $("tbody").empty();
                $("tbody").append(`
                    <tr>
                        <th scope="row">${book.id}</th>
                        <td>${book.name}</td>
                        <td>${book.author.name}</td>
                        <td>${book.genre.name}</td>
                        <td>${book.publishDate}</td>
                        <td>
                            <button data-field="${book.id}" id="edit" type="button" class="btn btn-warning">Изменить</button>
                        </td>
                        <td>
                            <button data-field="${book.id}" id="delete" type="button" class="btn btn-danger">Удалить</button>
                        </td>
                    </tr>
                `);
            });
        } else {
            loadAllBooks();
        }
    });

    $("#sendButton").click(function () {
        let str = $("#form").serialize();
        $.post("/api/book/", str);

        window.location = "books.html";
    });

    $("#editPage").on("click", "#saveButton", function () {
        let str = $("#form").serialize();
        $.ajax({
            url: "/api/book",
            type: "PUT",
            data: str,
            success: function () {
                loadAllBooks();
            }
        });
        window.location = "books.html";
    });

    $("#booksTable").on("click", "#delete", function (e) {
        e.preventDefault();

        let id = $(this).data("field");

        $.ajax({
            url: "/api/book/" + id,
            type: "DELETE",
            success: function () {
                loadAllBooks();
            }
        });
    });

    $("#booksTable").on("click", "#edit", function (e) {
        e.preventDefault();

        const id = $(this).data("field");

        $.cookie("bookId", id);
        window.location = "bookEdit.html";
    });

    $("#editPage").after(function () {
        $.get("/api/book/" + $.cookie("bookId"))
            .done(function (book) {
                $("div").append(`
                     <form id="form" class="form-group">
                        <h1>Книга:</h1>
                
                        <div class="form-group">
                            <label for="id-input">ID:</label>
                            <input class="form-control" id="id-input" name="id" readonly="readonly" type="text"
                                   value="${book.id}"/>
                        </div>
                
                        <div class="form-group">
                            <label for="holder-input">Название книги:</label>
                            <input class="form-control" id="holder-input" name="name" type="text"
                                   value="${book.name}"/>
                        </div>
                
                        <div class="form-group">
                            <label for="author-input">Автор:</label>
                            <input class="form-control" id="author-input" name="author" type="text"
                                   value="${book.author.name}"/>
                        </div>
                
                        <div class="form-group">
                            <label for="genre-input">Жанр:</label>
                            <input class="form-control" id="genre-input" name="genre" type="text"
                                   value="${book.genre.name}"/>
                        </div>
                
                        <div class="form-group">
                            <label for="year-input">Год издания:</label>
                            <input class="form-control" id="year-input" name="publishDate" type="text"
                                   value="${book.publishDate}"/>
                        </div>
                
                        <input name="authorId" hidden="true" value="${book.author.id}">
                        <input name="genreId" hidden="true" value="${book.genre.id}">
                        <input class="btn btn-primary" type="button" id="saveButton" value="Сохранить книгу"/>
                    </form>
                `);
            });
    });

    function loadAllBooks() {
        $.get("/api/book").done(function (books) {
            $("tbody").empty();
            books.forEach(function (book) {
                $("tbody").append(`
                    <tr>
                        <th scope="row">${book.id}</th>
                        <td>${book.name}</td>
                        <td>${book.author.name}</td>
                        <td>${book.genre.name}</td>
                        <td>${book.publishDate}</td>
                        <td>
                            <button data-field="${book.id}" id="edit" type="button" class="btn btn-warning">Изменить</button>
                        </td>
                        <td>
                            <button data-field="${book.id}" id="delete" type="button" class="btn btn-danger">Удалить</button>
                        </td>
                    </tr>
                `);
            });
        });
    }
});