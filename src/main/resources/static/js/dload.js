let serverUrl = 'http://localhost';
let port = 8080;

let longDescriptionService = '/task'

function loadLongDescription(target) {
    let request = new XMLHttpRequest();
    let chooseTask = target.textContent
        .replace(/\s+/g, ' ')
        .trim()

    let url = serverUrl + ':' + port
        + longDescriptionService + '?shortDescription='
        + chooseTask;

    request.open('GET', url);
    request.send();

    request.onload = function () {
        document
            .getElementById('long_description')
            .innerHTML = JSON.parse(request.response)['longDescription'];
    }

}