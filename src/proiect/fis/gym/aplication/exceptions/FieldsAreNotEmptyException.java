package proiect.fis.gym.aplication.exceptions;

public class FieldsAreNotEmptyException extends Exception{

    public FieldsAreNotEmptyException(){ super(String.format("Please complete all the fields!")); }
}
