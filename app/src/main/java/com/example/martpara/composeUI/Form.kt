package com.example.martpara.composeUI

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.martpara.MainViewModel
import com.example.martpara.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputField(viewModel: MainViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            viewModel.date =
                "${checkZero(selectedDay)}:${checkZero(selectedMonth + 1)}"
        },
        year, month, day
    )

    Box {
        OutlinedTextField(
            value = viewModel.date,
            onValueChange = { viewModel.date = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.navy),
                unfocusedContainerColor = colorResource(R.color.navy),
                disabledContainerColor = colorResource(R.color.navy),
                errorContainerColor = colorResource(R.color.navy),
            ),
            enabled = false
        )
        Text(
            text = if (viewModel.date.isNotEmpty()) viewModel.date
            else stringResource(R.string.choose_date),
            color = colorResource(R.color.blue),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputField(viewModel: MainViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            viewModel.time =
                "${checkZero(selectedHour)}:${checkZero(selectedMinute)}"
        },
        hour, minute, true
    )

    Box {
        OutlinedTextField(
            value = viewModel.time,
            onValueChange = { viewModel.time = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.navy),
                unfocusedContainerColor = colorResource(R.color.navy),
                disabledContainerColor = colorResource(R.color.navy),
                errorContainerColor = colorResource(R.color.navy),
            ),
            enabled = false
        )
        Text(
            text = if (viewModel.time.isNotEmpty()) viewModel.time
            else stringResource(R.string.choose_time),
            color = colorResource(R.color.blue),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )
    }
}

@Composable
fun CreateButton(onClick: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Button(
        onClick = {
            onClick()
            keyboardController?.hide()
        },
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(id = R.color.button_gradient_purple),
                        colorResource(id = R.color.button_gradient_blue)
                    )
                ),
                shape = RoundedCornerShape(15.dp)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Text(
            text = stringResource(id = R.string.form_create),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

// Вспомогательная функция для форматирования чисел с нулём
private fun checkZero(value: Int): String = if (value < 10) "0$value" else value.toString()