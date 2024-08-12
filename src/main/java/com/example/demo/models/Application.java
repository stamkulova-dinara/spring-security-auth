package com.example.demo.models;

// Lombok — это библиотека, которая помогает сократить код. Она автоматически генерирует стандартные методы,такие как getter,
// setter, toString, equals, и hashCode, а также конструкторы и другие полезные методы, что значительно упрощает код.
import lombok.Builder;
import lombok.Data;

@Data //@Data: Эта аннотация автоматически создаёт все стандартные методы (геттеры, сеттеры, toString, equals, и hashCode),
// а также конструктор для всех полей.
@Builder // -помогает удобно создавать объект со всеми нужными полями.с @Builder:ты говоришь, какие данные нужны, и он собирает объект для тебя.
public class Application { //Этот класс представляет собой модель (структуру данных), которая хранит информацию об одном
    // приложении, включая его ID, имя, автора и версию
    private int id;
    private String name;
    private String author;
    private String version;
}
