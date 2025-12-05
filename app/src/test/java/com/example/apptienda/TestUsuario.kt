package com.example.apptienda

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.apptienda.model.local.Usuario
import com.example.apptienda.model.repository.UsuarioRepository
import com.example.apptienda.viewmodel.UsuarioViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class TestUsuario {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var mockRepository: UsuarioRepository
    private lateinit var viewModel: UsuarioViewModel
    private val usuarioPrueba = Usuario(id = 1L, // Long
        nombre = "Mateo",
        correo = "mateo@test.com",
        contrasena = "123456",
        fechaNacimiento = "01-01-2000",
    )

    @Before
    fun setUp() {
        mockRepository = mockk()
        viewModel = UsuarioViewModel(mockRepository)
    }

    @Test
    fun `test login exitoso actualiza el usuario`() = runTest {
        coEvery { mockRepository.login("mateo@test.com", "123") } returns usuarioPrueba

        viewModel.login("mateo@test.com", "123")
        assertEquals(usuarioPrueba, viewModel.usuario.value)
    }

    @Test
    fun `test login fallido mantiene el usuario como nulo`() = runTest {
        coEvery { mockRepository.login("error@test.com", "badpass") } returns null
        viewModel.login("error@test.com", "badpass")
        assertNull(viewModel.usuario.value)
    }

    @Test
    fun `test logout limpia el StateFlow del usuario`() = runTest {
        coEvery { mockRepository.login("mateo@test.com", "123") } returns usuarioPrueba
        viewModel.login("mateo@test.com", "123")
        assertEquals(usuarioPrueba, viewModel.usuario.value) // Verificación intermedia
        viewModel.logout()
        assertNull(viewModel.usuario.value)
    }
}