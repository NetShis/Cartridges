const btn = document.querySelector('#replacing-the-cartridge')
const order = []



btn.addEventListener('click', event => {

    const modal = document.createElement('div')
    modal.classList.add('modal-replacing-the-cartridge')
    modal.insertAdjacentHTML('afterbegin', `
    <div class="modal-overlay">
        <div class="modal-window">
            <table>
                <thead>
                    <tr>
                        <th>S/N</th>
                        <th>Статус</th>
                        <th>Модель</th>
                        <th>Кому был выдан</th>
                        <th>Дата выдачи</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="footer">
                <button id="next-btn" class="ok-next">Далее</button>
                <button id="destroy-btn" class="destroy">Отмена</button>
            </div>
        </div>
    </div>
`)
    document.body.appendChild(modal)

    const btnClose = document.querySelector('#destroy-btn')
    const btnNext = document.querySelector('#next-btn')

    let allStatus
    fetch('http://127.0.0.1:8080/cartridge/getAllStatus')
        .then(response => response.json())
        .then(data => allStatus = data)

    let serialNumbersReceived = []

    document.onpaste = event => {
        let serialNumber = event.clipboardData.getData('text/plain')

        if (serialNumbersReceived.includes(serialNumber)){
            window.alert('S/N ' + serialNumber + ' уже в списке')
        }
        else{
            serialNumbersReceived.push(serialNumber)

            const tr = document.createElement('tr')
            let td = document.createElement('td')
            td.textContent = serialNumber
            tr.appendChild(td)

            fetch('http://127.0.0.1:8080/order/getOrder?serialNumber=' + serialNumber)
                .then(response => response.json())
                .then(data =>{
                    order.push(data)

                    td = document.createElement('td')

                    let status = document.createElement('select')
                    for (const dataKey of allStatus) {
                        const option = document.createElement('option')
                        option.textContent = dataKey['Status']
                        status.appendChild(option)
                    }

                    td.appendChild(status)
                    tr.appendChild(td)

                    td = document.createElement('td')
                    td.textContent = data['cartridge']['cartridgeModel']['cartridgeModel']
                    tr.appendChild(td)

                    td = document.createElement('td')
                    td.textContent = data['orderForConsumer']['consumer']['nameOfConsumer']
                    tr.appendChild(td)

                    td = document.createElement('td')
                    td.textContent = data['orderForConsumer']['orderDate']
                    tr.appendChild(td)

                    document.querySelector('tbody').appendChild(tr)
                })

        }



        console.log(order)
    }


    const listenerbtnClose = event => {
        modal.parentNode.removeChild(modal)
        btnClose.removeEventListener('click', listenerbtnClose)
    }

    btnClose.addEventListener('click', listenerbtnClose)
    btnNext.addEventListener('click', event => {
        listenerbtnClose()

    })
})