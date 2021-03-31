package proiect.fis.gym.aplication.exceptions;

public class corectEmailException extends Exception{

    public corectEmailException(){
        super(String.format("Introduceti o adresa de email valida!"));
    }

}
