package fpoly.shopbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ShopBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBeApplication.class, args);
    }


}
