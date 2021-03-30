package proiect.fis.gym.aplication.exceptions;

public class validPhoneNumberException extends Exception{

    public validPhoneNumberException(){ super(String.format("Please type a valid phone number!")); }
}
