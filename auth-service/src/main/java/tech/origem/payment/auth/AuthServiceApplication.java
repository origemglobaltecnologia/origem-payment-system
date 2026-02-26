package tech.origem.payment.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.origem.payment.auth.model.AccountStatus;
import tech.origem.payment.auth.model.User;
import tech.origem.payment.auth.repository.UserRepository;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.findByUsername("admin").isEmpty()) {
                repository.save(User.builder()
                        .username("admin")
                        // "123" agora será algo como $2a$10$... no banco
                        .password(passwordEncoder.encode("123")) 
                        .status(AccountStatus.ACTIVE)
                        .failedAttempts(0)
                        .build());
                System.out.println(">>> Usuário 'admin' criado com senha criptografada!");
            }
        };
    }
}
