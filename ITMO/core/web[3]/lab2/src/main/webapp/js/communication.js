function getTableBySession(){
	$.ajax({
		type: 'GET',
		url: 'ControllerServlet',
		data: {'getTable' : 'true'},
		success: function(data) {
			console.log("got from server:");
			console.log(data);
			initSessionTable(data);
		},
		error: function(data) {
			alert(data);
		}
	});
}

async function sendForm(x, y, r) {
	let hit=false
	$.ajax({
		type: 'POST',
		url: 'ControllerServlet',
		data: {
			'x': x,
			'y': y,
			'r': r,
			'offset': new Date().getTimezoneOffset()
		},
		success: function(info) {
			pointsContainer.push([info.x, info.y, info.r, info.hit]);
			addInTable(info);
			drawPointRes(info.x, info.y, info.hit)
		},
		error: function(data) {
			console.log("KURWA post req")
		}
	});
}

function clean_table() {
	$.ajax({
		type: "DELETE",
		url: "ControllerServlet?clean=true",
		data: null,
		success: function (response) {
			resultsTable.innerHTML = '';
			pointsContainer=[];
			//location.reload();
		},
		error: function (response) {
			alert(response);
		}
	});

}
	 // fetch('http://localhost:8080/web_lab_2-1.0-SNAPSHOT/ControllerServlet?clean=true', {method: "POST"})
		// .then(data => {
		// 	console.log(data);
		// 	resultsTable.innerHTML = '';
		// 	pointsContainer = [];
		// 	console.log(pointsContainer, 123)
	 //
		// 	//location.reload();
		// 	clearGraph();
		// })
		// .catch(error => {
		// 	// Handle any errors
		// 	console.error(error);
		// });



function initSessionTable(data){
	if(data === null || data.length === null){
		return;
	}
	data.forEach(point => {
		addInTable(point);
	});
	initialize_table(pointsContainer);
}
