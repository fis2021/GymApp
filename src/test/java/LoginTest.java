import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;
import proiect.fis.gym.aplication.services.*;

import javax.swing.*;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class LoginTest {

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-GymApplication";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CustomerService.initTestDatabase("CustomerTest.db");
        GymManagerService.initDatabase();
        AdminService.initDatabase();
        LoginService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Test
    void testCustomerLogin(FxRobot robot){
        robot.clickOn("#reg");
        robot.clickOn("#username");
        robot.write("userusername2");
        robot.clickOn("#password");
        robot.write("moneyMeremere@1");
        robot.clickOn("#firstname");
        robot.write("firstname");
        robot.clickOn("#lastname");
        robot.write("lastname");
        robot.clickOn("#phone");
        robot.write("+40727447520");
        robot.clickOn("#email");
        robot.write("geo@yahoo.com");
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registerMessage").queryText()).hasText(
                ("Account created successfully!")
        );
        robot.clickOn("#back");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginUsername");
        robot.write("useruser");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginPassword");
        robot.write("money");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginUsername");
        robot.write("name2");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginPassword");
        robot.write("Meremere@1");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#CustomerMessage").queryText()).hasText(
                ("")
        );
    }

    @Test
    void testManagerLogin(FxRobot robot){

        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginUsername");
        robot.write("Smart");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginPassword");
        robot.write("Meremere");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginUsername");
        robot.write("Fit");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginPassword");
        robot.write("@1");
        robot.clickOn("#LoginButton");
        assertThat(WindowMatchers.isShowing().equals("login.fxml"));
        assertThat(robot.lookup("#LoginTest").queryText()).hasText(
                ("")
        );
    }

    @Test
    void testAdminLogin(FxRobot robot){

        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginUsername");
        robot.write("Admi");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginPassword");
        robot.write("Meremere");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginUsername");
        robot.write("n");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText(
                String.format("Incorrect login!")
        );

        robot.clickOn("#LoginPassword");
        robot.write("@1");
        robot.clickOn("#LoginButton");
        assertThat(robot.lookup("#AdminTest").queryText()).hasText(
                ("")
        );
    }


}
