const { createApp } = Vue

const app = createApp({
      data(){
        return{
          user : '',
          password : '',
          validated : '',
          url : '',
          show : false,
          show2 : false,
          animateOut : false,
          animateIn : false,
          userInfo : '',
          community : false,
          associates : false,
          relationships : false,
          experience : false,
          shareholders : false,
          message : ''
        }
      },
      created(){
      },
      methods: {
          getPass(){
            this.userInfo = this.user.toLowerCase().trim()
            this.show = true
            this.show2 = true
            this.validated = 1
            if (this.userInfo.includes("@admin.com"))
            {
              axios.post('/api/login',`email=${this.userInfo}&password=${this.password}`)
              .then((response) => 
              {console.log(response)
              setTimeout(()=>{ window.location = ("/h2-console");}, 1000);})
              .catch(function (error) {
                if (error.response) {
                  setTimeout(() => {
                    let toast = new bootstrap.Toast(wrongCredentialsToast)
                    toast.show(); },1000)
                  setTimeout(()=>{ location.reload();}, 3000)
                  alert(error.response.status);
                  console.log(error.response.headers);
                } else if (error.request) {
                  console.log(error.request);
                } else {
                  console.log('Error', error.message);
                }
                console.log("Hola");
              }); 
            }else{
              axios.post('/api/login',`email=${this.userInfo}&password=${this.password}`)
              .then((response) => {
                console.log(response)
                setTimeout(()=>{ window.location = ("/web/accounts.html");}, 2000);
              })
              .catch(function (error) {
                if (error.response) {
                  setTimeout(() => {
                    let toast = new bootstrap.Toast(wrongCredentialsToast)
                    toast.show(); },1000)
                  setTimeout(()=>{ location.reload();}, 3000)
                } else if (error.request) {
                  console.log(error.request);
                } else {
                  console.log('Error', error.message);
                }
              }); 
            }
          },
          getpass(){
            if(this.show){
              this.show = false
              this.show2 = false
            }else{
              this.show = true
              setTimeout(()=>{this.show2 = true }, 150)
            }
          },
}
})

app.mount("#app");

