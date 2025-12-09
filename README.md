# Geo_Moba

## 1. Nombre del Proyecto

**Geo_Moba**

## 2. Integrantes

* Samuel Bueno
* Israel Gonzales
* Vicente Avila

## 3. Funcionalidades

La aplicación móvil **Geo_Moba** permite a los usuarios gestionar y visualizar dispositivos geolocalizados. Las principales funcionalidades incluyen:

* **Registro de Usuarios**: Creación de nuevas cuentas de usuario.
* **Inicio de Sesión**: Autenticación segura para acceder a la aplicación.
* **Listado de Dispositivos**: Visualización de una lista de dispositivos asociados.
* **Mapa de Dispositivos**: Visualización de la ubicación de los dispositivos en un mapa interactivo (Google Maps).

## 4. Endpoints Utilizados

La aplicación se comunica con un microservicio alojado en Railway.

**Base URL:** `https://ms-db-client-mobile-production.up.railway.app/`

| Método  | Endpoint           | Descripción                   |
| :------- | :----------------- | :----------------------------- |
| `POST` | `/clients`       | Registro de nuevos usuarios.   |
| `POST` | `/clients/login` | Inicio de sesión de usuarios. |

## 5. Pasos para Ejecutar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/Creative-Duoc/Geo_Moba.git
   ```
2. **Abrir en Android Studio**:
   * Inicia Android Studio y selecciona "Open".
   * Navega hasta la carpeta clonada y selecciona el archivo `build.gradle.kts` o la carpeta raíz.
3. **Sincronizar Gradle**:
   * Espera a que Android Studio descargue las dependencias y configure el proyecto.
4. **Configurar API Key de Google Maps**:
   * Asegúrate de tener una API Key válida en `local.properties` o en el archivo de recursos correspondiente (`google_map_api.xml`).
5. **Ejecutar la App**:
   * Conecta un dispositivo Android o inicia un emulador.
   * Presiona el botón "Run" (triángulo verde) en Android Studio.

## 6. Generación de APK Firmado

El proyecto está configurado para generar un APK firmado utilizando un Keystore incluido en el repositorio (para fines académicos).

### Ubicación de Archivos

* **Keystore (.jks)**: `app/release.keystore`
* **APK Firmado**: `app/release/app-release.apk` (se genera tras ejecutar el build)

### Pasos para Generar APK

Para generar el APK firmado manualmente desde la terminal:

```bash
./gradlew assembleRelease
```

El archivo APK generado se encontrará en: `app/build/outputs/apk/release/app-release.apk`.

## Evidencias y Planificación

* **Trello**: https://trello.com/invite/b/68ed9e6fa94344d22bc9545a/ATTI19da17b96793a124c50cf904f8d1f7b64E56B0B0/geo-moba
* **Código Fuente App**: https://github.com/Creative-Duoc/Geo_Moba
* **Código Fuente Microservicios**: https://github.com/Creative-Duoc/ms-db-client-mobile
* **Deploy base de dato**:https://railway.com/invite/vjr7bV_k2xx
* **Deploy Backend:**https://railway.com/invite/NTX6toqSUwX
