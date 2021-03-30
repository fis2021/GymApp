package proiect.fis.gym.aplication.services;

import org.dizitart.no2.*;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.meta.Attributes;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.UsernameAlreadyExistsException;
import proiect.fis.gym.aplication.model.Admin;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Objects;

import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class AdminService {

    private static ObjectRepository<Admin> adminRepository;


    public static void initDatabase() {
       // String userDirectory = Paths.get("").toAbsolutePath().toString();
        //System.out.println(userDirectory);

        //String dbPath = userDirectory + "\\src\\proiect\\fis\\gym\\aplication\\databases\\" ;

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        adminRepository = database.getRepository(Admin.class);
    }

    public static void addUser(String firstName, String lastName, String email, String username, String password) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        adminRepository.insert(new Admin(firstName, lastName, email, username, encodePassword(username, password)));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        //if(adminRepository != null) {
            for (Admin admin : adminRepository.find()) {
                if (username.equals(admin.getUsername()))
                    throw new UsernameAlreadyExistsException(username);
            }
        //}
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
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