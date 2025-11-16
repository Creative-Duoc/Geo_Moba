package com.example.geo_moba.model

/**
 * Repository que maneja las operaciones de datos de usuarios.
 * Actúa como capa intermedia entre el ViewModel y la base de datos.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Registra un nuevo usuario.
     * @return Result.success con el ID si se registró correctamente,
     *         Result.failure si el email ya existe.
     */
    suspend fun registerUser(name: String, email: String, password: String): Result<Long> {
        return try {
            // Verificar si el email ya existe
            if (userDao.emailExists(email)) {
                Result.failure(Exception("El correo ya está registrado"))
            } else {
                val user = User(name = name, email = email, password = password)
                val userId = userDao.insertUser(user)
                Result.success(userId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Intenta hacer login con las credenciales proporcionadas.
     * @return Result.success con el User si las credenciales son correctas,
     *         Result.failure si no coinciden.
     */
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val user = userDao.login(email, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

