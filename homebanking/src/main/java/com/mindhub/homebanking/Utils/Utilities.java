package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Utilities {
    @Autowired
    private static AccountService accountService;

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String checkAccountNumber(String accountNumber){
        boolean flag = true;
        do
        {
            try{
                if(accountService.findByNumberEquals(accountNumber) != null){
                    accountNumber = "VIN-" + getRandomNumber(10000000,99999999);
                }
            } catch (NullPointerException e){flag = false;}
        }
        while(flag);

        return accountNumber;
    }
    public static LocalDateTime dateFormat (LocalDateTime date)
    {
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String colloquies = date.format(formattedDate);
        return LocalDateTime.parse(colloquies,formattedDate);
    }
    public void  Dates(){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate dateToday = LocalDate.now();
    }
    List<Integer> mortagePayment = List.of(12,24,36,48,60);
    List<Integer> personalPayment = List.of(6,12,24);
    List<Integer> carLoanPayment = List.of(6,12,24,36);
    public static float interest (int payments){
        float interest;
        if(payments == 6)
            {
                interest = 1.08f;
            } else if (payments == 12)
                {
                    interest = 1.10f;
                } else if (payments == 24)
                    {
                       interest = 1.15f;
                    } else if (payments == 36)
                        {
                          interest = 1.18f;
                        } else if (payments == 48)
                            {
                                interest = 1.20f;
                            }else if(payments == 60)
                                {
                                    interest = 1.25f;
                                }else
                                    {
                                        interest = 1;
                                    }
        return  interest;
    }


}
