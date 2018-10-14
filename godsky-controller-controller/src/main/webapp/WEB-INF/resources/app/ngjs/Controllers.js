var ControllerAppController = angular.module('ControllerAppModuleController', []);
ControllerAppController.controller('menuController', [
    '$scope',
    '$http',
    '$rootScope',
    function ($scope, $http, $rootScope) {
        $("#menu li").click(function () {
            if ($scope.lastClick != undefined) {
                $scope.lastClick.removeClass("active");
                $scope.lastClick = undefined;
            }
            $(this).addClass("active");
            $scope.lastClick = $(this);
        });
    }]);
ControllerAppController.controller('systemController', [
    '$scope',
    '$http',
    '$rootScope',
    function ($scope, $http, $rootScope) {
        $http({
            method: 'GET',
            url: '/system/info'
        }).then(function successCallback(response) {
            $scope.systemInfoData = response.data;
        }, function errorCallback(response) {
        });
        $scope.recoverSystem = function () {
            var confirm = window.confirm("是否重置系统?");
            if (confirm == true) {
                alert("系统重置中...")
                $.ajax({
                    type: "GET",
                    url: "/system/recovery",
                    async: false,
                    success: function (response) {
                        if (response.error != undefined) {
                            $('#exceptionMod').modal({backdrop: 'static'});
                        }
                    }
                });
            }
            else {
                console.log("你取消了系统重置任务...");
            }
        }
        $scope.restartSystem = function () {
            var confirm = window.confirm("是否重启系统?");
            if (confirm == true) {
                alert("系统重启中...")
                $.ajax({
                    type: "GET",
                    url: "/system/restart",
                    async: false,
                    success: function (response) {
                        if (response.error != undefined) {
                            $('#exceptionMod').modal({backdrop: 'static'});
                        }
                    }
                });
            }
            else {
                console.log("你取消了系统重启任务...");
            }
        }
    }]);
ControllerAppController.controller('logController', [
    '$scope',
    '$http',
    '$rootScope',
    function ($scope, $http, $rootScope) {
        setInterval(function () {
            $("#logArea").scrollTop(9999999999);
            $http({
                method: 'GET',
                url: '/log/info'
            }).then(function successCallback(response) {
                $scope.logInfoData = response.data;
            }, function errorCallback(response) {
            });
        }, 3000)
    }]);
ControllerAppController.controller('devicesController', [
    '$scope',
    '$http',
    '$rootScope',
    function ($scope, $http, $rootScope) {
        var data = "";
        var ops = "";
        var dataSyn = function (asynced) {
            $.ajax({
                type: "GET",
                url: "/controller/list",
                dataType: "json",
                async: asynced,//异步还是同步
                success: function (response) {
                    if (response.error != undefined) {
                        $('#exceptionMod').modal({backdrop: 'static'});
                    }
                    data = response;
                    for (index in data.lightList) {
                        var lightId = data.lightList[index].id;
                        var switchId = data.lightList[index].aSwitch.id;
                        var doc = $("#" + lightId + "-" + switchId);
                        if (data.lightList[index].deviceStatus > 0) {
                            $(doc).text("[打开][引脚值][" + data.lightList[index].deviceStatus + "]");
                            $(doc).prop("lightness", data.lightList[index].deviceStatus);
                        } else {
                            $(doc).text("[关闭]");
                            $(doc).prop("lightness", 0);
                        }

                    }
                }
            });
        };
        dataSyn(false);
        $scope.devicesInfoData = data;
        $scope.freshFlag = false;
        $scope.autoFresh = function () {
            if (!$scope.freshFlag) {
                $scope.freshFlag = setInterval(function () {
                    dataSyn(true);
                }, 1000);
            } else {
                clearInterval($scope.freshFlag);
                $scope.freshFlag = false;
            }

        };
        $scope.autoFresh();
        // $.each(data.system.devices.device, function (index, value, array) {
        //     ops += "<option value=" + value.switch.id + ">" + value.switch.id + "," + value.switch.name + "</option>";
        // })
        $('#deviceSelect').html(ops);
        $scope.toggleDevice = function (data) {
            $.ajax({
                type: "GET",
                url: "/devices/toggle",
                dataType: "json",
                data: {pwm: data.pwm, name: data.deviceName, footer: data.deviceFooter},
                async: true,
                success: function (response) {
                    if (response.error != undefined) {
                        $('#exceptionMod').modal({backdrop: 'static'});
                    }
                }
            });
        };
        $scope.do = function (data) {
            var lightness = $("#" + data.id + "-" + data.aSwitch.id).prop("lightness");
            $("#lightness").html('<label>亮度</label> <input style="width:210px" id="slide" type="text" class="span2" value="" data-slider-min="0" data-slider-max="1023" data-slider-step="1" data-slider-value="0" data-slider-selection="after">');
            $("#slide").slider();
            $("#slide").slider('setValue', lightness);
            if (data.pwm) {
                $("#slide").on('change', function (e) {
                    console.info(e.value.oldValue + '--' + e.value.newValue);
                    $.ajax({
                        type: "GET",
                        url: "/devices/point",
                        dataType: "json",
                        data: {
                            pwm: data.pwm,
                            name: data.deviceName,
                            footer: data.deviceFooter,
                            lightness: e.value.newValue
                        },
                        async: true,
                        success: function (response) {
                            if (response.error != undefined) {
                                $('#exceptionMod').modal();
                                $('#exMsg').html(response.error);
                            }
                        },
                        error: function (response) {
                        }
                    });
                });
            } else {
                $("#slide").slider("disable");
            }
            $('#myModal').modal({backdrop: 'static'});
            $('#myModal #myModalLabel').text("设备,名称" + data.deviceName + "," + "引脚" + data.deviceFooter);
            $('#deviceSelect').selectpicker('val', data.aSwitch.id);
            $('#deviceSelect').prop('disabled', true)
        }

    }])
;

ControllerAppController.controller('configController', [
    '$scope',
    '$http',
    '$rootScope',
    function ($scope, $http, $rootScope) {
        $.ajax({
            type: "GET",
            url: "/system/server/ip",
            dataType: "text",
            async: false,
            success: function (response) {
                if (response.error != undefined) {
                    $('#exceptionMod').modal({backdrop: 'static'});
                }
                $scope.serverInfoDataIp = response;
            }
        });
        $.ajax({
            type: "GET",
            url: "/system/server/port",
            dataType: "text",
            async: false,
            success: function (response) {
                if (response.error != undefined) {
                    $('#exceptionMod').modal({backdrop: 'static'});
                }
                $scope.serverInfoDataPort = response;
            }
        });
        $.ajax({
            type: "GET",
            url: "/system/server/status",
            dataType: "text",
            async: false,
            success: function (response) {
                if (response.error != undefined) {
                    $('#exceptionMod').modal({backdrop: 'static'});
                }
                $scope.serverInfoDataStatus = response;
            }
        });
        $.ajax({
            type: "GET",
            url: "/system/server",
            dataType: "text",
            async: false,
            success: function (response) {
                if (response.error != undefined) {
                    $('#exceptionMod').modal({backdrop: 'static'});
                }
                $scope.serverInfoData = response;
            }
        });
        $scope.submitData = function () {
            var data = $("#properties").val();
            $.ajax({
                type: "POST",
                url: "/system/server",
                dataType: "json",
                data: {properties: data},
                async: false,
                success: function (response) {
                    if (response.error != undefined) {
                        $('#exceptionMod').modal({backdrop: 'static'});
                    }
                }
            });
        }
    }]);