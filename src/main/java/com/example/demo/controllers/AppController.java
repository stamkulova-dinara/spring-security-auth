package com.example.demo.controllers;

import com.example.demo.models.Application;
import com.example.demo.models.MyUser;
import com.example.demo.services.AppService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Аннотация, которая говорит Spring, что этот класс является REST-контроллером. Методы этого класса
// будут обрабатывать HTTP-запросы и возвращать данные в формате JSON.
@RequestMapping("/api/v1/apps") //Определяет базовый URL для всех методов в этом контроллере. Все методы будут доступны по пути /api/v1/apps/.
@AllArgsConstructor //Аннотация от Lombok, которая автоматически генерирует конструктор со всеми полями этого класса.
public class AppController {
    private AppService service; //Сервисный слой, который используется для выполнения бизнес-логики, такой как работа с данными приложений и пользователей.

    @GetMapping("/welcome") //Обрабатывает GET-запросы по адресу /api/v1/apps/welcome.
    public String welcome() { //Этот метод не требует аутентификации, и доступен для всех пользователей.
        return "welcome to the unprotected page";
    }

    @GetMapping("/all-app")
    @PreAuthorize("hasRole('USER')") //Ограничивает доступ к методу только для пользователей с ролью USER
    public List<Application> allApplication() {
        return service.allApplications(); //Возвращает список всех приложений, получаемых через сервис AppService.
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Application applicationByID(@PathVariable int id) {//Метод принимает id из URL и возвращает приложение с этим id через сервис.
        return service.applicationByID(id);
    }

    @PostMapping("/new-user")
    public String addUser(@RequestBody MyUser user) {//@RequestBody указывает, что данные пользователя должны быть переданы
        // в теле запроса в формате JSON, и они будут автоматически преобразованы в объект MyUser.
        service.addUser(user);
        return "User is saved";
    }
}
