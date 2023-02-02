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
          clientLoans : '',
          destAccount : '',
          amountInput : '',
          descriptionTextArea : '',
          accountDestInput : '',
          transaction : '',
          loanResult : '',
          clientLoan : '',
          loanName : '',
          cardNumber :'',
          accountNumber : '',
          filteredLoan : '',
          maxAmountLoan : '',
          payments : '',
          chosedPayment : '',
          dividePayment : 1,
          monthlyPayment : 1,
          interest : '',
          totalPayment : '',
          formattedAmount : '',
          loanID : '',
          clientAccountsCards : '',
          accountsComplete : '',
          payWithCardAccount: '',
          selectedCardSpan : '',
          dollarUSLocale : '',
          descriptionInput : ''
        }
      },
      created(){
        this.getClients()
        this.getLoans()
      },
      methods : {
          getClients(){
            axios.get('/api/clients/current')
            .then((result) => {
                this.results = result.data;
                this.dollarUSLocale = Intl.NumberFormat("en-US",
                {
                  style: "currency",
                  currency: "USD",
                });
                console.log(this.results);
                this.clients = this.results
                this.clientLoan = this.results.clientLoan
                //this.accounts = this.results.account
                //this.sortedAccount = this.accounts.sort((a,b) => a.id - b.id)
                this.clientLoans = this.results.clientLoan
                console.log(this.clients.account);
                this.accountsComplete = this.clients.account
                console.log(this.accountsComplete);
                let allcards = (this.clients.account.map(e => e.card));
                this.clientAccountsCards = allcards.reduce((a, b) => a.concat(b), []);
                })
                
            .catch(error => {console.log(error);})
            },
            getLoans(){
                axios.get('/api/loans')
            .then((loans) => {
                this.loanResult = loans.data;
                console.log(this.loanResult)
                this.payments = this.loanResult.map(item => item.payments)
                console.log(this.payments)
                // this.monthlyPayment = this.amountInput / this.chosedPayment
                // let dollarUSLocale = Intl.NumberFormat("en-US",
                // {
                //   style: "currency",
                //   currency: "USD",
                // });
                // this.loanAmount = dollarUSLocale.format());
                })
            .catch(error => {console.log(error);})

            },
            logOut(){
              axios.post('/api/logout').then(response => setTimeout(()=>{ window.location = ("/web/Index.html");}, 200))
                
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
            confirmPay(){
                console.log(this.amountInput)
                    if(this.amountInput == '' || this.cardNumber.number == ''){
                        alert("Some fields may be empty")
                    }else{
                        axios.post('/api/pay',{
                            "amount" : this.amountInput,
                            "cvv" : this.cardNumber.cvv,
                            "cardNumber" : this.cardNumber.number,
                            "description" : this.descriptionInput
                        })
                       .then( () => { let toast = new bootstrap.Toast(loanToast)
                        toast.show()
                        this.show2 = true , setTimeout(() => { window.location = ("/web/accounts.html") }, 2500) })
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
                    setTimeout(()=>{ window.location = ("/web/accounts.html");}, 400);
            },
            maxAmount(){
                this.amountInput = this.maxAmountLoan[0]
            }
  },
    computed : 
    {
            paymentsFilter(){
                console.log(this.cardNumber);
                this.payWithCardAccount = this.accountNumber.card
                this.selectedCardSpan = this.cardNumber.number
                console.log(this.payWithCardAccount);
                
                if(this.accountNumber.number != this.cardNumber.account)
                    {
                        this.selectedCardSpan = ''
                    }
                //this.filteredLoan = this.loanResult.filter(loan => loan.name == this.loanName).map(payment => payment.payments)
                //this.maxAmountLoan = this.loanResult.filter(loan => loan.name == this.loanName).map(payment => payment.maxAmount)
                //this.loanID = this.loanResult.filter(loan => loan.name == this.loanName).map(id => id.id)
               // console.log(this.loanID[0])
                //console.log(this.loanName)
                //console.log(this.clientLoans.map(item => item.loanName).includes(item2 => item2.loanName === this.loanName)) VERR
                //this.maxAmountHolder = dollarUSLocale.format(this.maxAmountLoan);
                console.log(this.filteredLoan)                
                this.monthlyPayment = this.dollarUSLocale.format((this.amountInput*this.interest) / this.dividePayment);
                this.totalPayment = this.dollarUSLocale.format(this.amountInput);
                this.formattedAmount = this.dollarUSLocale.format(this.amountInput);
                //this.monthlyPayment = parseInt()
                //this.totalPayment = parseInt(this.amountInput*this.interest)
            }
    }
})
app.mount("#app");

