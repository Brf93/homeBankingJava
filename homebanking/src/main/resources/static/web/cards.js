const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          clients : [],
          accounts : [],
          account : '',
          card : '',
          queryString : '',
          params : '',
          id : '',
          buscarId : '',
          orderById : '',
          show : true,
          cardsTypes : '',
          filterCheck : [],
          checked : [],
          show : true,
          show2 : true,
        }
      },
      created(){
        this.getClients()
        //this.buscarIdF()
      },
      methods: {
        getClients(){
          axios.get('/api/clients/current')
          .then((result) => {
              this.results = result.data;
              console.log(this.results)
              this.clients = this.results
              this.card = this.clients.card.map(client => client)
              console.log(this.card)
              })
          .catch(error => {console.log(error);})
          },
          showCvvMethod(){
            console.log(this.show)
            if(this.show){
              this.show = false
            }else{
              this.show = true
            }
          },
          buscarIdF(){
            axios.get('/api/clients')
          .then((result) => {
            let dollarUSLocale = Intl.NumberFormat("en-US", 
                {
                  style: "currency",
                  currency: "USD",
                });
            this.orderById.map( item => item.amount = dollarUSLocale.format(item.amount));
          })
          .catch(error => {console.log(error);})
          },
          newCard(){
            window.location.href = ("./create-cards.html")
          },
          menuUp(){
            console.log("menu")
            if(this.show){
              this.show = false
              this.show2 = false
            }else{
              this.show = true
              setTimeout(()=>{this.show2 = true }, 150)
            }
          },
          logOut(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            setTimeout(()=>{ window.location = ("/web/index.html");}, 300);  
          }
  },
  computed :
  {
    filtrar(){
      this.filterCheck = this.card.filter( card => this.checked.includes( card.cardType ) || this.checked.length === 0)
    console.log(this.card)
  }
}
})
app.mount("#app");

