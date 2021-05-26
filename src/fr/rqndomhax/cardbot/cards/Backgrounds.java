package fr.rqndomhax.cardbot.cards;

public enum Backgrounds {
    CRA("resources/backgrounds/cra.png"),
    ECAFLIP("resources/backgrounds/ecaflip.png"),
    ELIOTROPE("resources/backgrounds/eliotrope.png"),
    ENIRIPSA("resources/backgrounds/eniripsa.png"),
    ENUTROF("resources/backgrounds/enutrof.png"),
    FECA("resources/backgrounds/feca.png"),
    HUPPERMAGE("resources/backgrounds/huppermage.png"),
    IOP("resources/backgrounds/iop.png"),
    KERUBIM("resources/backgrounds/kerubim.png"),
    OSAMODAS("resources/backgrounds/samodas.png"),
    OUGINAK("resources/backgrounds/ouginak.png"),
    PANDAWA("resources/backgrounds/pandawa.png"),
    ROUBLARD("resources/backgrounds/roublard.png"),
    SACRIEUR("resources/backgrounds/sacrieur.png"),
    SADIDA("resources/backgrounds/sadida.png"),
    SRAM("resources/backgrounds/sram.png"),
    STEAMER("resources/backgrounds/steamer.png"),
    XELOR("resources/backgrounds/xelor.png"),
    ZOBAL("resources/backgrounds/zobal.png");

    private final String imagePath;

    Backgrounds(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
