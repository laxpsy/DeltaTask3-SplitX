package com.example.deltatask3splitx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deltatask3splitx.retrofit.dataclasses.UserIdDataClassForSplitIDArray
import com.example.deltatask3splitx.retrofit.dataclasses.UserNetSplitsDataClass
import com.example.deltatask3splitx.retrofit.dataclasses.UsernameUserIDDataClass
import com.example.deltatask3splitx.ui.theme.DeltaTask3SplitXTheme
import com.example.deltatask3splitx.ui.theme.FadedBlue
import com.example.deltatask3splitx.ui.theme.FadedGray
import com.example.deltatask3splitx.ui.theme.FadedOrange
import com.example.deltatask3splitx.ui.theme.FadedPink
import com.example.deltatask3splitx.ui.theme.FadedRed
import com.example.deltatask3splitx.ui.theme.FadedTeal
import com.example.deltatask3splitx.ui.theme.FadedYellow
import com.example.deltatask3splitx.ui.theme.HollowPurple
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.text.NumberFormat
import kotlin.math.roundToLong


class MainActivity : ComponentActivity() {

    private val RetrofitImplementation = RetrofitImplementation()

    var splitIDs: List<Int> = listOf()
    var listNames: MutableList<UsernameUserIDDataClass> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login_screen") {
                composable("login_screen") {
                    LoginScreen(navController)
                }
                composable(
                    "dashboard/{username}/{userID}",
                    arguments = listOf(navArgument("username") { type = NavType.StringType },
                        navArgument("userID") { type = NavType.IntType })
                ) {
                    Dashboard(
                        navController,
                        username = it.arguments?.getString("username"),
                        userID = it.arguments?.getInt("userID")
                    )
                }
                composable(
                    "splits_history/{username}/{userID}",
                    arguments = listOf(navArgument("username") { type = NavType.StringType },
                        navArgument("userID") { type = NavType.IntType })
                ) {
                    HistoryScreen(
                        navController = navController,
                        username = it.arguments?.getString("username"),
                        userID = it.arguments?.getInt("userID")
                    )
                }
                composable(
                    "new_split/{username}/{userID}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType },
                        (navArgument("userID") { type = NavType.IntType })
                    )
                ) {
                    NewSplit(
                        navController = navController,
                        username = it.arguments?.getString("username"),
                        userID = it.arguments?.getInt("userID"),
                    )
                }

            }


        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(navController: NavController) {

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        DeltaTask3SplitXTheme(darkTheme = true) {
            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { contentPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    color = MaterialTheme.colorScheme.background
                ) {


                    var regIfTrue by remember { mutableStateOf(true) }
                    var usernameState by remember { mutableStateOf("") }
                    var passwordState by remember { mutableStateOf("") }
                    val greenGrayAnimation by animateColorAsState(if (regIfTrue) Color.Green else Color.Gray)
                    val grayGreenAnimation by animateColorAsState(if (regIfTrue) Color.Gray else Color.Green)
                    val infiniteSplitXAnimation = rememberInfiniteTransition()
                    val SplitXColor by infiniteSplitXAnimation.animateColor(
                        initialValue = Color.Green,
                        targetValue = Color.Red,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 3000, easing = EaseInOutElastic),
                            repeatMode = RepeatMode.Reverse
                        )
                    )


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        SplitXStandardText(
                            text = "Greetings.", fontWeight = FontWeight.Medium, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        SplitXStandardText(
                            text = "Welcome to", fontWeight = FontWeight.Medium, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        SplitXDisplayText(
                            text = "SplitX", fontWeight = FontWeight.Bold, color = SplitXColor
                        )

                        Spacer(modifier = Modifier.height(120.dp))

                        SplitXTextField(
                            label = "username",
                            iconID = R.drawable.baseline_person_outline_24,
                            usernameState,
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        ) {
                            if (it.contains(" ")) {
                                scope.launch { snackbarHostState.showSnackbar("Spaces are not allowed in usernames!") }
                            }
                            usernameState = it
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        SplitXTextField(
                            label = "password",
                            iconID = R.drawable.outline_password_24,
                            passwordState,
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        ) { passwordState = it }

                        Spacer(modifier = Modifier.height(20.dp))



                        SplitXButton(iconID = if (regIfTrue) R.drawable.outline_send_24 else R.drawable.outline_fact_check_24) {
                            var userID: Int? = null
                            val exceptionHandler =
                                CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
                            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

                                if (usernameState.isBlank() || passwordState.isBlank()) {
                                    snackbarHostState.showSnackbar("Username/Password must be non-empty!")
                                } else {
                                    if (!usernameState.contains(regex = Regex("[A-Z]"))) {
                                        snackbarHostState.showSnackbar("Username must contain at least one capital letter.")
                                    } else {
                                        if (regIfTrue) RetrofitImplementation.createAccount(
                                            usernameState.replace(" ", ""), passwordState
                                        )
                                        else {
                                            withContext(Dispatchers.Default) {
                                                try {
                                                    userID = RetrofitImplementation.loginValidation(
                                                        usernameState.replace(" ", ""),
                                                        passwordState
                                                    )
                                                } catch (e: SocketTimeoutException) {
                                                    snackbarHostState.showSnackbar("Timeout!")
                                                }
                                            }
                                            if (userID == -1) {
                                                snackbarHostState.showSnackbar("Please enter correct password!")
                                            }
                                        }

                                        withContext(Dispatchers.Main) {
                                            println("testRun, at $userID")
                                            if (userID != null && userID != -1) {
                                                println("Navigating")
                                                navController.navigate("dashboard/${usernameState}/${userID}")
                                            }
                                        }


                                    }
                                }

                            }


                        }

                    }

                    Spacer(modifier = Modifier.height(200.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SplitXTextButton(
                            text = "register", onClick = {
                                regIfTrue = !regIfTrue
                            }, enabled = !regIfTrue, color = greenGrayAnimation
                        )
                        SplitXStandardText(
                            text = "|", fontSize = 20.sp
                        )
                        SplitXTextButton(
                            text = "login",
                            onClick = { regIfTrue = !regIfTrue },
                            enabled = regIfTrue,
                            color = grayGreenAnimation
                        )
                    }

                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Dashboard(navController: NavController, username: String?, userID: Int?) {
        DeltaTask3SplitXTheme(darkTheme = true) {

            val currencyFormat = NumberFormat.getCurrencyInstance()
            var totalDetails: List<Double> by remember { mutableStateOf(listOf()) }
            var youOwe: Double by remember { mutableStateOf(0.0) }
            var youLent: Double by remember { mutableStateOf(0.0) }
            var responseFromRetrofit: Response<UserNetSplitsDataClass>
            var canNavigateFlag by remember { mutableStateOf(false) }
            var timeoutFlag by remember { mutableStateOf(false) }


            LaunchedEffect(navController.currentBackStackEntry) {
                try {
                    responseFromRetrofit = RetrofitImplementation.fetchUserNetSplitData(userID!!)
                    if(responseFromRetrofit.body() != null) {
                        totalDetails = responseFromRetrofit.body()!!.userSplitAmountsArray
                        splitIDs = responseFromRetrofit.body()!!.userAllSplitsID
                    }
                    youOwe = 0.0
                    youLent = 0.0
                    for (i in totalDetails) {
                        if (i < 0) youOwe += i
                        else youLent += i
                        timeoutFlag = false
                    }
                    listNames = RetrofitImplementation.fetchUsers()
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    timeoutFlag = true
                }
                canNavigateFlag = true

            }

            //FAB TO RETRY FETCH ON TIMEOUT
            Scaffold(floatingActionButton = {
                if (timeoutFlag) {
                    SplitXFab {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                responseFromRetrofit =
                                    RetrofitImplementation.fetchUserNetSplitData(userID!!)
                                totalDetails = responseFromRetrofit.body()!!.userSplitAmountsArray
                                splitIDs = responseFromRetrofit.body()!!.userAllSplitsID

                                for (i in totalDetails) {
                                    if (i < 0) youOwe += i
                                    else youLent += i
                                    timeoutFlag = false
                                }
                                listNames = RetrofitImplementation.fetchUsers()
                            } catch (e: SocketTimeoutException) {
                                e.printStackTrace()
                                timeoutFlag = true
                            }
                            canNavigateFlag = true

                        }
                    }
                } else {

                }
            }) { it ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it), color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(top = 30.dp)
                    ) {

                        SplitXStandardText(
                            text = "Dashboard", fontWeight = FontWeight.Medium, fontSize = 40.sp
                        )

                        if (!username.isNullOrBlank()) {
                            SplitComposableBadge(
                                name = username,
                                color = HollowPurple,
                                80.dp,
                                40.sp
                            )
                        }

                        SplitXStandardText(
                            text = "Welcome,\n${username}",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )

                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                SplitXStandardText(
                                    text = if (!timeoutFlag) currencyFormat.format((0 - youOwe).roundToLong()) else "Timeout!",
                                    fontSize = 20.sp,
                                    color = Color.Red
                                )
                                SplitXStandardText(
                                    text = "You Owe!",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                SplitXStandardText(
                                    text = if (!timeoutFlag) currencyFormat.format((youLent).roundToLong()) else "Timeout!",
                                    fontSize = 20.sp,
                                    color = Color.Green
                                )
                                SplitXStandardText(
                                    text = "You Lent!",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        SplitXNavigation(
                            1, navController = navController, username, userID, canNavigateFlag
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NewSplit(
        navController: NavController, username: String?, userID: Int?
    ) {

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
            DeltaTask3SplitXTheme(darkTheme = true) {

                var reasonState by remember { mutableStateOf("") }
                var amountState by remember { mutableStateOf("") }
                val colors = remember {
                    listOf(
                        FadedBlue,
                        FadedGray,
                        HollowPurple,
                        FadedRed,
                        FadedYellow,
                        FadedTeal,
                        FadedOrange,
                        FadedPink
                    ).shuffled()
                }
                val listSelectedNames: MutableList<UserIdDataClassForSplitIDArray> = mutableListOf()
                listSelectedNames.add(UserIdDataClassForSplitIDArray(userID!!))
                var canNavigate by remember { mutableStateOf(false) }
                listNames.remove(UsernameUserIDDataClass(username!!, userID))


                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(top = 30.dp)
                    ) {

                        SplitXStandardText(
                            text = "New Split", fontWeight = FontWeight.Medium, fontSize = 40.sp
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        SplitXTextField(
                            label = "amount",
                            iconID = R.drawable.outline_currency_rupee_24,
                            amountState,
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )
                        ) { amountState = it }

                        SplitXTextField(
                            label = "reason",
                            iconID = R.drawable.outline_question_mark_24,
                            reasonState,
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        ) { reasonState = it }

                        Spacer(modifier = Modifier.height(60.dp))

                        SplitXButton(iconID = R.drawable.outline_add_circle_outline_24) {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (reasonState.isBlank() || amountState.isBlank()) {
                                    scope.launch { snackbarHostState.showSnackbar("Reason/Amount shouldn't be blank") }
                                }
                                try {
                                    RetrofitImplementation.newSplit(
                                        listSelectedNames,
                                        amountState.toInt(),
                                        userID, reasonState
                                    )
                                } catch (e: SocketTimeoutException) {
                                    e.printStackTrace()
                                    scope.launch { snackbarHostState.showSnackbar("Timeout!") }
                                }
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn((listNames.size * 50).dp, 200.dp)
                        ) {
                            itemsIndexed(listNames) { index: Int, name ->
                                SplitXNameComposable(
                                    name = name.username,
                                    id = name.userID,
                                    color = colors[index % colors.size],
                                    state = false,
                                    listSelectedNames
                                )
                            }
                        }

                        SplitXNavigation(
                            0, navController = navController, username, userID, true
                        )
                    }
                }


            }
        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HistoryScreen(
        navController: NavController, username: String?, userID: Int?
    ) {
        val listListUsernames = remember { mutableStateListOf((listOf<String>())) }
        val listListUserIDs = remember { mutableStateListOf((listOf<Int>())) }
        val listofAmounts = remember { mutableStateListOf<Int>() }
        val listOfOriginNames = remember { mutableStateListOf<String?>() }
        val listOfReasons = remember { mutableStateListOf<String?>() }
        var flag by remember { mutableStateOf(0) }
        var canNavigate by remember { mutableStateOf(false) }
        val listListSettled = remember { mutableStateListOf((listOf<Int>())) }
        var timeoutFlag by remember { mutableStateOf(false) }

        //FAB TO RETRY FETCH
        Scaffold(floatingActionButton = {
            if (timeoutFlag)
                SplitXFab {
                    CoroutineScope(Dispatchers.IO).launch {
                        splitIDs.forEachIndexed { index, it ->

                            try {
                                val response = RetrofitImplementation.getIndividualSplitDetails(it)
                                if (response.body() != null) {
                                    listListUsernames.add(
                                        RetrofitImplementation.findNames(
                                            listNames,
                                            response
                                        )
                                    )
                                    listofAmounts.add(RetrofitImplementation.findAmount(response)!!)
                                    listOfOriginNames.add(
                                        RetrofitImplementation.findOrigin(
                                            listNames,
                                            response
                                        )!!
                                    )
                                    listOfReasons.add(RetrofitImplementation.findReason(response)!!)
                                    listListSettled.add(RetrofitImplementation.findSettled(response))
                                    listListUserIDs.add(RetrofitImplementation.findIDs(response))
                                    println("${it}, ${listListUsernames[index]}, ${listofAmounts[index]}, ${listOfOriginNames[index]}, @ ${index}")
                                    timeoutFlag = false
                                }
                            } catch (e: SocketTimeoutException) {
                                flag = 2
                                canNavigate = true
                                timeoutFlag = true
                            }

                        }
                        flag = 1
                        canNavigate = true
                    }
                }
            else {

            }
        }) { it ->
            DeltaTask3SplitXTheme(darkTheme = true) {

                LaunchedEffect(navController.currentBackStackEntry) {
                    splitIDs.forEachIndexed { index, it ->

                        try {
                            val response = RetrofitImplementation.getIndividualSplitDetails(it)
                            if (response.body() != null) {
                                listListUsernames.add(
                                    RetrofitImplementation.findNames(
                                        listNames,
                                        response
                                    )
                                )
                                listofAmounts.add(RetrofitImplementation.findAmount(response)!!)
                                listOfOriginNames.add(
                                    RetrofitImplementation.findOrigin(
                                        listNames,
                                        response
                                    )!!
                                )
                                listOfReasons.add(RetrofitImplementation.findReason(response)!!)
                                listListSettled.add(RetrofitImplementation.findSettled(response))
                                listListUserIDs.add(RetrofitImplementation.findIDs(response))
                                println("${it}, ${listListUsernames[index]}, ${listofAmounts[index]}, ${listOfOriginNames[index]}, @ ${index}")
                            }
                        } catch (e: SocketTimeoutException) {
                            flag = 2
                            canNavigate = true
                            timeoutFlag = true
                        }

                    }
                    flag = 1
                    canNavigate = true
                }


                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(top = 40.dp)
                    ) {
                        SplitXStandardText(
                            text = "History of Splits",
                            fontWeight = FontWeight.Medium,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        SplitXStandardText(
                            text = username!!, fontSize = 20.sp
                        )

                        if (flag == 1) {
                            Spacer(modifier = Modifier.height(50.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .height(500.dp)
                                    .fillMaxWidth()
                            ) {
                                itemsIndexed(splitIDs) { index: Int, item ->
                                    SplitComposable(
                                        purpose = listOfReasons[index]!!,
                                        amount = listofAmounts[index],
                                        names = listListUsernames[index + 1],
                                        origin = listOfOriginNames[index],
                                        username, listListSettled[index + 1],
                                        splitIDs[index], listListUserIDs[index + 1]
                                    )
                                }
                            }
                        } else if (flag == 2) {
                            SplitXStandardText(
                                text = "Timeout",
                                color = Color.Red,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(620.dp))
                        } else {
                            SplitXStandardText(
                                text = "No Result",
                                color = Color.Red,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(620.dp))
                        }

                        SplitXNavigation(
                            screenID = 2,
                            navController = navController,
                            username = username,
                            userID = userID,
                            canNavigate = canNavigate
                        )


                    }
                }
            }
        }
    }

    // @Preview
    @Composable
    fun RegLogPreview() {
        LoginScreen(NavController((this)))
    }

    // @Preview
    @Composable
    fun DashboardPreview() {
        Dashboard(NavController((this)), "LaxPsy", 3)
    }

    //@Preview
    @Composable
    fun NewSplitPreview() {
        NewSplit(NavController((this)), "LaxPsy", 3)
    }

    @Preview
    @Composable
    fun HistoryScreenPreview() {
        HistoryScreen(NavController((this)), "LaxPsy", 3)
    }

}





