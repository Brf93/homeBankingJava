const { createApp } = Vue

const app = createApp({
  data() {
    return {
      results: '',
      clients: [],
      clientAccounts: [],
      card: [],
      showCVV: true,
      fecha: new Date(),
      fechaFormateada: '',
      expiredCards : [],
      nonExpireCards : 0,
      cardNumberSelect: ''
    }
  },
  created() {
    this.getClients()
  },
  methods: {
    getClients() {
      axios.get('/api/clients/current')
        .then((result) => {
          this.clients = result.data;
          this.fechaFormateada = this.fecha.toISOString().slice(0, 10);
          this.clientAccounts = this.clients.card || [];
          this.card = this.clientAccounts;
          this.expiredCards = this.card.filter(c => c.thruDate < this.fechaFormateada);
          this.nonExpireCards = this.card.length - this.expiredCards.length;
        })
        .catch(error => { console.log(error); })
    },
    deleteCard() {
      axios.post(`/api/clients/current/cards/delete`, `cardId=${this.cardNumberSelect.id}`)
      .then(() => {
        let toast = new bootstrap.Toast(document.getElementById('liveToast'))
        toast.show()
        setTimeout(()=>{window.location.reload(); }, 2000)
      })
    },
    newCard() {
      window.location.href = "create-cards.html"
    },
    logOut() {
      axios.post('/api/logout').then(() => {
        window.location.href = "Index.html";
      })
    }
  }
})
app.mount("#app");
