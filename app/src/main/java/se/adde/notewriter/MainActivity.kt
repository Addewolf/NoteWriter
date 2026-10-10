package se.adde.notewriter

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private lateinit var header: EditText
    private lateinit var body: EditText
    private lateinit var save: Button
    private lateinit var timestamp: TextView
    private var pendingText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        header = findViewById(R.id.header)
        body = findViewById(R.id.body)
        save = findViewById(R.id.save)
        timestamp = findViewById(R.id.timestamp)
        pendingText = savedInstanceState?.getString("pendingText")
        save.isEnabled = pendingText == null
        save.setOnClickListener { chooseDestination() }

        // Handle both system bars and the keyboard on edge-to-edge Android versions.
        if (Build.VERSION.SDK_INT >= 30) {
            window.setDecorFitsSystemWindows(false)
            val root = findViewById<View>(R.id.root)
            val padding = (16 * resources.displayMetrics.density).toInt()
            root.setOnApplyWindowInsetsListener { view, insets ->
                val safe = insets.getInsets(
                    WindowInsets.Type.systemBars() or WindowInsets.Type.ime() or
                        WindowInsets.Type.displayCutout()
                )
                view.setPadding(padding + safe.left, padding + safe.top,
                    padding + safe.right, padding + safe.bottom)
                insets
            }
            root.requestApplyInsets()
        }
    }

    override fun onResume() {
        super.onResume()
        showTimestamp(Date())
    }

    private fun formatted(pattern: String, date: Date): String =
        SimpleDateFormat(pattern, Locale.US).format(date)

    private fun showTimestamp(date: Date) {
        timestamp.text = getString(R.string.timestamp,
            formatted("yyyy-MM-dd", date), formatted("HH:mm", date))
    }

    @Suppress("DEPRECATION") // Platform Activity API keeps this small app free of UI libraries.
    private fun chooseDestination() {
        val now = Date()
        showTimestamp(now)
        // Snapshot the note and its local timestamp before leaving for the picker.
        pendingText = "Date: ${formatted("yyyy-MM-dd", now)}\n" +
            "Time: ${formatted("HH:mm", now)}\n" +
            "Header: ${header.text}\n\n${body.text}\n"
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "${formatted("yyyy-MM-dd_HHmm", now)}.txt")
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }
        save.isEnabled = false
        try {
            startActivityForResult(intent, CREATE_NOTE)
        } catch (_: ActivityNotFoundException) {
            pendingText = null
            save.isEnabled = true
            Toast.makeText(this, R.string.picker_missing, Toast.LENGTH_LONG).show()
        }
    }

    @Deprecated("Uses the platform result callback without an additional dependency")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != CREATE_NOTE) return
        val text = pendingText
        pendingText = null
        if (resultCode != RESULT_OK) {
            save.isEnabled = true
            return
        }
        val uri = data?.data
        if (uri == null || text == null) {
            save.isEnabled = true
            Toast.makeText(this, R.string.save_failed, Toast.LENGTH_LONG).show()
            return
        }
        val context = applicationContext
        // File-provider I/O can be slow. Keep the editor responsive and never clear it.
        Thread {
            val message = try {
                val stream = context.contentResolver.openOutputStream(uri, "wt")
                    ?: throw IOException("The document provider returned no stream")
                stream.bufferedWriter(Charsets.UTF_8).use { it.write(text) }
                R.string.saved
            } catch (_: Exception) {
                R.string.save_failed
            }
            runOnUiThread {
                if (!isDestroyed) save.isEnabled = true
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // EditText saves its own contents; retain the snapshot across picker recreation too.
        outState.putString("pendingText", pendingText)
    }

    private companion object {
        const val CREATE_NOTE = 1
    }
}
