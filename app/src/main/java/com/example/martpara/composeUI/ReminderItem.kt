package com.example.martpara.composeUI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.martpara.MainViewModel
import com.example.martpara.R
import com.example.martpara.Utils
import com.example.martpara.dataBase.Reminds
import androidx.compose.ui.text.TextStyle

@Composable
fun ReminderItem (reminder: Reminds, viewModel: MainViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp, end = 5.dp, bottom = 5.dp, top = 5.dp)
            .background(colorResource(R.color.teal_200), RoundedCornerShape(25.dp))
            .border(0.5.dp, Color.Cyan, RoundedCornerShape(25.dp))
            .clickable {
                Toast
                    .makeText(context, reminder.text, Toast.LENGTH_LONG)
                    .show()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = reminder.text,
            style = TextStyle(color = colorResource(R.color.white)),
            modifier = Modifier.fillMaxWidth(0.50f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis // Для отображения многоточия при наводке на жлемент
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = Utils.millisecondsToDate(reminder.datetime),
                style = TextStyle(color = colorResource(R.color.purple_700)),
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = Utils.millisecondsToTime(reminder.datetime),
                style = TextStyle(color = colorResource(R.color.purple_700)),
                modifier = Modifier.padding(8.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_launcher_background), // иконка (можно свою)
                contentDescription = "Delete",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .clickable{
                        viewModel.deleteReminder(context, reminder)
                    }
            )

        }
    }
}