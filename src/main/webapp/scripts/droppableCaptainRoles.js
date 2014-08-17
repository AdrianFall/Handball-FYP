
	$('.droppableCaptainRole').droppable({
		drop : handleCaptainRoleUIDropEvent
	});
	
	// initialize an array
	// TODO populate with the TODO obtained data with current captains in the team (if any)
	var captainsList = ["","","",""];
	
	
	function handleCaptainRoleUIDropEvent(event, ui) {

		
		// Split the id of the droppable object
		var splitDroppableId = this.id.split("captain");
		var droppableIndex = splitDroppableId[1];
		
		
		$('#' + this.id).fadeOut(10);
		
		var draggable = ui.draggable;
		var draggableAttribute = draggable.attr('id');
		
		var splitDraggableAttribute = draggableAttribute.split("tr");
		var draggableAttributeType = splitDraggableAttribute[0];
		var draggableAttributeIndex = splitDraggableAttribute[1];
		
	    var draggedPlayerNumber = $('#' + draggableAttribute + ' div div').html();
	    var draggedPlayerName = $('#' + draggableAttributeType + 'name' + draggableAttributeIndex).html();
	    var draggedPlayerId = $('#' + draggableAttributeType + 'id' + draggableAttributeIndex).html();
	    
	    
	    // Obtain the index of the player number in the captainsList. 
	    // Otherwise obtains -1 if the player number doesn't exist in the list.
	   var indexInCaptainsList = $.inArray(draggedPlayerNumber, captainsList);
	   // Check whether the player number is already in captainsList
	   if (new String(indexInCaptainsList).valueOf() == new String("-1").valueOf()) {
		   captainsList[(droppableIndex-1)] = draggedPlayerId;
		  
		   
	   } else { 
		   // Player is already in the captainsList
		   captainsList[indexInCaptainsList] = "";
		   
		   // Change the html content to reflect the changes
		   $('#captain' + (indexInCaptainsList+1)).val('');
		   
		   captainsList[(droppableIndex-1)] = draggedPlayerId;
	   }
	   
	   
	   
		
		$('#' + this.id).val(draggedPlayerNumber + '   ' + draggedPlayerName);

		$('#' + this.id).fadeIn(1000);
		
		// Display the save squad button
		$('#saveSquadButton').css('display', 'inline');
		$('#saveSquadButton').addClass('animated flash');
	}
		