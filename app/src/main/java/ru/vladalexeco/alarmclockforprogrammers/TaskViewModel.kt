package ru.vladalexeco.alarmclockforprogrammers

import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {

    private val taskBank = listOf(
        Task(R.drawable.task_001, "Чему равно значение 'x' в последней строке?", "7"),
        Task(R.drawable.task_002, "Чему равно значение 'sum' в последней строке?", "11"),
        Task(R.drawable.task_003, "Чему равно значение 'x' в последней строке?", "9"),
    )

    fun getRandomTask(): Task = taskBank.random()
}