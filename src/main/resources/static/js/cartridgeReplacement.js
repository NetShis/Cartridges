const btn = document.querySelector('#replacing-the-cartridge')
let order = []

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

        if (serialNumbersReceived.includes(serialNumber)) {
            window.alert('S/N ' + serialNumber + ' уже в списке')
        } else {

            const tr = document.createElement('tr')
            let td = document.createElement('td')
            td.textContent = serialNumber
            tr.appendChild(td)

            fetch('http://127.0.0.1:8080/order/getOrder?serialNumber=' + serialNumber)
                .then(response => {
                    if (response.ok)
                        response.json().then(data => {
                            td = document.createElement('td')

                            let status = document.createElement('select')
                            status.setAttribute('id', data['id'])
                            for (const dataKey of allStatus) {
                                const option = document.createElement('option')
                                option.textContent = dataKey['Status']
                                option.setAttribute('value', dataKey['id'])
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
                            serialNumbersReceived.push(serialNumber)
                            order.push(data)
                        })

                    else
                        response.json().then(data => window.alert(data['message']))
                })
        }
    }


    const listenerbtnClose = event => {
        modal.parentNode.removeChild(modal)
        btnClose.removeEventListener('click', listenerbtnClose)
    }

    btnClose.addEventListener('click', listenerbtnClose)
    btnNext.addEventListener('click', event => {
        for (let orderElement of order)
            orderElement['cartridgeStatus'] = Number.parseInt(document.getElementById(orderElement['id']).value)

        listenerbtnClose()

        const modal = document.createElement('div')
        modal.classList.add('modal-replacing-the-cartridge')
        modal.insertAdjacentHTML('afterbegin', `
        <div class="modal-overlay">
            <div class="modal-window">
                <table>
                    <thead>
                        <tr>
                            <th>Картридж заменен</th>
                            <th>Модель картриджа</th>
                            <th>S/N замененного картриджа</th>
                            <th>Кому выдается</th>
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

        for (let orderElement of order) {
            console.log(orderElement)
            const tr = document.createElement('tr')
            let td = document.createElement('td')

            td.textContent = 'vvvvv'
            tr.appendChild(td)

            td = document.createElement('td')
            td.textContent = orderElement['cartridge']['cartridgeModel']['cartridgeModel']
            tr.appendChild(td)

            td = document.createElement('td')
            td.textContent = 'vvvvv'
            tr.appendChild(td)

            td = document.createElement('td')
            td.textContent = orderElement['orderForConsumer']['nameOfConsumer']
            tr.appendChild(td)
            document.querySelector('tbody').appendChild(tr)
        }
    })
})