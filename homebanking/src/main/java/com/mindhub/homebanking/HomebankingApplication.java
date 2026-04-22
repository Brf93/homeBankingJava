package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args)
		{
			SpringApplication.run(HomebankingApplication.class, args);
		}
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.addAllowedOrigin("/**");
				source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository)
		{
			return (args)->{
				// La lgica de inicializacin se deja aqu por si se desea usar, 
				// pero se recomienda manejar los datos directamente en PostgreSQL.
			};
		}

	public double decimalFormat (double number)
		{
			return BigDecimal.valueOf(number)
					.setScale(2, RoundingMode.HALF_UP)
					.doubleValue();
		}

}
