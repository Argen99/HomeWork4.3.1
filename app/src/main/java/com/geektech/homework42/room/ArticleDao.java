package com.geektech.homework42.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.geektech.homework42.models.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Insert
    void insert(Article article);


    @Query("SELECT * FROM article WHERE text LIKE '%' || :search || '%'")
    List<Article> getSearch(String search);

    @Delete
    void delete(Article model);

}
