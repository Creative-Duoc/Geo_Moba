package com.example.geo_moba.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Pruebas unitarias simples para RegisterViewModel.
 * Solo probamos la LÓGICA de validación, sin bases de datos ni conexiones reales.
 */
@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    // Usamos un dispatcher de prueba para controlar las coroutines
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RegisterViewModel

    // --- Mocking de android.util.Patterns ---
    // Mockeamos la clase Pattern y Matcher para simular la validación de email
    private lateinit var mockPattern: Pattern
    private lateinit var mockMatcher: Matcher

    @Before
    fun setUp() {
        // Establecemos el dispatcher principal para los tests
        Dispatchers.setMain(testDispatcher)

        // Preparamos los mocks antes de cada test
        mockPattern = Mockito.mock(Pattern::class.java)
        mockMatcher = Mockito.mock(Matcher::class.java)

        // Mockeamos la clase estática `Patterns` para que no falle en el entorno de test
        val mockPatterns = Mockito.mockStatic(android.util.Patterns::class.java)
        // Cuando se pida el validador de email, devolvemos nuestro mock
        mockPatterns.`when`<Any> { android.util.Patterns.EMAIL_ADDRESS }.thenReturn(mockPattern)
        // Cuando se use el validador en cualquier email, devolvemos nuestro mock de Matcher
        Mockito.`when`(mockPattern.matcher(any())).thenReturn(mockMatcher)

        // Finalmente, creamos la instancia del ViewModel a probar
        viewModel = RegisterViewModel()
    }

    @After
    fun tearDown() {
        // Limpiamos el dispatcher principal después de los tests
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando los campos estan vacios debe mostrar error`() = runTest {
        // 1. Estado inicial (campos vacíos)
        viewModel.onNameChange("")
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")

        // 2. Ejecutamos la acción
        viewModel.register()

        // 3. Verificamos el resultado
        // El estado de la UI debe contener el mensaje de error esperado
        assertEquals("Complete todos los campos", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `cuando el email es invalido debe mostrar error`() = runTest {
        // 1. Estado inicial
        viewModel.onNameChange("Test User")
        viewModel.onEmailChange("email-invalido")
        viewModel.onPasswordChange("password123")

        // Configuramos el mock para que la validación de email falle
        Mockito.`when`(mockMatcher.matches()).thenReturn(false)

        // 2. Ejecutamos la acción
        viewModel.register()

        // 3. Verificamos el resultado
        assertEquals("Correo inválido", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `cuando la contrasena es muy corta debe mostrar error`() = runTest {
        // 1. Estado inicial
        viewModel.onNameChange("Test User")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123") // Contraseña corta

        // Configuramos el mock para que la validación de email sea exitosa
        Mockito.`when`(mockMatcher.matches()).thenReturn(true)

        // 2. Ejecutamos la acción
        viewModel.register()

        // 3. Verificamos el resultado
        assertEquals("Mínimo 6 caracteres", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `cuando los datos son validos no debe haber mensaje de error`() = runTest {
        // 1. Estado inicial
        viewModel.onNameChange("Test User")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("password123")

        // Configuramos el mock para que la validación de email sea exitosa
        Mockito.`when`(mockMatcher.matches()).thenReturn(true)

        // 2. Ejecutamos la acción
        // NOTA: La función register() intentará conectar al repositorio.
        // Como solo queremos probar la lógica de validación, no nos importa el resultado de la red.
        // Solo verificamos que no haya un mensaje de error ANTES de la llamada de red.
        viewModel.register()

        // 3. Verificamos el resultado
        // El mensaje de error debería ser nulo porque las validaciones pasaron
        assertNull(viewModel.uiState.value.errorMessage)
    }
}
