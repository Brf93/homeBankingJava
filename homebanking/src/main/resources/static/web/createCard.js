const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          clients : [],
          accounts : [],
          clientAccounts: [],
          account : '',
          accountId : '',
          card : [],
          silverClass : false,
          goldClass : false,
          titaniumClass : false,
          category : '',
          cardType : '',
          debitCount : 0,
          creditCount : 0,
          fecha: new Date(),
          fechaFormateada: '',
          cardDate: '',
          expiredCards : [],
          nonExpireCards : 0,
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
              this.clients = result.data;
              this.fechaFormateada = this.fecha.toISOString().slice(0, 10);
              
              // Obtener tarjetas directamente del cliente
              this.clientAccounts = this.clients.card || [];
              this.card = this.clientAccounts;
              
              // Filtrar tarjetas no expiradas
              let validCards = this.card.filter(c => c.thruDate >= this.fechaFormateada);
              
              this.creditCount = validCards.filter(c => c.cardType == "CREDIT").length;
              this.debitCount = validCards.filter(c => c.cardType == "DEBIT").length;
              this.nonExpireCards = validCards.length;
              
              console.log("Estado de tarjetas:", {
                total: this.card.length,
                validas: this.nonExpireCards,
                credit: this.creditCount,
                debit: this.debitCount
              });
          })
          .catch(error => {console.log(error);})
        },
        createCard(){
            if(!this.cardType || !this.category || !this.accountId) {
              alert("Please complete all fields");
              return;
            }
            
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.category}&id=${this.accountId}`)
            .then(() => {
              let toast = new bootstrap.Toast(liveToast)
              toast.show()
              setTimeout(()=>{ window.location = ("/web/cards.html") ;}, 2000)
            })
            .catch(function (error) {
              if (error.response) {
                alert(error.response.data);
              } else {
                console.log('Error', error.message);
              }
            }); 
        },
        logOut(){
            axios.post('/api/logout').then(response => {
              window.location.href = "Index.html";
            })
        }
      }
})
app.mount("#app");
