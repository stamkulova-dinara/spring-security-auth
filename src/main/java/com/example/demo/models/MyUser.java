package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity //Указывает, что этот класс является сущностью JPA, т.е. его экземпляры будут храниться в базе данных. Каждый
// объект этого класса будет представлять запись в таблице базы данных.
@Table(name = "users") //Задаёт имя таблицы в базе данных, в которой будут храниться объекты этого класса.
public class MyUser { //Этот класс описывает структуру таблицы users в базе данных, которая будет хранить информацию о
    // пользователях. Благодаря аннотациям JPA и Lombok, код становится более компактным и удобным для работы
    @Id //Указывает, что это поле является уникальным идентификатором сущности. Каждый объект MyUser будет иметь уникальный id.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id будет автоматически генерироваться базой данных
    private Long id;
    @Column(unique = true) //Указывает, что поле name должно быть уникальным в таблице.
    private String name;

    private String password;

    private String role;
}
