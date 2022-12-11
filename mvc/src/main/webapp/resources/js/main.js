function confirmAction() {
    return confirm("Are you sure you want to delete?");
}

async function filterClient() {
    let filter = document.getElementById("filter").value;

    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState === xhr.DONE && xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            console.log(response);
        }
    }
    xhr.open("GET","http://localhost:8080/rest/api/clients?login=" + filter, true);
    xhr.send();
}