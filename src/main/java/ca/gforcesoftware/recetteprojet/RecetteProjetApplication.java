package ca.gforcesoftware.recetteprojet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RecetteProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecetteProjetApplication.class, args);
    }

}
