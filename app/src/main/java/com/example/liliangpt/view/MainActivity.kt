package com.example.liliangpt.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.liliangpt.R
import com.example.liliangpt.model.OpenAiMessageBody
import com.example.liliangpt.model.response.GeneratedAnswer
import com.example.liliangpt.viewmodel.OpenAiViewModel
import com.example.liliangpt.ui.theme.AIEAIETheme


class MainActivity : ComponentActivity() {
    private val openAiViewModel = OpenAiViewModel()

    private val messagesList : MutableList<OpenAiMessageBody> = mutableStateListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIEAIETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(messagesList, openAiViewModel)
                    SubmitForm(messagesList, openAiViewModel)
                }
            }
        }
    }
}

@Composable
fun ChatScreen(messagesList: MutableList<OpenAiMessageBody>, openAiViewModel: OpenAiViewModel) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        NavBar(
            onHomeClick = {
               val intent = Intent(context, HomeScreen::class.java)
                context.startActivity(intent)
            }
            ,
            onBinClick = {
                messagesList.clear()
                openAiViewModel.clearConversation()
            }
        )
        MessagesItemList(
            messagesList = messagesList,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun NavBar(

    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onBinClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.Green
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier
                .size(24.dp)
                ////////////////////////////////
                .clickable(onClick = onHomeClick)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home"
                )
            }

            Text(
                text = "Lilian GPT </>",
                color = Color.Black,

                )
            Box(modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onBinClick)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bin),
                    contentDescription = "Bin"
                )
            }
        }
    }
}




@Composable
fun MessagesItemList(
    messagesList: MutableList<OpenAiMessageBody>,
    modifier: Modifier = Modifier
) {
    Surface(color = Color.Black) {
        LazyColumn(
            reverseLayout = false,
            modifier = Modifier.fillMaxSize()
        ) {
            items(messagesList) { message ->

                val bubbleColor = if (message.role == "user") {
                    Color.Blue
                } else {
                    Color.LightGray
                }
                val alignment = if (message.role == "user") Arrangement.End else Arrangement.Start
                Row(
                    horizontalArrangement = alignment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),

                    ){
                    Card(
                        modifier = Modifier.padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = message.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SubmitForm(messagesList : MutableList<OpenAiMessageBody>, openAiViewModel: OpenAiViewModel ) {
    var textState by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showPresentation by remember { mutableStateOf(true) }


    if (showError) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Rentre du texte si tu veux que je te réponde frérot",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showPresentation) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "En quoi puis-je t'aider aujourd'hui ?",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .weight(2f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (textState.isBlank()) {
                        showError = true
                    } else {
                        messagesList.add(OpenAiMessageBody("user", textState))
                        openAiViewModel.fetchMessages(messagesList)
                        textState = ""
                        showError = false
                        showPresentation = false
                    }
                },

                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text("Submit")
            }
        }
    }
}

/*
@Composable
fun MessageScreen(messagesList: MutableList<OpenAiMessageBody>, viewModel: OpenAiViewModel, openAiViewModel:OpenAiViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        if (messagesList.isEmpty()) {
            Text(
                text = "En quoi puis-je t'aider ??",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center


            )
        } else {
            ChatScreen(messagesList = messagesList, openAiViewModel)
        }
    }
}
*/