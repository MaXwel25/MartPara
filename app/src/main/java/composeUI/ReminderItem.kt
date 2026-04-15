package composeUI

import android.R
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.martpara.MainViewModel
import com.example.martpara.dataBase.Reminds

@Composable
fun ReminderItem (reminder: Reminds, viewModel: MainViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp, end = 5.dp, bottom = 5.dp, top = 5.dp)
            .background(colorResource(R.color.darker_gray), RoundedCornerShape(25.dp))
            .border(0.5.dp, colorResource(R.color.holo_blue_bright), RoundedCornerShape(25.dp))
            .clickable {
                Toast
                    .makeText(context, reminder.text, Toast.LENGTH_LONG)
                    .show()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )

    {
        Text(
            text = reminder.text,
            style = TextStyle(color = colorResource(R.color.white)),
            modifer = Modifier.fillMaxWidth(0.50f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis // Для отображения многоточия при наводке на жлемент
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alingment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = Utils.MillisecondsToDate(reminder.datetime),
                style = TextStyle(color = colorResource(R.color.blue)),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}