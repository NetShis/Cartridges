const takeCartridge = $.modal({
    footerButtons: [
        {
            text: 'Принять',
            cssType: 'ok-next',
            handler() {
                if (takeCartridge.cartridgeOrderList.length !== 0) {
                    for (let orderElement of takeCartridge.cartridgeOrderList) {
                        let status = document.getElementById(orderElement['id'])

                        orderElement['cartridgeStatus'] = {
                            id: Number.parseInt(status.value)
                        }
                    }

                    _request('PUT', '/order/closeOrders', null, takeCartridge.cartridgeOrderList)
                }
                else window.alert('Список пуст, дальнейшее оформление не возможно, ' +
                        'данное окно будет закрыто!')
                takeCartridge.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                takeCartridge.close()
            }
        }
    ],
    setContent: `
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
            </table>`,

    method: 'GET',
    path: '/order/getOrder?serialNumber=',
    handler: function (data) {

        let tbody = document.querySelector('tbody')
        let tr = document.createElement('tr')
        let td = document.createElement('td')

        td.textContent = data['cartridge']['serialNumber']
        tr.appendChild(td)

        td = document.createElement('td')
        let status = document.createElement('select')
        status.setAttribute('id', data['id'])
        for (const dataKey of takeCartridge.allStatus) {
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


        tbody.appendChild(tr)
        takeCartridge.cartridgeOrderList.push(data)
    }
})


