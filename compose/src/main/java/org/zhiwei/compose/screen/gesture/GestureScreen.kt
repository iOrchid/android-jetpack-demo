package org.zhiwei.compose.screen.gesture

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.zhiwei.compose.model.GestureScreenUIs

@Composable
internal fun Gesture_Screen(navController: NavController) {
    LazyColumn {
        items(GestureScreenUIs.layoutCourses()) { model ->
            ListItem(
                modifier = Modifier
                    .wrapContentHeight()
                    .clickable { navController.navigate(model.title) },
                headlineContent = { Text(text = model.title, fontSize = 14.sp) },
                supportingContent = { Text(text = model.description, fontSize = 12.sp) },
                colors = ListItemDefaults.colors(containerColor = Color(0XFFF2FDFF))
            )
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun PreviewGestureScreen() {
    Gesture_Screen(rememberNavController())
}