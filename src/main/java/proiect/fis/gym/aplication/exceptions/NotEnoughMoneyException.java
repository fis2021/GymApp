package proiect.fis.gym.aplication.exceptions;

public class NotEnoughMoneyException extends Exception{

    public NotEnoughMoneyException(){ super(String.format("Not enough money")); }
}
