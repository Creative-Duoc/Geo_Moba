package com.example.geo_moba.viewmodel

import com.example.geo_moba.data.repository.UserRepository
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    // Mock del repositorio (aunque para validaciones vacías ni siquiera se llamará)
    private val mockRepository = mockk<UserRepository>()

    private lateinit var viewModel: RegisterViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RegisterViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando registro tiene campos vacios, debe mostrar error de completar campos`() = runTest {
        // Given (Usuario no escribe nada)
        viewModel.onNameChange("")
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("")

        // When (Intenta registrarse)
        viewModel.register()

        // Then (Verificamos que el ViewModel detecte el error antes de llamar a la API)
        val state = viewModel.uiState.value
        
        // Debe ser falso el éxito
        assertFalse(state.isSuccess)
        // Debe mostrar el mensaje exacto que tienes en tu código
        assertEquals("Complete todos los campos", state.errorMessage)
    }
}
