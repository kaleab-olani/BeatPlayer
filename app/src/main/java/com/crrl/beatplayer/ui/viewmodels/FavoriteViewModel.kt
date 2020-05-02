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

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crrl.beatplayer.models.Favorite
import com.crrl.beatplayer.models.Song
import com.crrl.beatplayer.repository.FavoritesRepository
import com.crrl.beatplayer.ui.viewmodels.base.CoroutineViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val repository: FavoritesRepository
) : CoroutineViewModel(Main) {

    private val favoriteListData = MutableLiveData<List<Favorite>>()
    private val songListData = MutableLiveData<List<Song>>()

    fun deleteSong(id: Long){
        repository.deleteFavorites(longArrayOf(id))
    }

    fun songListFavorite(idFavorites: Long): LiveData<List<Song>> {
        launch {
            val songs = withContext(IO) {
                repository.getSongsForFavorite(idFavorites)
            }
            songListData.postValue(songs)
        }
        return songListData
    }

    fun getFavorites(): LiveData<List<Favorite>> {
        launch {
            val favorites = withContext(IO) {
                try {
                    repository.getFavorites()
                } catch (ex: SQLiteException) {
                    emptyList<Favorite>()
                } catch (ex: IllegalStateException) {
                    emptyList<Favorite>()
                }
            }
            favoriteListData.postValue(favorites)
        }
        return favoriteListData
    }

    fun getFavorite(id: Long): Favorite {
        return repository.getFavorite(id)
    }

    fun deleteFavorites(ids: LongArray) {
        repository.deleteFavorites(ids)
    }

    fun addToFavorite(favoriteId: Long, songs: List<Song>): Int {
        return repository.addSongByFavorite(favoriteId, songs)
    }

    fun remove(favoriteId: Long, ids: LongArray) {
        repository.deleteSongByFavorite(favoriteId, ids)
    }
}