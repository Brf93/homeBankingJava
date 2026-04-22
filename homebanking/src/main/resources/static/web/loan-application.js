const { createApp } = Vue

const app = createApp({
    data() {
        return {
            results: '',
            clients: [],
            accounts: [],
            sortedAccount: [],
            clientLoans: '',
            destAccount: '',
            amountInput: 0,
            loanResult: [],
            loanName: '',
            chosedPayment: '',
            interest: 1,
            dollarUSLocale: Intl.NumberFormat("en-US", {
                style: "currency",
                currency: "USD",
            })
        }
    },
    created() {
        this.getClients()
        this.getLoans()
    },
    methods: {
        getClients() {
            axios.get('/api/clients/current')
                .then((result) => {
                    this.clients = result.data;
                    this.accounts = this.clients.account;
                    this.sortedAccount = [...this.accounts].sort((a, b) => a.id - b.id);
                })
                .catch(error => { console.log(error); })
        },
        getLoans() {
            axios.get('/api/loans')
                .then((loans) => {
                    this.loanResult = loans.data;
                })
                .catch(error => { console.log(error); })
        },
        confirmLoan() {
            if (!this.amountInput || !this.chosedPayment || !this.destAccount || !this.loanName) {
                alert("Please fill in all fields");
                return;
            }
            
            const selectedLoan = this.loanResult.find(l => l.name === this.loanName);
            
            axios.post('/api/loans', {
                "id": selectedLoan.id,
                "amount": this.amountInput,
                "payments": this.chosedPayment,
                "destNumber": this.destAccount,
            })
            .then(() => {
                let toast = new bootstrap.Toast(document.getElementById('loanToast'));
                toast.show();
                setTimeout(() => { window.location = ("/web/accounts.html") }, 2500);
            })
            .catch(error => {
                alert(error.response?.data || "An error occurred");
            });
        },
        cancelTransaction() {
            window.location = "/web/accounts.html";
        },
        maxAmount() {
            this.amountInput = this.maxAmountLoan;
        },
        logOut() {
            axios.post('/api/logout').then(() => {
                window.location.href = "Index.html";
            });
        }
    },
    computed: {
        filteredLoanPayments() {
            const loan = this.loanResult.find(l => l.name === this.loanName);
            return loan ? loan.payments : [];
        },
        maxAmountLoan() {
            const loan = this.loanResult.find(l => l.name === this.loanName);
            return loan ? loan.maxAmount : 0;
        },
        currentInterest() {
            if (!this.chosedPayment) return 1;
            const rates = { 6: 1.08, 12: 1.10, 24: 1.15, 36: 1.18, 48: 1.20, 60: 1.25 };
            return rates[this.chosedPayment] || 1.10;
        },
        totalPayment() {
            return this.dollarUSLocale.format(this.amountInput * this.currentInterest);
        },
        monthlyPayment() {
            if (!this.chosedPayment) return this.dollarUSLocale.format(0);
            return this.dollarUSLocale.format((this.amountInput * this.currentInterest) / this.chosedPayment);
        },
        formattedAmount() {
            return this.dollarUSLocale.format(this.amountInput);
        }
    }
})
app.mount("#app");
