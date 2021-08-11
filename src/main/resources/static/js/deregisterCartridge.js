
const deregisterCartridge = $.modal({
    footerButtons: [
        {
            text: 'Списать',
            cssType: 'ok-next',
            handler() {
                if (deregisterCartridge.cartridgeList.length !==0)
                    _request('PUT','/cartridge/deregister',null, deregisterCartridge.cartridgeList)
                else window.alert('Список картриджей пуст, картриджы не будут списаны! ')
                deregisterCartridge.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                deregisterCartridge.close()
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
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`,

    method: 'GET',
    path: '/cartridge/getCartridgeBySerialNumber?operation=DeregisterCartridge&serialNumber=',
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

        tbody.appendChild(tr)
        deregisterCartridge.cartridgeList.push(data)
    }
})



