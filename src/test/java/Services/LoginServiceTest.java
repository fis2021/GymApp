package Services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.services.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginServiceTest {

    public static final String USERNAME="TestUser";
    public static final String PASSWORD="Meremere@1";
    public static final String FIRSTNAME="Geo";
    public static final String LASTNAME="Andrei";
    public static final String PHONE="+40727337520";
    public static final String EMAIL="geo@yahoo.com";
    public static final String ROLE="Customer";

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
        assertThat(BankService.getAllUsers()).isNotNull();
        assertThat(AdminService.getAllUsers()).isNotNull();
        assertThat(GymManagerService.getAllUsers()).isNotNull();
    }

    @Test
    @DisplayName("Testing incorect credentials to login exception")
    void testIncorrectLogin() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException, IncorectLoginException {
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        assertThrows(IncorectLoginException.class, () -> {
            LoginService.login(USERNAME+"1",PASSWORD);
        });
        assertThrows(IncorectLoginException.class, () -> {
            LoginService.login(USERNAME+"1",PASSWORD+"2");
        });
        assertThrows(IncorectLoginException.class, () -> {
            LoginService.login("","");
        });

    }

    @Test
    @DisplayName("Testing login function")
    void testLogin() throws FieldsAreNotEmptyException, ValidUsernameException, validPhoneNumberException, ValidPasswordException, UsernameAlreadyExistsException, corectEmailException, IncorectLoginException {
        CustomerService.addUser(USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, PHONE, EMAIL);
        int a;
        String tmp="";
        a=LoginService.login(USERNAME,PASSWORD);
        tmp += a;
        assertThat(tmp.equals("1"));
        int b=LoginService.login("SmartFit","Meremere@1");
        tmp ="";
        tmp+=b;
        assertThat(tmp.equals("2"));
        int c=LoginService.login("Admin","Meremere@1");
        tmp ="";
        tmp+=c;
        assertThat(tmp.equals("3"));

    }

}
