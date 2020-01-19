var	size;
var urlPath = '/requests-history-pageable';

window.onload = function(){
    size = document.querySelector(`#size`);
	size.onkeyup = function(){
            showResults(`${urlPath}?size=${size.value}`);
    }
	showResults(urlPath);
}

const makePageNavigation = pages => {
    let str = '';
    for(let i = 0; i < pages; i++)
        str += `<a class="btn" name="page${i}">${i+1}</a>`;
    return str;
}

const makeHTML = data => {
    let str = '';
	for(let index in data){
		console.log((data)[index]);
        str += `<tr>`
            + `<td><a href="">${(data)[index].title}</a></td>`
            + `<td>${(data)[index].status.code}</td>`
            + `<td>${(data)[index].description}</td>`
            + `<td>${(data)[index].user.email}</td>`
        + `</tr>`;
     }
     return str;
}

function addListeners(pages){
    let a;
    for(let i = 0; i < pages; i++){
        a = document.querySelector(`[name="page${i}"]`);
        a.onclick = function(){
            showResults(`${urlPath}?page=${i}&size=${size.value}`);
        }
    }
}

function showResults(url){
	ajaxJS(url, function(response){
        let tbody = document.getElementById('pageable-list');
        let div = document.getElementById('page-navigation');

        let totalElements = response.totalElements;
        let pageSize = response.size;
        let pages = Math.ceil(totalElements/pageSize);

        tbody.innerHTML = makeHTML(response.content);
        div.innerHTML = makePageNavigation(pages);

        addListeners(pages);
	});
}

function ajaxJS(url, callback){
    let xhr = new XMLHttpRequest();
    console.log(xhr);
    xhr.onreadystatechange = function(){
        if (xhr.status === 200 && xhr.readyState === 4){
            console.log(xhr.response);
            callback(JSON.parse(xhr.response));
        }
    }
    xhr.open('GET', url, true);
    xhr.send();
}