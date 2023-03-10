const { createApp } = Vue

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
          dialog: false,
        }
      },
      created(){
        this.getAccount()
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
            this.account = this.accountData.account.filter(item => item.id == this.ids)
            this.orderById = this.account[0].transactions.sort((a,b) => b.id - a.id)
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
            getType(type){
              if (type == "CREDIT"){
                return "text-success fw-bold"
              }else{
                return "text-danger fw-bold"
              }
            },
            logOut(){
              axios.post('/api/logout').then(response => setTimeout(()=>{ window.location = ("web/Index.html");}, 1000)) 
            },
            menuUp(){
             // console.log("menu")
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

