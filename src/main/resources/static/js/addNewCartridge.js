const addNewCartridge = $.modal({
    footerButtons: [
        {
            text: 'Добавить',
            cssType: 'ok-next',
            handler() {
                if (addNewCartridge.cartridgeList.length !== 0) {
                    const listOfSerialNumbersToAdd = {
                        cartridgeModel: {
                            id: Number.parseInt(document.querySelector('#model-list-select').value)
                        },
                        serialNumbers: addNewCartridge.cartridgeList
                    }

                    _request('POST', '/cartridge/addCartridges', null, listOfSerialNumbersToAdd)
                } else window.alert('Список картриджей для регистрации пуст' +
                    '. Новые картриджы в базу не будут добавлены!')
                addNewCartridge.close()
            }
        },
        {
            text: 'Отмена',
            cssType: 'destroy',
            handler() {
                addNewCartridge.close()
            }
        }
    ],
    setContent: `
            <h3>Сканируйте серийный номер картриджа</h3>
            <h3>Выберите модель картриджа</h3>
            <div id="choose-model-cartridge"></div>
            <table>
                <thead>
                    <tr>
                        <th>S/N</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`,

    method: 'GET',
    path: '/cartridge/checkCartridgeSerialNumber?serialNumber=',
    handler: function (data) {
        let tbody = document.querySelector('tbody')
        let tr = document.createElement('tr')
        let td = document.createElement('td')

        td.textContent = data
        tr.appendChild(td)

        tbody.appendChild(tr)
        addNewCartridge.cartridgeList.push(data)

    }
})



