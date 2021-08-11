const cartridgesFromRefueller = $.modal({
    footerButtons: [
        {
            text: 'Принять',
            cssType: 'ok-next',
            handler() {
                if (cartridgesFromRefueller.cartridgeOrderListForRefueller.length !== 0) {
                    for (let orderElement of cartridgesFromRefueller.cartridgeOrderListForRefueller) {
                        let status = document.getElementById(orderElement['id'])

                        orderElement['statusCartridgeAfterRefueller'] = {
                            id: Number.parseInt(status.value)
                        }
                    }
                    _request('PUT', '/refueller/closeOrders', null,
                        cartridgesFromRefueller.cartridgeOrderListForRefueller)
                } else window.alert('Список пуст, дальнейшее оформление не возможно, ' +
                    'данное окно будет закрыто!')
                cartridgesFromRefueller.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                cartridgesFromRefueller.close()
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
                        <th>Заправщик</th>
                        <th>Дата выдачи</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`,

    method: 'GET',
    path: '/refueller/getOrder?serialNumber=',
    handler: function (data) {

        let tbody = document.querySelector('tbody')
        let tr = document.createElement('tr')
        let td = document.createElement('td')

        td.textContent = data['cartridge']['serialNumber']
        tr.appendChild(td)

        td = document.createElement('td')
        let status = document.createElement('select')
        status.setAttribute('id', data['id'])
        for (const dataKey of cartridgesFromRefueller.allStatusAfterRefueller) {
            const option = document.createElement('option')
            option.textContent = dataKey['status']
            option.setAttribute('value', dataKey['id'])
            status.appendChild(option)
        }

        td.appendChild(status)
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['cartridge']['cartridgeModel']['cartridgeModel']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['orderForRefueller']['refueller']['nameOfRefueller']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['orderForRefueller']['orderDate']
        tr.appendChild(td)


        tbody.appendChild(tr)
        cartridgesFromRefueller.cartridgeOrderListForRefueller.push(data)
    }
})



