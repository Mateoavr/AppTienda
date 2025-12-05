package com.example.apptienda

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.viewmodel.ProductoViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

class TestProducto {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var mockRepository: ProductoRepository
    private lateinit var viewModel: ProductoViewModel


    private val listaProductosPrueba = listOf(
        Producto(
            codigo = "p1",
            nombre = "Laptop Gamer",
            precio = 1200,
            categoria = "Tech",
            descripcion = "Una laptop potente para juegos",
            rating = 5.0f
        ),
        Producto(
            codigo = "p2",
            nombre = "Mouse",
            precio = 40,
            categoria = "Tech",
            descripcion = "Mouse óptico con RGB",
            rating = 5.0f
        )
    )
    @Before
    fun setUp() {
        mockRepository = mockk()
        viewModel = ProductoViewModel(mockRepository)
    }

    @Test
    fun `test estado inicial de productos es una lista vacia`() {
        assertTrue(viewModel.productos.value.isEmpty())
    }

    @Test
    fun `test cargarProductos exitoso actualiza `() = runTest {

        coEvery { mockRepository.obtenerProductos() } returns listaProductosPrueba

        // 2. Act
        viewModel.cargarProductos()

        assertEquals(listaProductosPrueba, viewModel.productos.value)
        assertEquals(2, viewModel.productos.value.size)
    }


}