function doChangeFileValue(inputId, fillId) {
	var inputId = 'input[id=' + inputId + ']';
	var fillId = '#' + fillId;
	$(inputId).change(function() {
		$(fillId).val($(this).val());
	});
}

