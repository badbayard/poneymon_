package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

public class StringCommand extends Command {
    protected String mot;

    public StringCommand(String mot) {
        this.mot = mot;
    }

    @Override
    public void affichage() {
        System.err.println("Je suis une string command : " + mot);
    }

    @Override
    public void atReceive() {
        System.out.println("L'envoyeur dit : " + mot);
    }

    public String getMot() {
        return mot;
    }
}
