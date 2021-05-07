package proiect.fis.gym.aplication.exceptions;

public class CheckPaymentFieldNotEmptyException extends Exception{

    public CheckPaymentFieldNotEmptyException(){ super(String.format("Please complete all the fields")); }
}
