var accounts = "accounts";
var urlPath = `/admin/${accounts}`;

window.onload = () => {
    wizard(urlPath);
}

const makeRow = (rowData, index) => {
    let tableRow = document.createElement('tr');
    let tableData = document.createElement('td');
    let anchor = document.createElement('a');
    anchor.setAttribute('href', `${accounts}/${rowData.id}`);
    anchor.appendChild(document.createTextNode(rowData.username));
    tableData.appendChild(anchor);
    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(
        document
            .createTextNode(
                language==='uk' ? rowData.firstNameOrigin : rowData.firstName
            ));

    tableRow.appendChild(tableData);

    tableData = document.createElement('td');
    tableData.appendChild(
        document
            .createTextNode(
                language==='uk' ? rowData.lastNameOrigin : rowData.lastName
            ))
    tableRow.appendChild(tableData);
    tableData = document.createElement('td');
    tableData.appendChild(
        document
            .createTextNode(rowData.phone))
    tableRow.appendChild(tableData);
    return tableRow;
}