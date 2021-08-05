const cartridgeReplacementNext = $.modal({
    footerButtons: [
        {
            text: 'Выдать',
            cssType: 'ok-next',
            handler() {

                _request('PUT', '/order/closeOrders', null, cartridgeReplacementNext
                    .cartridgeOrderListAfterTakeStep)


                if (cartridgeReplacementNext.cartridgeList.length !== 0) {
                    const consumerReplacementCartridgesList = {
                        consumer: {
                            id: cartridgeReplacementNext
                                .cartridgeOrderListAfterTakeStep[0]
                                ['orderForConsumer']['consumer']['id']
                        },
                        cartridges: cartridgeReplacementNext.cartridgeList
                    }

                    _request('POST', '/order/createOrder', null, consumerReplacementCartridgesList)
                }
                else
                    window.alert('Список картриджей для выдачи пуст. Заказ на выдачу не сформирован!')

                cartridgeReplacementNext.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                cartridgeReplacementNext.close()
            }
        }
    ],
    setContent: `
            <h3>Сканируйте серийный номер картриджа</h3>
            <h3 id='consumer-name'></h3>
            <table>
                <thead>
                    <tr>
                         <th>Картридж заменен</th>
                         <th>Модель картриджа</th>
                         <th>S/N замененного картриджа</th>
                    </tr>
                </thead>
                <tbody id="cartridge-list-for-replacement">
                </tbody>
            </table>`,

    method: 'GET',
    path: '/cartridge/getCartrigeBySerialNumber?operation=giveOutCartridge&serialNumber=',
    handler: function (data) {

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

            document.querySelector('tbody').appendChild(tr)
        } else {
            change.textContent = '✅'
            let serialNumberElement = change.parentElement.querySelector('#serialNumber')
            serialNumberElement.textContent = data['serialNumber']
            change.removeAttribute('id')
        }

        cartridgeReplacementNext.cartridgeList.push(data)
    }
})



