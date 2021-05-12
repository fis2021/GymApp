package proiect.fis.gym.aplication.exceptions;

public class NotMatchingPasswordsException extends Exception{
    public NotMatchingPasswordsException(){
        super("The passwords are not matching");
    }
}
