package tech.origem.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) {
        // Cria a pasta data automaticamente se não existir
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
