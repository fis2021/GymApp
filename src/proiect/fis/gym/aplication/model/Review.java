package proiect.fis.gym.aplication.model;

import java.util.Objects;

public class Review {
    private String review;

    public Review(String review) {
        this.review = review;
    }
    public Review(){}

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return Objects.equals(review, review1.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review);
    }

    @Override
    public String toString() {
        return "Review{" +
                "review='" + review + '\'' +
                '}';
    }
}
