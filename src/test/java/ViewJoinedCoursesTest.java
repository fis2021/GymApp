import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import proiect.fis.gym.aplication.services.*;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class ViewJoinedCoursesTest {

    public static final String USERNAME="george";
    public static final String PASSWORD="Meremere@1";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-GymApplication";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BankService.initDatabase();
        AdminService.initDatabase();
        CustomerService.initDatabase();
        GymManagerService.initDatabase();
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
    void viewCoursesTest(FxRobot robot){
        robot.clickOn("#LoginUsername");
        robot.write(USERNAME);
        robot.clickOn("#LoginPassword");
        robot.write(PASSWORD);
        robot.clickOn("#LoginButton");
        robot.clickOn("#viewCourse");
        assertThat(robot.lookup("#CustomerMessage").queryText()).hasText(
                ("Smartfit: Aerobic trainer: Andrei schedule: 8-10\nSmartfit: Yoga trainer: Ana schedule: 12-14\n" +
                        "Smartfit: Fitness trainer: Mihai schedule: 8-10\n")
        );
    }
}
