// global variables to save the start and end squares for promotion 
var promotionStartSquare = '';
var promotionEndSquare = '';

// global variables to set refresh times
var GAME_RETRIEVE_TIME = 3000;

// global variable to keep track of current known move index
var moveIndex = -1;

function initialize() {
	setupSquares();
	applyRoundedCorners();
	retrieveGameStatus();

	setupEnterKeyEvent();
	setInputFocus();
	refreshChatUser();
	getUsers();
	getNewMessages();
}

function setupSquares() {
	$('.square').droppable({
		drop: function(event, ui) {
			var startSquare = ui.draggable;
			var endSquare = $(this);
			if (startSquare != endSquare) {
				if (isPawn(startSquare) && isMoveFromNextToLastToLastRow(startSquare, endSquare)) {
					promotionStartSquare = startSquare.attr('id');
					promotionEndSquare = endSquare.attr('id');
					startSquare.css({ top: 0, left: 0 });
					openPromotionDialog();
				}
				else {
					sendMove(startSquare, endSquare);
				}
			}
		}
	});
}

function applyRoundedCorners() {
	$(".subpanelDivInner").corner("round 8px top").parent().corner("round 12px top");
	$(".rightSubpanelDivInner").corner("round 8px top").parent().corner("round 12px top");
}

function retrieveGame() {
	$.post("chessMultiClient.json", { "command": "retrieve_game", "gameId" : gameId },
		function(data) {
			refreshGame(data);
		}, 
		"json"
	);
}

function retrieveGameStatus() {
	$.post("chessMultiClient.json", { "command": "retrieve_game_status", "gameId" : gameId },
		function(data) {
			if (moveIndex != data.move_index) {
				retrieveGame(data);
			}
			else {
				refreshGameUser(data);
			}
			setTimeout(retrieveGameStatus, GAME_RETRIEVE_TIME);
		}, 
		"json"
	);
}

function resetGame() {
	$.post("chessMultiClient.json", { "command": "reset_game", "gameId" : gameId },
		function(data) {
			refreshGame(data);
		}, 
		"json"
	);
}

function sendMove(startSquare, endSquare) {
	var json = { "command": "move", "start_square": startSquare.attr('id'), "end_square": endSquare.attr('id'), "gameId" : gameId };
	$.post("chessMultiClient.json", json,
  		function(data){
			updateGame(data);
			startSquare.css({ top: 0, left: 0 });
  		}, 
  	"json");
}

// Refresh the names of the users, the associated color of the current user, and the ability to reset the game
function refreshGameUser(data) {
	var whitePlayerName = (data.white_player_name != null ? data.white_player_name : "White");
	var blackPlayerName = (data.black_player_name != null ? data.black_player_name : "Black");
	$("#white_player_name").html(whitePlayerName);
	$("#black_player_name").html(blackPlayerName);
	$("#playersTurn").html(generatePlayersTurnText(data.players_turn, data.player_color));
	toggleResetButton(data.player_color); 
}

// Refresh the contents of the entire game 
function refreshGame(data) {
	moveIndex = data.move_index;
	clearBoard();
	clearCapturedPieces();
	clearMoves();
   	$.each(data.pieces, function(i,item) {
   		placePiece(item.square, item.piece);
	});
   	$.each(data.captured_pieces_white, function(i, item) {
   		addCapturedPiece(item);
   	});
   	$.each(data.captured_pieces_black, function(i, item) {
   		addCapturedPiece(item);
   	});
   	addMoves(data.move_notations);
   	resetMouseCursor();
	updateTurn(data.players_turn, data.player_color);
	refreshGameUser(data);
}

// Only update the contents of the game that changed 
function updateGame(data) {
	moveIndex = data.move_index;
   	$.each(data.cleared_squares, function(i,item) {
   		clearSquare(item.square);
	});	
   	$.each(data.updated_squares, function(i,item) {
   		placePiece(item.square, item.piece);
	});
   	if (data.captured_piece != null && data.captured_piece != "null") {
   		addCapturedPiece(data.captured_piece);
   	}
   	resetMouseCursor();
   	addMove(data.players_turn, data.move_notation);
	updateTurn(data.players_turn, data.player_color);
}

function toggleResetButton(playerColor) {
	if (playerColor == "white" || playerColor == "black") {
		$("#resetButton").show();
	}
	else {
		$("#resetButton").hide();
	}
}

function updateTurn(color, playerColor) {
	if (color == "white" || color == "black") {
		$("#promotion_queen").attr('src', 'img/queen_' + color + '.png');
		$("#promotion_knight").attr('src', 'img/knight_' + color + '.png');
		$("#promotion_rook").attr('src', 'img/rook_' + color + '.png');
		$("#promotion_bishop").attr('src', 'img/bishop_' + color + '.png');
		$("#playersTurn").html(generatePlayersTurnText(color, playerColor));
		$(".square").each(function(i) {
			if ($(this).attr('src').indexOf(color) > -1 && playerColor == color) {
				enableDraggable($(this));
			}
			else {
				disableDraggable($(this));
			}
		});
	}
}

function generatePlayersTurnText(color, playerColor) {
	var playersTurnText = "";
	
	if ((color == "white" || color == "black") && color == playerColor) {
		playersTurnText = "Your turn!";
	}
	else {
		if (color == "white") {
			playersTurnText = $("#white_player_name").text();
		}
		else {
			playersTurnText = $("#black_player_name").text();
		}
		playersTurnText += "'s turn";
	}
	
	return playersTurnText;
}

function clearBoard() {
	$('.square').each(function(i) {
		clearSquareJquery($(this));
	});
}

function clearSquare(square) {
	var squareElement = document.getElementById(square);
	clearSquareJquery($(squareElement));
}

function clearSquareJquery(squareJquery) {
	squareJquery.attr('src', 'img/blank.gif');
	squareJquery.draggable('destroy');	
}

function placePiece(square, piece) {
	var squareElement = document.getElementById(square);
	squareElement.src = 'img/' + piece + '.png';
	enableDraggable($(squareElement));
}

function isBlankSquare(imageSrc) {
	return imageSrc == 'img/blank.gif';
}

function isPawn(squareJquery) {
	if (squareJquery.attr('src').indexOf('pawn') > -1) {
		return true;
	}
	else {
		return false;
	}
}

function isMoveFromNextToLastToLastRow(startSquareJquery, endSquareJquery) {
	if (startSquareJquery.attr('src').indexOf('white') > -1 && 
			startSquareJquery.attr('id').indexOf('7') > -1 &&
			endSquareJquery.attr('id').indexOf('8') > -1) {
		return true;
	}
	else if (startSquareJquery.attr('src').indexOf('black') > -1 && 
			startSquareJquery.attr('id').indexOf('2') > -1 &&
			endSquareJquery.attr('id').indexOf('1') > -1) {
		return true;
	}
	else {
		return false;
	}
}

function enableDraggable(squareJquery) {
	squareJquery.draggable({ 
		revert: 'invalid',
		start: function(event, ui) { ui.helper.css('zIndex', 9000); },
		stop: function(event, ui) { ui.helper.css('zIndex', 5000); },
		cursor: 'move'
	});
	squareJquery.draggable('enable');
	squareJquery.addClass('draggableSquare');
}

function disableDraggable(squareJquery) {
	squareJquery.draggable('destroy');
	squareJquery.removeClass('draggableSquare');
}

function openPromotionDialog() {
	$("#promotion_dialog").dialog({width: 350, height: 100, modal: true, zIndex: 10000});
	$("#promotion_dialog").dialog('open');
}

function sendPromotion(pieceType) {
	$("#promotion_dialog").dialog('close');
	$.post("chessMultiClient.json", { "command": "promote", "start_square": promotionStartSquare, "end_square": promotionEndSquare, "piece_type": pieceType, "gameId" : gameId },
		function(data) {
			updateGame(data);
			var startSquare = document.getElementById(promotionStartSquare);
			$(startSquare).css({ top: 0, left: 0 });
		}, 
		"json"
	);
}

function addCapturedPiece(pieceString) {
	var imageString = "<img src='img/" + pieceString + "_small.png' />";
	var color = pieceString.substring(pieceString.length - 5);
	if (color == 'white') {
		$("#capturedPiecesWhite").append(imageString);
	}
	else if (color == 'black') {
		$("#capturedPiecesBlack").append(imageString);
	}
}

function clearCapturedPieces() {
	$("#capturedPiecesWhite").html("");
	$("#capturedPiecesBlack").html("");
}

function clearMoves() {
	$("#movesTable").find("tr:gt(0)").remove();
}

function resetMouseCursor() {
	$("body").css("cursor", "default");
}

function addMoves(moves) {
	for (var i = 0; i < moves.length; i++) {
		if (i % 2 == 0) {
			addMove("black", moves[i]);
		}
		else {
			addMove("white", moves[i]);
		}
	}
}

function addMove(nextMoveColor, move) {
	if (nextMoveColor == 'black' && move != null) {
		// adding a white move
		var lastMoveIndex = parseInt($('#movesTable tr:last td:first').html());
		var nextMoveIndex = 1;
		if (lastMoveIndex != null && !isNaN(lastMoveIndex)){
			nextMoveIndex = lastMoveIndex + 1;
		}
		$('#movesTable tr:last').after('<tr><td class="movesTableColumn1">' + nextMoveIndex + '</td><td class="movesTableColumn2">' + move + '</td><td class="movesTableColumn3"></td></tr>');
		$("#movesTableDiv").attr("scrollTop", $("#movesTableDiv").attr("scrollHeight"));
	}
	else if (nextMoveColor == 'white' && move != null) {
		// adding a black move
		$('#movesTable tr:last td:last').html(move);
		$("#movesTableDiv").attr("scrollTop", $("#movesTableDiv").attr("scrollHeight"));
	}
}

function openResetDialog() {
	$("#reset_dialog").dialog({width: 350, height: 100, modal: true, zIndex: 10000});
	$("#reset_dialog").dialog('open');
}

function closeResetDialogAndResetGame() {
	closeResetDialog();
	resetGame();
}

function closeResetDialog() {
	$("#reset_dialog").dialog('close');
}