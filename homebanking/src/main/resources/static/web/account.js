const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify(){
      data(),
}
const app = createApp({
      data(){
        return{
          results : '',
          accountData : '',
          clients : [],
          ids : '',
          accounts : [],
          account : '',
          card : '',
          queryString : '',
          params : '',
          buscarId : '',
          orderById : '',
          show : true,
          show2 : true,
        }
      },
      created(){
        this.getAccount()
        //this.getClients()
      },
      methods : {
        getAccount(){
            axios.get('/api/clients/current/')
          .then((result) => {
            this.accountData = result.data
            this.queryString = location.search
            const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
            const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
            this.params = new URLSearchParams(this.queryString)
            this.ids = this.params.get("id")
            console.log(this.ids)
            this.account = this.accountData.account.filter(item => item.id == this.ids)
            //this.account.sort((a,b) => a.id - b.id)
            this.orderById = this.account[0].transactions.sort((a,b) => b.id - a.id)
            // this.buscarId = this.accountData.find(account => (account.id == this.ids))
           // console.log(this.account[0].number)
            //this.orderById = this.accountData.transactions.sort((a,b) => b.id - a.id)
            let dollarUSLocale = Intl.NumberFormat("en-US",
                {
                  style: "currency",
                  currency: "USD",
                });
            this.account.map( item => item.balance = dollarUSLocale.format(item.balance));
            this.orderById.map( item => item.amount = dollarUSLocale.format(item.amount));
            this.orderById.map( item => item.afterBalance = dollarUSLocale.format(item.afterBalance));
          })
          .catch(error => {console.log(error);})
          },
          getClients(){
            axios.get('/api/clients/current')
            .then((result) => {
                // this.results = result.data;
                // console.log(this.results)
                // this.queryString = location.search 
                // this.params = new URLSearchParams(this.queryString)
                // this.ids = this.params.get("id")
                // console.log(this.ids)
                // console.log(this.results.map(result => result.account))
                // this.account = this.clients.map(client => client.account)
                // this.accounts = this.clients[0].account 
                // console.log(this.accounts)
                
                // console.log(this.buscarId.number)
                })
            .catch(error => {console.log(error);})
            },
            getType(type){
              if (type == "CREDIT"){
                return "text-success fw-bold"
              }else{
                return "text-danger fw-bold"
              }
            },
            logOut(){
              axios.post('/api/logout').then(response => setTimeout(()=>{ window.location = ("web/index.html");}, 300))
                
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
            }
  }
})
app.mount("#app");
app.use(vuetify).mount('#app')

