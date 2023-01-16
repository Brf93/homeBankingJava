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

	public static void main(String[] args)
		{
			SpringApplication.run(HomebankingApplication.class, args);
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
				Account MASTER = new Account("VIN-" + 100, Utilities.dateFormat(LocalDateTime.now()),decimalFormat(0D));
				Account VIN001 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),decimalFormat(5683.36D));
				Account VIN002 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),decimalFormat(7500.01D));
				Account VIN003 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),decimalFormat(15000.93D));
				Account VIN004 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),decimalFormat(9500.10D));
				Account VIN005 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),decimalFormat(21000.45D));
				Account VIN006 = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),decimalFormat(35200.41D));
				//Melba
				Transaction TR0001 = new Transaction(5000D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
				Transaction TR0002 = new Transaction(7500D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
				Transaction TR0003 = new Transaction(1023D,"Iphone 13 - Apple store",Utilities.dateFormat(LocalDateTime.now()), TransactionType.DEBIT);
				Transaction TR0004 = new Transaction(45.93D,"Iphone 13 Black case - Apple store MIAMI",Utilities.dateFormat(LocalDateTime.now()), TransactionType.DEBIT);
				Transaction TR0005 = new Transaction(10.12D,"Macchiato - Starbucks",Utilities.dateFormat(LocalDateTime.now()), TransactionType.DEBIT);
				Transaction TR0006 = new Transaction(590.09D,"Playstation 5 -Best buy",Utilities.dateFormat(LocalDateTime.now()), TransactionType.DEBIT);
				Transaction TR0007 = new Transaction(2350.50D,"Web page - Client: MindHub SA",Utilities.dateFormat(LocalDateTime.now()),TransactionType.CREDIT);
				//Demas
				Transaction TR0008 = new Transaction(15000.93D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
				Transaction TR0009 = new Transaction(9500.10D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
				Transaction TR0010 = new Transaction(21000.45D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
				Transaction TR0011 = new Transaction(35200.415D,"First deposit",Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
				//Lista de cantidad de cuotas
				List<Integer> mortagePayment = List.of(12,24,36,48,60);
				List<Integer> personalPayment = List.of(6,12,24);
				List<Integer> carLoanPayment = List.of(6,12,24,36);
				//Tipo de  Loans
				Loan mortage = new Loan("Mortage",500000D,mortagePayment);
				Loan personal = new Loan("Personal",100000D,personalPayment);
				Loan carLoan = new Loan("Car loan",300000D,carLoanPayment);
				//Loans de melba
				ClientLoan melbamortage = new ClientLoan(400000D,60,Utilities.dateFormat(LocalDateTime.now()),melba,mortage);
				ClientLoan melbaPersonal = new ClientLoan(50000D,12,Utilities.dateFormat(LocalDateTime.now()),melba,personal);
				//cards
				Card melbaGold = new Card((melba.getFirstName() +" "+ melba.getLastName()),CardType.CREDIT,CardColor.GOLD,"1234 5678 9101 3244",753, LocalDate.now().plus(5, ChronoUnit.YEARS),LocalDate.now());
				Card melbaTitanium = new Card((melba.getFirstName() +" "+ melba.getLastName()),CardType.CREDIT,CardColor.TITANIUM,"3485 5678 9101 2234",147,LocalDate.now().plus(5,ChronoUnit.YEARS),LocalDate.now());
				Card francoSilver = new Card((franco.getFirstName() +" "+ franco.getLastName()),CardType.DEBIT,CardColor.SILVER,"8574 5678 3587 1123",157,LocalDate.now().plus(4,ChronoUnit.YEARS),LocalDate.now());
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
				VIN002.addTransaction(TR0002);
				VIN001.addTransaction(TR0003);
				VIN001.addTransaction(TR0004);
				VIN001.addTransaction(TR0005);
				VIN001.addTransaction(TR0006);
				VIN001.addTransaction(TR0007);
				//Franco transacciones
				VIN003.addTransaction(TR0008);
				VIN004.addTransaction(TR0009);
				//Coca transacciones
				VIN005.addTransaction(TR0010);
				VIN006.addTransaction(TR0011);
				//Cards a cliente
				melba.addCard(melbaGold);
				melba.addCard(melbaTitanium);
				franco.addCard(francoSilver);
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
				transactionRepository.save(TR0003);
				transactionRepository.save(TR0004);
				transactionRepository.save(TR0005);
				transactionRepository.save(TR0006);
				transactionRepository.save(TR0007);
				transactionRepository.save(TR0008);
				transactionRepository.save(TR0009);
				transactionRepository.save(TR0010);
				transactionRepository.save(TR0011);
				loanRepository.save(mortage);
				loanRepository.save(personal);
				loanRepository.save(carLoan);
				clientLoanRepository.save(melbamortage);
				clientLoanRepository.save(melbaPersonal);
				cardRepository.save(melbaGold);
				cardRepository.save(melbaTitanium);
				cardRepository.save(francoSilver);
			};
		}
	public double decimalFormat (double number)
		{
			DecimalFormat dFormat = new DecimalFormat("#.00");
			return Double.parseDouble(dFormat.format(number));
		}

}
