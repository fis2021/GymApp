import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.FileSystemService;
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
        FileSystemService.APPLICATION_FOLDER=".test-GymAplication";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CustomerService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/registerCustomer.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Test
    void testRegistration(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAME);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#firstname");
        robot.write(FIRSTNAME);
        robot.clickOn("#lastname");
        robot.write(LASTNAME);
        robot.clickOn("#phone");
        robot.write(PHONE);
        robot.clickOn("#email");
        robot.write(EMAIL);
        robot.clickOn("#registerButton");

        assertThat(robot.lookup("#registerMessage").queryText()).hasText("Account created successfully!");
        assertThat(CustomerService.getAllUsers()).size().isEqualTo(1);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(
                String.format("An account with the username %s already exists!", USERNAME)
        );

        robot.clickOn("#username");
        robot.write("Andrei");
        robot.clickOn("#registerButton");

        assertThat(robot.lookup("#registerMessage").queryText()).hasText("Account created successfully!");
        assertThat(CustomerService.getAllUsers()).size().isEqualTo(2);
    }

}
