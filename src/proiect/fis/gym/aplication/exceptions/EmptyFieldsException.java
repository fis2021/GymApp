package proiect.fis.gym.aplication.exceptions;

public class EmptyFieldsException  extends  Exception{
    public EmptyFieldsException(){
        super("Trebuie sa completati toate field-urile");
    }
}
