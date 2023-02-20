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
            avatarName : '',
            errorMsg : ''
        }
    },
    created() {
    },
    methods: {
        registration() {
            if( this.gender == ''){ 
                let errorMessage = document.getElementById("errorMsg")
                errorMessage.innerHTML = 'Please choose a gender, or select "Prefer not to say"';
                let toast = new bootstrap.Toast(wrongDataToast)
                toast.show()
                console.log()
            }else{
                    axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&genderType=${this.gender}&enabled=${this.bool}&password=${this.passwordRegistration}&avatar=${this.avatarName}`)
                    .then(() => {
                        let toast = new bootstrap.Toast(toastRegistration)
                        toast.show()
                        setTimeout(()=>{ 
                        window.location = ("/web/Index.html");}, 2500)
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
                                console.log("Hola");
                            } else if (error.request) {
                            console.log(error.request);
                            } else {
                            console.log('Error', error.message);
                            }
                            console.log("Hola");
                        });
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

