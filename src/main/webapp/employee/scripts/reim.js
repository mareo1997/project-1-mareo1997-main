let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
	window.location = "http://localhost:8080/project-1/index.html"
} else {
	console.log(currentUser);
	if (currentUser != null) {
		welcome.innerHTML = "Welcome to Reimbursements " + currentUser.username;
	}
}

function home() {
	window.location = "http://localhost:8080/project-1/employee/Ehome.html"
}

function sendReim() {
	console.log("sendReim() started")
	let reimForm = document.reimForm;

	let amount = document.getElementById('amount').value;
	let description = document.getElementById('description').value;
	let type = document.getElementById('type').value;

	const reimtemplate = {
		username: currentUser.username,
		amount: amount,
		description: description,
		type: type
	}

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		console.log("Processing")
		if (this.readyState === 4 && this.status === 200) {
			//alert("Success");
			let data = JSON.parse(xhr.responseText);
			console.log(data);
			if (data != null) {
				renderHTML(data);
			}
		}

		if (this.readyState === 4 && this.status === 204) {
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Failed"
		}
	}

	xhr.open("POST", "http://localhost:8080/project-1/reim");
	xhr.send(JSON.stringify(reimtemplate));
}

function renderHTML(data) {

	let input = document.getElementById('warningText');
	input.textContent = "";

	input.append("ID: " + data.ersid);
	input.append(document.createElement("br"));

	input.append("Author: " + data.author.username);
	input.append(document.createElement("br"));

	input.append("Type: " + data.type.type);
	input.append(document.createElement("br"));

	input.append("Description: " + data.description);
	input.append(document.createElement("br"));

	input.append("Amount: $" + data.amt);
	input.append(document.createElement("br"));

	input.append("Submitted: " + converttime(data.submitted));
	input.append(document.createElement("br"));

	input.append(document.createElement("hr"));
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