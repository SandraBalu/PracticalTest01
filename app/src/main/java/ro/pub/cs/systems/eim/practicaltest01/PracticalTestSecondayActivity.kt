package ro.pub.cs.systems.eim.practicaltest01

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTestSecondayActivity : AppCompatActivity() {

    private lateinit var input1: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_secondary)

        // luam valorile transpise prin intent din activitatea principala
        // NUMELE TREBUIE SA FIE IDENTIC CU CEL SCRIS IN MAIN ACTIVITY
        val in1 = intent.getIntExtra("INPUT1", 0)


        // casetele text din interfata (asociem cu id-urile din xml)
        input1 = findViewById(R.id.textCount)

        // punem textul primit prin intent in casetele text din ui
        input1.text = in1.toString()

        val cancel = findViewById<Button>(R.id.buttonCANCEL)
        cancel.setOnClickListener {
            val intent = Intent()
            intent.putExtra("MESSAGE", "Butonul cancel a fost apasat")
            setResult(RESULT_CANCELED, intent)
            finish()
        }

        val ok = findViewById<Button>(R.id.buttonOK)
        ok.setOnClickListener {
            val intent = Intent()
            intent.putExtra("MESSAGE", "Butonul ok a fost apasat")
            setResult(RESULT_OK, intent)
            finish()
        }



    }
}
