package proiect.fis.gym.aplication.exceptions;

public class InvalidAdminCodeException extends Exception{
    public InvalidAdminCodeException(){
        super("Admin code is not valid");
    }
}
