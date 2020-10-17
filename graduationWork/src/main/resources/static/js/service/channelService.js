'use strict'

let channelServiceFn = function ($http) {
    this.getChannels = function () {
        return $http({
            method: "GET",
            url: "http://localhost:8080/api/channel"
        });
    };

    this.deleteChannel = function (id) {
        $http({
            method: "DELETE",
            url: "http://localhost:8080/api/channel?id=" + id
        });
    };

    this.createChannel = function (channel) {
        $http.post("http://localhost:8080/api/channel", channel);
    };
}

channelModule.service("channelService", channelServiceFn);