package ru.vladalexeco.alarmclockforprogrammers

import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ru.vladalexeco.alarmclockforprogrammers.databinding.ActivityTaskScreenBinding

class TaskScreenActivity : AppCompatActivity() {

    var ringtone: Ringtone? = null
    private lateinit var currentTask: Task

    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this)[TaskViewModel::class.java]
    }

    private lateinit var bind: ActivityTaskScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTaskScreenBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var notificationUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, notificationUri)

        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ringtone = RingtoneManager.getRingtone(this, notificationUri)
        }

        ringtone?.play() ?: Toast.makeText(this, "No Ringtone", Toast.LENGTH_LONG).show()

        currentTask = taskViewModel.getRandomTask()

        bind.questionTextView.text = currentTask.question
        bind.taskImageView.setImageResource(currentTask.taskId)

        bind.checkAnswerButton.setOnClickListener {
            val userAnswer: String = bind.answerEditText.text.toString()
            val taskAnswer: String = currentTask.answer

            if (userAnswer == taskAnswer) {
                Toast.makeText(this, "Верно!", Toast.LENGTH_LONG).show()
                ringtone?.stop()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Неверно. Думайте!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        ringtone?.stop()
        super.onDestroy()
    }
}