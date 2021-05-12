package Services;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.*;
import proiect.fis.gym.aplication.controllers.LoginController;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.services.*;

import java.time.LocalDate;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class CustomerServiceTest {

    public static final String USERNAME="TestUser";
    public static final String PASSWORD="Meremere@1";
    public static final String FIRSTNAME="Geo";
    public static final String LASTNAME="Andrei";
    public static final String PHONE="+40727337520";
    public static final String EMAIL="geo@yahoo.com";
    public static final String ROLE="Customer";
    private static ObjectRepository<Customer> customerRepository;
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-GymApplication";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BankService.initDatabase();
        AdminService.initDatabase();
        CustomerService.initTestDatabase("CustomerTest.db");
        GymManagerService.initDatabase();
        LoginService.initDatabase();
        customerRepository=CustomerService.getCustomerRepository();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }


    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(CustomerService.getAllUsers()).isNotNull();
        assertThat(CustomerService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully persisted to Database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException, FieldsAreNotEmptyException, validPhoneNumberException, ValidPasswordException, ValidUsernameException, corectEmailException {
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        assertThat(CustomerService.getAllUsers()).isNotEmpty();
        assertThat(CustomerService.getAllUsers()).size().isEqualTo(1);
        Customer customer = CustomerService.getAllUsers().get(0);
        assertThat(customer).isNotNull();
        assertThat(customer.getUsername()).isEqualTo(USERNAME);
        assertThat(customer.getPassword()).isEqualTo(CustomerService.encodePassword(USERNAME, PASSWORD));
        assertThat(customer.getRole()).isEqualTo(ROLE);
        assertThat(customer.getFirstName()).isEqualTo(FIRSTNAME);
        assertThat(customer.getLastName()).isEqualTo(LASTNAME);
        assertThat(customer.getPhoneNumber()).isEqualTo(PHONE);
        assertThat(customer.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice(){
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });
    }


    @Test
    @DisplayName("Fields can not be empty")
    void testRegisterFieldsCanNotBeEmpty(){
        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser("", PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser(USERNAME, "", ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, "", LASTNAME, PHONE, EMAIL);
        });

        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, "", PHONE, EMAIL);
        });

        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "", EMAIL);
        });

        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, "");
        });

        assertThrows(FieldsAreNotEmptyException.class , () -> {
            CustomerService.addUser("", "", ROLE, "", "", "", "");
        });
    }

    @Test
    @DisplayName("Username length must be at least 5 characters long")
    void testInvalidUsername(){
        assertThrows(ValidUsernameException.class , () -> {
            CustomerService.addUser("M", PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(ValidUsernameException.class , () -> {
            CustomerService.addUser("Me", PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(ValidUsernameException.class , () -> {
            CustomerService.addUser("Mer", PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(ValidUsernameException.class , () -> {
            CustomerService.addUser("Mere", PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });
    }

    @Test
    @DisplayName("The password must respect a pattern")
    void testInvalidPassword(){
        assertThrows(ValidPasswordException.class , () -> {
            CustomerService.addUser(USERNAME, "mere", ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(ValidPasswordException.class , () -> {
            CustomerService.addUser(USERNAME, "meremere", ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(ValidPasswordException.class , () -> {
            CustomerService.addUser(USERNAME, "Meremere1", ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });

        assertThrows(ValidPasswordException.class , () -> {
            CustomerService.addUser(USERNAME, "Meremere@", ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        });
    }

    @Test
    @DisplayName("The email should be valid")
    void testInvalidEmail(){
        assertThrows(corectEmailException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, "geo");
        });
        assertThrows(corectEmailException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, "geo@");
        });
        assertThrows(corectEmailException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, "geo@yahoo");
        });
        assertThrows(corectEmailException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, "geo@yahoo.");
        });
    }

    @Test
    @DisplayName("The phone number should be valid!")
    void testInvalidPhoneNumber(){
        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "0727337520", EMAIL);
        });

        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "+2", EMAIL);
        });

        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "+23", EMAIL);
        });

        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "+234", EMAIL);
        });

        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "+2345", EMAIL);
        });

        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "+23456", EMAIL);
        });

        assertThrows(validPhoneNumberException.class , () -> {
            CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, "+234567", EMAIL);
        });
    }

    @Test
    @DisplayName("Testing to see if a user can create a subscription")
    void testCreateSubscription() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException, incorectCardDetailsException, IncorectCVCException, NotEnoughMoneyException, CheckPaymentFieldNotEmptyException, IncorectCardNumberException {
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        LoginController.setUsername(USERNAME);
        CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        CustomerService.makePayment("GymOne","Radu Mihai","01","21","1234567812342323","303","3 months - 130$");
        CustomerService.makePayment("Iguana","Radu Mihai","01","21","1234567812342323","303","6 months - 240$");
        Customer customer = CustomerService.getAllUsers().get(0);
        LocalDate date = LocalDate.now();
        date=date.plusMonths(1);
        assertThat(customer.getDate(0,customer.getDate2())).isEqualTo(date);
        assertThat(customer.getGym(0, customer.getGym2())).isEqualTo("SmartFit");
        date=date.plusMonths(2);
        assertThat(customer.getDate(1,customer.getDate2())).isEqualTo(date);
        assertThat(customer.getGym(1, customer.getGym2())).isEqualTo("GymOne");
        date=date.plusMonths(3);
        assertThat(customer.getDate(2,customer.getDate2())).isEqualTo(date);
        assertThat(customer.getGym(2, customer.getGym2())).isEqualTo("Iguana");
    }

    @Test
    @DisplayName("Testing to see if a user can extend his subscription")
    void testExtendSubscription() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException, incorectCardDetailsException, IncorectCVCException, NotEnoughMoneyException, CheckPaymentFieldNotEmptyException, IncorectCardNumberException, noActiveSubscriptionException {
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        LoginController.setUsername(USERNAME);
        CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        LocalDate date = LocalDate.now();
        date=date.plusMonths(2);
        Customer customer = CustomerService.getAllUsers().get(0);
        assertThat(customer.getDate(0,customer.getDate2())).isEqualTo(date);
        CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","3 months - 130$");
        date=date.plusMonths(3);
        customer = CustomerService.getAllUsers().get(0);
        assertThat(customer.getDate(0,customer.getDate2())).isEqualTo(date);
        CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","6 months - 240$");
        date=date.plusMonths(6);
        customer = CustomerService.getAllUsers().get(0);
        assertThat(customer.getDate(0,customer.getDate2())).isEqualTo(date);
        CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 year - 440$");
        date=date.plusMonths(12);
        customer = CustomerService.getAllUsers().get(0);
        assertThat(customer.getDate(0,customer.getDate2())).isEqualTo(date);
    }

    @Test
    @DisplayName("A customer can not extend his subscription to a gym if he doesn't have an active subscription")
    void testNoActiveSubs() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException, incorectCardDetailsException, IncorectCVCException, NotEnoughMoneyException, CheckPaymentFieldNotEmptyException, IncorectCardNumberException {
        LoginController.setUsername(USERNAME);
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);

        assertThrows(noActiveSubscriptionException.class , () -> {
            CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        });
        CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        Customer customer = CustomerService.getAllUsers().get(0);
        LocalDate date = LocalDate.now();
        date=date.minusMonths(12);
        customer.setDate(0,date);
        customerRepository.update(customer);
        assertThrows(noActiveSubscriptionException.class , () -> {
            CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        });

        CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        customer = CustomerService.getAllUsers().get(0);
        date = LocalDate.now();
        date=date.minusMonths(1);
        customer.setDate(0,date);
        customerRepository.update(customer);
        assertThrows(noActiveSubscriptionException.class , () -> {
            CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        });

        CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        customer = CustomerService.getAllUsers().get(0);
        date = LocalDate.now();
        date=date.minusDays(1);
        customer.setDate(0,date);
        customerRepository.update(customer);
        assertThrows(noActiveSubscriptionException.class , () -> {
            CustomerService.extendSubscription("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        });
    }

    @Test
    @DisplayName("A customer can not join a course if he is already enrolled to the specific course")
    void testAlreadyJoinedException() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException {
        LoginController.setUsername(USERNAME);
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        Course c = new Course("Fitness","Andrei","10-12");
        Customer customer = CustomerService.getAllUsers().get(0);
        customer.addSmartfitCourse(c);
        customerRepository.update(customer);
        assertThrows(CheckJoinedCourse.class , () -> {
            CustomerService.alreadyJoined(customer,c);
        });
    }

    @Test
    @DisplayName("Testing show active subscription function")
    void testShowActiveSubscription() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException, incorectCardDetailsException, IncorectCVCException, NotEnoughMoneyException, CheckPaymentFieldNotEmptyException, IncorectCardNumberException {
        LoginController.setUsername(USERNAME);
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        Customer customer = CustomerService.getAllUsers().get(0);
        String tmp = CustomerService.showSubscriptions(customer);
        LocalDate date = LocalDate.now();
        date=date.plusMonths(1);
        assertThat(tmp).isEqualTo("SmartFit : " + date + "\n");

        CustomerService.makePayment("GymOne","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        customer = CustomerService.getAllUsers().get(0);
        tmp = CustomerService.showSubscriptions(customer);
        assertThat(tmp).isEqualTo("SmartFit : " + date + "\n" + "GymOne : " + date + "\n");

        CustomerService.makePayment("Iguana","Radu Mihai","01","21","1234567812342323","303","1 month - 50$");
        customer = CustomerService.getAllUsers().get(0);
        tmp = CustomerService.showSubscriptions(customer);
        assertThat(tmp).isEqualTo("SmartFit : " + date + "\n" + "GymOne : " + date + "\n" + "Iguana : " + date + "\n");
    }

    @Test
    @DisplayName("Testing empty fields exception for make payment")
    void testMakePaymentFieldsAreNotEmpty(){
        assertThrows(CheckPaymentFieldNotEmptyException.class , () -> {
            CustomerService.makePayment("SmartFit","","01","21","1234567812342323","303","1 month - 50$");
        });

        assertThrows(CheckPaymentFieldNotEmptyException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","","303","1 month - 50$");
        });

        assertThrows(CheckPaymentFieldNotEmptyException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","","1 month - 50$");
        });
    }


    @Test
    @DisplayName("Card number must be 16 characters long")
    void testInvalidCardNumberException(){
        assertThrows(IncorectCardNumberException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234","303","1 month - 50$");
        });

        assertThrows(IncorectCardNumberException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","12345678","303","1 month - 50$");
        });

        assertThrows(IncorectCardNumberException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","123456781234","303","1 month - 50$");
        });

        assertThrows(IncorectCardNumberException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","12345678232","303","1 month - 50$");
        });
    }

    @Test
    @DisplayName("The card details should be in the bank database")
    void testInvalidCardDetails(){
        assertThrows(incorectCardDetailsException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Miha","01","21","1234567812342323","303","1 month - 50$");
        });

        assertThrows(incorectCardDetailsException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","02","21","1234567812342323","303","1 month - 50$");
        });

        assertThrows(incorectCardDetailsException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","19","1234567812342323","303","1 month - 50$");
        });

        assertThrows(incorectCardDetailsException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342324","303","1 month - 50$");
        });

        assertThrows(incorectCardDetailsException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","302","1 month - 50$");
        });
    }


    @Test
    @DisplayName("The cvc must be 3 characters long")
    void testInvalidCvc(){
        assertThrows(IncorectCVCException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","30","1 month - 50$");
        });

        assertThrows(IncorectCVCException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","3033","1 month - 50$");
        });

        assertThrows(IncorectCVCException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","1","1 month - 50$");
        });

        assertThrows(IncorectCVCException.class , () -> {
            CustomerService.makePayment("SmartFit","Radu Mihai","01","21","1234567812342323","12345678","1 month - 50$");
        });
    }



    @Test
    @DisplayName("The customer needs to have enough money on the card to pay for the subscription")
    void testEnoughMoneyException(){
        assertThrows(NotEnoughMoneyException.class , () -> {
            CustomerService.makePayment("SmartFit","Andrei Marian","01","21","1234567812345678","111","1 month - 50$");
        });

        assertThrows(NotEnoughMoneyException.class , () -> {
            CustomerService.makePayment("SmartFit","Andrei Marian","01","21","1234567812345678","111","3 months - 130$");
        });

        assertThrows(NotEnoughMoneyException.class , () -> {
            CustomerService.makePayment("SmartFit","Andrei Marian","01","21","1234567812345678","111","6 months - 240$");
        });

        assertThrows(NotEnoughMoneyException.class , () -> {
            CustomerService.makePayment("SmartFit","Andrei Marian","01","21","1234567812345678","111","1 year - 440$");
        });
    }
}
