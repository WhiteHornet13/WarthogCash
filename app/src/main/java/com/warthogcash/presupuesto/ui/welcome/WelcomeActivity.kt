package com.warthogcash.presupuesto.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.warthogcash.presupuesto.databinding.ActivityWelcomeBinding
import com.warthogcash.presupuesto.ui.createmonth.CreateMonthActivity

/**
 * Pantalla "Bienvenida (primer uso)". Solo aparece cuando no existe
 * ningún mes creado (decidido en [LauncherActivity]). Su único botón,
 * "Empezar", navega directamente a "Crear mes nuevo" (spec, sección 4.2).
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmpezar.setOnClickListener {
            startActivity(Intent(this, CreateMonthActivity::class.java))
            finish()
        }
    }
}
