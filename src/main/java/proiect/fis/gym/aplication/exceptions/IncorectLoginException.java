package proiect.fis.gym.aplication.exceptions;

public class IncorectLoginException extends Exception{

    public IncorectLoginException(){
        super(String.format("Incorrect login!"));
    }
}
