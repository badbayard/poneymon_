package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

public class StringCommand extends Command {
    protected String mot;

    public StringCommand(int id, String mot) {
        this.id = id;
        this.mot = mot;
    }

    @Override
    public void affichage() {
        System.err.println("Je suis une string command : " + id + ", " + mot);
    }
}
