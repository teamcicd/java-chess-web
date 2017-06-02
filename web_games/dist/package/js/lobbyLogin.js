function initialize() {
	applyRoundedCorners();
	setInputFocus();
}

function applyRoundedCorners() {
	$(".leftSubpanelDivInner").corner("round 8px top").parent().corner("round 12px top");
}

function setInputFocus() {
	$("#usernameInput").focus();
	$("#usernameInput").select();
}

function submitLoginForm() {
	$("#loginForm").submit();
}