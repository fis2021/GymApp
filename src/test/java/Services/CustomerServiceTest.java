package Services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.FileSystemService;
import static org.testfx.assertions.api.Assertions.assertThat;

public class CustomerServiceTest {

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
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CustomerService.initDatabase();
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
    }
}
