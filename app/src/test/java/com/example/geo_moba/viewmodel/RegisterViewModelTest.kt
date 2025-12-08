package com.example.geo_moba.viewmodel

import app.cash.turbine.test
import com.example.geo_moba.data.network.UserResponse
import com.example.geo_moba.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.regex.Matcher
import java.util.regex.Pattern

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    // SUT (System Under Test)
    private lateinit var viewModel: RegisterViewModel

    // Mocks
    private lateinit var userRepository: UserRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mock()
        viewModel = RegisterViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `register when fields are blank sets error message`() = runTest {
        viewModel.register()
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Complete todos los campos", state.errorMessage)
        }
    }

    @Test
    fun `register when email is invalid sets error message`() = runTest {
        // Simular la clase estática `Patterns` de Android
        Mockito.mockStatic(android.util.Patterns::class.java).use { mockedPatterns ->
            // Configurar el mock para que la validación de CUALQUIER email devuelva `false`
            mockedPatterns.`when`<Boolean> {
                android.util.Patterns.EMAIL_ADDRESS.matcher(any()).matches()
            }.thenReturn(false)

            // Ejecutar la lógica de la prueba
            viewModel.onNameChange("Test")
            viewModel.onPasswordChange("password")
            viewModel.onEmailChange("invalid-email") // El valor exacto ya no importa
            viewModel.register()

            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals("Correo inválido", state.errorMessage)
            }
        }
    }

    @Test
    fun `register when password is too short sets error message`() = runTest {
        viewModel.onNameChange("Test")
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123")
        viewModel.register()
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Mínimo 6 caracteres", state.errorMessage)
        }
    }

    @Test
    fun `register when registration succeeds sets success state`() = runTest {
        // Given
        val name = "Test User"
        val email = "test@example.com"
        val password = "password123"
        val userResponse = UserResponse(1L, name, email)

        whenever(userRepository.registerUser(name, email, password)).thenReturn(Result.success(userResponse))

        val viewModel = RegisterViewModel()
        viewModel.onNameChange(name)
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        // When
        viewModel.register()

        // Then
        viewModel.uiState.test {
            // Initial state
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.errorMessage)
            assertFalse(state.isSuccess)

            // Loading state
            state = awaitItem()
            assertTrue(state.isLoading)

            // Success state
            state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.isSuccess)
        }
    }

    @Test
    fun `register when registration fails sets error message`() = runTest {
        // Given
        val name = "Test User"
        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Email ya en uso"

        whenever(userRepository.registerUser(name, email, password)).thenReturn(Result.failure(Exception(errorMessage)))

        val viewModel = RegisterViewModel()
        viewModel.onNameChange(name)
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        // When
        viewModel.register()

        // Then
        viewModel.uiState.test {
            // Initial state
            var state = awaitItem()
            assertFalse(state.isLoading)

            // Loading state
            state = awaitItem()
            assertTrue(state.isLoading)

            // Error state
            state = awaitItem()
            assertFalse(state.isLoading)
            assertNotNull(state.errorMessage)
            assertEquals(errorMessage, state.errorMessage)
        }
    }
}
