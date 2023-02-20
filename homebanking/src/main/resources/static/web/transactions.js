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
          amountInput : '',
          descriptionTextArea : '',
          accountDestInput : '',
          transaction : '',
          dollarUSLocale : '',
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
                this.dollarUSLocale = Intl.NumberFormat("en-US",
                {
                  style: "currency",
                  currency: "USD",
                });
                this.sortedAccount.map( item => item.balance = this.dollarUSLocale.format(item.balance));
                })
            .catch(error => {console.log(error);})
            },
            logOut(){
              axios.post('/api/logout').then(response => setTimeout(()=>{ window.location = ("web/Index.html");}, 200))
                
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
                  console.log(parseFloat(this.amountInput));
                  if(this.amountInput == ''){
                    axios.post('/api/clients/current/transaction',`amount=${parseFloat(this.amountInput)}&originNumber=${this.originAccount}&destNumber=${this.destAccount}&descr=${this.descriptionTextArea}`)
                    .then(() => { 
                      let toast = new bootstrap.Toast(transactionToast)
                      toast.show()
                      this.show2 = true , setTimeout(()=>{ window.location = ("/web/accounts.html");}, 2500);
                  })
                  .catch(function (error) {
                      if (error.response) {
                        let errorMessage = document.getElementById("errorMsg")
                        errorMessage.innerHTML = error.response.data;
                        let toast = new bootstrap.Toast(wrongDataToast)
                          toast.show()
                        console.log(error.response.data);
                        console.log(error.response.status);
                        console.log(error.response.headers);
                      } else if (error.request) {
                        console.log(error.request);
                      } else {
                        console.log('Error', error.message);
                      }
                    });
                  }else{
                    axios.post('/api/clients/current/transaction',`amount=${this.amountInput}&originNumber=${this.originAccount}&destNumber=${this.destAccount}&descr=${this.descriptionTextArea}`)
                    .then(() => { 
                        let toast = new bootstrap.Toast(transactionToast)
                        toast.show()
                        this.show2 = true , setTimeout(()=>{ window.location = ("/web/accounts.html");}, 2500);
                    })
                    .catch(function (error) {
                        if (error.response) {
                          let errorMessage = document.getElementById("errorMsg")
                          errorMessage.innerHTML = error.response.data;
                          let toast = new bootstrap.Toast(wrongDataToast)
                            toast.show()
                          console.log(error.response.data);
                          console.log(error.response.status);
                          console.log(error.response.headers);
                        } else if (error.request) {
                          console.log(error.request);
                        } else {
                          console.log('Error', error.message);
                        }
                      });
                  }
                }else{
                  if(this.amountInput == ''){
                    axios.post('/api/clients/current/transaction',`amount=${parseFloat(this.amountInput)}&originNumber=${this.originAccount}&destNumber=${this.accountDestInput}&descr=${this.descriptionTextArea}`)
                  .then( () => { 
                    let toast = new bootstrap.Toast(transactionToast)
                    toast.show()
                    this.show2 = true , setTimeout(() => { window.location = ("/web/accounts.html") }, 2500) })
                  .catch(function (error) {
                      if (error.response) {
                        let errorMessage = document.getElementById("errorMsg")
                        errorMessage.innerHTML = error.response.data;
                        let toast = new bootstrap.Toast(wrongDataToast)
                          toast.show()
                        console.log(this.errorMessage)
                        console.log(error.response.data);
                        console.log(error.response.status);
                        console.log(error.response.headers);
                      } else if (error.request) {
                        console.log(error.request);
                      } else {
                        console.log('Error', error.message);
                      }
                    });}
                    else{
                      axios.post('/api/clients/current/transaction',`amount=${this.amountInput}&originNumber=${this.originAccount}&destNumber=${this.accountDestInput}&descr=${this.descriptionTextArea}`)
                    .then( () => { 
                      let toast = new bootstrap.Toast(transactionToast)
                      toast.show()
                      this.show2 = true , setTimeout(() => { window.location = ("/web/accounts.html") }, 2500) })
                    .catch(function (error) {
                        if (error.response) {
                          let errorMessage = document.getElementById("errorMsg")
                          errorMessage.innerHTML = error.response.data;
                          let toast = new bootstrap.Toast(wrongDataToast)
                          toast.show()
                          console.log(this.errorMessage)
                          console.log(error.response.data);
                          console.log(error.response.status);
                          console.log(error.response.headers);
                        } else if (error.request) {
                          console.log(error.request);
                        } else {
                          console.log('Error', error.message);
                        }
                      });
                    }
                }
            },
            cancelTransaction(){
                    this.show = true
                    setTimeout(()=>{ window.location = ("/web/accounts.html");}, 400);
            }
  }
})
app.mount("#app");

