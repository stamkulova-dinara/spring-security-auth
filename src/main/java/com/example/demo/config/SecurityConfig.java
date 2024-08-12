package com.example.demo.config;

import com.example.demo.services.MyUserdetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Эта аннотация указывает, что класс является конфигурационным и может содержать бины, которые будут управляться контейнером Spring
@EnableWebSecurity //Включает безопасность веб-приложения с использованием Spring Security.
@EnableMethodSecurity //@EnableMethodSecurity: Позволяет использовать аннотацию @PreAuthorize для ограничения
// доступа к методам на основе ролей или других условий.
public class SecurityConfig {
    @Bean //@Bean Метод, аннотированный этой аннотацией, регистрируется как бин в контексте Spring, и его результат может
    // быть использован в других частях приложения.
    public UserDetailsService userDetailsService() { //UserDetailsService: Интерфейс, который загружает данные пользователя
        // для аутентификации. Ваш сервис MyUserdetailService реализует этот интерфейс и предоставляет информацию о пользователях.
        return new MyUserdetailService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                //authorizeHttpRequests(): Определяет правила авторизации для разных маршрутов
                //Разрешает доступ ко всем пользователям для маршрутов /welcome и /new-user.
                .authorizeHttpRequests(auth-> auth.requestMatchers("api/v1/apps/welcome", "api/v1/apps/new-user").permitAll()
                        .requestMatchers("api/v1/apps/**").authenticated()) //Требует аутентификации для всех других маршрутов, начинающихся с /api/v1/apps/.
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll) // Включает поддержку формы логина и разрешает доступ ко всем.
                .build();
    }

    @Bean
    //AuthenticationProvider: Компонент, который управляет процессом аутентификации.
    public AuthenticationProvider authenticationProvider() { //DaoAuthenticationProvider: Провайдер аутентификации,
        // использующий UserDetailsService для загрузки пользователя из базы данных и PasswordEncoder для проверки пароля.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());//setUserDetailsService(): Устанавливает пользовательский UserDetailsService для загрузки пользователей.
        provider.setPasswordEncoder(passwordEncoder());//setPasswordEncoder(): Устанавливает PasswordEncoder для проверки паролей.
        return provider;
    }
    @Bean
    //PasswordEncoder: Интерфейс для кодирования паролей. В этом случае используется BCryptPasswordEncoder, который
    // считается одним из наиболее безопасных алгоритмов для хранения паролей
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
