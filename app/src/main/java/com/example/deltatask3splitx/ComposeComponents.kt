package com.example.deltatask3splitx

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.deltatask3splitx.ui.theme.DeltaTask3SplitXTheme
import com.example.deltatask3splitx.ui.theme.FadedBlue
import com.example.deltatask3splitx.ui.theme.FadedGray
import com.example.deltatask3splitx.ui.theme.HollowPurple
import com.example.deltatask3splitx.ui.theme.FadedRed
import com.example.deltatask3splitx.ui.theme.FadedYellow
import com.example.deltatask3splitx.ui.theme.IBMPlexMonoFamily
import java.text.NumberFormat
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.deltatask3splitx.retrofit.dataclasses.UserIdDataClassForSplitIDArray
import com.example.deltatask3splitx.ui.theme.FadedOrange
import com.example.deltatask3splitx.ui.theme.FadedPink
import com.example.deltatask3splitx.ui.theme.FadedTeal
import com.example.deltatask3splitx.ui.theme.HollowGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


val items = listOf("Split", "Profile", "History")
val iconIDs = listOf(
    R.drawable.outline_add_circle_outline_24,
    R.drawable.baseline_person_outline_24,
    R.drawable.outline_format_list_bulleted_24
)

val currencyFormat = NumberFormat.getCurrencyInstance()
val retrofitImplementation = RetrofitImplementation()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitXTextField(
    label: String,
    iconID: Int,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = false,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(painterResource(id = iconID), "Person") },
        label = {
            Row {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    label,
                    fontFamily = IBMPlexMonoFamily,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 3.sp,
                    color = Color.White
                )
            }
        },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}

@Composable
fun SplitXStandardText(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text,
        fontFamily = IBMPlexMonoFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun SplitXDisplayText(text: String, fontWeight: FontWeight, color: Color = Color.White) {
    Text(
        text,
        fontFamily = IBMPlexMonoFamily,
        fontWeight = fontWeight,
        fontSize = 40.sp,
        color = color
    )
}

@Composable
fun SplitXTextButton(text: String, enabled: Boolean, onClick: () -> Unit, color: Color) {
    TextButton(onClick = onClick, content = {
        Text(
            text,
            fontFamily = IBMPlexMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = color
        )
    }, enabled = enabled)
}


@Composable
fun SplitXButton(iconID: Int, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        shape = RectangleShape,
        elevation = ButtonDefaults.elevatedButtonElevation()
    ) {
        Icon(painterResource(id = iconID), "Submit", tint = Color.White)


        Spacer(modifier = Modifier.width(5.dp))
        SplitXStandardText(text = "Submit", fontWeight = FontWeight.Normal, fontSize = 20.sp)
    }
}

@Composable
fun SplitXNavigation(
    screenID: Int, navController: NavController,
    username: String?, userID: Int?, canNavigate: Boolean
) {

    var selectedItem by remember { mutableStateOf(screenID) }
    val routes = listOf(
        "new_split/${username}/${userID}",
        "dashboard/${username}/${userID}",
        "splits_history/${username}/${userID}"
    )

    NavigationBar() {
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(id = iconIDs[index]), contentDescription = item
                )
            }, label = {
                SplitXStandardText(
                    text = item, fontWeight = FontWeight.Normal, fontSize = 15.sp
                )
            }, selected = selectedItem == index, onClick =
            {
                if (selectedItem != index && canNavigate) {
                    selectedItem = index
                    navController.navigate(routes[selectedItem])
                }
            })
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun SplitComposable(
    purpose: String,
    amount: Int,
    names: List<String>,
    origin: String?,
    username: String?,
    settled: List<Int>,
    splitID: Int,
    userIDs: List<Int>,
   onClick: () -> Unit
) {
    val usernameIndex = names.indexOf(username)
    val settledMutable = remember{ mutableStateListOf<Int>()}
    for(i in settled)
    {
        settledMutable.add(i)
    }
    var expanded by remember { mutableStateOf(false) }
    val colors = remember {
        listOf(
            FadedBlue,
            FadedGray, HollowPurple, FadedRed,
            FadedYellow, FadedTeal, FadedOrange, FadedPink
        ).shuffled()
    }

    ElevatedCard(modifier =
    if (!expanded)
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 10.dp)
            .clickable
            {
                expanded = !expanded
            }
    else
        Modifier
            .fillMaxWidth()
            .heightIn(((names.size) * (80)).dp, 650.dp)
            .padding(vertical = 10.dp)
            .clickable
            {
                expanded = !expanded
            }
    )
    {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        {
            SplitXStandardText(text = purpose, fontWeight = FontWeight.Medium)
            SplitXStandardText(text = currencyFormat.format(amount*((names.size)-(settled.filter { it == 1 }.size))/names.size))
        }

        if (!expanded) {

            FlowRow(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            {
                names.forEachIndexed { index, name ->
                    if (name != origin)
                        SplitComposableBadge(name = name, color = colors[index % colors.size])
                    else
                        SplitComposableBadge(
                            name = name,
                            color = colors[index % colors.size],
                            id = 2
                        )
                }
            }
        } else {
            LazyColumn()
            {
                itemsIndexed(names)
                { index: Int, name ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp)
                    )
                    {
                        if (name != origin)
                            SplitComposableBadge(name = name, color = colors[index % colors.size])
                        else
                            SplitComposableBadge(
                                name = name,
                                color = colors[index % colors.size],
                                id = 2
                            )
                        SplitXStandardText(
                            text = /*if (name != origin) name
                            else if (name == username) "$name\n(You)"
                            else "$name\nI Paid!"*/
                            when(name){
                                origin -> if(username != origin) "$name\nI paid!" else "You\npaid!"
                                username -> "$name (You)"
                                else -> name},
                            fontSize = 15.sp,
                            fontWeight =
                            if (name != origin) FontWeight.Normal else FontWeight.Bold
                        )
                        SplitXStandardText(text = (currencyFormat.format(amount / names.size)).toString())


                        if (username != origin && (name == origin &&
                                    settledMutable[index] == 0) || username == origin && name != username
                        ) {
                            ElevatedButton(
                                onClick = onClick

                                , enabled = username!= origin && settledMutable[usernameIndex] == 0,
                                modifier = Modifier.width(IntrinsicSize.Min)
                            )
                            {
                                SplitXStandardText(
                                    text = when (name) {
                                        origin -> if (origin != username && settledMutable[usernameIndex] == 0)
                                            "Settle" else if (origin != username && settledMutable[usernameIndex] == 1)
                                            "Settled" else "You"
                                        else -> "Owes\nYou!"
                                    }, fontSize = 12.sp
                                )
                            }
                        } else{}
                    }
                }
            }
        }
    }
}


@Composable
fun SplitComposableBadge(
    name: String,
    color: Color,
    size: Dp = 40.dp,
    fontSize: TextUnit = 25.sp,
    id: Int = 0
) {
    //id = 0 for normal, id = 1 for checked, id = 2 for animated
    val untrimmedBadgeLetters = name.filter { it.isUpperCase() }
    val trimmedBadgeLetters = if (untrimmedBadgeLetters.length < 2)
        untrimmedBadgeLetters.substring(0, 1) else untrimmedBadgeLetters.substring(0, 2)
    val infiniteBorderAnimation = rememberInfiniteTransition()
    val BorderColor by infiniteBorderAnimation.animateColor(
        initialValue = Color.Green,
        targetValue = Color.Red,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = EaseInOutElastic),
            repeatMode = RepeatMode.Reverse
        )
    )


    Surface(
        modifier = if (id != 2) Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape)
            .size(size)
        else
            Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clip(CircleShape)
                .border(2.dp, BorderColor, CircleShape)
                .size(size), color = if (id != 1) color else HollowGreen
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            if (id != 1) {
                SplitXStandardText(
                    text = trimmedBadgeLetters,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
            } else {
                Icon(Icons.Outlined.Check, contentDescription = "Check")
                Spacer(modifier = Modifier)
            }
        }
    }
}

@Composable
fun SplitXNameComposable(
    name: String,
    id: Int,
    color: Color,
    state: Boolean,
    list: MutableList<UserIdDataClassForSplitIDArray>
) {
    var internalState by remember { mutableStateOf(state) }
    ElevatedCard(modifier = Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth()
        .clickable(onClick = {
            internalState = !internalState
            if (internalState)
                list.add(UserIdDataClassForSplitIDArray(id))
            else
                list.remove(UserIdDataClassForSplitIDArray(id))
        })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (!internalState)
                SplitComposableBadge(name = name, color = color)
            else
                SplitComposableBadge(name = name, color, id = 1)
            SplitXStandardText(text = name)
        }
    }
}

@Composable
fun SplitXFab(onClick: () -> Unit)
{
    val infiniteBorderAnimation = rememberInfiniteTransition()
    val BorderColor by infiniteBorderAnimation.animateColor(
        initialValue = Color.Green,
        targetValue = Color.Red,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = EaseInOutElastic),
            repeatMode = RepeatMode.Reverse
        )
    )
    FloatingActionButton(onClick = onClick,
        containerColor = HollowGreen,
    modifier = Modifier.border(2.dp, BorderColor, RoundedCornerShape(12.dp)))
    {
        Icon(Icons.Outlined.Refresh, "Retry")
    }
}

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TextPreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitXStandardText(
                text = "Hello there!",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        }
    }
}


//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun UsernamePreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            Surface() {
                SplitXTextField(
                    label = "username",
                    iconID = R.drawable.baseline_person_outline_24,
                    onValueChange = {},
                    value = ""
                )
            }
        }
    }
}

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PasswordPreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitXTextField(label = "password", iconID = R.drawable.outline_password_24, "") {}
        }
    }
}

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ButtonPreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitXButton(R.drawable.outline_password_24) {}
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplitComposablePreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitComposable(
                "Placeholder Reason", 1000,
                listOf(
                    "Laxpsy",
                    "GojoSatoru",
                    "SuguruGeto",
                    "RyomenSukuna",
                    "ItachiUchiha",
                    "HiruzenSarutobi", "YujiItadori",
                    "NoritoshiKamo",
                    "AoiTodo",
                    "MakiZenin"
                ), "Laxpsy", "GojoSatoru",
                listOf(0, 1, 0, 0, 1, 0, 0, 1, 0, 0), 69, listOf()){}
        }
    }
}

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplitComposableBadgePreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitComposableBadge("GujralRituAnsh", HollowPurple)
        }
    }
}

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplitXNameComposablePreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitXNameComposable(
                "GujralRituAnsh", 3,
                HollowPurple, false, mutableListOf()
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplitXFabPreview() {
    DeltaTask3SplitXTheme {
        Surface() {
            SplitXFab {

            }
        }
    }
}
