package com.example.martpara.composeUI


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.martpara.AppRepository
import com.example.martpara.MainViewModel
import com.example.martpara.dataBase.Reminds
import java.nio.file.WatchEvent
import java.util.Date


@Composable
fun ReminderList(viewModel: MainViewModel= viewModel()) {

    val remindersList = remember { mutableStateListOf<Reminds>() }

    viewModel.remindList.observe(LocalLifecycleOwner.current) { // doing list observe - смотрит за изменениями
        remindersList.clear()
        val tl = it
        val cdt = Date().time
        tl?.forEach { reminds ->
            if (reminds.datetime<cdt)
                AppRepository.getInstance().deleteRemind(reminds)
            else
                remindersList.add(reminds)
        }
    }

    LazyColumn( // для плавной размектки
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(remindersList) {
            _,remind -> ReminderItem(remind, viewModel)
        }
    }
}
