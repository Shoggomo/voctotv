/*
 * Copyright (c) 2026. Janne Mareike Koschinski
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package de.justjanne.voctotv.mobile.route.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.navigation3.runtime.NavKey
import de.justjanne.voctotv.common.viewmodel.SearchViewModel
import de.justjanne.voctotv.mobile.Routes
import de.justjanne.voctotv.mobile.ui.LectureItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    navigate: (NavKey) -> Unit,
    back: () -> Unit,
) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchLayout(
        searchField = {
            SearchField(
                query = query,
                onChange = { viewModel.query.value = it },
                onBack = back,
                modifier = Modifier.focusRequester(focusRequester),
            )
        }
    ) { contentPadding ->
        if (results.isEmpty()) {
            EmptyState(
                modifier = Modifier.padding(contentPadding),
                query = query,
            )
        } else {
            LazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(results, key = { it.guid }) { item ->
                    LectureItem(
                        item = item,
                        onClick = { navigate(Routes.PlayerVod(item.guid)) },
                    )
                }
            }
        }
    }
}
