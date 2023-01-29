const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          clients : [],
          arrayLength : [],
          aux : [],
          queryString : '',
          params : '',
          ids : '',
          account : '',
          sortAccount : '',
          totalBalance : '',
          creationFormatDate : '',
          dollarUSLocale : '',
          card : '',
          loans : '',
          formattedBalance : '',
          formattedOneAccountBalance : '',
          show : true,
          show2 : true,
          exchange : true,
          pay : true,
          withdrawals : true,
          address : true,
          transfers : true,
          loanAmountFormat : '',
          accountType : '',
          accountId : '',
          accountNumber : ''

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
              this.dollarUSLocale = Intl.NumberFormat("en-US", 
                {
                  style: "currency",
                  currency: "USD",
                });
              this.clients = this.results
              this.queryString = location.search
              this.params = new URLSearchParams(this.queryString)
              this.ids = this.params.get("id")
              this.clients.account
              this.account = this.clients.account
              this.loans = this.clients.clientLoan
              this.card = this.clients.card
              this.sortAccount = this.account.sort((a,b) => a.id - b.id)
              console.log(this.totalBalance = (this.clients.account).map(e => e.balance).reduce(function (previousValue, currentValue) {
              return previousValue + currentValue;}))
              this.loans.sort((a,b) => a.date - b.date)
              this.loans.map(item => item.amount = this.dollarUSLocale.format(item.amount))
              this.formattedOneAccountBalance = this.clients.account.map( item => item.balance = this.dollarUSLocale.format(item.balance))
              this.formattedBalance = this.dollarUSLocale.format(this.totalBalance)
          })
          .catch(error => {console.log(error);})
          },
          getCard(card){
            if (card == "MASTER"){
              return "fab fa-cc-mastercard fa-1x d-flex justify-content-end"
            }else{
              return "fab fa-cc-visa fa-1x d-flex justify-content-end"
            }
          },
          transaction(){
            setTimeout(()=>{ window.location = ("/web/account.html");}, 500);
          },
          logOut(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            setTimeout(()=>{ window.location = ("/web/index.html");}, 300);  
          },
          menuUp(){
          //  console.log("menu")
            if(this.show){
              this.show = false
              this.show2 = false
            }else{
              this.show = true
              setTimeout(()=>{this.show2 = true }, 150)
            }
          },
          btnOptions(option){
            switch (option) {
              case 'exchange':
                if(this.exchange){
                  this.exchange = false
                }else{
                  this.exchange = true
                }   
                break;
              case 'pay':
                if(this.pay){
                  this.pay = false
                }else{
                  this.pay = true
                }
                break
              case 'withdrawals':
                if(this.withdrawals){
                  this.withdrawals = false
                  localStorage.setItem('transaction','withdrawals')
                 setTimeout(()=>{window.location = ("http://localhost:8080/web/transactions.html")}, 500)
                }else{
                  this.withdrawals = true
                }
                break;
                case 'transfers':
                if(this.withdrawals){
                  this.withdrawals = false
                  localStorage.setItem('transaction','transfer')
                 setTimeout(()=>{window.location = ("http://localhost:8080/web/transactions.html")}, 500)
                }else{
                  this.withdrawals = true
                }
                break;
              case 'address':
                if(this.address){
                  this.address = false
                }else{
                  this.address = true
                }
                break;
            }
          },
          createAccount(){
            axios.post('/api/clients/current/accounts', `accountType=${this.accountType}`)
            .then(() => {
              let toast = new bootstrap.Toast(liveToast)
              toast.show()
              setTimeout(()=>{window.location.reload(); }, 2000)
            }).catch(function (error) {
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
          deleteAccount(){
            axios.post('/api/clients/current/accounts/delete', `accountId=${this.accountId.id}`)
            .then(() => {
              let toastDeleting = new bootstrap.Toast(deleteToast)
              toastDeleting.show()
              setTimeout(()=>{window.location.reload(); }, 2000)
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
          }
    }
})
app.mount("#app");

