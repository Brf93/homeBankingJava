const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          clients : [],
          accounts : [],
          account : '',
          accountId : '',
          card : '',
          silverClass : false,
          goldClass : false,
          titaniumClass : false,
          category : '',
          cardType : '',
          debitCount : '',
          creditCount : '',
          fecha: new Date(),
          fechaFormateada: '',
          cardDate: '',
          expiredCards : '',
          nonExpireCards : '',
          dollarUSLocale : ''
        }
      },
      created(){
        this.getClients()
      },
      methods: {
        getClients(){
          axios.get('/api/clients/current')
          .then((result) => {
              this.results = result.data; 
              console.log(this.results)
              this.clients = this.results
              console.log(this.clients.account[0]);
              this.clientAccounts = (this.clients.account.map(e => e.card).filter(e => e.length >0));
              console.log(this.clientAccounts[0]);
              this.cardDate = this.clientAccounts[0].map(card => card.thruDate)
              console.log(this.cardDate);
              this.fechaFormateada = this.fecha.toISOString().slice(0, 10)
              this.nonExpireCards = this.cardDate.filter( date => date > this.fechaFormateada)
              this.nonExpireCards = this.clientAccounts[0].length - this.expiredCards.length
              this.creditCount = this.clientAccounts[0].filter(cardType => cardType.cardType == "CREDIT").filter(date => date.thruDate > this.fechaFormateada)
              console.log(this.nonExpireCards.length)
              this.debitCount = this.clientAccounts[0].filter(cardType => cardType.cardType == "DEBIT").filter(date => date.thruDate > this.fechaFormateada)
              console.log(this.category)
              console.log(this.debitCount)
              console.log(this.accountId);
              })
          .catch(error => {console.log(error);})
          },
        createCard(){
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.category}&id=${this.accountId}`)
            .then(() => {
              let toast = new bootstrap.Toast(liveToast)
              toast.show()
              console.log(this.accountId)
              setTimeout(()=>{ window.location = ("/web/cards.html") ;}, 2000)
            })
            .catch(function (error) {
              if (error.response) {
                alert(error.response.data);
                location.reload()
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
              } else if (error.request) {
                console.log(error.request);
              } else {
                console.log('Error', error.message);
              }
              console.log("Hola");
            }); 
          },
          logOut(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            setTimeout(()=>{ window.location = ("/web/Index.html");}, 300);  
          }
        },
})
app.mount("#app");

