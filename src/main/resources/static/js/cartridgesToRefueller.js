const cartridgesToRefueller = $.modal({
    footerButtons: [
        {
            text: 'Сформировать',
            cssType: 'ok-next',
            handler() {
                if (cartridgesToRefueller.cartridgeList.length !== 0)
                    _request('POST', '/refueller/createOrder', null, cartridgesToRefueller.cartridgeList)
                else
                    window.alert('Список пуст, поручение на заправку не сформировано!')
                cartridgesToRefueller.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                cartridgesToRefueller.close()
            }
        }
    ],
    setContent: `
            <div id="refueller-item"></div>
            <h3>Сканируйте серийный номер картриджа</h3>
            <table>
                <thead>
                    <tr>
                        <th>№</th> 
                        <th>Модель</th>    
                        <th>S/N</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`,

    method: 'GET',
    path: '/cartridge/getCartridgeBySerialNumber?operation=CartridgesToRefueller&serialNumber=',
    handler: function (data) {
        let tbody = document.querySelector('tbody')
        let tr = document.createElement('tr')
        let td = document.createElement('td')

        td.textContent = (cartridgesToRefueller.cartridgeList.length + 1) + ''
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['cartridgeModel']['cartridgeModel']
        tr.appendChild(td)

        td = document.createElement('td')
        td.textContent = data['serialNumber']
        tr.appendChild(td)

        tbody.appendChild(tr)
        cartridgesToRefueller.cartridgeList.push(data)
    }
})



