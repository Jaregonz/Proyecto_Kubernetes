# PROYECTO KUBERNETES - API REST CINES

## Alfonso Jesús Anillo Romero
## Jacinto Reguera González


## INSTRUCCIONES

### 1- Requisitos previos

Se debe tener instalado:

1. **Java y Graddle:** Java debe estar instalado en una versión 17 o mayor.
2. **Spring Boot:** Framework de código abierto que sirve para crear aplicaciones autónomas de producción que se ejecutan en una máquina virtual Java.
3. **Docker:** para construir imágenes de contenedor.
4. **Minikube:** para crear clústers y realizar pruebas locales.
5. **Kubectl:** herramienta de líneas de comando para interactuar con kubernetes.

### 2- Proyecto Spring Boot
El proyecto seleccionado ya se realizó con anterioridad, y es el denominado **"APIRestPelículas"**. 

Existen dos entidades, **Pelicula** y **Sesion**, con una relación **Many To One**, donde una película puede tener muchas películas, pero una sesión corresponde a una sóla película.
**Película** tiene las siguientes entidades: *id*, *title*, *director*, *time*, *trailer*, *posterImage*, *screenshot*, *synopsis*, *trailer* y *rating*.
**Sesión** tiene las entidades: *id*, *pelicula*, *room_id* y *date*.

En ambas entidades, se implementan todas las operaciones de CRUD.


### 3- Crear un dockerfile
Así que, ahora hay que realizar un archivo **dockerfile** para establecer un conjunto de comandos o instrucciones. Estos comandos/instrucciones se ejecutan sucesivamente para realizar acciones sobre la imagen base para crear una nueva imagen Docker.

*Dockerfile*:

```
FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
```

### 4 - Construir y subir la imagen a Docker
A través del dockerfile, se sube la imagen a Docker.

```
docker build -t usuario/nombreImagen .
docker run -p 8000:8080 usuario/nombreImagen
```

### 5- Definir los manifiestos de Kubernetes:
A través de archivos **yaml**, que son los archivos o conjunto de archivos que describen el estado deseado de los recursos en el clúster de Kubernetes.

#### 5.1 - Crear un deployment:
Controlador de la plataforma. Define el clúster que se va a utilizar, el número de réplicas de la aplicación y la gestión de las actualizaciones.

#### 5.2 - Crear un Service:
Para almacenar configuraciones externas a la aplicación. Puede establecer cambios en la configuración sin tener que modificar el archivo .dockerfile.

#### 5.3 - Crear un Secret:
Secret se encarga de almacenar información sensible, como pueden ser las credenciales de las bases de datos.

#### 5.4 - Crear un mysql-deployment:
Para definir la base de datos.


### 6 - Desplegar en Kubernetes:

**Comandos:**

Iniciar Minikube: 
  *minikube start*

Aplicar los manifiestos:
  *kubectl apply -f k8s/*

Verificar los pods y servicios:
  *kubectl get pods*
  *kubectl get services*

Si todo está bien, obtendrás la IP del servicio y podrás acceder a la aplicación en el navegador.

### 7 - Escalar y monitorizar la aplicación:

**Comandos:**

Escalar:
  *kubectl scale deployment springboot-app --replicas=3*

Monitorear logs:
  *kubectl logs -f deployment/springboot-app*

Ver detalles del servicio:
  *kubectl describe service springboot-service*


