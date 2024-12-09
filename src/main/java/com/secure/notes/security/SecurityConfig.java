package com.secure.notes.security;

import com.secure.notes.model.AppRole;
import com.secure.notes.model.Role;
import com.secure.notes.model.User;
import com.secure.notes.repository.RoleRepository;
import com.secure.notes.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.time.LocalDate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) ->
//                requests
//                        .requestMatchers("/contact").permitAll()
//                        .requestMatchers("/public/**").permitAll()
//                        .requestMatchers("/admin").denyAll()
//                        .requestMatchers("/admin/**").denyAll()
//                        .anyRequest().authenticated());
//        //http.formLogin(withDefaults());
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.httpBasic(withDefaults());
//        return http.build();
//    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        // http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();

    }

//    @Bean
//    public JdbcUserDetailsManager userDetailService(DataSource dataSource) {
//        //InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        if (!manager.userExists("user1")) {
//            manager.createUser(User.withUsername("user1").password("{noop}password1").roles("USER").build());
//        }
//        if (!manager.userExists("admin")) {
//            manager.createUser(User.withUsername("admin").password("{noop}adminPass").roles("ADMIN").build());
//        }
//        return manager;
//    }



    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository){
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        System.out.println("User role does not exist, creating one");
                               return roleRepository.save(new Role(AppRole.ROLE_USER));
                            });
            System.out.println("User already exist " + userRole.getRoleId());
            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        System.out.println("Admin role does not exist, creating a new one.");
                        return roleRepository.save(new Role(AppRole.ROLE_ADMIN));
                    });
            System.out.println("admin role already exist " + adminRole.getRoleName());

            if(!userRepository.existsByUserName("user1")) {
                System.out.println("creating user1 since it does not exist");
                User user1 = new User("user1", "user1@example.com", "{noop}password1");
                user1.setAccountNonLocked(false);
                user1.setAccountNonExpired(true);
                user1.setCredentialsNonExpired(true);
                user1.setEnabled(true);
                user1.setCredentialsExpiryDate(LocalDate.now().
                        plusYears(1));
                user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                user1.setTwoFactorEnabled(false);
                user1.setSignUpMethod("email");
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if(!userRepository.existsByUserName("admin1")) {
                System.out.println("creating admin1 since it does not exist");
                User admin = new User("admin1", "admin1@example.com", "{noop}password1");
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().
                        plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setTwoFactorEnabled(false);
                admin.setSignUpMethod("email");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}
