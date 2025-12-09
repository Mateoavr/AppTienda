package com.example.apptienda.model.remote


import com.example.apptienda.model.controller.CarritoAdd
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.local.Resenia
import com.example.apptienda.model.local.Usuario
import com.example.apptienda.model.repository.ProductoRepository

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    @GET("api/producto")
    suspend fun getProductos(): Response<List<Producto>>

    @POST("api/login")
    suspend fun login(@Body request: ProductoRepository.Login): Response<Usuario>

    @GET("api/usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @GET("api/carrito/{usuarioId}")
    suspend fun getCarrito(@Path("usuarioId") usuarioId: Long): Response<List<CarritoAdd>>

    @POST("api/registrar")
    suspend fun registrar(@Body request: ProductoRepository.Registro): Response<Usuario>

    @POST("api/carrito/vaciar/{usuarioId}")
    suspend fun vaciarCarrito(@Path("usuarioId") usuarioId: Long): Response<Unit>

    @POST("api/carrito/agregar")
    suspend fun agregarAlCarrito(@Body item: CarritoAdd): Response<Unit>

    @POST("resenias")
    suspend fun enviarResenia(@Body resenia: Resenia): Response<Void>
    @POST("resenias")
    suspend fun crearResenia(@Body resenia: Resenia): Response<Void>
}
