const { createApp } = Vue

const app = createApp({
  data() {
    return {
      results: '',
      clients: [],
      clientAccounts: '',
      account: '',
      card: '',
      cards : '',
      queryString: '',
      params: '',
      id: '',
      buscarId: '',
      orderById: '',
      showCVV: true,
      cardsTypes: '',
      filterCheck: [],
      checked: [],
      show: true,
      show2: true,
      cardNumberSelect: '',
      fecha: new Date(),
      fechaFormateada: '',
      cardDate: '',
      expiredCards : '',
      nonExpireCards : '',
      prueba : ''
    }
  },
  created() {
    this.getClients()
    //this.buscarIdF()
  },
  methods: {
    getClients() {
      axios.get('/api/clients/current')
        .then((result) => {
          this.results = result.data;
          console.log(this.results)
          this.clients = this.results
          console.log(this.clients.account);
          let allcards = (this.clients.account.map(e => e.card));;
          this.clientAccounts = allcards.reduce((a, b) => a.concat(b), []);
          // this.card = this.account.map(account => account.card)
          // //typeof (this.fecha.toISOString().slice(0, 10))
          // this.cardDate = this.card.map(card => card.thruDate)
          // this.fechaFormateada = this.fecha.toISOString().slice(0, 10)
          // this.expiredCards = this.cardDate.filter( date => date < this.fechaFormateada)
          // this.nonExpireCards = this.card.length - this.expiredCards.length
          // if (this.fecha.toISOString().slice(0, 10) < this.cardDate) { console.log("Mayor") }
          // else {
          // //  console.log("menor")
          // }
        })
        .catch(error => { console.log(error); })
    },
    deleteCard() {
     // console.log(this.cardNumberSelect.id)
      axios.post(`/api/clients/current/cards/delete`, `cardId=${this.cardNumberSelect.id}`)
      .then(() => {
        let toast = new bootstrap.Toast(liveToast)
        toast.show()
        setTimeout(()=>{window.location.reload(); }, 2000)
      })
      .catch(error => { console.log(error); })
      //location.reload()
    },
    buscarIdF() {
      axios.get('/api/clients')
        .then((result) => {
          let dollarUSLocale = Intl.NumberFormat("en-US",
            {
              style: "currency",
              currency: "USD",
            });
          this.orderById.map(item => item.amount = dollarUSLocale.format(item.amount));
        })
        .catch(error => { console.log(error); })
    },
    newCard() {
      window.location.href = ("./create-cards.html")
    },
    menuUp() {
      console.log("menu")
      if (this.show) {
        this.show = false
        this.show2 = false
      } else {
        this.show = true
        setTimeout(() => { this.show2 = true }, 150)
      }
    },
    logOut() {
      axios.post('/api/logout').then(response => console.log('signed out!!!'))
      setTimeout(() => { window.location = ("/web/Index.html"); }, 300);
    }
  },
  // computed:
  // {
  //   filtrar() {
  //     this.filterCheck = this.clientAccounts.filter(card => this.checked.includes(card.cardType) || this.checked.length === 0)
  
  //   }
  // }
})
app.mount("#app");

