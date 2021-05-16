import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.services.*;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class ViewSubscriptionsTest {

    private static ObjectRepository<Bank> bankRepository;
    public static final String USERNAME="userusername3";
    public static final String PASSWORD="moneyMeremere@1";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-GymApplication";
        FileSystemService.initDirectory();
        //FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BankService.initDatabase();
        AdminService.initDatabase();
        CustomerService.initTestDatabase("CustomerTest.db");
        GymManagerService.initDatabase();
        LoginService.initDatabase();
        bankRepository=BankService.getBankRepository();
    }

    @AfterEach
    void tearDown() {
        AdminService.getDatabase().close();
        BankService.getDatabase().close();
        CustomerService.getDatabase().close();
        GymManagerService.getDatabase().close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Test
    void viewSubs(FxRobot robot){
        robot.clickOn("#LoginUsername");
        robot.write(USERNAME);
        robot.clickOn("#LoginPassword");
        robot.write(PASSWORD);
        robot.clickOn("#LoginButton");
        robot.clickOn("#viewSubs");
        /*assertThat(robot.lookup("#CustomerMessage").queryText()).hasText(
                ("SmartFit : 2021-07-13\n")
        );*/
    }
}
