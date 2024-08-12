package com.example.demo.config;

import com.example.demo.models.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; //UserDetails: Интерфейс, который предоставляет
// информацию о пользователе для Spring Security.

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails { //MyUserDetails: Это реализация интерфейса UserDetails,
    // который Spring Security использует для представления информации о пользователе.
    public MyUser user;
    public MyUserDetails(MyUser user) { //Конструктор: Принимает объект MyUser и сохраняет его в поле user.
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//Этот метод возвращает коллекцию привилегий (ролей) пользователя.
        return Arrays.stream(user.getRole().split(" "))//user.getRole().split(" ") разделяет строку ролей (предполагается, что роли разделены пробелами).
                .map(SimpleGrantedAuthority::new) //map(SimpleGrantedAuthority::new) преобразует каждую роль в объект SimpleGrantedAuthority.
                .collect(Collectors.toList()); // собирает все привилегии в список.
    }

    @Override
    public String getPassword() { //Возвращает пароль пользователя.
        return user.getPassword();
    }

    @Override
    public String getUsername() { //Возвращает имя пользователя (или его логин).
        return user.getName();
    }

    @Override
    public boolean isCredentialsNonExpired() { //Указывает, истёк ли срок действия учётных данных пользователя.
        // В этом примере всегда возвращается true, что означает, что учётные данные не истекли
        return true;
    }

    @Override
    public boolean isAccountNonExpired() { //Указывает, истёк ли срок действия аккаунта пользователя.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //Указывает, заблокирован ли аккаунт пользователя.
        return true;
    }

    @Override
    public boolean isEnabled() { //Указывает, активен ли аккаунт пользователя.
        return true;
    }
}
