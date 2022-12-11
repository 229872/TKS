function confirmAction() {
    return confirm("Are you sure you want to delete?");
}

function filterEquipment() {
    const filterValue = document.getElementById('filterValue').value;
    let table = document.getElementById('equipmentTable');
    let rows = table.getElementsByTagName("tr");

    for (let i = 0; i < rows.length; i++) {
        let name = rows[i].getElementsByTagName("td")[1];
        if (name) {
            let txtValue = name.textContent || name.innerText;
            if (txtValue.toUpperCase().indexOf(filterValue) > -1) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }
}