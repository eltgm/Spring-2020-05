'use strict'

let postServiceFn = function ($http) {
    this.sendNews = function (news) {
        let payload = new FormData();
        payload.append("text", news.text);

        if (news.photos !== undefined) {
            Array.from(news.photos).forEach(function (item) {
                payload.append("photos", item);
            });
        }

        $http.post("http://localhost:8080/api/news", payload, {
            headers: {'Content-Type': undefined}
        });
    };
}

postModule.service("postService", postServiceFn);