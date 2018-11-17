$(document).ready(function() {
	$("#new-column-button").on("click", function() {
		var newAttachment = $(".form-group", "#column-template").clone();
		$("#column-input-container").append(newAttachment);
		$("button.remove-column", newAttachment).on("click", function() {
			$(this).parent().remove();
		});
	});
});