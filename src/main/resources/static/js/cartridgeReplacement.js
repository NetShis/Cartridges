const btn = document.querySelector('#replacing-the-cartridge')


btn.addEventListener('click', event => {
    let order = []
    let consumer
    let replacementCartridges = []
    let serialNumbersReceived = []

    const modalOne = document.createElement('div')
    modalOne.classList.add('modal-replacing-the-cartridge')
    modalOne.insertAdjacentHTML('afterbegin', `
    <div class="modal-overlay">
        <div class="modal-window">
        <h3>Сканируйте серийный номер картриджа</h3>
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
    document.body.appendChild(modalOne)

    const btnCloseOne = document.querySelector('#destroy-btn')

    btnCloseOne.addEventListener('click', event => {
        modalOne.parentNode.removeChild(modalOne)
        btnCloseOne.removeEventListener('click', this)
    })

    const btnNext = document.querySelector('#next-btn')
    btnNext.setAttribute('disabled', 'true')

    let allStatus
    fetch('http://127.0.0.1:8080/cartridge/getAllStatus')
        .then(response => response.json())
        .then(data => allStatus = data)


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
                            if (typeof consumer === 'undefined')
                                consumer = data['orderForConsumer']['consumer']['id']

                            if (consumer !== data['orderForConsumer']['consumer']['id'])
                                window.alert('Картридж S/N '
                                    + serialNumber + 'не выдавался'
                                    + data['orderForConsumer']['consumer']['nameOfConsumer'] + ' потребителю')

                            else {
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
                                btnNext.removeAttribute('disabled')
                            }
                        })

                    else
                        response.json().then(data => window.alert(data['message']))
                })
        }
    }

    btnNext.addEventListener('click', event => {
        for (let orderElement of order)
            orderElement['cartridgeStatus'] = {
                id: Number.parseInt(document.getElementById(orderElement['id']).value)
            }

        modalOne.parentNode.removeChild(modalOne)
        btnCloseOne.removeEventListener('click', this)

        const modalTwo = document.createElement('div')
        modalTwo.classList.add('modal-replacing-the-cartridge')
        modalTwo.insertAdjacentHTML('afterbegin', `
        <div class="modal-overlay">
            <div class="modal-window">
            <h3>Сканируйте серийный номер картриджа</h3>
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
                <button id="confirm-btn" class="ok-next">Подтвердить замену</button>
                <button id="destroy-btn" class="destroy">Отмена</button>
                </div>
            </div>
        </div>
        `)
        document.body.appendChild(modalTwo)
        const btnConfirm = document.querySelector('#confirm-btn')
        const btnCloseTwo = document.querySelector('#destroy-btn')

        btnCloseTwo.addEventListener('click', event => {
            modalTwo.parentNode.removeChild(modalTwo)
            btnCloseTwo.removeEventListener('click', this)
        })

        btnConfirm.addEventListener('click', event => {
            modalTwo.parentNode.removeChild(modalTwo)
            btnCloseTwo.removeEventListener('click', this)

            fetch('http://127.0.0.1:8080/order/closeOrders', {
                method: 'PUT',
                body: JSON.stringify(order),
                headers: {'Content-Type': 'application/json'}
            }).then(response => {
                if (response.ok) {
                    const consumerReplacementCartridgesList = {
                        consumer: order[0]['orderForConsumer']['consumer'],
                        cartridges: replacementCartridges
                    }

                    fetch('http://127.0.0.1:8080/order/createOrder', {
                        method: 'POST',
                        body: JSON.stringify(consumerReplacementCartridgesList),
                        headers: {'Content-Type': 'application/json'}
                    }).then(response => response.json())
                        .then(data => window.alert(data['message']))
                }
            })
        })

        for (let orderElement of order) {
            const tr = document.createElement('tr')
            let td = document.createElement('td')


            td.setAttribute('id', orderElement['cartridge']['cartridgeModel']['cartridgeModel'])
            tr.appendChild(td)

            td = document.createElement('td')
            td.textContent = orderElement['cartridge']['cartridgeModel']['cartridgeModel']
            tr.appendChild(td)

            td = document.createElement('td')
            td.setAttribute('id', 'serialNumber')
            tr.appendChild(td)

            td = document.createElement('td')
            td.textContent = orderElement['orderForConsumer']['consumer']['nameOfConsumer']
            tr.appendChild(td)
            document.querySelector('tbody').appendChild(tr)
        }

        document.onpaste = event => {
            let serialNumber = event.clipboardData.getData('text/plain')

            if (serialNumbersReceived.includes(serialNumber)) {
                window.alert('S/N ' + serialNumber + ' уже в списке')
            } else {
                fetch('http://127.0.0.1:8080/cartridge/getCartrigeBySerialNumber?serialNumber=' + serialNumber)
                    .then(response => {
                        if (response.ok)
                            response.json().then(data => {
                                let change = document.getElementById(data['cartridgeModel']['cartridgeModel'])

                                if (change == null) {
                                    const tr = document.createElement('tr')
                                    let td = document.createElement('td')

                                    tr.appendChild(td)

                                    td = document.createElement('td')
                                    td.textContent = data['cartridgeModel']['cartridgeModel']
                                    tr.appendChild(td)

                                    td = document.createElement('td')
                                    td.textContent = data['serialNumber']
                                    tr.appendChild(td)

                                    td = document.createElement('td')
                                    td.textContent = order[0]['orderForConsumer']['consumer']['nameOfConsumer']
                                    tr.appendChild(td)
                                    document.querySelector('tbody').appendChild(tr)
                                } else {
                                    change.textContent = '✅'
                                    let serialNumberElement = change.parentElement.querySelector('#serialNumber')
                                    serialNumberElement.textContent = data['serialNumber']
                                    change.removeAttribute('id')
                                }
                                replacementCartridges.push(data)
                                serialNumbersReceived.push(serialNumber)
                            })
                        else
                            response.json().then(data => window.alert(data['message']))
                    })
            }
        }
    })
})