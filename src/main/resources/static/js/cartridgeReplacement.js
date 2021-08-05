const cartridgeReplacement = $.modal({
    footerButtons: [
        {
            text: 'Далее',
            cssType: 'ok-next',
            handler() {
                if (cartridgeReplacement.cartridgeOrderList.length === 0) {
                    window.alert('Список пуст, дальнейшее оформление не возможно, ' +
                        'данное окно будет закрыто!')
                    cartridgeReplacement.close()
                } else {
                    for (let orderElement of cartridgeReplacement.cartridgeOrderList) {
                        let status = document.getElementById(orderElement['id'])

                        orderElement['cartridgeStatus'] = {
                            id: Number.parseInt(status.value)
                        }
                    }
                    cartridgeReplacementNext.cartridgeOrderListAfterTakeStep
                        = cartridgeReplacement.cartridgeOrderList
                    cartridgeReplacement.close()
                    cartridgeReplacementNext.open()
                }
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                cartridgeReplacement.close()
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
        if (cartridgeReplacement.cartridgeOrderList.length === 0) {
            let tbody = document.querySelector('tbody')
            let tr = document.createElement('tr')
            let td = document.createElement('td')

            td.textContent = data['cartridge']['serialNumber']
            tr.appendChild(td)

            td = document.createElement('td')
            let status = document.createElement('select')
            status.setAttribute('id', data['id'])
            for (const dataKey of cartridgeReplacement.allStatus) {
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
            cartridgeReplacement.cartridgeOrderList.push(data)
        } else {
            if (cartridgeReplacement
                    .cartridgeOrderList[0]
                    ['orderForConsumer']['consumer']['id']
                === data['orderForConsumer']['consumer']['id']) {

                let tbody = document.querySelector('tbody')
                let tr = document.createElement('tr')
                let td = document.createElement('td')

                td.textContent = data['cartridge']['serialNumber']
                tr.appendChild(td)

                td = document.createElement('td')
                let status = document.createElement('select')
                status.setAttribute('id', data['id'])
                for (const dataKey of cartridgeReplacement.allStatus) {
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
                cartridgeReplacement.cartridgeOrderList.push(data)

            } else
                window.alert('Картридж числиться выданным другому пользователю: '
                    + data['orderForConsumer']['consumer']['nameOfConsumer'])
        }
    }
})



