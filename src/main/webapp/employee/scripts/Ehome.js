let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
    window.location = "http://localhost:8080/project-1/index.html"
} else {
    console.log(currentUser);
    if (currentUser != null) {
        welcome.innerHTML = "Welcome to the Employee Home Page " + currentUser.username;
    }
}

function logout() { //DONE
    let xhr = new XMLHttpRequest();

    xhr.open("POST", "http://localhost:8080/project-1/logout");
    xhr.send();

    sessionStorage.removeItem('currentUser');
    window.location = "http://localhost:8080/project-1/index.html";
}

function reim() { //DONE
    console.log("reim() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/employee/reim.html"
}

function pending() {  //DONE
    console.log("pending() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/employee/pending.html"
}

function resolved() { //DONE
    console.log("resolved() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/employee/resolved.html"
}

function profile() { //DONE
    console.log("profile() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/employee/profile.html"
}

function update() { //DONE
    console.log("update() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/employee/update.html"
}