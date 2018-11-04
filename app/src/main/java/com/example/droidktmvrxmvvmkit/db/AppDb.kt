/* Copyright 2018 Fred Grott

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.example.droidktmvrxmvvmkit.db

import com.example.droidktmvrxmvvmkit.model.Repo
import androidx.room.Room
import io.reactivex.Flowable
import retrofit2.http.Query
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Database(entities = arrayOf(Repo::class), version = 2, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)


    //m

    // got a not applicable for target erro so rewrote it
    abstract fun loadRepositories(@Query("SELECT * FROM Repo "
            + "WHERE lower(owner_login) = lower(:owner)"
            + "ORDER BY stars DESC")owner: String): Flowable<List<Repo>>

}