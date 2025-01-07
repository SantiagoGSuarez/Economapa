# Índice

1. [Economapa](#economapa)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Pantallas de la Aplicación](#pantallas-de-la-aplicación)

# <div align="center"><img src="Economapa App/Economapa/app/src/main/res/drawable/Economapa logo.png" alt="Economapa" width="200" style="margin: 0 auto;"></div> Economapa
Economapa es una aplicación móvil diseñada para facilitar el acceso a productos en promoción entre Rivera y Santana do Livramento, donde frecuentemente llegan turistas para realizar compras. Ideal para mostrar productos en promoción y ayudar a los usuarios a encontrar ofertas de manera eficiente. 

## Tecnologías Utilizadas

Para la creación de Economapa, se utilizó el lenguaje <a target="_blank" href="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" style="display: inline-block;"><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="28" height="28" /></a> <img src="https://img.shields.io/badge/-Java-red?style=for-the-badge&color=ea2d2e"> en <a target="_blank" href="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/androidstudio/androidstudio-original.svg" style="display: inline-block;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/androidstudio/androidstudio-original.svg" alt="android" width="25" height="25" /></a> <img src="https://img.shields.io/badge/-Android Studio-blue?style=for-the-badge&color=4285f4">. Además, se implementó una API <a target="_blank" href="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" style="display: inline-block;"><img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="22" height="22" /></a> <img src="https://img.shields.io/badge/-Spring%20Boot-green?style=for-the-badge&color=77bc1f"> como intermediaria entre la aplicación y la base de datos. Esta API también gestiona métodos útiles, como la eliminación de productos cuya promoción haya vencido.
Para la visualización de mapas, se utilizó <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Openstreetmap_logo.svg/1200px-Openstreetmap_logo.svg.png" alt="OpenStreetMap" width="28" height="28" /> </a> <a target="_blank" href="https://www.openstreetmap.org/" style="display: inline-block;"><img src="https://img.shields.io/badge/-Open%20Street%20Map%20(WebView)-green?style=for-the-badge&color=BDE4AA"></a> junto con la API <img src="https://avatars.githubusercontent.com/u/6683114?s=48&v=4" alt="OpenStreetMap" width="28" height="28" /> <a target="_blank" href="https://github.com/osmdroid/osmdroid" style="display: inline-block;"> <img src="https://img.shields.io/badge/-osmdroid-yellow?style=for-the-badge&color=E3FFC5"></a>, lo que permite mostrar puntos en el mapa que representan las ubicaciones de las empresas y la posición del usuario.

## Pantallas de la Aplicación

### 1. Pantalla de inicio
<div align="center">
  <img src="Economapa App/Economapa/app/src/main/res/drawable/1 pantalla inicio.PNG" alt="Pantalla de inicio" width="250" style="margin: 0 auto;">
</div>

- La pantalla de inicio muestra el logo y el nombre de la aplicación, junto con un mensaje de bienvenida en español y portugués, y un botón para iniciar, el cual permite acceder a la pantalla principal de la aplicación.

### 2. Pantalla Principal
<div align="center">
  <img src="Economapa App/Economapa/app/src/main/res/drawable/2 pantalla principal.PNG" alt="Pantalla principal" width="250" style="margin: 0 auto;">
</div>

- La pantalla principal de la aplicación presenta una lista de productos, donde se visualizan la imagen, el nombre, el precio regular y el precio con descuento de cada uno. Además, incluye una barra de búsqueda que permite a los usuarios encontrar productos por su nombre.

  #### 2.1 Notificación de Vencimiento de Promoción
  <div align="center">
    <img src="Economapa App/Economapa/app/src/main/res/drawable/6 notificacion backgroundservice.PNG" alt="Notificacion 2" width="250" style="margin: 0 auto;">
  </div>
    
  - Esta notificación se activa al iniciar la aplicación, cuando una promoción de producto está a punto de vencer. La       notificación aparece cuando faltan tres días para que la oferta expire.

  #### 2.2 Ver Producto
  <div align="center">
    <img src="Economapa App/Economapa/app/src/main/res/drawable/3 ver producto.PNG" alt="producto" width="250" style="margin: 0 auto;">
  </div>
    
  - Al hacer clic en un producto desde la lista, se accede a una pantalla detallada que muestra la imagen del producto, su    nombre, una breve descripción, la empresa (local) que lo ofrece, y su precio regular junto con el precio promocional.       Además, incluye un botón "GPS"

  #### 2.3 Ruta hasta el Producto
  <div align="center">
    <img src="Economapa App/Economapa/app/src/main/res/drawable/4 gps.PNG" alt="GPS" width="250" style="margin: 0 auto;">
  </div>
    
  - Al tocar el botón "GPS", se muestra un mapa con la ruta desde la ubicación actual del usuario hasta la empresa que        ofrece el producto en promoción.

  #### 2.4 Notificación de Ruta al Producto
  <div align="center">
    <img src="Economapa App/Economapa/app/src/main/res/drawable/5 notificacion foregroundservicec.PNG" alt="Notificacion 1" width="250" style="margin: 0 auto;">
  </div>
    
  - Esta notificación se activa al hacer clic en el botón "GPS" y acceder a la pantalla *"Ruta hasta el Producto"*. Informa     al usuario sobre su destino.

### 3. Pantalla Mapa de Ubicación
<div align="center">
  <img src="Economapa App/Economapa/app/src/main/res/drawable/7 mapa.PNG" alt="Pantalla mapa" width="250" style="margin: 0 auto;">
</div>

- En esta pantalla, se muestra un mapa con la ubicación actual del usuario, así como las ubicaciones de las empresas. Al hacer clic sobre el punto que representa una empresa, el usuario podrá ver información relevante, como el nombre de la empresa y la distancia en kilómetros desde su ubicación actual.
