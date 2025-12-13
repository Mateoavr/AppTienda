
o Nombre de la app: LEVEL-UP
o Nombres de los integrantes: Mateo Viera
o Funcionalidades: RegistarUsuario, IniciarSesion, Soporte, BuscarProducto, CompartirProducto, AgregarAlAcarrito, VaciarCarrito, Pagar, DejarReseña, VerReseñas, VerEventos
o Endpoints usados (propios y externos):

GET /api/producto: Obtiene el listado Productos.
GET /api/producto/buscar: Busca productos por nombre.
GET /api/producto/categoria: Filtra los productos por su categoría.

POST /api/registrar: Registra un nuevo cliente en la base de datos.
POST /api/login: Autenticación de usuario.
GET /api/usuarios:Obtiene la lista usuarios registrados.

POST /api/carrito/agregar: Agrega un ítem al carrito de compras.
GET /api/carrito/{usuarioId}: Obtiene los productos actuales en el carrito de un usuario.
POST /api/carrito/vaciar/{usuarioId}: Elimina todos los productos del carrito.

POST /resenias: Guarda una nueva reseña/comentario.
GET /resenias: Obtiene todas las reseñas del sistema.
GET /resenias/{codigo}: Obtiene las reseñas filtradas por el código del producto.

Google Maps Platform
Base de Datos
o Instrucciones para ejecutar el proyecto:

Backend (Servidor):

Abrir el proyecto del Backend en IntelliJ IDEA.
Verificar la conexión a la base de datos.
Ejecutar la clase principal (AppTiendaApplication.kt).
Esperar a que la consola indique: Started AppTiendaApplication in....

Aplicación Móvil :
Abrir el proyecto Android en Android Studio.
Verificar en RetrofitClient o Constants que la IP apunte a la máquina local .
Dar clic en "Run" (botón Play verde) seleccionando el dispositivo destino.

