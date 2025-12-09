package com.example.geo_moba.viewmodel

import com.example.geo_moba.data.network.UserResponse
import com.example.geo_moba.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    // 1. Mock del Repositorio (es un repositorio "falso" controlado por nosotros)
    private val mockRepository = mockk<UserRepository>()

    // El ViewModel que vamos a probar
    private lateinit var viewModel: LoginViewModel

    // Dispatcher para controlar las corrutinas en los tests
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Configuramos el dispatcher de Main para tests
        Dispatchers.setMain(testDispatcher)
        // Inicializamos el ViewModel inyectándole el mock
        viewModel = LoginViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando login se llama con campos vacios, debe establecer errorMessage`() = runTest {
        // Given (Dado un estado inicial vacío)
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")

        // When (Cuando intentamos login)
        viewModel.login()

        // Advance coroutines (si hubiera alguna, aunque aquí es síncrono por la validación)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then (Entonces debe haber error)
        val state = viewModel.uiState.value
        assertEquals("Complete todos los campos", state.errorMessage)
        assertFalse(state.isSuccess)
    }

    @Test
    fun `cuando login es exitoso en repositorio, isSuccess debe ser true`() = runTest {
        // Given (Preparamos datos válidos y comportamiento del mock)
        val email = "test@duoc.cl"
        val pass = "123456"
        val fakeUser = UserResponse(1, "Test User", email) // Ajusta esto según tu UserResponse real

        // Entrenamos al mock: "Cuando te pidan login, devuelve éxito"
        coEvery { mockRepository.login(email, pass) } returns Result.success(fakeUser)

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(pass)

        // When (Ejecutamos login)
        viewModel.login()

        // Esperamos a que terminen las corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Then (Verificamos resultados)
        val state = viewModel.uiState.value
        assertTrue(state.isSuccess)
        assertFalse(state.isLoading)
        assertEquals(null, state.errorMessage)
    }
}
