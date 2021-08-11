const cartridgeReplacementNext = $.modal({
    footerButtons: [
        {
            text: 'Выдать',
            cssType: 'ok-next',
            handler() {

                _request('PUT', '/consumer/closeOrders', null, cartridgeReplacementNext
                    .cartridgeOrderListForConsumerAfterFirstStep)


                if (cartridgeReplacementNext.cartridgeList.length !== 0) {
                    const consumerReplacementCartridgesList = {
                        consumer: {
                            id: cartridgeReplacementNext
                                .cartridgeOrderListForConsumerAfterFirstStep[0]
                                ['orderForConsumer']['consumer']['id']
                        },
                        cartridges: cartridgeReplacementNext.cartridgeList
                    }

                    _request('POST', '/consumer/createOrder', null, consumerReplacementCartridgesList)
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
    path: '/cartridge/getCartridgeBySerialNumber?operation=giveOutCartridge&serialNumber=',
    handler: function (data) {

        let replace = document.getElementById(data['cartridgeModel']['cartridgeModel'])

        if (replace == null) {
            window.alert('В списке на замену нет такой модели картриджа!')

        } else {
            replace.textContent = '✅'
            let serialNumberElement = replace.parentElement.querySelector('#serialNumber')
            serialNumberElement.textContent = data['serialNumber']
            replace.removeAttribute('id')

            cartridgeReplacementNext.cartridgeList.push(data)
        }

    }
})



