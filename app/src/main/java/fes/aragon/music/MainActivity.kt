package fes.aragon.music

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fes.aragon.music.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var archivos: ArrayList<Modelo>
    private lateinit var binding: ActivityMainBinding
    private lateinit var adap: RecipeAdapter
    private var mediaPlayer: MediaPlayer? = null
    private var indice: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checarPermisos()
    }

    private fun checarPermisos() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            //el permiso no fue aceptado
            pedirPermisoLectura()
        } else {
            InicioCodigo()
            print("simon si jalo")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                InicioCodigo()
            } else {
                Toast.makeText(this, "Permiso rechazado por primera vez", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun pedirPermisoLectura() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(this, "Permiso rechazado", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }

    private fun InicioCodigo() {

        val stadoSD: String = Environment.getExternalStorageState()
        if (stadoSD == Environment.MEDIA_MOUNTED) {
            println(
                "sistema=: " +
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path
            )
            println("sistema2: " + this.getExternalFilesDir(null))
            val ff =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path)
            archivos = ArrayList()
            ff.listFiles()!!.forEach {
                val substring = it.name.substring(it.name.length - 4)
                if (it.isFile) {
                    println(it)
                    print(substring)
                    archivos.add(Modelo(it.name, R.drawable.musica_img, it.path))
                }
            }
        }
        adap = RecipeAdapter(this, archivos)
        binding.list.adapter = adap
        binding.list.setOnItemClickListener { parent, view, position, id ->
            val data: Modelo = archivos.get(position)
            indice = position
            var nombre = data.namefile
            var ruta = data.path
            val intent = Intent(this, Rep_audio::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("ruta", ruta)
            startActivity(intent)
        }
    }
}