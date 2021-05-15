import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.services.*;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class ViewCustomersTest {
    @BeforeEach
    void setUp() throws Exception{
        System.out.println("before");

        FileSystemService.APPLICATION_FOLDER = ".test-GymApplication";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        GymManagerService.initTestDatabase("ManagersTest.db");
        BankService.initDatabase();
        AdminService.initDatabase();
        CustomerService.initDatabase();
        LoginService.initDatabase();

        /*GymManagerService.getGymManagerRepository().insert(new GymManager("x", "sef", "+400757806405", "r@r.com", "tm",
             "gym one", "GymOne", "meremere1@M"));

        GymManagerService.getGymManagerRepository().insert(new GymManager("y", "sef", "+400757806405", "r@r.com", "tm",
                "smartfit", "SmartFit", "meremere1@M"));*/

        GymManagerService.addUser("meremere1@M","ana","are","+400757806405",
                "ana@ana.com", "tm", "gym one", "GymOne", "meremere1@M");
    }

    @AfterEach
    void tearDown() throws Exception{
        System.out.println("after");
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    @Test
    void viewCoursesTest(FxRobot robot) {
        robot.clickOn("#LoginUsername");
        robot.write("GymOne");
        robot.clickOn("#LoginPassword");
        robot.write("meremere1@M");

        robot.clickOn("#LoginButton");

        robot.clickOn("#addCourseButton");

        robot.clickOn("#courseNameTextField");
        robot.write("curs");
        robot.clickOn("#trainerNameTextField");
        robot.write("trainer");

        robot.clickOn("#scheduleTextField");
        robot.write("program");

        robot.clickOn("#submitNewCourseButton");

        robot.clickOn("#scheduleTextField");
        robot.write("1");
        robot.clickOn("#submitNewCourseButton");

        robot.clickOn("#scheduleTextField");
        robot.write("12");
        robot.clickOn("#submitNewCourseButton");

        robot.clickOn("#ViewCustomersButton");

        Customer customer = new Customer("user", "meremere1@M", "Customer","ana", "are",
                "+40757806405", "ana@ana.com" );

        try {
            CustomerService.addUser("user", "meremere1@M", "Customer","ana", "are",
                    "+40757806405", "ana@ana.com");
        } catch (UsernameAlreadyExistsException | corectEmailException | FieldsAreNotEmptyException
                | ValidPasswordException | ValidUsernameException | validPhoneNumberException e) {
            e.printStackTrace();
        } catch (inappropriateUsernameException e) {
            e.printStackTrace();
        }

        try {
            CustomerService.makePayment("Iguana", "Rares Matei","01","2022",
                    "1111222233334444","111", "1 month - 50$");
        } catch (incorectCardDetailsException | IncorectCardNumberException | IncorectCVCException | NotEnoughMoneyException
                | CheckPaymentFieldNotEmptyException e) {
            e.printStackTrace();
        }

        //CustomerService.cre
    }
}
