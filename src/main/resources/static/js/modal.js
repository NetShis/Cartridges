const $ = {}

$.modal = function (options) {

    function _createModal(options) {
        const modal = document.createElement('div')
        modal.insertAdjacentHTML('afterbegin', `
        <div class="modal-overlay">
            <div class="modal-window">
                ${options.setContent}
                <div class="footer"></div>
            </div>
        </div>`)
        return modal
    }

    function _getAllConsumers() {
        const select = document.createElement('select')
        select.setAttribute('class', 'list')
        select.setAttribute('id', 'consumer-list-select')

        _request('GET', '/consumer/getAllConsumers', data => {
            data.forEach(consumer => {
                const option = document.createElement('option')
                option.textContent = consumer['nameOfConsumer']
                option.setAttribute('value', consumer['id'])
                select.appendChild(option)
            })
        })

        return select
    }

    function _getAllModelCartridge() {
        const select = document.createElement('select')
        select.setAttribute('class', 'list')
        select.setAttribute('id', 'model-list-select')

        _request('GET', '/cartridge/getAllModels', data => {
            data.forEach(model => {
                const option = document.createElement('option')
                option.textContent = model['cartridgeModel']
                option.setAttribute('value', model['id'])
                select.appendChild(option)
            })
        })

        return select

    }


    const returnModal = {
        myModal: '',
        cartridgeList: [],
        serialNumbersReceived: [],
        cartridgeOrderList: [],
        allStatus: [],
        cartridgeOrderListAfterTakeStep: [],

        open() {
            this.serialNumbersReceived = []
            this.cartridgeList = []
            this.cartridgeOrderList = []

            _request('GET', '/cartridge/getAllStatus',
                data => this.allStatus = data)

            this.myModal = _createModal(options)

            document.body.appendChild(this.myModal)

            const footer = document.querySelector('.footer')
            options.footerButtons.forEach(btn => {
                const $btn = document.createElement('button')
                $btn.textContent = btn.text
                $btn.classList.add(btn.cssType)
                $btn.onclick = btn.handler
                footer.appendChild($btn)
            })

            const consumerList = document.querySelector('#consumer-list')
            if (consumerList !== null)
                consumerList.appendChild(_getAllConsumers())

            const chooseModelCartridge = document.querySelector('#choose-model-cartridge')
            if (chooseModelCartridge !== null) {
                chooseModelCartridge.appendChild(_getAllModelCartridge())
            }


            const cartridgeListForReplacement = document.querySelector('#cartridge-list-for-replacement')
            if (cartridgeListForReplacement !== null) {
                document.querySelector('#consumer-name').textContent
                    = 'Для пользователя: ' + this.cartridgeOrderListAfterTakeStep[0]
                    ['orderForConsumer']['consumer']['nameOfConsumer']

                for (let orderElement of this.cartridgeOrderListAfterTakeStep) {
                    const tr = document.createElement('tr')
                    let td = document.createElement('td')

                    td.setAttribute('id', orderElement['cartridge']['cartridgeModel']['cartridgeModel'])
                    tr.appendChild(td)

                    td = document.createElement('td')
                    td.textContent = orderElement['cartridge']['cartridgeModel']['cartridgeModel']
                    tr.appendChild(td)

                    td = document.createElement('td')
                    td.setAttribute('id', 'serialNumber')
                    tr.appendChild(td)

                    cartridgeListForReplacement.appendChild(tr)
                }
            }


            document.onpaste = event => {
                let serialNumber = event.clipboardData.getData('text/plain')
                if (this.serialNumbersReceived.includes(serialNumber))
                    window.alert('S/N ' + serialNumber + ' уже сканировался')
                else {
                    _request(options.method, options.path + serialNumber, options.handler)
                    this.serialNumbersReceived.push(serialNumber)
                }
            }
        },
        close() {
            options.footerButtons.forEach(btn =>
                document.querySelector('.'.concat(btn.cssType))
                    .removeEventListener('click', btn.handler))

            this.myModal.parentNode.removeChild(this.myModal)
        }
    }

    return returnModal
}
