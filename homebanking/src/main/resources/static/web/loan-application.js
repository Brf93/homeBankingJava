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
          loanName : '',
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
                this.accounts = this.results.account
                this.sortedAccount = this.accounts.sort((a,b) => a.id - b.id)
                this.clientLoans = this.results.clientLoan
                let dollarUSLocale = Intl.NumberFormat("en-US",
                {
                  style: "currency",
                  currency: "USD",
                });
                this.sortedAccount.map( item => item.balance = dollarUSLocale.format(item.balance));
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
              axios.post('/api/logout').then(response => setTimeout(()=>{ window.location = ("/web/index.html");}, 200))
                
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
            confirmLoan(){
                console.log(this.amountInput)
                    if(this.amountInput == '' || this.chosedPayment == '' || this.destAccount == ''){
                        alert("Some fields may be empty")
                    }else{

                        axios.post('/api/loans',{
                            "id" : this.loanID[0],
                            "amount" : this.amountInput,
                            "payments" : this.chosedPayment,
                            "destNumber" : this.destAccount
                        })
                       .then( () => { let toast = new bootstrap.Toast(loanToast)
                        toast.show()
                        this.show2 = true , setTimeout(() => { window.location = ("http://localhost:8080/web/accounts.html") }, 2500) })
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
            },
            maxAmount(){
                this.amountInput = this.maxAmountLoan[0]
            }
  },
    computed : 
    {
            paymentsFilter(){
                let dollarUSLocale = Intl.NumberFormat("en-US",
                {
                  style: "currency",
                  currency: "USD",
                });
                this.filteredLoan = this.loanResult.filter(loan => loan.name == this.loanName).map(payment => payment.payments)
                this.maxAmountLoan = this.loanResult.filter(loan => loan.name == this.loanName).map(payment => payment.maxAmount)
                this.loanID = this.loanResult.filter(loan => loan.name == this.loanName).map(id => id.id)
                console.log(this.loanID[0])
                console.log(this.loanName)
                //console.log(this.clientLoans.map(item => item.loanName).includes(item2 => item2.loanName === this.loanName)) VERR
                //this.maxAmountHolder = dollarUSLocale.format(this.maxAmountLoan);
                console.log(this.filteredLoan)
                if(!this.chosedPayment == ''){
                    this.dividePayment = this.chosedPayment
                }
                if(this.chosedPayment == 6)
                    {
                        this.interest = 1.8;
                    } else if (this.chosedPayment == 12)
                        {
                            this.interest = 1.10;
                        } else if (this.chosedPayment == 24)
                            {
                                this.interest = 1.15;
                            } else if (this.chosedPayment == 36)
                                {
                                    this.interest = 1.18;
                                } else if (this.chosedPayment == 48)
                                    {
                                        this.interest = 1.20;
                                    }else if(this.chosedPayment == 60)
                                        {
                                            this.interest = 1.25;
                                        }else
                                            {
                                                this.interest = 1;
                                            }
                
                this.monthlyPayment = dollarUSLocale.format((this.amountInput*this.interest) / this.dividePayment);
                this.totalPayment = dollarUSLocale.format(this.amountInput*this.interest);
                this.formattedAmount = dollarUSLocale.format(this.amountInput);
                //this.monthlyPayment = parseInt()
                //this.totalPayment = parseInt(this.amountInput*this.interest)
            }
    }
})
app.mount("#app");

