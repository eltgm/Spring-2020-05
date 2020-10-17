'use strict'

postModule.controller("PostController",
    function PostController($scope, postService, $window) {

        $scope.createPost = function (post, postForm) {
            if (postForm.$valid) {
                let file = $scope.photos;
                let news = {
                    photos: file,
                    text: $scope.text
                }
                postService.sendNews(news);
            }
        };
    });