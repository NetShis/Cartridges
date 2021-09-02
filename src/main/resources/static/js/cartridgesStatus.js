const cartridgesStatus = $.modal({
    footerButtons: [
        {
            text: 'Закрыть',
            cssType: 'destroy',
            handler() {
                cartridgesStatus.close()
            }
        }
    ],
    setContent: `
            <h3>Сканируйте серийный номер картриджа</h3>
            <table>
                <thead>
                    <tr>
                        <th>S/N</th>
                        <th>Модель</th>
                        <th>Дата регистрации</th>
                        <th>Дата списания</th>
                        <th>Статус</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`,

    method: 'GET',
    path: '/cartridge/getStatus?serialNumber=',
    handler: function (data) {
        let tbody = document.querySelector('tbody')
        let tr = document.createElement('tr')
        let td = document.createElement('td')

        td.textContent = data['serialNumber']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['cartridgeModel']['cartridgeModel']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['registrationDate']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['deregistrationDate']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['stateCartridge']
        tr.appendChild(td)

        tbody.appendChild(tr)
        cartridgesStatus.cartridgeList.push(data)
    }
})



