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