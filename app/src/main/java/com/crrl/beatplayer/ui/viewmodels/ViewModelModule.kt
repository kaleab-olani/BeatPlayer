/*
 * Copyright (c) 2020. Carlos René Ramos López. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crrl.beatplayer.ui.viewmodels

import android.content.Context
import com.crrl.beatplayer.repository.AlbumsRepository
import com.crrl.beatplayer.repository.ArtistsRepository
import com.crrl.beatplayer.repository.FavoritesRepository
import com.crrl.beatplayer.ui.activities.MainActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { (context: Context) ->
        SongViewModel(context)
    }

    viewModel { (repository: AlbumsRepository) ->
        AlbumViewModel(repository)
    }

    viewModel { (repository: ArtistsRepository) ->
        ArtistViewModel(repository)
    }

    viewModel { (context: Context) ->
        FolderViewModel(context)
    }

    viewModel { (context: Context) ->
        MainViewModel(context)
    }

    viewModel { (safeActivity: MainActivity) ->
        SongDetailViewModel(safeActivity)
    }

    viewModel { (repository: FavoritesRepository) ->
        FavoriteViewModel(repository)
    }
}