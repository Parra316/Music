package fes.aragon.music


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.AdapterView
import android.widget.MediaController
import android.widget.Toast
import androidx.core.net.toUri
import fes.aragon.music.databinding.ActivitySecondBinding
import java.io.File

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var model: ArrayList<Modelo>
    private lateinit var adap: RecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller = MediaController(this)
        binding.surface.setMediaController(controller)
        controller.setAnchorView(binding.surface)
        fillList()
        binding.list.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val data: Modelo = model.get(position)
            val ruta =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).path)
            val rutaUri = ruta.toUri()
            binding.surface.setVideoURI(rutaUri)
            binding.surface.start()
            Toast.makeText(this, data.namefile, Toast.LENGTH_SHORT).show()
        })
    }
    private fun fillList() {
        val ruta =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).path)
        model = ArrayList()
        ruta.listFiles()!!.forEach {
            val substring = it.name.substring(it.name.length-4)
            if (it.isFile && substring==".mp4") {
                println(it)
                print(substring)
                model.add(Modelo(it.name, R.drawable.musica_img, it.path))
            }
        }
        adap = RecipeAdapter(this, model)
        binding.list.adapter = adap
    }
}