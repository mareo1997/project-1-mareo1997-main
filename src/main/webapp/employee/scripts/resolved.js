let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
	window.location = "http://localhost:8080/project-1/index.html"
} else {
	console.log(currentUser);
	if (currentUser != null) {
		welcome.innerHTML = "Welcome to your resolved requests " + currentUser.username;
	}
}
resolved();

function home() {
	window.location = "http://localhost:8080/project-1/employee/Ehome.html"
}

function resolved() {
	console.log("resolved() started");

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		console.log("Process");
		if (this.readyState === 4 && this.status === 200) {
			//alert("Success");
			let data = JSON.parse(xhr.responseText);
			console.log(data);
			renderHTML(data);
		}

		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			let childDiv = document.getElementById("resolved");
			childDiv.textContent = "No resolved requests";
		}
	}

	xhr.open("GET", "http://localhost:8080/project-1/resolved");
	xhr.send();
}

function renderHTML(data) {

	let input = document.getElementById('resolved')
	input.textContent = "";

	for (var i = 0; i < data.length; i++) {

		input.append("ID: " + data[i].ersid);
		input.append(document.createElement("br"));

		input.append("Author: " + data[i].author.username);
		input.append(document.createElement("br"));

		input.append("Resolver: " + data[i].resolve.username);
		input.append(document.createElement("br"));

		input.append("Status: " + data[i].status.status);
		input.append(document.createElement("br"));

		input.append("Type: " + data[i].type.type);
		input.append(document.createElement("br"));

		input.append("Description: " + data[i].description);
		input.append(document.createElement("br"));

		input.append("Amount: $" + data[i].amt);
		input.append(document.createElement("br"));

		input.append("Submitted: " + converttime(data[i].submitted));
		input.append(document.createElement("br"));

		input.append("Resolved: " + converttime(data[i].resolved));
		input.append(document.createElement("br"));

		input.append(document.createElement("hr"));
	}
}

function converttime(time) {
	var d = new Date(time);
	var formattedDate = (d.getMonth() + 1) + "/" + d.getDate() + "/" + d.getFullYear();
	var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
	var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
	var hr;
	if (hours > 12) {
		hours = hours - 12;
		hr = "PM";
	} else {
		hr = "AM";
	}

	var formattedTime = hours + ":" + minutes + " " + hr;

	formattedDate = formattedDate + " " + formattedTime;
	return formattedDate;
}