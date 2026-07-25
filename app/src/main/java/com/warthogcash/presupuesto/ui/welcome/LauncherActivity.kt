package com.warthogcash.presupuesto.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.ui.main.MainActivity
import kotlinx.coroutines.launch

/**
 * Punto de entrada real de la app. No tiene interfaz propia: decide de
 * forma centralizada si debe mostrarse la pantalla de Bienvenida (ningún
 * mes creado todavía) o la Pantalla principal (ya existe al menos un mes),
 * según la especificación de la pantalla "Bienvenida (primer uso)", sección 2.
 */
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repositorio = (application as App).repository

        lifecycleScope.launch {
            val existeAlgunMes = repositorio.existeAlgunMes()
            val destino = if (existeAlgunMes) {
                Intent(this@LauncherActivity, MainActivity::class.java)
            } else {
                Intent(this@LauncherActivity, WelcomeActivity::class.java)
            }
            startActivity(destino)
            finish()
        }
    }
}
