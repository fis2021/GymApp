package proiect.fis.gym.aplication.exceptions;

public class IncorectCVCException extends Exception{

    public IncorectCVCException(){ super(String.format("Cvc must be 3 digits long")); }
}
