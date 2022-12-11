function confirmAction() {
    return confirm("Are you sure you want to delete?");
}


async function filterClient() {
    let filter = document.getElementById("filter").value;
    let table = document.getElementById("clientTable");
    let tbody = table.children[1];
    tbody.innerHTML = ''; // clear table

    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState === xhr.DONE && xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            response.forEach(client => {
                let tr = document.createElement("tr");
                let id = document.createElement("td");
                id.innerHTML = client.entityId;
                let login = document.createElement("td");
                login.innerHTML = client.login;
                let active = document.createElement("td");
                active.innerHTML = client.active;
                tr.append(id, login, active);
                tbody.appendChild(tr);
            })
            console.log(response);
        }
    }
    xhr.open("GET","http://localhost:8080/rest/api/clients?login=" + filter, true);
    xhr.send();
}