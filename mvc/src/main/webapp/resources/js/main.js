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

function validate() {
    let rLogin = new RegExp(/^(Cat|Dog|Sheep)_[0-9]{4}-[a-zA-Z]{1,8}$/);
    let rIceCream = new RegExp(/^(Vanilla|Chocolate|Sorbet)$/);
    let inputLogin = document.getElementById("createAdmin:login");
    let inputIceCream = document.getElementById("createAdmin:iceCream");

    let goodLogin = testTextInput(rLogin, inputLogin, 'login');
    let goodIceCream = testTextInput(rIceCream, inputIceCream, 'iceCream');

    let button = document.getElementById("createAdmin:button");
    button.disabled = !(goodLogin && goodIceCream);
}

function testTextInput(regex, input, name) {
    let label = document.getElementById(name + 'Label');
    console.log(label);
    if(input.value == '') {
        input.style.background = "orange";
        label.innerHTML = "Cannot be empty";
        return false;
    } else if (!regex.test(input.value)) {
        input.style.background = "red";
        label.innerHTML = "Not matching regex: " + regex.toString();
        return false;
    }
    label.innerHTML = "";
    input.style.background = "green";
    return true;
}