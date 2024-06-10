
  
  
  

  // Manejador de eventos del botón de cerrar sesión
  
  let tableBody = document.querySelector("tbody");
  let addMeeting = document.querySelector(".add-user"),
      popup = document.querySelector(".popup"),
      addForm = document.querySelector(".add form"),
      updateForm = document.querySelector(".update form");
  
  var user = firebase.auth().currentUser;
  var meetingsRef;
  var database = firebase.database();
  var meetingsRef = firebase.database().ref('Entrevistas/');
  
  // Función para cerrar sesión

function cerrarSesion() {
    auth.signOut()
      .then(() => {
        console.log('Sesión cerrada exitosamente');
        window.location.href = "índice.html";
      })
      .catch(error => {
        console.error('Error al cerrar sesión:', error);
      });
  }
  
  
  // Manejador de eventos del botón de cerrar sesión
  const logout = document.querySelector('#logout');
  
  if (logout) {
      logout.addEventListener('click', function (e) {
          e.preventDefault();
          cerrarSesion();
      });
  }
  
  // Configurar el observador de autenticación
  firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
          // Si hay un usuario autenticado, configura la referencia a la base de datos específica del usuario
          var meetingsRef = firebase.database().ref('Entrevistas/' + user.uid,);
         
  
          function writeMeetingData(fecha,crono, hora, areaDeTrabajo, nombre, edad, telefono, correo, tiempo, situacion, nivel, 
            horas, idioma,habilidades, notas ) {
              let meetingId = meetingsRef.push().key;
              meetingsRef.child(meetingId).set({
                  fecha: fecha,
                  crono:crono,
                  hora: hora,
                  areaDeTrabajo: areaDeTrabajo,
                  nombre: nombre,
                  edad:edad,
                  telefono: telefono,
                  correo: correo,
                  tiempo:tiempo,            
                  situacion: situacion,
                  nivel : nivel,
                  horas: horas,
                  idioma:idioma,
                  habilidades:habilidades,
                  notas: notas
                  
                  
              }).then((onFullFilled) => {
                  console.log("writed");
                  popup.classList.remove("active");
                  addForm.reset();
                  updateForm.reset();
                  location.reload();
              }, (onRejected) => {
                  console.log(onRejected);
                  addForm.reset();
                  updateForm.reset();
              });
          }
  
          meetingsRef.on('value', (snapshot) => {
              const meetings = snapshot.val();
              tableBody.innerHTML = "";
              for (meetingId in meetings) {
                  let tr = `
                 
                  <tr data-id=${meetingId}>
                      <td>${meetings[meetingId].fecha}</td>
                      <td>${meetings[meetingId].crono}</td>
                      <td>${meetings[meetingId].hora}</td>
                      <td>${meetings[meetingId].areaDeTrabajo}</td>
                      <td>${meetings[meetingId].nombre}</td>
                      <td>${meetings[meetingId].edad}</td>
                      <td>${meetings[meetingId].telefono}</td>
                      <td>${meetings[meetingId].correo}</td>
                      <td>${meetings[meetingId].tiempo}</td>
                      <td>${meetings[meetingId].situacion}</td>
                      <td>${meetings[meetingId].nivel}</td>
                      <td>${meetings[meetingId].horas}</td>
                      <td>${meetings[meetingId].idioma}</td>
                      <td>${meetings[meetingId].habilidades}</td>
                      <td>${meetings[meetingId].notas}</td>
                      <td>
                          <button class="edit">Editar</button>
                          <button class="delete">Borrar</button>
                      </td>
                  </tr>`;
                  tableBody.innerHTML += tr;
              }
  
              let editButtons = document.querySelectorAll(".edit");
              editButtons.forEach(edit => {
                  edit.addEventListener("click", () => {
                      document.querySelector(".update").classList.add("active");
                      let meetingId = edit.parentElement.parentElement.dataset.id;
                      meetingsRef.child(meetingId).get().then((snapshot => {

                      

                          updateForm.fecha.value = snapshot.val().fecha;
                          updateForm.crono.value = snapshot.val().crono;
                          updateForm.hora.value = snapshot.val().hora;
                          updateForm.areaDeTrabajo.value = snapshot.val().areaDeTrabajo;
                          updateForm.nombre.value = snapshot.val().nombre;
                          updateForm.edad.value = snapshot.val().edad;
                          updateForm.telefono.value = snapshot.val().telefono;
                          updateForm.correo.value = snapshot.val().correo;
                          updateForm.tiempo.value = snapshot.val().tiempo;
                          updateForm.situacion.value = snapshot.val().situacion;
                          updateForm.nivel.value = snapshot.val().nivel;
                          updateForm.horas.value = snapshot.val().horas;
                          updateForm.idioma.value = snapshot.val().idioma;
                          updateForm.habilidades.value = snapshot.val().habilidades;
                          updateForm.notas.value = snapshot.val().notas;
                      }));
  
                      updateForm.addEventListener("submit", (e) => {
                          e.preventDefault();
  
                          meetingsRef.child(meetingId).set({
                              fecha: updateForm.fecha.value,
                              crono: updateForm.crono.value,
                              hora: updateForm.hora.value,
                              areaDeTrabajo: updateForm.areaDeTrabajo.value,
                              nombre: updateForm.nombre.value,
                              edad: updateForm.edad.value,
                              telefono: updateForm.telefono.value,
                              correo: updateForm.correo.value,
                              tiempo: updateForm.tiempo.value,
                              situacion: updateForm.situacion.value,
                              nivel: updateForm.nivel.value,                            
                              horas: updateForm.horas.value,
                              idioma: updateForm.idioma.value,
                              habilidades: updateForm.habilidades.value,
                              notas: updateForm.notas.value
                              
                          }).then((onFullFilled) => {
                              alert("Registro modificado exitosamente");
                              document.querySelector(".update").classList.remove("active");
                              updateForm.reset();
                              location.reload();
                          }, (onRejected) => {
                              console.log(onRejected);
                          });
                      });
                  });
              });
  
              let deleteButtons = document.querySelectorAll(".delete");
              deleteButtons.forEach(deleteBtn => {
                  deleteBtn.addEventListener("click", () => {
                      let meetingId = deleteBtn.parentElement.parentElement.dataset.id;
                      if (confirm("¿Está Seguro que desea eliminar el registro?")) {
                          meetingsRef.child(meetingId).remove().then(() => {
                              alert("Registro eliminado");
                          });
                      }
                  });
              });
          });
  
          addMeeting.addEventListener("click", () => {
              document.querySelector(".add").classList.add("active");
              addForm.addEventListener("submit", (e) => {
                  e.preventDefault();
                  writeMeetingData(

                      addForm.fecha.value,  addForm.crono.value, addForm.hora.value, addForm.areaDeTrabajo.value, addForm.nombre.value, 
                      addForm.edad.value, addForm.telefono.value, addForm.correo.value, addForm.tiempo.value,
                      addForm.situacion.value, addForm.nivel.value, addForm.horas.value, addForm.idioma.value,
                       addForm.habilidades.value, addForm.notas.value
                  );
              });
          });


      
          window.addEventListener("click", (e) => {
              if (e.target == popup) {
                  popup.classList.remove("active");
                  addForm.reset();
                  updateForm.reset();
                  location.reload();
              }
          });
  
      } else {
          // Manejar el caso donde no hay usuario autenticado
          console.error("Usuario no autenticado.");
      }
  });
  
  document.getElementById('datosmapa').addEventListener('click', function () {
      window.location.href = 'usuiniciado.html';
  });
  