let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
    window.location = "http://localhost:8080/project-1/index.html"
} else {
    console.log(currentUser);
    if (currentUser != null) {
        welcome.innerHTML = "Welcome to your profile " + currentUser.username;
    }
}
profile();

function home() {
    window.location = "http://localhost:8080/project-1/employee/Ehome.html"
}

function profile() {
    console.log("profile() started")

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        console.log("Processing")

        if (this.readyState === 4 && this.status === 200) {
            //alert("Success");
            let data = JSON.parse(xhr.responseText);
            if (data != null) {
                renderHTML(data);
            }
        }

        if (this.readyState === 4 && this.status === 204) {
            alert("Failed");
            window.location = "http://localhost:8080/project-1/employee/Ehome.html"
        }
    }

    xhr.open("GET", "http://localhost:8080/project-1/profile");
    xhr.send();
}

function renderHTML(data) {

	let input = document.getElementById('input');
	input.textContent = "";	

    input.append("ID: " + data.userid);
    input.append(document.createElement("br"));

    input.append("Name: " + data.firstname + " " + data.lastname); 
    input.append(document.createElement("br"));
  
    input.append("Username: " + data.username); 
    input.append(document.createElement("br"));

    input.append("Email: " + data.email); 
    input.append(document.createElement("br"));

    input.append(document.createElement("hr"));
}