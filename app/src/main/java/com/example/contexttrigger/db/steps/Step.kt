package com.example.contexttrigger.db.steps


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
class Step(
    @PrimaryKey
    val date: String,
    var steps: Int
) {

}

