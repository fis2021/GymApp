package proiect.fis.gym.aplication.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Bank {
    @Id
    private String numeDetinator;
    private String luna, anu;
    private String numarCard;//16
    private String CVC;// 3 cifre
    private String sum;

    public Bank(){}

    public Bank(String numeDetinator, String luna, String anu, String numarCard, String CVC, String sum) {
        this.numeDetinator = numeDetinator;
        this.luna = luna;
        this.anu = anu;
        this.numarCard = numarCard;
        this.CVC = CVC;
        this.sum = sum;
    }

    public String getNumeDetinator() {
        return numeDetinator;
    }

    public void setNumeDetinator(String numeDetinator) {
        this.numeDetinator = numeDetinator;
    }

    public String getLuna() {
        return luna;
    }

    public void setLuna(String luna) {
        this.luna = luna;
    }

    public String getAnu() {
        return anu;
    }

    public void setAnu(String anu) {
        this.anu = anu;
    }

    public String getNumarCard() {
        return numarCard;
    }

    public void setNumarCard(String numarCard) {
        this.numarCard = numarCard;
    }

    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(numeDetinator, bank.numeDetinator) && Objects.equals(luna, bank.luna) && Objects.equals(anu, bank.anu) && Objects.equals(numarCard, bank.numarCard) && Objects.equals(CVC, bank.CVC) && Objects.equals(sum, bank.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeDetinator, luna, anu, numarCard, CVC, sum);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "numeDetinator='" + numeDetinator + '\'' +
                ", luna='" + luna + '\'' +
                ", anu='" + anu + '\'' +
                ", numarCard='" + numarCard + '\'' +
                ", CVC='" + CVC + '\'' +
                ", sum='" + sum + '\'' +
                '}';
    }
}
