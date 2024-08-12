package com.example.demo.repository;

import com.example.demo.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository; //JpaRepository, который предоставляет базовые методы для работы с базой данных.

import java.util.Optional; //Optional, который используется для обработки случаев, когда объект может быть null

public interface UserRepository extends JpaRepository<MyUser, Long> { //Этот интерфейс расширяет JpaRepository, который является
    // частью Spring Data JPA. JpaRepository предоставляет множество методов для работы с базой данных, таких как save(), findAll(), findById() итд.
    //MyUser — это тип сущности, с которой работает репозиторий.
    //Long — это тип данных для идентификатора (id) сущности.
    Optional<MyUser> findByName(String username);//Метод findByName автоматически создаёт запрос к базе данных для поиска
    // пользователя по имени, что упрощает реализацию поиска. Optional<MyUser>. Это означает, что метод может вернуть
    // либо объект MyUser, либо пустой объект, если пользователь с таким именем не найден.
}
