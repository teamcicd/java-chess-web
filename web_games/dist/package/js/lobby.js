// global variables to set refresh times
var CHAT_RETRIEVE_TIME = 1500;
var USER_RETRIEVE_TIME = 5000;
var USER_REFRESH_TIME = 2500;
var GAME_REFRESH_TIME = 10000;

//global variable to keep track of current known message index
var messageIndex = 0;

function initialize() {
	applyRoundedCorners();
	setupEnterKeyEvent();
	setInputFocus();
	refreshUser();
	getUsers();
	getNewMessages();
	refreshGameStatuses();
	refreshPreviewImages();
}

function applyRoundedCorners() {
	$(".subpanelDivInner").corner("round 8px top").parent().corner("round 12px top");
	$(".leftSubpanelDivInner").corner("round 8px top").parent().corner("round 12px top");
}

function setupEnterKeyEvent() {
	$("#chatInput").keyup(function(e) {
		if (e.keyCode == 13) {
			submitChatInput();
		}
	});
}

function setInputFocus() {
	$("#chatInput").focus();
}

function submitChatInput() {
	var chatInputJquery = $("#chatInput");
	var messageText = chatInputJquery.attr("value");
	chatInputJquery.attr("value", "");

	sendMessage(messageText);
	
	setInputFocus();
}

function sendMessage(messageText) {
	var json = { "command": "add_message", "message": messageText, "message_index": messageIndex };
	$.post("lobby.json", json,
  		function(data){
			updateChatOutput(data.messages);
			messageIndex = data.message_index;
  		},
  	"json");
}

function refreshUser() {
	var json = { "command": "refresh_user" };
	$.post("lobby.json", json,
  		function(data){
			setTimeout(refreshUser, USER_REFRESH_TIME);
  		},
  	"json");
}

function refreshGameStatuses() {
	$(".gameHeader").each (
		function(index) {
			var gameId = $(this).attr("id").substring(10); // remove gameHeader to get the gameId
			$.post("chessMultiClient.json", { "command": "retrieve_game_status", "gameId" : gameId },
				function(data) {
					refreshGameStatus(data, gameId);
				}, 
				"json"
			);
		}
	);
	setTimeout(refreshGameStatuses, GAME_REFRESH_TIME);
}

function refreshGameStatus(data, gameId) {
	$("#gameDescription" + gameId).html(data.description);
	
	if (data.white_player_name != null) {
		$("#playWhite" + gameId).hide();
	}
	else {
		$("#playWhite" + gameId).show();	
	}
	
	if (data.black_player_name != null) {
		$("#playBlack" + gameId).hide();
	}
	else {
		$("#playBlack" + gameId).show();	
	}
	
	if (data.white_player_name == null || data.black_player_name == null) {
		$("#watchGame" + gameId).hide();
	}
	else {
		$("#watchGame" + gameId).show();		
	}
}

function refreshPreviewImages() {
	$(".previewImage").each (
		function(index) {
			var src = $(this).attr("src");
			var timestampIndex = src.indexOf("&ts=");
			if (timestampIndex > 0) {
				src = src.substring(0, timestampIndex);
			}
			src = src + "&ts=" + new Date().getTime();				
			$(this).attr("src", src);
		}
	);
	setTimeout(refreshPreviewImages, GAME_REFRESH_TIME);
}

function getUsers() {
	var json = { "command": "retrieve_users" };
	$.post("lobby.json", json,
  		function(data){
			if (data.users) {
				updateUserList(data.users);
			}
			setTimeout(getUsers, USER_RETRIEVE_TIME);
  		},
  	"json");
}

function getNewMessages() {
	var json = { "command": "retrieve_messages", "message_index": messageIndex };
	$.post("lobby.json", json,
  		function(data){
			if (data.messages) {
				updateChatOutput(data.messages);
			}
			messageIndex = data.message_index;
			setTimeout(getNewMessages, CHAT_RETRIEVE_TIME);
  		},
  	"json");
}

function updateUserList(users) {
	var userListJquery = $("#userList");
	
	var outputText = "";
	for (var i = 0; i < users.length; i++) {
		outputText += users[i] + "<br />";
	}

	userListJquery.html(outputText);
}

function updateChatOutput(messages) {
	var chatInputJquery = $("#chatInput");
	var chatOutputJquery = $("#chatOutput");

	for (var i = 0; i < messages.length; i++) {
		var outputText = messages[i] + "<br/>";
		chatOutputJquery.append(outputText);
	}
	
	chatOutputJquery.attr("scrollTop", chatOutputJquery.attr("scrollHeight"));
}