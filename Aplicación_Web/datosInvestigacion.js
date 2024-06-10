
  

  // Manejador de eventos del botón de cerrar sesión
  
  let tableBody = document.querySelector("tbody");
  let addMeeting = document.querySelector(".add-user"),
      popup = document.querySelector(".popup"),
      addForm = document.querySelector(".add form"),
      updateForm = document.querySelector(".update form");
  
  var user = firebase.auth().currentUser;
  var meetingsRef;
  var database = firebase.database();
  var meetingsRef = firebase.database().ref('Investigacion/');

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
          var meetingsRef = firebase.database().ref('Investigacion/' + user.uid,);
         
  
          function writeMeetingData(fecha, crono,hora, titulo, area, nombre, avance, resultados, presupuesto, recordatorio ) {
              let meetingId = meetingsRef.push().key;
              meetingsRef.child(meetingId).set({
                  fecha: fecha,
                  crono:crono,
                  hora: hora,
                  titulo:titulo,
                  area:area,
                  nombre: nombre,
                  avance:avance,
                  resultados:resultados,
                  presupuesto:presupuesto,
                  recordatorio:recordatorio
                                   
                  
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
                      <td>${meetings[meetingId].titulo}</td>
                      <td>${meetings[meetingId].area}</td>
                      <td>${meetings[meetingId].nombre}</td>
                      <td>${meetings[meetingId].avance}</td>
                      <td>${meetings[meetingId].resultados}</td>
                      <td>${meetings[meetingId].presupuesto}</td>
                      <td>${meetings[meetingId].recordatorio}</td>
                      
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
                          updateForm.titulo.value = snapshot.val().titulo;
                          updateForm.area.value = snapshot.val().area;
                          updateForm.nombre.value = snapshot.val().nombre;
                          updateForm.avance.value = snapshot.val().avance;
                          updateForm.resultados.value = snapshot.val().resultados;
                          updateForm.presupuesto.value = snapshot.val().presupuesto;
                          updateForm.recordatorio.value = snapshot.val().recordatorio;
                         
                      }));
  
                      updateForm.addEventListener("submit", (e) => {
                          e.preventDefault();            


                          meetingsRef.child(meetingId).set({
                              fecha: updateForm.fecha.value,
                              crono: updateForm.crono.value,
                              hora: updateForm.hora.value,
                              titulo: updateForm.titulo.value,
                              area: updateForm.area.value,
                              nombre: updateForm.nombre.value,
                              avance: updateForm.avance.value,
                              resultados: updateForm.resultados.value,
                              presupuesto: updateForm.presupuesto.value,
                              recordatorio: updateForm.recordatorio.value,
                              
                              
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

                      addForm.fecha.value, addForm.crono.value, addForm.hora.value, addForm.titulo.value, addForm.area.value, 
                      addForm.nombre.value, addForm.avance.value, addForm.resultados.value, addForm.presupuesto.value,
                      addForm.recordatorio.value
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
  