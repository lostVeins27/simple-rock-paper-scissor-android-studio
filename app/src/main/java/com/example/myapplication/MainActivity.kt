package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen(){

    var aiMove by remember {mutableStateOf("")}
    var result by remember {mutableStateOf("Waiting for pick.")}
    var isButtonVisible by remember {mutableStateOf(false)}
    var win by remember {mutableStateOf(0)}
    var lose by remember {mutableStateOf(0)}
    var tie by remember {mutableStateOf(0)}

    fun Restart(){
        aiMove = ""
        result = "Waiting for pick."
    }

    fun PlayerMove(move: String){

        aiMove = when((1..3).random()){
            1 -> "Rock"
            2 -> "Paper"
            3 -> "Scissor"
            else -> "Error"
        }

        if (
            (move == "Paper" && aiMove == "Rock") ||
            (move == "Rock" && aiMove == "Scissor") ||
            (move == "Scissor" && aiMove == "Paper")
        ){
            win++
            result = "You won."
        }
        else if(
            (move == "Scissor" && aiMove == "Rock") ||
            (move == "Rock" && aiMove == "Paper") ||
            (move == "Paper" && aiMove == "Scissor")
        ){
            lose++
            result = "You lost."
        }else{
            tie++
            result = "Tie"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(
            text = "Ai move: $aiMove",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = result,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)

        ){

            Button(
                onClick = {
                    PlayerMove("Rock")
                    isButtonVisible = true
                    Log.d("Move", "Rock")
                          },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                enabled = !isButtonVisible
            ){
                Text("Rock")
            }
            Button(
                onClick = {
                    PlayerMove("Scissor")
                    isButtonVisible = true
                    Log.d("Move", "Scissor")
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                enabled = !isButtonVisible
            ){
                Text("Scissor")
            }

            Button(
                onClick = {
                    PlayerMove("Paper")
                    isButtonVisible = true
                    Log.d("Move", "Paper")
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                enabled = !isButtonVisible
            ){
                Text("Paper")
            }



        }
        AnimatedVisibility(visible = isButtonVisible) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Button(
                    onClick = {
                        Restart()
                        isButtonVisible = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 50.dp),
                    enabled = isButtonVisible
                ){
                    Text("Try again")
                }

                Text(
                    text = "win: $win\nlose: $lose\ntie: $tie"
                )

            }

        }



    }

}

@Preview(showBackground = true)
@Composable
fun GamePreview(){
    GameScreen()
}