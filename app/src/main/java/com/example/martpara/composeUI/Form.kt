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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextField
import com.example.martpara.MainViewModel
import com.example.martpara.R


@Composable
fun Form(viewModel: MainViewModel= viewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(colorResource(R.color.dark_navy),
                RoundedCornerShape(15.dp))
            .border(0.5.dp, colorResource(R.color.blue),
                RoundedCornerShape(15.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.create_reminder),
            style = TextStyle(
                color = colorResource(R.color.white),
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Thin
            )
        )
        ReminderTextField(viewModel)
        DataTimeInputField(viewModel)
        CreateButton { viewModel.addReminder(context) }
    }
}

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
        TextField(
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
            else stringResource(R.string.choose_a_time),
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

@Composable
fun DataTimeInputField(viewModel: MainViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day=calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker,
        selectedYear: Int,
        selectedMonth: Int,
        selectedDay: Int ->
            viewModel.date =
                "${checkZero(selectedDay)}.${checkZero(selectedMonth + 1)}.$selectedYear"
        },
        year, month, day
    )
    Box {

        TextField(
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
            else stringResource(R.string.select_date),
            color = colorResource(R.color.blue),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTextField(viewModel: MainViewModel) {  // Исправлена сигнатура функции
    OutlinedTextField(
        value = viewModel.text,
        onValueChange = { viewModel.text = it },  // Добавлена запятая
        label = { Text(text = stringResource(R.string.form_text_hint)) },
        singleLine = true,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.navy),  // Исправлено: colorResouce → colorResource
            unfocusedBorderColor = colorResource(R.color.blue),
            focusedTextColor = colorResource(R.color.white),    // Исправлено
            unfocusedTextColor = colorResource(R.color.dark),
            disabledTextColor = colorResource(R.color.dark),    // Исправлено
            errorTextColor = colorResource(R.color.dark),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = colorResource(R.color.black),
            errorCursorColor = colorResource(R.color.black),
            selectionColors = TextSelectionColors(
                handleColor = colorResource(R.color.white),
                backgroundColor = colorResource(R.color.navy)
            ),
            focusedPlaceholderColor = colorResource(R.color.navy),
            unfocusedPlaceholderColor = colorResource(R.color.blue),
            disabledPlaceholderColor = colorResource(R.color.blue),
            errorPlaceholderColor = colorResource(R.color.blue)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier.fillMaxWidth()
    )
}

// Вспомогательная функция для форматирования чисел с нулём
private fun checkZero(value: Int): String = if (value < 10) "0$value" else value.toString()