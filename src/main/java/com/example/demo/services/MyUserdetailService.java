package com.example.demo.services;

import com.example.demo.config.MyUserDetails;
import com.example.demo.models.MyUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;//Интерфейс, который предоставляет метод для загрузки пользовательских данных.
import org.springframework.security.core.userdetails.UsernameNotFoundException;// Исключение, которое выбрасывается, когда пользователь не найден.

import java.util.Optional;

//Когда пользователь пытается войти в систему, Spring Security вызывает метод loadUserByUsername для проверки учётных данных.
// Если имя пользователя и пароль верны, UserDetails возвращает информацию о пользователе для дальнейшей обработки.
public class MyUserdetailService implements UserDetailsService {
    @Autowired //Аннотация для автоматического внедрения зависимости UserRepository. Spring автоматически предоставляет
    // экземпляр UserRepository, чтобы этот класс мог использовать его.
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findByName(username); //Ищет пользователя по имени в базе данных.
        return user.map(MyUserDetails::new)//Если пользователь найден, преобразует его в объект MyUserDetails.
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));//Если пользователь не найден,
        // выбрасывает исключение UsernameNotFoundException с сообщением, что пользователь не найден.
    }
}
