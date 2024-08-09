let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

let f = document.getElementById('firstname');
let l = document.getElementById('lastname');
let e = document.getElementById('email');
let u = document.getElementById('username');

if (userString === null) {
	window.location = "http://localhost:8080/project-1/index.html"
} else {
	console.log(currentUser);
	if (currentUser != null) {
		welcome.innerHTML = "Welcome to Updates " + currentUser.username;
		f.value = currentUser.firstname;
		l.value = currentUser.lastname;
		u.value = currentUser.username;
		e.value = currentUser.email;
	}
}

function home() {
	window.location = "http://localhost:8080/project-1/employee/Ehome.html"
}

function profile() { //DONE
	console.log("profile() started")
	console.log(sessionStorage.getItem('currentUser'))
	window.location = "http://localhost:8080/project-1/employee/profile.html"
}

function update() {
	console.log("update() started")
	let updateForm = document.updateForm;

	let firstname = document.getElementById('firstname').value;
	let lastname = document.getElementById('lastname').value;
	let email = document.getElementById('email').value;
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	let repassword = document.getElementById('repassword').value;

	const updatetemplate = {
		updater: currentUser.username,
		firstname: firstname,
		lastname: lastname,
		email: email,
		username: username,
		password: password,
		repassword: repassword
	}

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		console.log("Processing")
		if (this.readyState === 4 && this.status === 200) {
			console.log("Success");
			sendLogin();
		}

		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Failed"
		}

		if (this.readyState === 4 && this.status === 500) {
			console.log("Failed");
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Duplicate username or email"
		}

	}

	xhr.open("POST", "http://localhost:8080/project-1/update");
	xhr.send(JSON.stringify(updatetemplate));
}

function sendLogin() {
	console.log("sendLogin() started")
	let loginForm = document.loginForm;
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	console.log("Username: " + username + " Password: " + password);

	const logintemplate = {
		username: username,
		password: password
	}

	//This begins AJAX workflow
	let xhr = new XMLHttpRequest();

	// setting up a callback function for when ready state changed (ready stat starts from 0 to 4)
	// this call back is called everytime readyState changes
	xhr.onreadystatechange = function() {
		console.log("Processing")
		if (this.readyState === 4 && this.status === 200) {
			console.log("Employee");
			profile();
		}
        
		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			alert("Failed to log in! Username or password is incorrect");
        }
        
	};

	xhr.open("POST", "http://localhost:8080/project-1/login");
    xhr.send(JSON.stringify(logintemplate));
}

