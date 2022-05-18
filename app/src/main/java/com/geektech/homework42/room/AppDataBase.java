package com.geektech.homework42.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.homework42.models.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}