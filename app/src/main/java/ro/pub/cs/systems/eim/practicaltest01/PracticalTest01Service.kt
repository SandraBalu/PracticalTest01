package ro.pub.cs.systems.eim.practicaltest01

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

/*
 * D.1:
 * Serviciu de tip "started".
 * Primește cele două numere prin Intent (NR1, NR2),
 * pornește un ProcessingThread care trimite broadcast-uri.
 * Rulează ca foreground service pentru a evita oprirea de către sistem.
 */
class PracticalTest01Service : Service() {

    private var processingThread: ProcessingThread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val nr1 = intent?.getIntExtra("NR1", 0) ?: 0
        val nr2 = intent?.getIntExtra("NR2", 0) ?: 0

        if (processingThread == null || !processingThread!!.isAlive) {
            processingThread = ProcessingThread(applicationContext, nr1, nr2)
            processingThread!!.start()
        }

        // pentru test & simplitate e ok START_REDELIVER_INTENT sau START_STICKY
        return START_STICKY
    }

    override fun onBind(intent: Intent?) = null

    override fun onDestroy() {
        processingThread?.stopThread()
        super.onDestroy()
    }
}
