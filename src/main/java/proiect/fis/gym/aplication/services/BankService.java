package proiect.fis.gym.aplication.services;

import proiect.fis.gym.aplication.model.Bank;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

public class BankService {

    private static ObjectRepository<Bank> bankRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Bank.db").toFile())
                .openOrCreate("Geo", "Rares");

        bankRepository = database.getRepository(Bank.class);

    }

    public static ObjectRepository<Bank> getBankRepository(){
        return bankRepository;
    }

    public static void addBank(String ownerName, String month, String year, String cardNumber, String CVC,String sum) {
        bankRepository.insert(new Bank(ownerName,month,year,cardNumber,CVC,sum));
    }

}
