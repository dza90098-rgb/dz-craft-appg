package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAr: String,
    val titleEn: String,
    val contentAr: String,
    val contentEn: String,
    val date: String,
    val categoryAr: String,
    val categoryEn: String,
    val imageResName: String = ""
)

@Entity(tableName = "rules")
data class RuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ruleAr: String,
    val ruleEn: String,
    val categoryAr: String,
    val categoryEn: String,
    val index: Int
)

@Entity(tableName = "faqs")
data class FaqEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionAr: String,
    val questionEn: String,
    val answerAr: String,
    val answerEn: String
)

@Entity(tableName = "staff")
data class StaffEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val roleAr: String,
    val roleEn: String,
    val rankColorHex: String,
    val avatarUrl: String = ""
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val date: String,
    val rewardAr: String,
    val rewardEn: String,
    val isActive: Boolean = true
)

@Entity(tableName = "gallery")
data class GalleryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUrl: String,
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String,
    val descriptionEn: String
)
