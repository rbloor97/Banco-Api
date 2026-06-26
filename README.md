# Banco-Api
Prueba Técnica Devsu

# Sistema Bancario API - Frontend & Backend

Este proyecto consiste en una aplicación bancaria compuesta por un backend en Spring Boot y un frontend desarrollado en Angular 19. Todo el ecosistema está completamente contenido en Docker para facilitar su despliegue, y validación.

---

## Requisitos

* **Docker** y **Docker Compose**
* **Node.js** (Opcional)

---

## Despliegue con Docker

El proyecto está configurado para compilarse e inicializarse en un entorno aislado de forma automática con todos sus servicios vinculados.

Para compilar las imágenes y levantar todos los contenedores en segundo plano, se debe ejecutar el siguiente comando en la raíz del proyecto:

```bash 
docker-compose up --build -d
```

## Acceso a la Aplicacion
Una vez que Docker haya levantado los servicios de manera exitosa:
* Frontend (UIX): Puede acceder desde su navegador en http://localhost:8081.
* Backend (API REST): Corriendo de forma interna y expuesto para consumo en el puerto 8080 (http://localhost:8080/api).

Para apagar los servicios y limpiar los contenedores activos, utilizar:
```
docker-compose down
```

La suite de pruebas unitarias está configurada con Jest en Angular 19 (clientes, cuentas, movimientos).
Para ejecutar todos los tests de manera local y verificar que la suite se encuentre OK, dirigirse a la carpeta del frontend y ejecutar:

```
npm run test
```

Se incluye el archivo de colección a importar en Postman:
* Archivo: postman/Banco_API_Collection.json
  
La colección apunta a http://localhost:8080/api e incluye cobertura para operaciones CRUD (GET, POST, PUT, DELETE) organizadas de la siguiente manera:
* Clientes: Creación, consultas por ID, listado general, actualización de datos y eliminación.
* Cuentas: Registro de cuentas asociadas a clientes, actualización de estados y listados.
* Movimientos: Registro de transacciones (Depósitos y Retiros) con control de excepciones (Saldo no Disponible o Cupo diario excedido).
* Reportes: Consulta de movimientos contables por usuario y fecha.
