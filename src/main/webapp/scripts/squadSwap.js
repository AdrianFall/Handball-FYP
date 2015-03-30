	
	
	/* START droppable */
	$('.droppablePitchTable').droppable({
		drop : handlePitchTableUIDropEvent
	});
	
	$('.droppableSquadTable').droppable({
		drop : handleSquadTableUIDropEvent
	});

	
	
	function handleSquadTableUIDropEvent(event, ui) {
		var trId = this.id;
		
		var splitDroppableId = trId.split("tr");
		var droppableAttributeType = splitDroppableId[0];
		var droppableAttributeIndex = splitDroppableId[1];
		
		var draggable = ui.draggable;
		var draggableAttribute = draggable.attr('id');
		
		var splitDraggableAttribute = draggableAttribute.split("tr");
		var draggableAttributeType = splitDraggableAttribute[0];
		var draggableAttributeIndex = splitDraggableAttribute[1];
		
		
		// obtain the dragged player name
		var dragged_player_name = $('#' + $.trim(draggableAttributeType)  + 'name' + $.trim(draggableAttributeIndex)).html();
		$('#dragged_player').html(dragged_player_name);
		// obtain the dropped swapped name
		var dropped_player_name = $('#' + $.trim(droppableAttributeType) + 'name' + $.trim(droppableAttributeIndex)).html();
		$('#dropped_player').html(dropped_player_name)

		
		$('#modal_semantic')
		  .modal({
		    closable  : true,
		    onDeny    : function(){
		      //do nothing
		    },
		    onApprove : function() {
		    	performSwapOnSquadTable(trId, event,ui);
		    }
		  }).modal('show');
		
		
		
	}
	
	function performSwapOnSquadTable(id, event, ui) {
		
		
		var splitDroppableId = id.split("tr");
		var droppableAttributeType = splitDroppableId[0];
		var droppableAttributeIndex = splitDroppableId[1];
		
		var draggable = ui.draggable;
		var draggableAttribute = draggable.attr('id');
		
		var splitDraggableAttribute = draggableAttribute.split("tr");
		var draggableAttributeType = splitDraggableAttribute[0];
		var draggableAttributeIndex = splitDraggableAttribute[1];
		
		// Animate the slide up of the interchanged rows
	     $('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideUp('slow');
		 $('#' + droppableAttributeType + 'td' + droppableAttributeIndex + ' div').slideUp('slow'); 
		 
		swapPlayerDetailsOnSquadTableCells('number', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('position', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('name', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('marketValue', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('age', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('form', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('id', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('quality', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		
		// Call method to perform changes (if needed) to the pitch table 
		performPitchTableChangeOnShirtNumbers('h3', draggableAttributeType, draggableAttributeIndex, droppableAttributeType, droppableAttributeIndex);
		
		
		
		
		// Animate the slide down of the interchanged rows
		$('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideDown('slow');
		 $('#' + droppableAttributeType + 'td' + droppableAttributeIndex + ' div').slideDown('slow'); 
	}
	
	
	// Performs (if needed) a swap in the shirt numbers on the pitch table
	function performPitchTableChangeOnShirtNumbers(textElementIdentifier, draggableAttributeType, draggableAttributeIndex, droppableAttributeType, droppableAttributeIndex) {
		
		var droppablePlayerNumber = $('#' + droppableAttributeType + 'number' + droppableAttributeIndex).text();
		var draggablePlayerNumber = $('#' + draggableAttributeType + 'number' + draggableAttributeIndex).text();
		
		if (new String(draggableAttributeType).valueOf() == new String("fsqp_").valueOf() 
				&& new String(droppableAttributeType).valueOf() == new String("fsqp_").valueOf()) {
		
			// Add fade out animation, swap the text and fade in the droppable player number
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeOut(10);
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).text($.trim(draggablePlayerNumber)); 
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeIn(700);
			
			// Add fade out animation, swap the text and fade in the draggable player number
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeOut(10);
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).text($.trim(droppablePlayerNumber)); 
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeIn(700);
			
			
			// Swap the elements' identifiers. To perform a change, set the ids of the elements
			// to a temporary ids, to address them uniquely and make a successful swap.
			var id1 = 'changer1';
			var id2 = 'changer2';
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).attr('id', id1);
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).attr('id', id2);
			
			// swap.
			$('#' + id1).attr('id', textElementIdentifier + $.trim(draggablePlayerNumber));
			$('#' + id2).attr('id', textElementIdentifier + $.trim(droppablePlayerNumber));
			
			
		} else if (new String(draggableAttributeType).valueOf() == new String("fsqp_").valueOf()) { // Draggable is first squad player, but droppable not. Therefore droppable becomes the fsqp now
			// Fade out animation
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeOut(10);
			// Swap the text
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).text($.trim(draggablePlayerNumber)); 
			// Fade in animation
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeIn(700);
			// Swap the elements' identifiers
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).attr('id', textElementIdentifier + $.trim(draggablePlayerNumber));
			
			
		
			} else if (new String(droppableAttributeType).valueOf() == new String("fsqp_").valueOf()) { // Droppable is first squad player. Therefore draggable becomes the fsqp.
				// Fade out animation
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeOut(10);
				// Swap the text
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).text($.trim(droppablePlayerNumber)); 
				// Fade in animation
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeIn(700);
				// Swap the elements' identifiers
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).attr('id', textElementIdentifier + $.trim(droppablePlayerNumber));
			} 
		// Otherwise no change needed on the pitch table
	}
	
	function swapPlayerDetailsOnSquadTableCells(cellIdentifier, droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex) {

		var swapDroppablePlayer = $('#' + $.trim(droppableAttributeType)  + cellIdentifier + $.trim(droppableAttributeIndex));
		var tempDroppablePlayerHTML = swapDroppablePlayer.html();
		var swapDraggedPlayer = $('#' + $.trim(draggableAttributeType) + cellIdentifier + $.trim(draggableAttributeIndex));
		var tempDraggedPlayerHTML = swapDraggedPlayer.html();
		// Swap the _numbers
		swapDroppablePlayer.html($.trim(tempDraggedPlayerHTML));
		swapDraggedPlayer.html($.trim(tempDroppablePlayerHTML));
		
		// Display the save squad button
		$('#saveSquadButton').css('display', 'inline');
		$('#saveSquadButton').addClass('animated flash');
		
		setTimeout(function() {$('#saveSquadButton').removeClass('flash');}, 600);
		
		
	}

	function handlePitchTableUIDropEvent(event, ui) {

		var trId = this.id;
		$('#modal_semantic')
		  .modal({
		    closable  : true,
		    onDeny    : function(){
		      //do nothing
		    },
		    onApprove : function() {
		    	var draggable = ui.draggable;
				var draggableAttribute = draggable.attr('id');
				var splitDraggableAttribute = draggableAttribute.split("tr");
				var draggableAttributeType = splitDraggableAttribute[0];
				var draggableAttributeIndex = splitDraggableAttribute[1];
				
				var fieldPlayerNumber = $('#' + trId).text();
				
				// Obtain the player number and figure out which table tr/td holds the player number record
				
				var trNode;
				var splitTrNode;
				
				try {
					// Try obtaining the trNode from first squad player row
					trNode = $('#fsqp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
					splitTrNode = trNode.attr('id').split("tr");
				} catch (e) {
					
					try {
						trNode = $('#bp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
						splitTrNode = trNode.attr('id').split("tr");
					} catch (e) {
						
						trNode = $('#rp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
						splitTrNode = trNode.attr('id').split("tr");
					}
				}
				/* var trNode = $('#fsqp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent(); */
				
				/* var fsqp = document.getElementById('fsqp_pn' + fieldPlayerNumber); */
				
				
				 /* var splitTrNode = trNode.attr('id').split("tr");  */
				 var trType = splitTrNode[0];
				var trIndex = splitTrNode[1];
				

				
				// Animate the slide up of the interchanged rows
				 $('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideUp('slow');
				 $('#' + trType + 'td' + trIndex + ' div').slideUp('slow');
			
				 swapPlayerDetailsOnSquadTableCells('number', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('position', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('name', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('marketValue', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('age', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('form', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('id', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
					swapPlayerDetailsOnSquadTableCells('quality', trType, trIndex, draggableAttributeType, draggableAttributeIndex);

					
				
			 	
					performPitchTableChangeOnShirtNumbers('h3', draggableAttributeType, draggableAttributeIndex, trType, trIndex);
				
				$('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideDown('slow');
				$('#' + trType + 'td' + trIndex + ' div').slideDown('fast');
				
		    }
		  }).modal('show');
		
		
		 
	} /* END droppablePitchTable */
	 