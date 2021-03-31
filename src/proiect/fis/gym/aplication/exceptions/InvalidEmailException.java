package proiect.fis.gym.aplication.exceptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(){
        super("email is not valid");
    }
}
