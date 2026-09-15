# Mi Cacharrito - Backend

## Importar en Eclipse

1. Instalar un JDK 17 o superior y configurar `JAVA_HOME`.
2. En Eclipse seleccionar `File > Import > Maven > Existing Maven Projects`.
3. Seleccionar la carpeta del proyecto y ejecutar `Maven > Update Project`.
4. Ejecutar `ProgramacionWeb2Application` como una aplicación Spring Boot.

## Configuración

Por defecto se conecta a MySQL en `localhost:3306/mi_cacharrito`. Se pueden sobrescribir
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `ADMIN_USERNAME`, `ADMIN_PASSWORD`
y `PORT` mediante variables de entorno.

El administrador inicial se crea automáticamente si no existe. Los valores por defecto son
`admin` / `admin123`; deben cambiarse antes de publicar el sistema.

## Endpoints de autenticación

- `POST /api/auth/register`: registra un usuario con identificación, datos de licencia, correo, teléfono y contraseña.
- `POST /api/auth/login`: recibe `{ "identifier": "...", "password": "..." }`. El usuario usa su identificación; el administrador usa su nombre de usuario.
- `GET /api/auth/me`: requiere `Authorization: Bearer <token>`.
- `GET /api/admin/me`: requiere un token con rol `ADMIN`.

## Vehículos y alquileres

- `GET /api/vehicles/available`: lista vehículos disponibles. Acepta `?type=AUTOMOVIL`, `CAMIONETA`, `CAMPERO`, `MICROBUS` o `MOTOCICLETA`.
- `POST /api/rentals`: crea un alquiler para el usuario autenticado. Recibe `vehicleId`, `startDate` y `deliveryDate`.
- `GET /api/rentals/mine`: lista los alquileres del usuario autenticado.
- `DELETE /api/rentals/{rentalNumber}`: cancela un alquiler del usuario y libera el vehículo.
- `GET /api/admin/rentals/pending`: lista alquileres pendientes de entrega para el administrador.
- `PATCH /api/admin/rentals/{rentalNumber}/deliver`: marca el alquiler como entregado.
- `PATCH /api/admin/rentals/{rentalNumber}/return`: registra la devolución, calcula días y cobro extra, y libera el vehículo. Recibe `returnDate`.

Los vehículos de prueba se crean automáticamente si la tabla está vacía. Cada alquiler devuelve la información necesaria para generar posteriormente el comprobante PDF.

El login devuelve un JWT. En las rutas protegidas se debe enviar el token en la cabecera
`Authorization` con el esquema `Bearer`.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.1/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.1.1/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.1.1/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.1/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

