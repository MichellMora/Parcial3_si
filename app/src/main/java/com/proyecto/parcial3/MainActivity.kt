package com.proyecto.parcial3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
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
        iniciar()
    }

    private fun registrar() {

        btn_registrar.setOnClickListener {

            if(etUser.text.toString().isNotEmpty() && etPass.text.toString().isNotEmpty()) {

                bd.collection("usuarios").document(etUser.text.toString())
                        .get().addOnSuccessListener { documento ->
                            if(documento.exists()){

                                registroAlertaError2()

                            }else{
                                bd.collection("usuarios").document(etUser.text.toString()).set(
                                        hashMapOf("Nombre" to etUser.text.toString(),
                                                "Contraseña" to etPass.text.toString(),
                                                "Puntaje" to "0"
                                        )
                                )
                                registro_datos(etUser.text.toString())
                            }
                        }

            }else

            {
                registroAlertaError()
            }

        }
    }

    private fun iniciar() {

        btn_inicio.setOnClickListener {

            bd.collection("usuarios").document(etUser.text.toString())
                   .get().addOnSuccessListener { documento ->

                        if (documento.exists()){

                            if (documento.data?.get("Nombre") == etUser.text.toString()){

                                if(documento.data?.get("Contraseña") == etPass.text.toString()){

                                    registro_datos(etUser.text.toString())

                                }else{
                                    contraseñamal()
                                }
                            }
                        }else {
                           usuariomal()
                        }

                    }
        }
    }


    private fun registroAlertaError() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro NO Exitoso")
        builder.setMessage("Completa todos los campos para realizar el registro")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun registroAlertaError2() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro NO Exitoso")
        builder.setMessage("El nombre de usuario ya existe, intenta con otro nombre")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun contraseñamal() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingreso NO Exitoso")
        builder.setMessage("La contraseña no es correcta")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun usuariomal() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingreso NO Exitoso")
        builder.setMessage("Usuario no registrado")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun registroAlertaBien() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Verificación")
        builder.setMessage("Si tomo en cuenta esto")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun registro_datos(nombre:String) {

        bd.collection("usuarios").document(nombre).get()
            .addOnSuccessListener { documentSnapshot ->
                val puntaje = documentSnapshot.getString("Puntaje")

                val registro = Intent(this, Juego()::class.java).apply {
                    putExtra("usuario", etUser.text.toString())
                    putExtra("contraseña", etPass.text.toString())
                    putExtra("puntaje", puntaje)

                }

                startActivity(registro)

            }

    }

}
