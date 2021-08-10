const giveOutCartridge = $.modal({
    footerButtons: [
        {
            text: 'Выдать',
            cssType: 'ok-next',
            handler() {

                if (giveOutCartridge.cartridgeList !== 0) {
                    const consumerReplacementCartridgesList = {
                        consumer: {
                            id: Number.parseInt(document.querySelector('#consumer-list-select').value)
                        },
                        cartridges: giveOutCartridge.cartridgeList
                    }

                    _request('POST', '/order/createOrder', null, consumerReplacementCartridgesList)
                }
                else window.alert('Список картриджей для выдачи пуст. Заказ на выдачу не сформирован!')
                giveOutCartridge.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                giveOutCartridge.close()
            }
        }
    ],
    setContent: `
            <h3>Выберите пользователя</h3>
            <div id="consumer-list"></div>
            <h3>Сканируйте серийный номер картриджа</h3>
            <table>
                <thead>
                    <tr>
                        <th>S/N</th>
                        <th>Модель</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`,

    method: 'GET',
    path: '/cartridge/getCartridgeBySerialNumber?operation=giveOutCartridge&serialNumber=',
    handler: function (data) {
        let tbody = document.querySelector('tbody')
        let tr = document.createElement('tr')
        let td = document.createElement('td')

        td.textContent = data['serialNumber']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['cartridgeModel']['cartridgeModel']
        tr.appendChild(td)

        tbody.appendChild(tr)
        giveOutCartridge.cartridgeList.push(data)
    }
})



