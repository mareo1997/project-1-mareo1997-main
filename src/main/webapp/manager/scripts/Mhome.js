let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
    window.location = "http://localhost:8080/project-1/index.html"
} else {
    console.log(currentUser);
    if (currentUser != null) {
        welcome.innerHTML = "Welcome to the Manager Home Page " + currentUser.username;
    }
}

function logout() { //DONE
    let xhr = new XMLHttpRequest();

    xhr.open("POST", "http://localhost:8080/project-1/logout");
    xhr.send();

    sessionStorage.removeItem('currentUser');
    window.location = "http://localhost:8080/project-1/index.html";
}

function empl() { //DONE
    console.log("empl() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/manager/empl.html"
}

function allrequests() {  //DONE
    console.log("allrequests() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/manager/allrequests.html"
}

function resolve() { //DONE
    console.log("resolve() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/manager/resolve.html"
}

function allresolved() { //DONE
    console.log("allresolved() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/manager/allresolved.html"
}

function requests() { //DONE
    console.log("requests() started")
    console.log(sessionStorage.getItem('currentUser'))
    window.location = "http://localhost:8080/project-1/manager/requests.html"
}