$(document).ready(function() {
	$("#new-attachment-button").on("click", function() {
		var newAttachment = $(".form-inline", "#attachment-template").clone();
		$("#attachment-input-container").append(newAttachment);
		$("button.remove-attachment", newAttachment).on("click", function() {
			$(this).parent().remove();
		});
	});
});