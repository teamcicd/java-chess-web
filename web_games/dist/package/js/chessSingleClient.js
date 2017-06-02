// global variables to save the start and end squares for promotion 
var promotionStartSquare = '';
var promotionEndSquare = '';

function initialize() {
	setupSquares();
	applyRoundedCorners();
	retrieveGame();
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
	$.post("chessSingleClient.json", { "command": "retrieve_game" },
		function(data) {
			refreshGame(data);
		}, 
		"json"
	);
}

function resetGame() {
	$.post("chessSingleClient.json", { "command": "reset_game" },
		function(data) {
			refreshGame(data);
		}, 
		"json"
	);
}

function sendMove(startSquare, endSquare) {
	var json = { "command": "move", "start_square": startSquare.attr('id'), "end_square": endSquare.attr('id') };
	$.post("chessSingleClient.json", json,
  		function(data){
			updateGame(data);
			startSquare.css({ top: 0, left: 0 });
  		}, 
  	"json");
}

// Refresh the contents of the entire game 
function refreshGame(data) {
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
	updateTurn(data.players_turn);
}

// Only update the contents of the game that changed 
function updateGame(data) {
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
	updateTurn(data.players_turn);
}

function updateTurn(color) {
	if (color == "white" || color == "black") {
		$("#promotion_queen").attr('src', 'img/queen_' + color + '.png');
		$("#promotion_knight").attr('src', 'img/knight_' + color + '.png');
		$("#promotion_rook").attr('src', 'img/rook_' + color + '.png');
		$("#promotion_bishop").attr('src', 'img/bishop_' + color + '.png');
		$("#playersTurn").html(generatePlayersTurnText(color));
		$(".square").each(function(i) {
			if ($(this).attr('src').indexOf(color) > -1) {
				enableDraggable($(this));
			}
			else {
				disableDraggable($(this));
			}
		});
	}
}

function generatePlayersTurnText(color) {
	var playersTurnText = "";
	if (color == "white") {
		playersTurnText = $("#white_name").text();
	}
	else {
		playersTurnText = $("#black_name").text();
	}
	
	return playersTurnText + "'s turn";
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
	$.post("chessSingleClient.json", { "command": "promote", "start_square": promotionStartSquare, "end_square": promotionEndSquare, "piece_type": pieceType },
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