package ro.pub.cs.systems.eim.practicaltest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class PracticalTest01MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE = 1
        private const val THRESHOLD = 5 // pragul peste care se pornește serviciul
        private const val ACTION_PROCESSING = "com.example.practicaltest01vr07.PROCESSING_THREAD"
    }


    private lateinit var text1 : TextView
    private lateinit var text2 : TextView
    private lateinit var button1 : Button
    private lateinit var button2 : Button
    private lateinit var buttonNav : Button


    private var int1 : Int = 0
    private var int2 : Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_main)


/*
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    face ecranul sa ramana la fel atunci cand intoarcem telefonul
    practic se blocheaza ecranul
 */

        // Legăm componentele UI
        text1 = findViewById(R.id.Text1)
        text2 = findViewById(R.id.Text2)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        buttonNav = findViewById(R.id.navigateButton)


        // Restaurăm manual valorile din bundle, dacă activitatea a fost recreată - b2.b
        if (savedInstanceState != null) {
            int1 = savedInstanceState.getInt("KEY_INT1", 0)
            int2 = savedInstanceState.getInt("KEY_INT2", 0)

            text1.text = int1.toString()
            text2.text = int2.toString()
        }


        // Când se apasă un buton, incrementăm și verificăm dacă pornim serviciul
        button1.setOnClickListener {
            int1++
            text1.text = int1.toString()
        }

        button2.setOnClickListener {
            int2++
            text2.text = int2.toString()
        }

        buttonNav.setOnClickListener {
            val s1 = (int1 + int2).toString()
            val intent = Intent(this, PracticalTestSecondayActivity::class.java).apply {
                putExtra("INPUT1", s1.toInt())
            }
            startActivityForResult(intent, REQUEST_CODE)
        }




    }

    //B2.b
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("KEY_INT1", int1)
        outState.putInt("KEY_INT2", int2)
    }

    /*
       Restaurăm manual valorile din obiectul Bundle după recrearea activității,
       similar cu exemplul din laborator.
    */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        int1 = savedInstanceState.getInt("KEY_INT1", 0)
        int2 = savedInstanceState.getInt("KEY_INT2", 0)

        text1.text = int1.toString()
        text2.text = int2.toString()
    }

    // trebuie asta adaugata ca sa se faca afisajul mesajelor in toast
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val msg = data?.getStringExtra("MESSAGE") ?: "Niciun mesaj"
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }



}



