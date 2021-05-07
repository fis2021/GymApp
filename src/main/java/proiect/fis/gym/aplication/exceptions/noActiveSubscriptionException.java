package proiect.fis.gym.aplication.exceptions;

public class noActiveSubscriptionException extends Exception{
    public noActiveSubscriptionException() {
        super("You don't have an active subscription to the selected gym!");
    }
}
