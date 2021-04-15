package proiect.fis.gym.aplication.model;

import java.util.Objects;

public class Bank {

    private String numeDetinator;
    private String valabilitate;
    private String numarCard;//16
    private String CVC;// 3 cifre

    public Bank(String numeDetinator, String valabilitate, String numarCard, String CVC) {
        this.numeDetinator = numeDetinator;
        this.valabilitate = valabilitate;
        this.numarCard = numarCard;
        this.CVC = CVC;
    }

    public String getNumeDetinator() {
        return numeDetinator;
    }

    public void setNumeDetinator(String numeDetinator) {
        this.numeDetinator = numeDetinator;
    }

    public String getValabilitate() {
        return valabilitate;
    }

    public void setValabilitate(String valabilitate) {
        this.valabilitate = valabilitate;
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

    @Override
    public String toString() {
        return "Bank{" +
                "numeDetinator='" + numeDetinator + '\'' +
                ", valabilitate='" + valabilitate + '\'' +
                ", numarCard='" + numarCard + '\'' +
                ", CVC='" + CVC + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(numeDetinator, bank.numeDetinator) && Objects.equals(valabilitate, bank.valabilitate) && Objects.equals(numarCard, bank.numarCard) && Objects.equals(CVC, bank.CVC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeDetinator, valabilitate, numarCard, CVC);
    }
}
