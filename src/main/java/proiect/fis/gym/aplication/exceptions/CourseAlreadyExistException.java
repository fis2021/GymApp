package proiect.fis.gym.aplication.exceptions;

public class CourseAlreadyExistException extends Exception{
    public CourseAlreadyExistException(){
        super("This course already exists");
    }
}
