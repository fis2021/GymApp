package proiect.fis.gym.aplication.exceptions;

public class CheckJoinedCourse extends Exception{
    public CheckJoinedCourse(){ super(String.format("You already participate to this course!")); }
}
