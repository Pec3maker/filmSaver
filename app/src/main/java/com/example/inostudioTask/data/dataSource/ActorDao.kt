package com.example.inostudioTask.data.dataSource

import androidx.room.*
import com.example.inostudioTask.data.dataSource.dto.ActorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao  {

    @Query("SELECT * FROM ActorEntity")
    fun getActors(): Flow<List<ActorEntity>>

    @Query("SELECT * FROM ActorEntity WHERE id = :id")
    suspend fun getActorById(id: Int): ActorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActorEntity)

    @Delete
    suspend fun deleteActor(actor: ActorEntity)
}