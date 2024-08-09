function home() { //DONE
    window.location = "http://localhost:8080/project-1/index.html";
}

function signup() {
	console.log("update() started")
	let signupForm = document.signupForm;

	let firstname = document.getElementById('firstname').value;
	let lastname = document.getElementById('lastname').value;
	let email = document.getElementById('email').value;
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	let repassword = document.getElementById('repassword').value;

	const signuptemplate = {
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

		if (this.readyState === 4 && this.status === 202) {
			console.log("Failed");
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Passwords did not match"
		}

		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Failed to register user"
        }
        
        if (this.readyState === 4 && this.status === 500) {
			console.log("Failed");
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Duplicate username or email"
		}
	}

	xhr.open("POST", "http://localhost:8080/project-1/signup");
	xhr.send(JSON.stringify(signuptemplate));
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
            sessionStorage.setItem('currentUser',this.responseText);
			window.location = "http://localhost:8080/project-1/employee/Ehome.html"
		}
		if (this.readyState === 4 && this.status === 201) {
			console.log("Manager");
            sessionStorage.setItem('currentUser',this.responseText);
			window.location = "http://localhost:8080/project-1/manager/Mhome.html"
        }
        
		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			alert("Failed to log in! Username or password is incorrect");
        }
        
	};

	xhr.open("POST", "http://localhost:8080/project-1/login");
    xhr.send(JSON.stringify(logintemplate));
}
