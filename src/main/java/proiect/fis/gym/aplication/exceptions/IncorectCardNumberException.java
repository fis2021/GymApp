package proiect.fis.gym.aplication.exceptions;

public class IncorectCardNumberException extends Exception{

    public IncorectCardNumberException(){ super(String.format("Card number must have 16 digits")); }
}
