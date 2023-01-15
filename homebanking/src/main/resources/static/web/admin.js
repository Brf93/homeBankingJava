const { createApp } = Vue

const app = createApp({
      data(){
        return{
          results : '',
          json : '',
          clients : [],
          accounts : [],
          arrayLength : [],
          addFirstName : '',
          addLastName : '',
          addEmail : '',
          aux : [],
          bandera : 0,
          editFirstName : '',
          editLastName : '',
          editEmail : '',
          validationParam : [],
          enabled : ''
        }
      },
      created(){
        this.getClients()
        
      },
      methods: {
        getClients(){
          axios.get('http://localhost:8080/api/clients')
          .then((result) => {
              this.results = result.data;
              this.clients = [...this.results.map(client => client)]
              this.enabled = this.clients.map(element => element)
              this.json = result
              console.log(this.enabled)
          })
          .catch(error => {console.log(error);})
          axios.get('http://localhost:8080/api/accounts')
          .then((result) => {
            this.accounts = result.data;
            console.log(this.accounts)
            console.log(this.clients.map(element => element.enabled))
            
        })
          },
        addClient(name,lastName,email){
            let aux = ''
            if(name != '' && lastName != '' && email != '' && email.includes('@'))
            {
              aux = {
                firstName : name,
                lastName : lastName,
                email : email,
                accounts : []
              }
            axios.post('http://localhost:8080/rest/clients', (aux))
            .then(() => location.reload())
            .catch(error => {console.log(error)})
            }else{
                  alert("You must complete all the input fields properly")
                }
        },
        deleteClient(){
          let enabled = this.clients.map(element => element.enabled)
          
          console.log(enabled)
          // if(this.clients != 0){
          //   let auxiliar = `http://localhost:8080/api/accounts/${this.validationParam.id}/client`
          //   let clienteAuxiliar = `http://localhost:8080/api/clients/${this.validationParam.id}`
          //   axios.delete(auxiliar)
          // .then((response) => {
          //   alert(`${this.validationParam.firstName} deleted`)
          //   location.reload()})
          // .catch(error => {console.log(error)});
          // axios.delete(clienteAuxiliar).then(() => alert("Cliente borrado")).catch(err => {console.log(err)});
          // console.log(`http://localhost:8080/api/accounts/${this.validationParam.id}/client` + "" + clienteAuxiliar)
          // }
        },
        validation(client,operation){
          if(operation == "edit")
          { 
            this.validationParam = client //Le asigno el valor de cliente del for en html, para poder usar el mismo objeto en las funciones
            return this.bandera = "edit"
          }else if (operation == "delete")
            { 
              this.validationParam = client
              return this.bandera = "delete"
            }else if (operation == "firstName")
            {
              this.validationParam = client
              console.log(this.validationParam.id)
              return this.bandera = "firstName"
            }else if (operation == "lastName")
            { 
              this.validationParam = client
              return this.bandera = "lastName"
            }else(operation == "email")
            { 
              this.validationParam = client
              return this.bandera = "email"
            }
        },
        saludar(){
          console.log(this.validationParam)
        },
        saveName(editInfo){
          if(editInfo != ''){
            axios.patch(`http://localhost:8080/api/clients/${this.validationParam.id}`, {
              firstName: editInfo.firstName
            })
            .then(() => location.reload())
            .catch(error => {console.log(error)})
          }else{
            alert("You must complete all the input fields properly")
          }
        },
        saveLastName(editInfo){
          if(editInfo != ''){
            axios.patch(`http://localhost:8080/rest/clients/${this.validationParam.id}`, {
              lastName: editInfo
            })
            .then(() => location.reload())
            .catch(error => {console.log(error)})
          }else{
            alert("You must complete all the input fields properly")
          }
        },
        saveEmail(editInfo){
          if(editInfo != '' && editInfo.includes('@')){
            axios.patch(this.validationParam._links.self.href, {
              email: editInfo
            })
            .then(() => location.reload())
            .catch(error => {console.log(error)})
          }else{
            alert("You must complete all the input fields properly")
          }
        },
        saveAllChanges(newFirstName,newLastName,newEmail){
          if(newFirstName != '' && newLastName != '' && newEmail != '' && newEmail.includes('@')){
            axios.put(`http://localhost:8080/rest/clients/${this.validationParam.id}`, {
              firstName: newFirstName,
              lastName : newLastName,
              email : newEmail
            })
            .then(() => location.reload())
            .catch(error => {console.log(error)})
          }else{
            alert("You must complete all the input fields properly")
            this.newEmail = ''
          }
          console.log(editInfo + " " +`http://localhost:8080/rest/clients/${this.validationParam.id}`)
        }
  }
})
app.mount("#app");

