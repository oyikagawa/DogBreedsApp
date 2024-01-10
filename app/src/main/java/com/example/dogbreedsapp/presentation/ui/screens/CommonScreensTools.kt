package com.example.dogbreedsapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.presentation.ui.TrailingIcon

@Composable
fun EmptyDataLabel(labelText: String) {
    Column {
        Box(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
        ) {
            Text(
                text = labelText,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }
        Box(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TopBarWContent(
    sortValue: Boolean,
    searchValue: String,
    onSortValueChanged: (sortVal: Boolean) -> Unit,
    onSearchValueChanged: (searchVal: String) -> Unit,
    content: @Composable ((topBarHeight: Dp) -> Unit)
) {
    val topBarHeight = 70.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight)
    ) {
        Box(modifier = Modifier.weight(5f)) {
            SearchBar(searchValue = searchValue, onSearchValueChanged = onSearchValueChanged)
        }
        Box(modifier = Modifier.weight(1f)) {
            DropdownIcon(sortValue = sortValue, onSortValueChanged = onSortValueChanged)
        }
    }
    content(topBarHeight)
}

@Composable
fun SearchBar(searchValue: String, onSearchValueChanged: (searchVal: String) -> Unit) {
    BasicTextField(
        modifier = Modifier
            .padding(start = 32.dp, top = 16.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        value = searchValue,
        onValueChange = {
            onSearchValueChanged.invoke(it)
        },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    if (searchValue.isEmpty())
                        Text(
                            text = "Search the breed",
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                fontSize = 15.sp
                            )
                        )
                    innerTextField()
                }
                if (searchValue.isNotEmpty()) {
                    IconButton(onClick = { onSearchValueChanged.invoke("") }) {
                        Icon(imageVector = Icons.Rounded.Clear, contentDescription = "Clear search")
                    }
                }
            }
        }
    )
}

@Composable
fun DropdownIcon(sortValue: Boolean, onSortValueChanged: (sortVal: Boolean) -> Unit) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    fun changeSort(straightSort: Boolean) =
        onSortValueChanged.invoke(straightSort)

    IconButton(
        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
        onClick = { dropdownExpanded = true }) {
        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Sorting Menu")
    }
    DropdownMenu(
        expanded = dropdownExpanded,
        onDismissRequest = { dropdownExpanded = false }) {
        DropdownMenuItem(
            text = { Text(text = "A -> Z") },
            onClick = { changeSort(true) },
            trailingIcon = { TrailingIcon(showIcon = sortValue) },
            enabled = !sortValue
        )
        DropdownMenuItem(
            text = { Text(text = "Z -> A") },
            onClick = { changeSort(false) },
            trailingIcon = { TrailingIcon(showIcon = !sortValue) },
            enabled = sortValue
        )
    }
}

fun setupBreedLabel(breedData: BreedData): String {
    var breedText = breedData.breedType.breedName.replaceFirstChar(Char::titlecase)
    if (breedData.breedType.subBreedName.isNotEmpty()) breedText += " ${
        breedData.breedType.subBreedName.replaceFirstChar(
            kotlin.Char::titlecase
        )
    }"
    return breedText
}