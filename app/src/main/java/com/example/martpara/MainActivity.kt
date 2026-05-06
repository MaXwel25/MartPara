package com.example.martpara

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.provider.Telephony
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.martpara.ui.theme.MartParaTheme
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.example.martpara.composeUI.Form
import com.example.martpara.composeUI.ReminderList


class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this, R.string.permission_warning, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                view.setBackgroundColor(android.graphics.Color.BLACK)
                view.setPadding(0, statusBarInsets.top, 0, 0)
                insets
            }
        } else {
            window.statusBarColor = android.graphics.Color.BLACK
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }

        setContent {
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }



        setContent {
            MartParaTheme {
                // скрытие системных баров
                WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                    controller.hide(WindowInsetsCompat.Type.systemBars())
                    controller.hide(WindowInsetsCompat.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }

                val viewModel: MainViewModel = viewModel()
                viewModel.alarmManager = alarmManager

                // основной интерфейс приложения
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(R.color.black),
                                    colorResource(R.color.navy)
                                )
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppTitle()
                    Form(viewModel)
                    ReminderList(viewModel)
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun calculate() {
        if (firstNumber.isEmpty() || secondNumber.isEmpty()) {
            Toast.makeText(context, "Введите оба числа!", Toast.LENGTH_SHORT).show()
            return
        }
        if (operation.isEmpty()) {
            Toast.makeText(context, "Выберите операцию!", Toast.LENGTH_SHORT).show()
            return
        }
        val num1 = firstNumber.toDoubleOrNull()
        val num2 = secondNumber.toDoubleOrNull()
        if (num1 == null || num2 == null) {
            Toast.makeText(context, "Введите корректные числа!", Toast.LENGTH_SHORT).show()
            return
        }
        if (operation == "/" && num2 == 0.0) {
            Toast.makeText(context, "Делить на ноль нельзя!", Toast.LENGTH_SHORT).show()
            return
        }
        val calculationResult = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> 0.0
        }
        result = calculationResult.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Простой калькулятор",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = firstNumber,
            onValueChange = { firstNumber = it },
            label = { Text("Первое число") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = secondNumber,          // исправлено
            onValueChange = { secondNumber = it },
            label = { Text("Второе число") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Выберите операцию: ", modifier = Modifier.padding(bottom = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly   // исправлено
        ) {
            OperationButton(
                operation = "+",
                isSelected = operation == "+",
                onClick = { operation = "+" }
            )
            OperationButton(
                operation = "-",
                isSelected = operation == "-",
                onClick = { operation = "-" }
            )
            OperationButton(
                operation = "*",
                isSelected = operation == "*",
                onClick = { operation = "*" }
            )
            OperationButton(
                operation = "/",
                isSelected = operation == "/",
                onClick = { operation = "/" }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { calculate() },
            modifier = Modifier
                .fillMaxWidth()          // исправлено
                .height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Посчитать", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text(
                    text = "Результат: $result",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun OperationButton(
    operation: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Blue else Color.Gray
        )
    ) {
        Text(
            text = operation,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    MartParaTheme {
        CalculatorApp()
    }
}

@Composable
fun AppTitle() {
    Text(
        text = stringResource(R.string.app_title),
        style = TextStyle(
            color = colorResource(R.color.white),
            fontSize = 26.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Thin
        )
    )
}