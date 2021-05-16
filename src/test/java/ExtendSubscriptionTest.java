import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
public class ExtendSubscriptionTest {

    private static ObjectRepository<Bank> bankRepository;

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-GymApplication";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
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
    void extendSubscriptionTest(FxRobot robot){
        robot.clickOn("#reg");
        robot.clickOn("#username");
        robot.write("userusername3");
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
        robot.clickOn("#LoginUsername");
        robot.write("userusername3");
        robot.clickOn("#LoginPassword");
        robot.write("moneyMeremere@1");
        robot.clickOn("#LoginButton");
        robot.clickOn("#createSubscription");

        robot.clickOn("#cardOwnerName");
        robot.write("Radu Mihai");
        robot.clickOn("#cardNumber");
        robot.write("1234567812342323");
        robot.clickOn("#cvc");
        robot.write("303");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                ("Payment made successfully")
        );

        robot.clickOn("#backToMain");
        String cardOwnerName1="Radu Mihai";
        float a=0;
        for(Bank bank : bankRepository.find()){
            if(cardOwnerName1.equals(bank.getNumeDetinator())){
                a=Float.parseFloat(bank.getSum());
            }
        }
        robot.clickOn("#Extend");
        robot.clickOn("#cardOwnerName");
        robot.write("Radu ");
        robot.clickOn("#extendSubs");
        assertThat(robot.lookup("#extendMessage").queryText()).hasText(
                String.format("Please complete all the fields")
        );

        robot.clickOn("#cardOwnerName");
        robot.write("Mihai");
        robot.clickOn("#extendCardNumber");
        robot.write("1234567812342323");
        robot.clickOn("#extendCVC");
        robot.write("303");
        robot.clickOn("#extendSubs");
        assertThat(robot.lookup("#extendMessage").queryText()).hasText(
                ("Subscription extended successfully")
        );

        float b=0;
        for(Bank bank : bankRepository.find()){
            if(cardOwnerName1.equals(bank.getNumeDetinator())){
                b=Float.parseFloat(bank.getSum());
            }
        }
        a=a-50;
        if(a!=b){
            assertThat(robot.lookup("#extendMessage").queryText()).hasText(
                    ("Error")
            );
        }

    }
}
