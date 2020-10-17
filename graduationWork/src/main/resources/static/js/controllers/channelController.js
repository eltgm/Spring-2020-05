'use strict'

channelModule.controller("ChannelController",
    function ChannelController($scope, channelService, $window) {

        $scope.init = function () {
            channelService.getChannels()
                .then(function successCallback(response) {
                    $scope.channels = response.data.sort(sort);
                }, function errorCallback(response) {
                    console.log(response);
                });
        }

        $scope.createChannel = function (channelDto, channelForm) {
            if (channelForm.$valid) {
                if (channelDto.messengerType !== null && channelDto.chatId !== null) {
                    let channel = {
                        messenger: channelDto.messengerType,
                        chatId: channelDto.chatId
                    };
                    channelService.createChannel(channel);
                    $scope.channels.push(channel);
                    $scope.channels.sort(sort);
                }
            }
        }

        $scope.channelDelete = function (id, index) {
            channelService.deleteChannel(id);
            $scope.channels.splice(index, 1);
        }

        function sort(a, b) {
            let messengerA = a.messenger.toLowerCase(), messengerB = b.messenger.toLowerCase();
            if (messengerA < messengerB)
                return -1;
            if (messengerA > messengerB)
                return 1;
            return 0;
        }
    });