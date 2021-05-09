package proiect.fis.gym.aplication.exceptions;

public class incorectCardDetailsException extends Exception{

    public incorectCardDetailsException(){ super(String.format("Incorrect card details")); }
}
