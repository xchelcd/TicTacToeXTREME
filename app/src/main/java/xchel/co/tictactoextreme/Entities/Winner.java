package xchel.co.tictactoextreme.Entities;

public class Winner {

    private int i;
    private int j;
    private int player;
    private boolean winner = false;

    public Winner() {
    }

    public Winner(int i, int j, int player, boolean winner) {
        this.i = i;
        this.j = j;
        this.player = player;
        this.winner = winner;
    }

    public Winner(int i, int j, int player) {
        this.i = i;
        this.j = j;
        this.player = player;
    }

    public Winner(int i, int j, boolean winner) {
        this.i = i;
        this.j = j;
        this.winner = winner;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
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

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
