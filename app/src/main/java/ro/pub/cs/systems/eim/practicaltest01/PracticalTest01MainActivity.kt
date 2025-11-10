package ro.pub.cs.systems.eim.practicaltest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE = 1
        private const val THRESHOLD = 5 // pragul peste care se pornește serviciul (D.1.b)
    }

    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var buttonNav: Button

    private var int1: Int = 0
    private var int2: Int = 0

    /*
     * D.2: BroadcastReceiver care ascultă mesajele trimise de serviciu.
     * Primește:
     *  - TIMESTAMP
     *  - ARITH_MEAN
     *  - GEOM_MEAN
     * Loghează conținutul în consolă.
     */
    // sus, în clasă (DEJA ai variabila, doar modificăm conținutul)
    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            val timestamp = intent?.getStringExtra("TIMESTAMP")
            val arit = intent?.getDoubleExtra("ARITH_MEAN", 0.0)
            val geom = intent?.getDoubleExtra("GEOM_MEAN", 0.0)

            Log.d(
                "PracticalTest01Receiver",
                "Action=$action | Time=$timestamp | Arit=$arit | Geom=$geom"
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_main)

        // A.2: legăm componentele UI
        text1 = findViewById(R.id.Text1)
        text2 = findViewById(R.id.Text2)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        buttonNav = findViewById(R.id.navigateButton) // asigură-te că există în XML

        // B.2.b: restaurăm manual valorile salvate dacă activitatea a fost recreată
        if (savedInstanceState != null) {
            int1 = savedInstanceState.getInt("KEY_INT1", 0)
            int2 = savedInstanceState.getInt("KEY_INT2", 0)
            text1.text = int1.toString()
            text2.text = int2.toString()
        }

        // B.1: asociem butoanelor ascultători care incrementează câmpurile text
        // D.1.b: după incrementare, verificăm dacă pornim serviciul
        button1.setOnClickListener {
            int1++
            text1.text = int1.toString()
            checkStartService()
        }

        button2.setOnClickListener {
            int2++
            text2.text = int2.toString()
            checkStartService()
        }

        // C.2: buton care pornește activitatea secundară și trimite suma totală
        buttonNav.setOnClickListener {
            val total = int1 + int2
            val intent = Intent(this, PracticalTestSecondayActivity::class.java).apply {
                putExtra("INPUT1", total)
            }
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    // B.2.b: salvăm manual valorile înainte de distrugerea activității
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_INT1", int1)
        outState.putInt("KEY_INT2", int2)
    }

    // B.2.b: restaurăm manual valorile după recreare
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        int1 = savedInstanceState.getInt("KEY_INT1", 0)
        int2 = savedInstanceState.getInt("KEY_INT2", 0)
        text1.text = int1.toString()
        text2.text = int2.toString()
    }

    // C.2: la revenirea din activitatea secundară, afișăm rezultatul într-un Toast
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val msg = data?.getStringExtra("MESSAGE") ?: "Niciun mesaj"
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }

    /*
     * D.1.b: pornește serviciul PracticalTest01Service
     * dacă suma celor două valori depășește THRESHOLD.
     * Trimitem serviciului cele două numere prin Intent.
     */
    private fun checkStartService() {
        val sum = int1 + int2
        if (sum > THRESHOLD) {
            val serviceIntent = Intent(this, PracticalTest01Service::class.java).apply {
                putExtra("NR1", int1)
                putExtra("NR2", int2)
            }
            startService(serviceIntent)
        }
    }

    /*
     * D.2: Înregistrăm BroadcastReceiver-ul atunci când activitatea devine vizibilă.
     * Ascultă cele 3 acțiuni definite de ProcessingThread.
     */
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply {
            addAction("ro.pub.cs.systems.eim.practicaltest01.action.MATH1")
            addAction("ro.pub.cs.systems.eim.practicaltest01.action.MATH2")
            addAction("ro.pub.cs.systems.eim.practicaltest01.action.MATH3")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, filter)
        }
    }

    /*
     * D.2: Dezregistrăm BroadcastReceiver-ul când activitatea nu mai este vizibilă,
     * pentru a evita leak-uri.
     */
    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    /*
     * D.1.b: când activitatea este distrusă,
     * oprim serviciul astfel încât să nu mai trimită broadcast-uri.
     */
    override fun onDestroy() {
        val serviceIntent = Intent(this, PracticalTest01Service::class.java)
        stopService(serviceIntent)
        super.onDestroy()
    }
}
