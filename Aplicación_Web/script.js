

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      // El usuario ha iniciado sesión, redirige a la página de destino
      window.location.href = "usuiniciado.html";
    } else {
      // El usuario no ha iniciado sesión, permite que se cargue la página de inicio de sesión
    }
  });

//Ejecutando funciones
document.getElementById("btn__iniciar-sesion").addEventListener("click", iniciarSesion);
document.getElementById("btn__registrarse").addEventListener("click", register);
window.addEventListener("resize", anchoPage);



//Declarando variables
var formulario_login = document.querySelector(".formulario__login");
var formulario_register = document.querySelector(".formulario__register");
var contenedor_login_register = document.querySelector(".contenedor__login-register");
var caja_trasera_login = document.querySelector(".caja__trasera-login");
var caja_trasera_register = document.querySelector(".caja__trasera-register");

    //FUNCIONES

function anchoPage(){

    if (window.innerWidth > 850){
        caja_trasera_register.style.display = "block";
        caja_trasera_login.style.display = "block";
    }else{
        caja_trasera_register.style.display = "block";
        caja_trasera_register.style.opacity = "1";
        caja_trasera_login.style.display = "none";
        formulario_login.style.display = "block";
        contenedor_login_register.style.left = "0px";
        formulario_register.style.display = "none";   
    }
}

anchoPage();


    function iniciarSesion(){
        if (window.innerWidth > 850){
            formulario_login.style.display = "block";
            contenedor_login_register.style.left = "10px";
            formulario_register.style.display = "none";
            caja_trasera_register.style.opacity = "1";
            caja_trasera_login.style.opacity = "0";
        }else{
            formulario_login.style.display = "block";
            contenedor_login_register.style.left = "0px";
            formulario_register.style.display = "none";
            caja_trasera_register.style.display = "block";
            caja_trasera_login.style.display = "none";
        }
    }

    function register(){
        if (window.innerWidth > 850){
            formulario_register.style.display = "block";
            contenedor_login_register.style.left = "410px";
            formulario_login.style.display = "none";
            caja_trasera_register.style.opacity = "0";
            caja_trasera_login.style.opacity = "1";
        }else{
            formulario_register.style.display = "block";
            contenedor_login_register.style.left = "0px";
            formulario_login.style.display = "none";
            caja_trasera_register.style.display = "none";
            caja_trasera_login.style.display = "block";
            caja_trasera_login.style.opacity = "1";
        }
}


//registrar 

    // Obtén una referencia al formulario de registro
const registrarusu = document.querySelector('#registrarusu');

registrarusu.addEventListener('submit', (e) => {
    e.preventDefault();

    // Obtén los valores de los campos de registro
    const registraremail = document.querySelector('#Correoregis').value;
    const registrrarcontraseña = document.querySelector('#Contraseñaregis').value;
    const registrrarconfiontraseña = document.querySelector('#confcontraseña').value;

    if (!registraremail || !registrrarcontraseña || !registrrarconfiontraseña) {
        alert("Por favor, completa todos los campos.");
        return; // Detén el proceso si algún campo está vacío
    }


    if (registrrarcontraseña !== registrrarconfiontraseña) {
        alert("Las contraseñas no coinciden. Por favor, verifica.");
        return; // Detiene el proceso si las contraseñas no coinciden
    }

    // Registra al usuario en Firebase Authentication
    auth.createUserWithEmailAndPassword(registraremail, registrrarcontraseña)
        .then(userCredential => {
            // El usuario se registró correctamente
            console.log('Usuario registrado con éxito:', userCredential.user);
            
            // Redirige al usuario a la página "usuiniciado.html" después del registro
            window.location.href = "usuiniciado.html";
        })
        .catch(error => {
            // Hubo un error en el registro
            console.error('Error al registrar usuario:', error);
            alert("Error al registrar usuario. Por favor, verifique usuario y contraseña e inténtalo de nuevo.");
        });
   


    })


    //Iniciar secion 
    

   // Obtén una referencia al botón de inicio de sesión
   const inicioSesionButton = document.querySelector('#iniciarusu');
//const inicioSesionButton = document.getElementById("Inisesion");

// Agrega un evento click al botón
inicioSesionButton.addEventListener('submit', (e) => {
    e.preventDefault();
    // Obtén los valores del correo electrónico y la contraseña
    const email = document.querySelector('#Correo').value;
    const contraseña = document.querySelector('#Contraseña').value;

    if (!email || !contraseña) {
        alert("Por favor, completa todos los campos.");
        return; // Detén el proceso si algún campo está vacío
    }


    // Por ejemplo, con Firebase Authentication:
    auth
    .signInWithEmailAndPassword(email, contraseña)

        .then(userCredential => {
            // Inicio de sesión exitoso, redirige al usuario a la página de inicio
            window.location.href = "usuiniciado.html";
        })
        .catch(error => {

            if (error.code === "auth/wrong-password" || error.code === "auth/user-not-found") {
                // Mensaje de error personalizado para contraseñas incorrectas o usuarios inexistentes
                alert("Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.");
            } else {
                // Otro tipo de error, muestra un mensaje genérico
                console.error('Error en el inicio de sesión:', error);
                alert("Error al iniciar sesión. Por favor, inténtalo de nuevo.");
            }
           
        });


});





