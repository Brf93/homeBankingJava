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
              this.creditCount = this.card.filter(cardType => cardType.cardType == "CREDIT")
              this.debitCount = this.card.filter(cardType => cardType.cardType == "DEBIT")
              console.log(this.category)
              console.log(this.creditCount)
              })
          .catch(error => {console.log(error);})
          },
        createCard(){
            console.log(this.cardType)
            console.log(this.category)
            let toast = new bootstrap.Toast(liveToast)
            toast.show()
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.category}`)
            .then(setTimeout(()=>{ window.location = ("http://localhost:8080/web/cards.html") ;}, 2000))
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
            setTimeout(()=>{ window.location = ("/web/index.html");}, 300);  
          }
        },
})
app.mount("#app");

