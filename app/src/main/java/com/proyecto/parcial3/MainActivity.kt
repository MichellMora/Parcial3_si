package com.proyecto.parcial3

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración con Firebase completa")
        analytics.logEvent("InitScreen", bundle)


        registrar()
    }

    private fun registrar() {

        btn_registrar.setOnClickListener {

            etPass.setText(etUser.text.toString())

            if(etUser.text.toString().isNotEmpty()) {

                bd.collection("usuarios").document(etUser.text.toString()).set(
                        hashMapOf("Nombre" to etUser.text.toString(),
                            "Puntaje" to "0"
                        )
                    )
                registro_datos()

            }else

            {
                registroAlertaError()
            }

        }


    }


    private fun registroAlertaError() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error en Registro")
        builder.setMessage("Registro NO Exitoso")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun registro_datos() {

        val registro = Intent(this, Juego()::class.java).apply {
            putExtra("usuario", etUser.text.toString())
            putExtra("puntaje", "0")

        }

        startActivity(registro)
    }

}
