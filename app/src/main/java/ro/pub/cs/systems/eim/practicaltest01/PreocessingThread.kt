package ro.pub.cs.systems.eim.practicaltest01

import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sqrt
import kotlin.random.Random

/*
 * D.1.a:
 * Thread-ul pornește în cadrul serviciului PracticalTest01Service.
 * La fiecare 10 secunde trimite un broadcast cu:
 *  - o acțiune aleasă aleator din 3 posibile;
 *  - TIMESTAMP: data/ora curentă;
 *  - ARITH_MEAN: media aritmetică a celor două numere;
 *  - GEOM_MEAN: media geometrică a celor două numere.
 */
class ProcessingThread(
    private val context: Context,
    private val nr1: Int,
    private val nr2: Int
) : Thread() {

    @Volatile
    private var isRunning = true

    private val actions = arrayOf(
        "ro.pub.cs.systems.eim.practicaltest01.action.MATH1",
        "ro.pub.cs.systems.eim.practicaltest01.action.MATH2",
        "ro.pub.cs.systems.eim.practicaltest01.action.MATH3"
    )

    override fun run() {
        Log.d("ProcessingThread", "Thread started")

        val arithmeticMean = (nr1 + nr2) / 2.0
        val geometricMean = kotlin.math.sqrt(nr1.toDouble() * nr2.toDouble())

        while (isRunning) {
            val now = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val action = actions[Random.nextInt(actions.size)]

            val intent = Intent().apply {
                this.action = action
                putExtra("TIMESTAMP", now)
                putExtra("ARITH_MEAN", arithmeticMean)
                putExtra("GEOM_MEAN", geometricMean)
            }

            Log.d(
                "ProcessingThread",
                "Broadcast: $action @ $now | arit=$arithmeticMean geom=$geometricMean"
            )

            context.sendBroadcast(intent)

            try {
                sleep(10_000)
            } catch (e: InterruptedException) {
                isRunning = false
            }
        }

        Log.d("ProcessingThread", "Thread stopped")
    }

    fun stopThread() {
        isRunning = false
        interrupt()
    }
}
