<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Talk</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap-theme.min.css">
    <script type="text/javascript" src="/resources/jQuery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var downLoadUserData;
            var currentChatWithData;
            var name = 56756;//prompt("请输入你的名称", "");
            if (name != undefined && name != "") {
                $.ajax({
                    type: "POST",
                    url: "/user/saveAndCreateConversation",
                    dataType: 'json',
                    data: {'name': name},
                    async: true,
                    success: function (response) {
                    },
                    error: function (response) {

                    },
                });
            }

            var loadUserRealtions = function (name) {
                $.ajax({
                    type: "GET",
                    url: "/user/relations/list",
                    dataType: 'json',
                    data: {'name': name},
                    async: true,
                    success: function (response) {
                        applayForView(response);
                        downLoadUserData = response;
                    }
                });
            };

            function sendWhereClick(self, target) {
                alert(222)
            };
            loadUserRealtions(name);
            var applayForView = function (userRelations) {
                var usersInfoDoc = $("#usersInfo");
                //clear
                usersInfoDoc.html("");
                for (index in userRelations) {
                    var innerHtmlText = "";
                    var userRelation = userRelations[index];
                    var friend = userRelation.friend;
                    var self = userRelation.self;
                    var friendConversation = friend.conversation;
                    var selfConversation = self.conversation;
                    innerHtmlText += "<div id='" + friendConversation.queueKey + "'>";
                    innerHtmlText += friend.name;
                    innerHtmlText += "</div></br>";
                    usersInfoDoc.append(innerHtmlText);
                    $("#" + friendConversation.queueKey).on("click", {'self': self, 'friend': friend}, function (data) {
                        var friend = data.data.friend;
                        var self = data.data.self;
                        var text = "MessageTo:";
                        text += friend.name;
                        text += ",CoversationId:";
                        text += friend.conversation.queueKey;
                        $("#chatWithWho").html(text);
                        currentChatWithData = data.data;
                    });
                }
            };
            var submitMessage = function (self, friend, messageInfo) {
                $.ajax({
                    type: "POST",
                    url: "/rabbit/send",
                    dataType: 'json',
                    data: {'selfID': self.id, 'targetID': friend.id, 'messageInfo': messageInfo},
                    async: true,
                    success: function (response) {
                    }
                });
            };
            $("#submitMessage").submit(function () {
                var data = $(this).serializeArray();
                var sendUser = currentChatWithData;
                submitMessage(sendUser.self, sendUser.friend, data[1].value);
                return false;
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-2" id="usersInfo" style="">
        </div>
        <div class="col-sm-10">
            <form role="form" id="submitMessage">
                <div class="form-group">
                    <label for="message" id="chatWithWho">Message</label>
                    <textarea class="form-control" rows="12" name="messageInfos"></textarea>
                </div>
                <div class="form-group">
                    <label for="sendMessage">sendMessage</label>
                    <textarea class="form-control" rows="3" name="messageInfo"></textarea>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default pull-right" id="sendMessageToMQ">SEND</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
