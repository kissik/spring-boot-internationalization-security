window.onload = () => {
    let request = getRequestObject();
    request.onreadystatechange = () => {
        handleResponse(request);
    }
}

const handleResponse = (request) => {
    if (request.readyState===4 && request.state===200)
        console.log(request);
}

const getRequestObject = () => {
    return window.XMLHttpRequest;
}