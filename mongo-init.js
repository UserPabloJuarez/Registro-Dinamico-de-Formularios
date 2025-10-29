// Inicialización de la base de datos
db = db.getSiblingDB('formulariosdb');

// Crear colección y índices
db.createCollection('formularios');

// Índices para mejor rendimiento
db.formularios.createIndex({ "numeroDocumento": 1 }, { unique: true });
db.formularios.createIndex({ "correo": 1 });
db.formularios.createIndex({ "fechaCreacion": -1 });

print('Base de datos formulariosdb inicializada correctamente');