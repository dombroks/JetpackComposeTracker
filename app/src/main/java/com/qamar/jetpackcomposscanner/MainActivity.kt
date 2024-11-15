package com.qamar.jetpackcomposscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qamar.composescanner.trackRecompositions
import com.qamar.jetpackcomposscanner.ui.theme.JetpackComposScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposScannerTheme {
                RecompositionTrackerScreen()
            }
        }
    }
}

@Composable
fun RecompositionTrackerScreen() {
    val inputText = remember { mutableStateOf("") }
    val items = remember { mutableStateListOf<String>() }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            label = { Text("Enter item") },
            modifier = Modifier
                .fillMaxWidth()
                .trackRecompositions()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                if (inputText.value.isNotBlank()) {
                    items.add(inputText.value)
                    inputText.value = ""
                }
            },
            modifier = Modifier.trackRecompositions()
        ) {
            Text("Add Item")
        }
        Spacer(modifier = Modifier.height(16.dp))
        List(items)
    }
}

@Composable
private fun List(items: SnapshotStateList<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .trackRecompositions(),
        verticalArrangement = Arrangement.spacedBy(23.dp)
    ) {
        items(items, key = {
            it
        }) { item ->
            RecompositionItem(
                item = item,
            )
        }
    }
}

@Composable
fun RecompositionItem(
    item: String,
    modifier: Modifier = Modifier
) {
    // Recomposition tracking happens inside the modifier itself
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}