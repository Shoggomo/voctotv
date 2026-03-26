package de.justjanne.voctotv.mobile.route.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import de.justjanne.voctotv.mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    query: String,
    onChange: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBarDefaults.InputField(
        modifier = modifier,
        query = query,
        onQueryChange = onChange,
        onSearch = {},
        expanded = false,
        onExpandedChange = {},
        enabled = true,
        placeholder = { Text(stringResource(R.string.placeholder_search)) },
        leadingIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.action_back),
                )
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onChange("") }) {
                    Icon(
                        painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.action_close),
                    )
                }
            } else {
                Icon(
                    painterResource(R.drawable.ic_search),
                    contentDescription = null,
                )
            }
        },
        colors = SearchBarDefaults.colors().inputFieldColors,
        interactionSource = null,
    )
}
