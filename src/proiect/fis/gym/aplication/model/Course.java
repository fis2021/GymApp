package proiect.fis.gym.aplication.model;

import java.util.Objects;

public class Course {
    private String name;
    private String trainer;
    private String schedule;

    public Course(String name, String trainer, String schedule) {
        this.name = name;
        this.trainer = trainer;
        this.schedule = schedule;
    }

    public Course(){

    }

    @Override
    public String toString() {
        return name  + " trainer: " + trainer + " schedule: " + schedule ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) &&
                trainer.equals(course.trainer) &&
                schedule.equals(course.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, trainer, schedule);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
