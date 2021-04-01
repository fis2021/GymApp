package proiect.fis.gym.aplication.services;


import javafx.fxml.FXML;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.event.ChangeInfo;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.event.ChangeType;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.Customer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class LoginService {

    private static ObjectRepository<Customer> customerRepository;

    public static void initDatabase() {

       /* Nitrite database = Nitrite.builder()
               .filePath(getPathToFile("GymAplication.db").toFile())
                .openOrCreate("Geo", "Rares");*/

        customerRepository = CustomerService.getCustomerRepository();//database.getRepository(Customer.class);

    }

    public static int login(String username,String password) throws IncorectLoginException{
        int ok=0;
        String pw = encodePassword(username,password);
        for (Customer customer : customerRepository.find()) {
            if ( username.equals(customer.getUsername()) && pw.equals(customer.getPassword())  ){
                ok=1;
                break;
            }
        }
        if(ok==1){
            return 1;
        }else {
            throw new IncorectLoginException();
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));


        return new String(hashedPassword, StandardCharsets.UTF_8).replace("\"", "");
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}
