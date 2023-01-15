const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          accountData : '',
          clients : [],
          accounts : [],
          sortedAccount : '',
          orderById : '',
          show : false,
          show2 : false,
          originAccount : '',
          destAccount : '',
          amountInput : 0,
          descriptionTextArea : '',
          accountDestInput : '',
          transaction : ''
        }
      },
      created(){
        this.getClients()
      },
      methods : {
          getClients(){
            axios.get('/api/clients/current')
            .then((result) => {
                this.results = result.data;
                this.accounts = this.results.account
                this.sortedAccount = this.accounts.sort((a,b) => a.id - b.id)
                this.transaction = localStorage.getItem('transaction')
                console.log(this.transaction)
                let dollarUSLocale = Intl.NumberFormat("en-US",
                {
                  style: "currency",
                  currency: "USD",
                });
                this.sortedAccount.map( item => item.balance = dollarUSLocale.format(item.balance));
                })
            .catch(error => {console.log(error);})
            },
            logOut(){
              axios.post('/api/logout').then(response => setTimeout(()=>{ window.location = ("web/index.html");}, 200))
                
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
            alternate(){
                let aux = ''
                aux = this.destAccount
                this.destAccount = this.originAccount
                this.originAccount = aux
                
              },
            confirmTransaction(){
                console.log(this.amountInput)
                if(this.transaction == 'withdrawals'){
                    axios.post('/api/clients/current/transaction',`amount=${this.amountInput}&originNumber=${this.originAccount}&destNumber=${this.destAccount}&descr=${this.descriptionTextArea}`)
                    .then(() => { this.show2 = true , setTimeout(()=>{ window.location = ("http://localhost:8080/web/accounts.html");}, 900);
                    })
                    .catch(function (error) {
                        if (error.response) {
                          alert(error.response.data);
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
                }else{
                    axios.post('/api/clients/current/transaction',`amount=${this.amountInput}&originNumber=${this.originAccount}&destNumber=${this.accountDestInput}&descr=${this.descriptionTextArea}`)
                    .then( () => { this.show2 = true , setTimeout(() => { window.location = ("http://localhost:8080/web/accounts.html") }, 900) })
                    .catch(function (error) {
                        if (error.response) {
                          alert(error.response.data);
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
                }
            },
            cancelTransaction(){
                
                    this.show = true
                    setTimeout(()=>{ window.location = ("http://localhost:8080/web/accounts.html");}, 400);
            }
  }
})
app.mount("#app");

