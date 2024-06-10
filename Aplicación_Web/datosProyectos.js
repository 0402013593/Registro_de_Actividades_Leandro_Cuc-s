 
  
  

  // Manejador de eventos del botón de cerrar sesión
  
  let tableBody = document.querySelector("tbody");
  let addMeeting = document.querySelector(".add-user"),
      popup = document.querySelector(".popup"),
      addForm = document.querySelector(".add form"),
      updateForm = document.querySelector(".update form");
  
  var user = firebase.auth().currentUser;
  var meetingsRef;
  var database = firebase.database();
  var meetingsRef = firebase.database().ref('Gestion de proyectos/');

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
          var meetingsRef = firebase.database().ref('Gestion de proyectos/' + user.uid,);
         
  
          function writeMeetingData(fecha, crono,hora, latitud, longitud, area, jefe, nombreProyecto, actividad, fechaIn,costo, notas ) {
              let meetingId = meetingsRef.push().key;
              meetingsRef.child(meetingId).set({
                  fecha: fecha,
                  crono:crono,
                  hora: hora,
                  latitud: latitud,
                  longitud: longitud,
                  area: area,
                  jefe: jefe,
                  nombreProyecto:nombreProyecto,
                  actividad: actividad,
                  fechaIn:fechaIn,
                  costo:costo,            
                  notas: notas,
                  
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
                      <td>${meetings[meetingId].latitud}</td>
                      <td>${meetings[meetingId].longitud}</td>
                      <td>${meetings[meetingId].area}</td>
                      <td>${meetings[meetingId].jefe}</td>
                      <td>${meetings[meetingId].nombreProyecto}</td>
                      <td>${meetings[meetingId].actividad}</td>
                      <td>${meetings[meetingId].fechaIn}</td>
                      <td>${meetings[meetingId].costo}</td>
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
                          updateForm.latitud.value = snapshot.val().latitud;
                          updateForm.longitud.value = snapshot.val().longitud;
                          updateForm.area.value = snapshot.val().area;
                          updateForm.jefe.value = snapshot.val().jefe;
                          updateForm.nombreProyecto.value = snapshot.val().nombreProyecto;
                          updateForm.actividad.value = snapshot.val().actividad;
                          updateForm.fechaIn.value = snapshot.val().fechaIn;
                          updateForm.costo.value = snapshot.val().costo;
                          updateForm.notas.value = snapshot.val().notas;
                      }));
  
                      updateForm.addEventListener("submit", (e) => {
                          e.preventDefault();
  
                          meetingsRef.child(meetingId).set({
                              fecha: updateForm.fecha.value,
                              crono: updateForm.crono.value,
                              hora: updateForm.hora.value,
                              latitud: updateForm.latitud.value,
                              longitud: updateForm.longitud.value,
                              area: updateForm.area.value,
                              jefe: updateForm.jefe.value,
                              nombreProyecto: updateForm.nombreProyecto.value,
                              actividad: updateForm.actividad.value,
                              fechaIn: updateForm.fechaIn.value,
                              costo: updateForm.costo.value,                            
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
                      addForm.fecha.value, addForm.crono.value, addForm.hora.value, addForm.latitud.value, addForm.longitud.value, 
                      addForm.area.value, addForm.jefe.value, addForm.nombreProyecto.value, addForm.actividad.value,
                      addForm.fechaIn.value, addForm.costo.value, addForm.notas.value
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
  