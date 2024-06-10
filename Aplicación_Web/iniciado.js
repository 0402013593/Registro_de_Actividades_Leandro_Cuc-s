
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
const logout = document.querySelector('#logouut');

if (logout) {
  logout.addEventListener('click', function (e) {
    e.preventDefault();
    cerrarSesion();
  });
}
// Funcion para ir a la pantalla de Datos en tabla

const irReuniones = document.querySelector('#dreuniones');

irReuniones.addEventListener('click', function (e) {   
  window.location.href = "datosreuniones.html";
  
});

const irProyectos= document.querySelector('#dproyectos');

irProyectos.addEventListener('click', function (e) {   
  window.location.href = "datosproyectos.html";
  
});

const irEntrevistas= document.querySelector('#dentrevistas');

irEntrevistas.addEventListener('click', function (e) {   
  window.location.href = "datosentrevistas.html";
  
});

const irConferencias= document.querySelector('#dconferencias');

irConferencias.addEventListener('click', function (e) {   
  window.location.href = "datosconferencias.html";
  
});

const irInvestigacion= document.querySelector('#dinvestigacion');

irInvestigacion.addEventListener('click', function (e) {   
  window.location.href = "datosinvestigacion.html";
  
});



//Crear el mapa con Leaflet
const map = L.map('map').setView([-1.8312, -78.1834], 6);// Cambia las coordenadas y el nivel de zoom según tu preferencia

// Añadir un mapa base (OpenStreetMap)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© OpenStreetMap contributors'
}).addTo(map);

// Obtener datos de Firebase y agregar marcadores al mapa
firebase.auth().onAuthStateChanged(user => {
  if (user) {
    const userId = user.uid;
    const reunionesRef = database.ref(`Reuniones/${userId}`);
    const entrevistasRef = database.ref(`Entrevistas/${userId}`);
    const proyectosRef = database.ref(`Gestion de proyectos/${userId}`);
    const conferencuasRef = database.ref(`Conferencias/${userId}`);
    const investigacionRef = database.ref(`Investigacion/${userId}`);

    const reunionIcon = L.icon({
      iconUrl: 'imagenes/reuniones.png',
      iconSize: [40, 40],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32],
      shadowUrl: 'imagenes/fondo.png', 
      shadowSize: [41, 41],
      shadowAnchor: [16, 32],
    });

    const entrevistaIcon = L.icon({
      iconUrl: 'imagenes/entrevista.png',
      iconSize: [40, 40],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32],
      shadowUrl: 'imagenes/fondo.png', 
      shadowSize: [41, 41],
      shadowAnchor: [16, 32],
    });

    const proyectosIcon = L.icon({
      iconUrl: 'imagenes/proyectos.png',
      iconSize: [40, 40],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32],
      shadowUrl: 'imagenes/fondo.png', 
      shadowSize: [41, 41],
      shadowAnchor: [16, 32],
    });

    const conferenciaIcon = L.icon({
      iconUrl: 'imagenes/conferencia.png',
      iconSize: [40, 40],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32],
      shadowUrl: 'imagenes/fondo.png', 
      shadowSize: [41, 41],
      shadowAnchor: [16, 32],
    });

    const investigacionIcon = L.icon({
      iconUrl: 'imagenes/investigavion.png',
      iconSize: [40, 40],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32],
      shadowUrl: 'imagenes/fondo.png', 
      shadowSize: [41, 41],
      shadowAnchor: [16, 32],
    });


    reunionesRef.once('value')
      .then(snapshot => {
        const reuniones = snapshot.val();

        if (reuniones) {
          Object.entries(reuniones).forEach(([reunionId, reunion]) => {
            const { crono,latitud, longitud, fecha, hora, integrantes, notas, personasAusentes, personasPresentes, resumen, tematica } = reunion;

            // Crea un marcador y ábrelo con información relevante
            const marker = L.marker([parseFloat(latitud), parseFloat(longitud)],{ icon: reunionIcon })
              .addTo(map)
              .bindPopup(`
                <strong>Reuniones</strong><br>
                <strong>Fecha:</strong> ${fecha}<br>
                <strong>Duración del registro</strong> ${crono}<br>
                <strong>Hora:</strong> ${hora}<br>
                <strong>Integrantes:</strong> ${integrantes}<br>
                <strong>Personas Ausentes:</strong> ${personasAusentes}<br>
                <strong>Personas Presentes:</strong> ${personasPresentes}<br>
                <strong>Temática:</strong> ${tematica}<br>
                <strong>Resumen:</strong> ${resumen}<br>
                <strong>Notas:</strong> ${notas}
              `);
          });
        }
      })

      entrevistasRef.once('value')
      .then(snapshot => {
        const Entre = snapshot.val();

        if (Entre) {
          Object.entries(Entre).forEach(([reunionId, reunion]) => {
            const { crono,latitud, longitud, fecha, hora, areaDeTrabajo,nombre,edad,telefono,correo,tiempo,situacion,nivel,horas,idioma,habilidades,notas } = reunion;

            // Crea un marcador y ábrelo con información relevante
            const marker = L.marker([parseFloat(latitud), parseFloat(longitud)], {icon: entrevistaIcon})
              .addTo(map)
              .bindPopup(`
                <strong>Entrevistas</strong><br>
                <strong>Fecha:</strong> ${fecha}<br>
                <strong>Duración del registro</strong> ${crono}<br>
                <strong>Hora:</strong> ${hora}<br>
                <strong>Area de trabajo:</strong> ${areaDeTrabajo}<br>
                <strong>Nombre:</strong> ${nombre}<br>
                <strong>Edad:</strong> ${edad}<br>
                <strong>Telefono:</strong> ${telefono}<br>
                <strong>Correo:</strong> ${correo}<br>
                <strong>Tiempo:</strong> ${tiempo}<br>
                <strong>Situacion Laboral:</strong> ${situacion}<br>
                <strong>Nivel de educación:</strong> ${nivel}<br>
                <strong>Horas de experiencia laboral:</strong> ${horas}<br>
                <strong>Idiomas:</strong> ${idioma}<br>
                <strong>Havilidades:</strong> ${habilidades}<br>
                <strong>Observaciones:</strong> ${notas}
              `);
          });
        }
      })


      proyectosRef.once('value')
      .then(snapshot => {
        const Entre = snapshot.val();

        if (Entre) {
          Object.entries(Entre).forEach(([reunionId, reunion]) => {
            const { crono,latitud, longitud, fecha, hora, area,jefe,nombreProyecto,actividad,fechaIn,costo,notas } = reunion;

            // Crea un marcador y ábrelo con información relevante
            const marker = L.marker([parseFloat(latitud), parseFloat(longitud)], {icon: proyectosIcon})
              .addTo(map)
              .bindPopup(`
                <strong>Gestion de Proyectos</strong><br>
                <strong>Fecha:</strong> ${fecha}<br>
                <strong>Duración del registro</strong> ${crono}<br>
                <strong>Hora:</strong> ${hora}<br>
                <strong>Area de trabajo:</strong> ${area}<br>
                <strong>Nombre del jefe de area:</strong> ${jefe}<br>
                <strong>Nombre del proyecto:</strong> ${nombreProyecto}<br>
                <strong>tipo de actividad:</strong> ${actividad}<br>
                <strong>Fecha de inicio :</strong> ${fechaIn}<br>
                <strong>Presupuesto estimado:</strong> ${costo}<br>
                <strong>Observaciones o comentarios:</strong> ${notas}
              `);
          });
        }
      })



      conferencuasRef.once('value')
      .then(snapshot => {
        const Entre = snapshot.val();

        if (Entre) {
          Object.entries(Entre).forEach(([reunionId, reunion]) => {
            const { crono,latitud, longitud, fecha, hora,nombreDelEvento,nombreDelPonente,tematica,duracionPonencia,notas } = reunion;

            // Crea un marcador y ábrelo con información relevante
            const marker = L.marker([parseFloat(latitud), parseFloat(longitud)], {icon: conferenciaIcon})
              .addTo(map)
              .bindPopup(`
                <strong>Conferencias</strong><br>
                <strong>Fecha:</strong> ${fecha}<br>
                <strong>Duración del registro</strong> ${crono}<br>
                <strong>Hora:</strong> ${hora}<br>
                <strong>Nombre del evento:</strong> ${nombreDelEvento}<br>
                <strong>Nombre del ponente:</strong> ${nombreDelPonente}<br>
                <strong>Tematica:</strong> ${tematica}<br>
                <strong>Duracion de la Ponencia:</strong> ${duracionPonencia}<br>
                <strong>Observaciones o notas:</strong> ${notas}
              `);
          });
        }
      })


      investigacionRef.once('value')
      .then(snapshot => {
        const Entre = snapshot.val();

        if (Entre) {
          Object.entries(Entre).forEach(([reunionId, reunion]) => {
            const { crono,latitud, longitud, fecha, hora,titulo,area,nombre,avance,resultados,presupuesto,recordatorio } = reunion;

            // Crea un marcador y ábrelo con información relevante
            const marker = L.marker([parseFloat(latitud), parseFloat(longitud)], {icon: investigacionIcon})
              .addTo(map)
              .bindPopup(`
                <strong>Investigación</strong><br>
                <strong>Fecha:</strong> ${fecha}<br>
                <strong>Duración del registro</strong> ${crono}<br>
                <strong>Hora:</strong> ${hora}<br>
                <strong>Nombre del proyecto:</strong> ${titulo}<br>
                <strong>Area de inbestigación:</strong> ${area}<br>
                <strong>nombre del responsable:</strong> ${nombre}<br>
                <strong>Numero de avance:</strong> ${avance}<br>
                <strong>resultados obtenudos:</strong> ${resultados}<br>
                <strong>presupuesto estimado:</strong> ${presupuesto}<br>
                <strong>Observaciones o notas:</strong> ${recordatorio}
              `);
          });
        }
      })




      .catch(error => {
        console.error('Error al recuperar datos de Firebase:', error);
      });
  }
});


