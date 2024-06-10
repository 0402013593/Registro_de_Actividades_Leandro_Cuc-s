import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import re
import sqlite3
import os

def limpiarNombre(nombre):
    return re.sub(r'\W+', '_', nombre)

# conexión con la base de datos
cred = credentials.Certificate(r'C:\Users\2dcuc\Desktop\codigo\datos.json')
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://analisisdedatos-1762c-default-rtdb.firebaseio.com/'
})

# almacenar los datos
datosColecciones = {}

# obtener los datos de todas las colecciones
colecciones = ["Conferencias", "Reuniones", "Entrevistas", "Investigacion", "Gestion de proyectos"]
for nombreColeccion in colecciones:
    referencia = db.reference(nombreColeccion)
    datosColecciones[nombreColeccion] = referencia.get()

# Obtener la ruta del directorio actual del script
script_dir = os.path.dirname(os.path.abspath(__file__))

# Construir la ruta completa del archivo datos.sqlite
db_path = os.path.join(script_dir, 'datos.sqlite')

# crear el archivo
conn = sqlite3.connect(db_path)
cursor = conn.cursor()

# borrar los datos existentes en el archivo e ingresar los nuevos
for nombreColeccion, _ in datosColecciones.items():
    nombreTabla = limpiarNombre(nombreColeccion)
    cursor.execute(f"DROP TABLE IF EXISTS {nombreTabla}")
conn.commit()

# crear tablas en la base de datos
for nombreColeccion, datos in datosColecciones.items():
    nombreTabla = limpiarNombre(nombreColeccion)
    if datos and isinstance(datos, dict) and datos:
        campos = set()
        for idUsuario, registros in datos.items():
            for idRegistro, registroDatos in registros.items():
                campos.update(registroDatos.keys())

        # crear la tabla
        create_table_query = f"CREATE TABLE IF NOT EXISTS {nombreTabla} (idUsuario TEXT, idRegistro TEXT, {', '.join([f'{limpiarNombre(campo)} TEXT' for campo in campos])})"
        cursor.execute(create_table_query)
        conn.commit()

# Insertar datos en las tablas
for nombreColeccion, datos in datosColecciones.items():
    nombreTabla = limpiarNombre(nombreColeccion)
    if datos and isinstance(datos, dict) and datos:
        campos = set()
        for idUsuario, registros in datos.items():
            for idRegistro, registroDatos in registros.items():
                campos.update(registroDatos.keys())

        insert_query = f"INSERT INTO {nombreTabla} (idUsuario, idRegistro, {', '.join([limpiarNombre(campo) for campo in campos])}) VALUES (?, ?, {', '.join(['?' for _ in campos])})"
        for idUsuario, registros in datos.items():
            for idRegistro, registroDatos in registros.items():
                cursor.execute(insert_query, (idUsuario, idRegistro) + tuple(map(str, [registroDatos.get(campo, '') for campo in campos])))
                conn.commit()

# cierra la conexión con la base de datos sqlite
conn.close()

print('Proceso completado. Se ha generado el archivo SQLite (datos.sqlite).')
