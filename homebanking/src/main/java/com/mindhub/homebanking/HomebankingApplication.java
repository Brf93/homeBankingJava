package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private ClientLoanRepository clientLoanRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private CardService cardService;

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
				//Clientes
				Client mindHubBrothers = new Client("Mindhub","Brothers","bank@mindhub.com",GenderType.CORP,true, passwordEncoder.encode("bank"),"meeting");
				Client melba = new Client("Melba","Morel","melba@mindhub.com", GenderType.FEMALE,true , passwordEncoder.encode("Melba123"),"cuentaMujer");
				Client franco = new Client( "Franco","Brizzio","mailfalso@gmail.com",GenderType.MALE,true,passwordEncoder.encode("Franco123"),"cuentaHombre");
				Client cocaCola = new Client("Coca","Cola","ernestoGuevara@gmail.com",GenderType.CORP,true,passwordEncoder.encode("Coca123"),"personas");
				Client admin = new Client("admin","admin","admin@admin.com",GenderType.MALE,true, passwordEncoder.encode("admin"),"personas");
				//Account
				Account MASTER = new Account("VIN-" + 100, Utilities.dateFormat(LocalDateTime.now()),decimalFormat(0D), AccountType.CHECKING,true);
				Account VIN001 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),decimalFormat(5000D),AccountType.CHECKING,true);

				Account VIN002 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),decimalFormat(7500D), AccountType.SAVINGS,true);

				Account VIN003 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),decimalFormat(15000.93D),AccountType.SAVINGS,true);
				Account VIN004 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),decimalFormat(9500.10D),AccountType.SAVINGS,true);

				Account VIN005 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),decimalFormat(21000.45D),AccountType.CHECKING,true);

				Account VIN006 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),decimalFormat(35200.41D),AccountType.SAVINGS,true);
				//Melba
				Transaction TR0001 = new Transaction(5000D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT,VIN001.getBalance());
				Transaction TR0002 = new Transaction(7500D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT, VIN002.getBalance());
				//Demas
				Transaction TR0008 = new Transaction(15000.93D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT, VIN003.getBalance());
				//Lista de cantidad de cuotas
				List<Integer> mortagePayment = List.of(12,24,36,48,60);
				List<Integer> personalPayment = List.of(6,12,24);
				List<Integer> carLoanPayment = List.of(6,12,24,36);
				//Tipo de  Loans
				Loan mortage = new Loan("Mortage",500000D,mortagePayment);
				Loan personal = new Loan("Personal",100000D,personalPayment);
				Loan carLoan = new Loan("Car loan",300000D,carLoanPayment);
				//Loans de melba
				/*ClientLoan melbamortage = new ClientLoan(400000D,60,Utilities.dateFormat(LocalDateTime.now()),melba,mortage);
				ClientLoan melbaPersonal = new ClientLoan(50000D,12,Utilities.dateFormat(LocalDateTime.now()),melba,personal);*/
				//cards
				//Card melbaGold = new Card((melba.getFirstName() +" "+ melba.getLastName()),CardType.CREDIT,CardColor.GOLD,"1234 5678 9101 3244",753, LocalDate.now().plus(5, ChronoUnit.YEARS),LocalDate.now(),true);
				Card melbaTitanium = new Card((melba.getFirstName() +" "+ melba.getLastName()),CardType.DEBIT,CardColor.TITANIUM,"3485 5678 9101 2234",147,LocalDate.now().minus(5,ChronoUnit.YEARS),LocalDate.now().minus(6,ChronoUnit.YEARS),true);
				Card melbaSilver = new Card((melba.getFirstName() +" "+ melba.getLastName()),CardType.DEBIT,CardColor.SILVER,"3485 5678 9101 2111",147,LocalDate.now().minus(5,ChronoUnit.YEARS),LocalDate.now().minus(6,ChronoUnit.YEARS),true);
				Card francoSilver = new Card((franco.getFirstName() +" "+ franco.getLastName()),CardType.DEBIT,CardColor.SILVER,"8574 5678 3587 1123",157,LocalDate.now().plus(4,ChronoUnit.YEARS),LocalDate.now(),true);
				//agregar cuentas
				mindHubBrothers.addAccount(MASTER);
				melba.addAccount(VIN001);
				melba.addAccount(VIN002);
				franco.addAccount(VIN003);
				franco.addAccount(VIN004);
				cocaCola.addAccount(VIN005);
				cocaCola.addAccount(VIN006);
				//Melba transacciones
				VIN001.addTransaction(TR0001);
				//VIN001.addCard(melbaGold);
				VIN002.addTransaction(TR0002);
				//Franco transacciones
				VIN003.addTransaction(TR0008);
				//Cards a cliente
				cardService.saveCards(melbaTitanium);

				//guardado
				clientRepository.save(mindHubBrothers);
				clientRepository.save(melba);
				clientRepository.save(franco);
				clientRepository.save(cocaCola);
				clientRepository.save(admin);
				accountRepository.save(MASTER);
				accountRepository.save(VIN001);
				accountRepository.save(VIN002);
				accountRepository.save(VIN003);
				accountRepository.save(VIN004);
				accountRepository.save(VIN005);
				accountRepository.save(VIN006);
				transactionRepository.save(TR0001);
				transactionRepository.save(TR0002);
				transactionRepository.save(TR0008);
				loanRepository.save(mortage);
				loanRepository.save(personal);
				loanRepository.save(carLoan);

				//clientLoanRepository.save(melbamortage);
				//clientLoanRepository.save(melbaPersonal);
				/*cardRepository.save(melbaGold);
				cardRepository.save(melbaTitanium);
				cardRepository.save(melbaSilver);
				cardRepository.save(francoSilver);*/
			};
		}
	public double decimalFormat (double number)
		{
			DecimalFormat dFormat = new DecimalFormat("#.00");
			return Double.parseDouble(dFormat.format(number));
		}

}
