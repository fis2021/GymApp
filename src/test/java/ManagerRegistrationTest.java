import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.testfx.assertions.api.Assertions.assertThat;

import java.io.File;

@ExtendWith(ApplicationExtension.class)
public class ManagerRegistrationTest {

    @BeforeEach
    void setUp() throws Exception{
        System.out.println("before");

        FileSystemService.APPLICATION_FOLDER = ".test-GymApplication";
        FileSystemService.initDirectory();
        if(GymManagerService.getGymManagerRepository() != null){
            System.out.println(GymManagerService.getGymManagerRepository().isClosed());
        }
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        GymManagerService.initTestDatabase("ManagersTest.db");

        //GymManagerService.getGymManagerTestRepository().insert(new GymManager("x", "sef", "+400757806405", "r@r.com", "tm",
          //      "gym one", "GymOne", "meremere1@M"));

        GymManagerService.getGymManagerRepository().insert(new GymManager("y", "sef", "+400757806405", "r@r.com", "tm",
                "smartfit", "SmartFit", "meremere1@M"));

        GymManagerService.getGymManagerRepository().insert(new GymManager("z", "sef", "+400757806405", "r@r.com", "tm",
                "iguana", "Iguana", "meremere1@M"));
    }

    @AfterEach
    void tearDown() throws Exception{
        System.out.println("after");
        GymManagerService.getGymManagerRepository().close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/registerGymManager.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
    }

    @Test
    void emptyTest(FxRobot robot) {
        robot.clickOn("#firstNameField");
        robot.write("x");
        robot.clickOn("#lastNameField");
        robot.write("sef");

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessageLabel").queryLabeled()).hasText(
                String.format("Please complete all the fields!")
        );
    }

    @Test
    void successfulRegistrationTest(FxRobot robot) {
        robot.clickOn("#firstNameField");
        robot.write("x");
        robot.clickOn("#lastNameField");
        robot.write("sef");

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessageLabel").queryLabeled()).hasText(
                String.format("Please complete all the fields!")
        );

        robot.clickOn("#phoneField");
        robot.write("+400757806405");
        robot.clickOn("#emailField");
        robot.write("r@r.com");
        robot.clickOn("#gymLocation");
        robot.write("tm");
        robot.clickOn("#companyName");
        robot.write("gym one");
        robot.clickOn("#usernameField");
        robot.write("GymOne");
        robot.clickOn("#passwordField");
        robot.write("meremere1@M");
        robot.clickOn("#confirmPasswordField");
        robot.write("meremere1@M");

        //test inregistrare cu succes:
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessageLabel").queryLabeled()).hasText(
                String.format("Account created successfully!")
        );

    }
}
