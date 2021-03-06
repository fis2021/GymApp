import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import proiect.fis.gym.aplication.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class CustomerRegistrationTest {
    public static final String USERNAME="username";
    public static final String PASSWORD="Meremere@1";
    public static final String FIRSTNAME="Geo";
    public static final String LASTNAME="Andrei";
    public static final String PHONE="+40727337520";
    public static final String EMAIL="geo@yahoo.com";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-GymApplication";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CustomerService.initTestDatabase("CustomerTest.db");
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/registerCustomer.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    @AfterEach
    void tearDown() {
        CustomerService.getDatabase().close();
    }

    @Test
    void testRegistration(FxRobot robot) {

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
                String.format("Please complete all the fields!")
        );

        robot.clickOn("#username");
        robot.write("user");
        robot.clickOn("#password");
        robot.write("money");
        robot.clickOn("#firstname");
        robot.write(FIRSTNAME);
        robot.clickOn("#lastname");
        robot.write(LASTNAME);
        robot.clickOn("#phone");
        robot.write("+2");
        robot.clickOn("#email");
        robot.write("geo");
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
                String.format("The username must be at least 5 characters long.")
        );

        robot.clickOn("#username");
        robot.write(USERNAME);
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
               "Please enter a valid password!\nThe password must contain:\n-at least one numeric character\n-at least one lowercase character,\n-at least one uppercase character\n-at least one special symbol among @#$%^&+=_\n-the password length must be between 8 and 20 characters"
        );

        robot.clickOn("#password");
        robot.write(PASSWORD);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
                String.format("Please enter a valid email adress!")
        );

        robot.clickOn("#email");
        robot.write("@yahoo.com");
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
                String.format("Please type a valid phone number!")
        );

        robot.clickOn("#phone");
        robot.write("0727337520");

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText("Account created successfully!");
        assertThat(CustomerService.getAllUsers()).size().isEqualTo(1);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
                String.format("An account with the username %s already exists!", "user" + USERNAME)
        );

        robot.clickOn("#username");
        robot.write("Andrei");
        robot.clickOn("#registerButton");

        assertThat(robot.lookup("#registerMessage").queryText()).hasText("Account created successfully!");
        assertThat(CustomerService.getAllUsers()).size().isEqualTo(2);
    }

}
