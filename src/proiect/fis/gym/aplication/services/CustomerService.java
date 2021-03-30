package proiect.fis.gym.aplication.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.UsernameAlreadyExistsException;
import proiect.fis.gym.aplication.model.Customer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class CustomerService {

    private static ObjectRepository<Customer> customerRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("GymAplication.db").toFile())
                .openOrCreate("Geo", "Rares");

        customerRepository = database.getRepository(Customer.class);
    }

    public static void addUser(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        customerRepository.insert(new Customer(username, encodePassword(username, password), role, firstName, lastName, phoneNumber, email));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Customer customer : customerRepository.find()) {
            if (Objects.equals(username, customer.getUsername()))
                throw new UsernameAlreadyExistsException(username);
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
