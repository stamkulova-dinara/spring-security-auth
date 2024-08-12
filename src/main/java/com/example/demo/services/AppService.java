package com.example.demo.services;

import com.example.demo.models.Application;
import com.example.demo.models.MyUser;
import com.example.demo.repository.UserRepository; //UserRepository: Импортируем репозиторий для управления пользователями в базе данных.
import com.github.javafaker.Faker; //Faker: Библиотека для генерации фиктивных данных. Используется для генерации случайных данных.
import jakarta.annotation.PostConstruct; //@PostConstruct:Аннотация для методов, которые должны быть выполнены сразу после создания бина(н: инициализация данных).
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; //PasswordEncoder: Интерфейс для шифрования паролей.
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service //@Service: Аннотация, указывающая, что этот класс является сервисом и управляется Spring.
@AllArgsConstructor //@AllArgsConstructor: Аннотация Lombok, создающая конструктор для всех полей класса.
public class AppService {
    private List<Application> applications;
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @PostConstruct //@PostConstruct: Этот метод будет выполнен сразу после создания объекта AppService. Он инициализирует список приложений фиктивными данными.
    public void loadAppInDB(){ //создаёт фейковые данные для тестирования
        Faker faker = new Faker();
        applications = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Application.builder() //Для каждого числа создаётся новый объект Application с использованием фиктивных данных.
                        .id(i)
                        .name(faker.app().name())
                        .author(faker.app().author())
                        .version(faker.app().version())
                        .build())
                .toList(); //Собирает все созданные объекты в список.
    }

    //Возвращает список всех приложений
    public List<Application> allApplications() {
        return applications;
    }

    //Ищет приложение по его ID и возвращает его. Если приложение не найдено, возвращает null
    public Application applicationByID(int id) {
        return applications.stream()
                .filter(app -> app.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addUser(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));//Шифрует пароль пользователя с помощью PasswordEncoder
        repository.save(user); //Сохраняет пользователя в базе данных через репозиторий.
    }
}