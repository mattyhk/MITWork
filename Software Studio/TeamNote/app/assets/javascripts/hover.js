$(function() {
	console.log("script is running");
	$(".showButton").each (function() {

			var sB = $(this);

			sB.hover( function () {

				// var goTo = sB.attr('href'); 
				// console.log(goTo);
				request = $.ajax({
					url: "notes/10",
					dataType: 'json',
					type: "get"

				});

				request.done(function(response){
					console.log(response);
					var contentDiv = $('<div id="preview"></div>');
					$("#previewBox").append(contentDiv);
					$(contentDiv).append(response.content)
				});
			
			}, function () {
				$("#preview").remove();
			});	


	});				
});
