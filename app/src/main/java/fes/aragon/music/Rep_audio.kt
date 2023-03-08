package fes.aragon.music


import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Rep_audio : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rep_audio)
        val play = findViewById<ImageButton>(R.id.play) as ImageButton
        val pause = findViewById<ImageButton>(R.id.pause) as ImageButton
        val stop = findViewById<ImageButton>(R.id.stop) as ImageButton
        val m_message = intent.getStringExtra("nombre")
        val m_rute = intent.getStringExtra("ruta")
        val msgText = findViewById<TextView>(R.id.Nombre) as TextView
        msgText.text = m_message
        play.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(m_rute)
                    prepare()
                    start()
                }
            }else{
                mediaPlayer!!.start()
            }
        }
        stop.setOnClickListener{
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer=null
            }
        }
        pause.setOnClickListener{
            if(mediaPlayer!=null){mediaPlayer!!.pause()
            }
        }
    }
}