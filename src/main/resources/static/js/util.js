const URL = 'http://127.0.0.1:8080'

function _request(method, path, handler, sendData) {
    if (method === 'GET') {
        fetch(URL + path).then(response => {
            if (response.ok)
                response.json().then(data => handler(data))
            else
                response.json().then(data => window.alert(data['message']))
        })
    }
    if (method === 'PUT' || method === 'POST') {
        fetch(URL + path, {
            method: method,
            body: JSON.stringify(sendData),
            headers: {'Content-Type': 'application/json'}
        }).then(response => response.json())
            .then(data => window.alert(data['message']))
    }
}

document.addEventListener('click', event=>{
    if (event.target.dataset.btn === 'cartridge-replacement')
        cartridgeReplacement.open()
    if (event.target.dataset.btn === 'take-cartridge')
        takeCartridge.open()
    if (event.target.dataset.btn === 'give-out-cartridge')
        giveOutCartridge.open()
    if (event.target.dataset.btn === 'deregister-cartridge')
        deregisterCartridge.open()
    if (event.target.dataset.btn === 'add-new-cartridge')
        addNewCartridge.open()
    if (event.target.dataset.btn === 'refueling-order') {
        _request('GET', '/refueller/getDefaultRefueller', data => {
            refuelingOrder.open()
        })
    }

})