const { createApp } = Vue

const app = createApp({
    data() {
        return {
            firstName: '',
            lastName: '',
            passwordRegistration: '',
            email: '',
            validated: '',
            male: '',
            female: '',
            noSay: '',
            other: '',
            gender: '',
            genderOther: '',
            bool : true,
            hidePswd : true,
            avatarName : ''
        }
    },
    created() {
    },
    methods: {
        registration() {
            if(this.firstName != 0 && this.lastName != 0 && this.email != 0 && this.gender != 0 && this.passwordRegistration != 0){
                axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&genderType=${this.gender}&enabled=${this.bool}&password=${this.passwordRegistration}&avatar=${this.avatarName}`)
                .then(setTimeout(()=>{ axios.post('/api/login',`email=${this.email}&password=${this.passwordRegistration}`);}, 500))
                .then(() =>
                    {
                        setTimeout(()=>{ axios.post('/api/clients/current/accounts')},700)
                        setTimeout(()=>{ window.location = ("http://localhost:8080/web/accounts.html");}, 1000);
                        console.log("creado")
                    })
                    .catch(function (error) {
                        if (error.response) {
                          alert(error.response.data);
                          //location.reload()
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
                alert("You must to complete all the fields")
        }
        },
        hidePassword(){
            if(this.hidePswd){
                this.hidePswd = false
            }else{
                this.hidePswd = true
            }
        },
        avatar(avatar){
            console.log(avatar)
            return this.avatarName = avatar
        }
    }
})

app.mount("#app");

