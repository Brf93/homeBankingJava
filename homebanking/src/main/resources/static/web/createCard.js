const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          clients : [],
          accounts : [],
          account : '',
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
          nonExpireCards : ''
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
              this.card = this.results.card
              this.cardDate = this.card.map(card => card.thruDate)
              this.fechaFormateada = this.fecha.toISOString().slice(0, 10)
              this.nonExpireCards = this.cardDate.filter( date => date > this.fechaFormateada)
              // this.nonExpireCards = this.card.length - this.expiredCards.length
              this.creditCount = this.card.filter(cardType => cardType.cardType == "CREDIT").filter(date => date.thruDate > this.fechaFormateada)
              console.log(this.nonExpireCards.length)
              this.debitCount = this.card.filter(cardType => cardType.cardType == "DEBIT").filter(date => date.thruDate > this.fechaFormateada)
              console.log(this.category)
              console.log(this.debitCount)
              })
          .catch(error => {console.log(error);})
          },
        createCard(){
            console.log(this.cardType)
            console.log(this.category)
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.category}`)
            .then(() => {
              let toast = new bootstrap.Toast(liveToast)
              toast.show()
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
          // deleteAccount() {
          //   axios.post(`/api/clients/current/cards/delete`, `cardId=${this.cardNumberSelect.id}`)
          //   .then(() => {
          //     let toast = new bootstrap.Toast(liveToast)
          //     toast.show()
          //     setTimeout(()=>{window.location.reload(); }, 2000)
          //   })
          //   .catch(error => { console.log(error); })
          //   //location.reload()
          // },
          logOut(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            setTimeout(()=>{ window.location = ("/web/index.html");}, 300);  
          }
        },
})
app.mount("#app");

