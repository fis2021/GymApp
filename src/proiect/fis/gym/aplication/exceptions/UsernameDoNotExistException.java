package proiect.fis.gym.aplication.exceptions;

public class UsernameDoNotExistException extends Exception{
    public UsernameDoNotExistException() {
        super("This username is invalid");
    }
}
