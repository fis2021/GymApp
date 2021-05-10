package proiect.fis.gym.aplication.exceptions;

public class corectEmailException extends Exception{

    public corectEmailException(){
        super(String.format("Please enter a valid email adress!"));
    }

}
