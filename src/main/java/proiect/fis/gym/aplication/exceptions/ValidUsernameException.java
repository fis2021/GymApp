package proiect.fis.gym.aplication.exceptions;

public class ValidUsernameException extends Exception{

    public ValidUsernameException(){super(String.format("The username must be at least 5 characters long."));}
}
