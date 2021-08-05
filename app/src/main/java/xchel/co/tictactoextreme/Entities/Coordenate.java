package xchel.co.tictactoextreme.Entities;

public class Coordenate {

    private int i;
    private int j;
    private int k;
    private int l;
    private int state;//jugador activo
    private boolean winner = false;

    public Coordenate() {
    }

    public Coordenate(int i, int j, int k, int l) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }

    public Coordenate(int i, int j, int k, int l, int state) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
        this.state = state;
    }
    public Coordenate(int i, int j, int k, int l, int state, boolean winner) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
        this.state = state;
        this.winner = winner;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }
}
