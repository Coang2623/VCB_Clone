package com.vcb.backend.configuration;


import com.vcb.backend.entity.User;
import com.vcb.backend.enums.UserStatusEnum;
import com.vcb.backend.repository.RoleRepository;
import com.vcb.backend.repository.UserRepository;
import com.vcb.backend.service.impl.PasswordServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    RoleRepository roleRepository;
    PasswordServiceImpl passwordService;


  @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(!userRepository.existsByUserName("admin")){
                List<String> roleList = List.of("ADMIN");
                var roles = roleRepository.findAllById(roleList);

                User user = new User();
                user.setUserName("admin");
                user.setUserPassword(passwordService.passwordEncoder("admin"));
                user.setRoles(new HashSet<>(roles));
                user.setUserStatus(UserStatusEnum.ACTIVE);

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
            }
        };
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Cho phép domain frontend
        configuration.setAllowedMethods(Arrays.asList("*")); // Phương thức cho phép
        configuration.setAllowCredentials(true); // Cho phép cookie nếu cần
        configuration.setAllowedHeaders(Arrays.asList("*")); // Cho phép tất cả header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cho tất cả endpoint

        return source;
    }
}
