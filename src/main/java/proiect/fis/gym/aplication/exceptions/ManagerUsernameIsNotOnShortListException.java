package proiect.fis.gym.aplication.exceptions;

public class ManagerUsernameIsNotOnShortListException extends Exception{
    public ManagerUsernameIsNotOnShortListException() {
        super("This username is not on our list of managers");
    }
}
