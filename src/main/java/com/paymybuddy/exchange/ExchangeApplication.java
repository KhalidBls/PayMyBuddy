package com.paymybuddy.exchange;
import com.paymybuddy.exchange.configuration.DatabaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@SpringBootApplication
public class ExchangeApplication {

	public static void main(String[] args) throws FileNotFoundException, SQLException, ClassNotFoundException {
		DatabaseConfig config = new DatabaseConfig();
		config.initDataBaseRequestFromFile();
		SpringApplication.run(ExchangeApplication.class, args);
	}

}
