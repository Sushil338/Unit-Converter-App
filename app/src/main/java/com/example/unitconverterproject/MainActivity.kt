package com.example.unitconverterproject


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverterproject.ui.theme.UnitConverterProjectTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterProjectTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {Text("Unit Converter")},
                            modifier = Modifier.height(48.dp),
                            windowInsets = TopAppBarDefaults.windowInsets,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                    }
                ){innerPadding ->
                    UnitConverterUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterUI(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("Length") }
    var inputUnit by remember { mutableStateOf("Meter") }
    var outputUnit by remember { mutableStateOf("Meter") }

    var cExpanded by remember { mutableStateOf(false) }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    fun getUnitsForCategory(cat: String): List<String> {
        return when (cat) {
            "Length" -> Converter.lengthUnits.keys.toList()
            "Weight" -> Converter.weightUnits.keys.toList()
            "Time" -> Converter.timeUnits.keys.toList()
            "Speed" -> Converter.speedUnits.keys.toList()
            "Temperature" -> Converter.temperatureUnits
            "Currency" -> Converter.currencyUnits.keys.toList()
            else -> emptyList()
        }
    }

    fun convertUnits() {
        val inputValDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = Converter.convert(category, inputValDouble, inputUnit, outputUnit)
        outputValue = ((result * 100.0).roundToInt() / 100.0).toString()
    }

    fun clearFields() {
        inputValue = ""
        outputValue = ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Unit Converter",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )

        // Value Input
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Enter Value") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Category Selector
        ExposedDropdownMenuBox(expanded = cExpanded, onExpandedChange = { cExpanded = it }) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = cExpanded, onDismissRequest = { cExpanded = false }) {
                Converter.unitCategories.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            category = it
                            val units = getUnitsForCategory(it)
                            inputUnit = units.first()
                            outputUnit = units.getOrElse(1) { units.first() }
                            cExpanded = false
                        }
                    )
                }
            }
        }

        // From & To Unit Selectors
        // From & To Unit Selectors
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val units = getUnitsForCategory(category)

            ExposedDropdownMenuBox(
                expanded = iExpanded,
                onExpandedChange = { iExpanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = inputUnit,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("From Unit") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    units.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                inputUnit = it
                                iExpanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = oExpanded,
                onExpandedChange = { oExpanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = outputUnit,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("To Unit") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    units.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                outputUnit = it
                                oExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { convertUnits() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Convert")
            }
            OutlinedButton(
                onClick = { clearFields() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }
        }

        // Result
        if (outputValue.isNotEmpty()) {
            Text(
                "Result: $outputValue $outputUnit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUnitConverter() {
    UnitConverterProjectTheme {
        UnitConverterUI()
    }
}