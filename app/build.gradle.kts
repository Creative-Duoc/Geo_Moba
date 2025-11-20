// --- BLOQUE DE PLUGINS ---
// Define los plugins que permiten construir una app Android con Kotlin y Compose.
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") // 👈 Ahora sí será encontrado
    id("org.jetbrains.kotlin.kapt")
}



android {
    // Namespace de la aplicación (debe coincidir con el package del código fuente)
    namespace = "com.example.geo_moba"

    // Versión del SDK con la que se compila el proyecto
    compileSdk = 36

    defaultConfig {
        // Identificador único de la app
        applicationId = "com.example.geo_moba"

        // Versión mínima de Android soportada
        minSdk = 24

        // Versión de Android objetivo (normalmente igual a compileSdk)
        targetSdk = 36

        // Código y nombre de versión de la app
        versionCode = 1
        versionName = "1.0"

        // Runner de pruebas instrumentadas
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // --- BLOQUE DE BUILD TYPES ---
    buildTypes {
        release {
            isMinifyEnabled = false // Evita ofuscación para desarrollo (usar true en producción)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // --- OPCIONES DE COMPATIBILIDAD DE JAVA Y KOTLIN ---
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // --- HABILITA JETPACK COMPOSE ---
    buildFeatures {
        compose = true // Activa la compatibilidad con Compose
    }

    // --- CONFIGURA EL COMPILADOR DE COMPOSE ---
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Versión del compilador de Compose
    }
}

dependencies {
    // --- DEPENDENCIAS BÁSICAS DE ANDROID ---
    implementation(libs.androidx.core.ktx)           // Extensiones Kotlin para Android.
    implementation(libs.androidx.appcompat)          // Compatibilidad con versiones antiguas de Android.
    implementation(libs.material)                    // Componentes Material Design clásicos.
    implementation(libs.androidx.activity)           // Soporte para Activities.
    implementation(libs.androidx.constraintlayout)   // Layout flexible y moderno.
    implementation(libs.play.services.maps)          // Google Maps SDK for Android.
    testImplementation(libs.junit)                   // Framework de pruebas unitarias.
    androidTestImplementation(libs.androidx.junit)   // Pruebas instrumentadas.
    androidTestImplementation(libs.androidx.espresso.core) // Pruebas UI automáticas.

    // --- DEPENDENCIAS DE JETPACK COMPOSE ---
    implementation("androidx.compose.ui:ui:1.6.7") // Núcleo de Compose.
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.7") // Soporte para vista previa.
    implementation("androidx.compose.material3:material3:1.3.0") // Material Design 3.

    // Activity Compose: puente entre Activity y setContent{} (necesario para Compose)
    implementation("androidx.activity:activity-compose:1.9.0")

    // Navigation Compose: sistema de rutas (NavHost, composable)
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // ViewModel Compose: integra ViewModels con Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Runtime Compose: maneja ciclos de vida y recomposición
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")

    // --- COROUTINES (ASINCRONÍA) ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1") // Corrutinas base
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1") // Dispatcher para Android.

    // --- SPLASH SCREEN (PANTALLA DE CARGA NATIVA) ---
    implementation("androidx.core:core-splashscreen:1.0.1")

    // --- DEBUG Y HERRAMIENTAS DE VISTA PREVIA ---
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.7") // Herramientas de diseño
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7") // Manifesto de pruebas UI
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
}
