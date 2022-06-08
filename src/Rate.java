public class Rate {
    private int laufzeit;
    private double kosten;
    private boolean status = true;

    public Rate(int laufzeit, double kosten) {
        this.laufzeit = laufzeit;
        this.kosten = kosten;
    }


    public int getLaufzeit() {
        return laufzeit;
    }

    public double getKosten() {
        return kosten;
    }

    public String getAll() {
        return "Kosten: " + kosten + ", Laufzeit: " + laufzeit;
    }

    public boolean getStatus(boolean status) {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
