package net.lingala;

import net.lingala.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;

import static java.lang.System.exit;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) {

        printPageResults(0);
        printPageResults(1);

        System.out.println("Done!");

        exit(0);
    }

    private void printPageResults(int pageNumber) {
        System.out.println("***********");
        System.out.println("Results for page: " + pageNumber);
        customerRepository.findContractsToBeDeleted(new Date(), PageRequest.of(pageNumber, 2))
            .stream()
            .mapToLong(e -> e.longValue())
            .forEach(System.out::println);
    }

}