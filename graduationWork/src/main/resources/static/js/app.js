"use strict";

let channelModule = angular.module("channelModule", []);
let postModule = angular.module("postModule", []);

postModule.directive("fileModel", ["$parse", function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            let model = $parse(attrs.fileModel);
            let modelSetter = model.assign;

            element.bind("change", function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files);
                });
            });
        }
    };
}]);