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
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.*;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class AddReviewTest {

    public static final String USERNAME="george";
    public static final String PASSWORD="Meremere@1";
    private static ObjectRepository<GymManager> managerRepository;

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-GymApplication";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BankService.initDatabase();
        AdminService.initDatabase();
        CustomerService.initDatabase();
        GymManagerService.initDatabase();
        LoginService.initDatabase();
        managerRepository=GymManagerService.getGymManagerRepository();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    @AfterEach
    void tearDown() {
        AdminService.getDatabase().close();
        BankService.getDatabase().close();
        CustomerService.getDatabase().close();
        GymManagerService.getDatabase().close();
    }

    @Test
    void viewCoursesTest(FxRobot robot){
        int a=0,b=0;
        for(GymManager manager: managerRepository.find()){
            if(manager.getUsername().equals("SmartFit")){
                a=manager.getReviewList().size();
            }
        }

        robot.clickOn("#LoginUsername");
        robot.write(USERNAME);
        robot.clickOn("#LoginPassword");
        robot.write(PASSWORD);
        robot.clickOn("#LoginButton");
        robot.clickOn("#smartfit");
        robot.clickOn("#addReviewSmart");
        robot.clickOn("#textSmart");
        robot.write("Excellent gym!");
        robot.clickOn("#submitSmart");
        assertThat(robot.lookup("#messageSmart").queryText()).hasText(
                ("Review submitted successfully")
        );

        for(GymManager manager: managerRepository.find()){
            if(manager.getUsername().equals("SmartFit")){
                b=manager.getReviewList().size();
            }
        }
        a=a+1;
        if(a!=b && a!=0 ){
            assertThat(robot.lookup("#messageSmart").queryText()).hasText(
                    ("Error")
            );
        }

    }
}
