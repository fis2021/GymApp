package proiect.fis.gym.aplication.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.util.Iterables;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.model.Customer;

import javax.swing.plaf.synth.Region;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static proiect.fis.gym.aplication.services.FileSystemService.getPathToFile;

public class CustomerService {

    private static ObjectRepository<Customer> customerRepository;
    private static ObjectRepository<Bank> bankRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("GymAplication.db").toFile())
                .openOrCreate("Geo", "Rares");

        customerRepository = database.getRepository(Customer.class);
        bankRepository=BankService.getBankRepository();
    }

    public static ObjectRepository<Customer> getCustomerRepository(){
        return customerRepository;
    }

    public static void addUser(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) throws UsernameAlreadyExistsException, corectEmailException, FieldsAreNotEmptyException, ValidPasswordException, ValidUsernameException, validPhoneNumberException {
        checkFieldsAreNotEmpty(username,password,role,firstName,lastName,phoneNumber,email);
        checkUserDoesNotAlreadyExist(username);
        checkUsername(username);
        checkPassword(password);
        checkEmailIsValid(email);
        checkPhoneNumber(phoneNumber);
        customerRepository.insert(new Customer(username, encodePassword(username, password), role, firstName, lastName, phoneNumber, email));
    }

    public static String showSubscriptions(Customer customer){
        String tmp = "";
            for(int i=0;i<3;i++){
                if( customer.getGym(i,customer.getGym2())!=null && customer.getDate(i,customer.getDate2())!=null){
                    tmp += customer.getGym(i,customer.getGym2()) + " : " + customer.getDate(i,customer.getDate2()) + "\n";
                }
            }
            if(tmp.equals(""))
                tmp +="No subscriptions";
            return tmp;
    }

    public static void makePayment(String gym,String cardOwnerName,String expM,String expY,String cardN,String CVC,String duration,String username) throws incorectCardDetailsException, IncorectCardNumberException, IncorectCVCException,NotEnoughMoneyException,CheckPaymentFieldNotEmptyException{
        paymentFieldsException(cardOwnerName,expM,expY,cardN,CVC,username);
        CVCException(CVC);
        cardNumberException(cardN);
        cardDetailsException(cardOwnerName,expM,expY,cardN,CVC);
        enoughMoney(cardOwnerName,expM,expY,cardN,CVC,duration);
        for (Customer customer : customerRepository.find()) {
            if (Objects.equals(username, customer.getUsername())) {
                int i;
                if(gym.equals("Smartfit"))
                    i=0;
                else if(gym.equals("Gym one"))
                    i=1;
                else i=2;
                LocalDate currentdate= LocalDate.now();
                if(duration.equals("1 month - 50$")) {
                    currentdate = currentdate.plusMonths(1);
                }else if(duration.equals("3 months - 130$")){
                    currentdate = currentdate.plusMonths(3);
                }else if(duration.equals("6 months - 240$")){
                    currentdate = currentdate.plusMonths(6);
                }else if(duration.equals("1 year - 440$")){
                    currentdate = currentdate.plusMonths(12);
                }
                customer.setGym(i,gym);
                customer.setDate(i,currentdate);
                customerRepository.update(customer);
                break;
            }
        }
    }

    private static void paymentFieldsException(String cardOwnerName,String expM,String expY,String cardN,String CVC,String username) throws CheckPaymentFieldNotEmptyException{
        if(cardOwnerName.isEmpty() || expM.isEmpty() || expY.isEmpty() || cardN.isEmpty() || CVC.isEmpty() || username.isEmpty()) {
            throw new CheckPaymentFieldNotEmptyException();
        }
    }

    private static void enoughMoney(String cardOwnerName,String expM,String expY,String cardN,String CVC,String duration) throws NotEnoughMoneyException{
        float a=0;
        if(duration.equals("1 month - 50$"))
            a=50;
        if(duration.equals("3 months - 130$"))
            a=130;
        if(duration.equals("6 months - 240$"))
            a=240;
        if(duration.equals("1 year - 440$"))
            a=440;

        for(Bank bank : bankRepository.find()){
            if(cardOwnerName.equals(bank.getNumeDetinator()) && expM.equals(bank.getLuna()) && expY.equals(bank.getAnu()) && cardN.equals(bank.getNumarCard()) && CVC.equals(bank.getCVC())){
                if((a > Float.parseFloat(bank.getSum()))){
                    throw new NotEnoughMoneyException();
                }else{
                    bank.setSum(Float.toString(Float.parseFloat(bank.getSum())-a) );
                   bankRepository.update(bank);
                }
            }
        }
    }

    private static void CVCException(String CVC) throws IncorectCVCException{
        if(CVC.length()!=3){
            throw new IncorectCVCException();
        }
    }

    private static void cardNumberException(String cardN) throws IncorectCardNumberException{
        String regex= "^\\d{16}$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(cardN).matches()) )
            throw new IncorectCardNumberException();
    }

    private static void cardDetailsException(String cardOwnerName,String expM,String expY,String cardN,String CVC) throws incorectCardDetailsException {
        int ok=0;
        for(Bank bank : bankRepository.find()){
            if(cardOwnerName.equals(bank.getNumeDetinator()) && expM.equals(bank.getLuna()) && expY.equals(bank.getAnu()) && cardN.equals(bank.getNumarCard()) && CVC.equals(bank.getCVC())){
                ok=1;
                break;
            }
        }
        if(ok==0)
            throw new incorectCardDetailsException();
    }

    private static void checkPhoneNumber(String phoneNumber) throws validPhoneNumberException{
        String regex= "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(phoneNumber).matches()) )
            throw new validPhoneNumberException();
    }

    private static void checkUsername(String username) throws ValidUsernameException{
        if(username.length()<5)
            throw new ValidUsernameException();
    }

    private static void checkPassword(String password) throws ValidPasswordException{
        String regex= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,20}$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(password).matches()) )
            throw new ValidPasswordException();
    }

    private static void checkFieldsAreNotEmpty(String username, String password, String role, String firstName, String lastName, String phoneNumber, String email) throws FieldsAreNotEmptyException{
        if(username.isEmpty() || password.isEmpty() || role.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()){
            throw new FieldsAreNotEmptyException();
        }
    }

    private static void checkEmailIsValid(String email) throws corectEmailException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new corectEmailException();
        if( !(pat.matcher(email).matches()) )
            throw new corectEmailException();
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
