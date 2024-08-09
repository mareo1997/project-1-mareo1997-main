let welcome = document.getElementById('welcome');
let instruct = document.getElementById('instruction');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
    window.location = "http://localhost:8080/project-1/index.html"
} else {
    console.log(currentUser);
    if (currentUser != null) {
        welcome.innerHTML = "Welcome to your employees " + currentUser.username;
        instruct.innerHTML = "These are your employees";
    }
}
empl()

function home() {
    window.location = "http://localhost:8080/project-1/manager/Mhome.html"
}

function empl() {
    console.log("empl() started");

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        console.log("Process");
        if (this.readyState === 4 && this.status === 200) {
			//alert("Success");
			let data = JSON.parse(xhr.responseText);
			console.log(data);
			renderHTML(data);
        }

        if (this.readyState === 4 && this.status === 204) {
            console.log("Failed");
            let childDiv = document.getElementById("empl");
            childDiv.textContent = "No employees found";
        }
    }

    xhr.open("GET", "http://localhost:8080/project-1/allempls");
    xhr.send();
}

function renderHTML(data) {

	let input = document.getElementById("empl");
	input.textContent = "";

	for (var i = 0; i < data.length; i++) {
	
        input.append("ID: " + data[i].userid);
        input.append(document.createElement("br"));
    
        input.append("Name: " + data[i].firstname + " " + data[i].lastname); 
        input.append(document.createElement("br"));
      
        input.append("Username: " + data[i].username); 
        input.append(document.createElement("br"));
    
        input.append("Email: " + data[i].email); 
        input.append(document.createElement("br"));
    
        input.append(document.createElement("hr"));
    
	}
}