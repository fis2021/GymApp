import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.ObjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.services.*;

import java.time.LocalDate;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class CreateSubscriptionTest {

    private static ObjectRepository<Bank> bankRepository;
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

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Gym Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Test
    void createSubscriptionTest(FxRobot robot){

        robot.clickOn("#reg");
        robot.clickOn("#username");
        robot.write("userusername1");
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
        robot.write("userusername1");
        robot.clickOn("#LoginPassword");
        robot.write("moneyMeremere@1");
        robot.clickOn("#LoginButton");
        robot.clickOn("#createSubscription");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Please complete all the fields")
        );

        robot.clickOn("#cardOwnerName");
        robot.write("Radu ");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Please complete all the fields")
        );

        robot.clickOn("#cardNumber");
        robot.write("12345678");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Please complete all the fields")
        );

        robot.clickOn("#cvc");
        robot.write("30");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Cvc must be 3 digits long")
        );

        robot.clickOn("#cvc");
        robot.write("3");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Card number must have 16 digits")
        );

        robot.clickOn("#cardNumber");
        robot.write("12342323");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Incorrect card details")
        );

        robot.clickOn("#cardOwnerName");
        robot.write("Mihai");

        String cardOwnerName1="Radu Mihai";
        float a=0;
        for(Bank bank : bankRepository.find()){
            if(cardOwnerName1.equals(bank.getNumeDetinator())){
                a=Float.parseFloat(bank.getSum());
            }
        }

        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                ("Payment made successfully")
        );


        float b=0;
        for(Bank bank : bankRepository.find()){
            if(cardOwnerName1.equals(bank.getNumeDetinator())){
                b=Float.parseFloat(bank.getSum());
            }
        }
        a=a-50;
        if(a!=b){
            assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                    ("Error")
            );
        }
    }

    @Test
    void testEnoughMoneyException(FxRobot robot){
        robot.clickOn("#LoginUsername");
        robot.write("userusername1");
        robot.clickOn("#LoginPassword");
        robot.write("moneyMeremere@1");
        robot.clickOn("#LoginButton");
        robot.clickOn("#createSubscription");

        robot.clickOn("#cardOwnerName");
        robot.write("Andrei Marian");
        robot.clickOn("#cardNumber");
        robot.write("1234567812345678");
        robot.clickOn("#cvc");
        robot.write("111");
        robot.clickOn("#MakePayment");
        assertThat(robot.lookup("#paymentMessage").queryText()).hasText(
                String.format("Not enough money")
        );
    }

}
