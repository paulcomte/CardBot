package fr.rqndomhax.cardbot.utils;

import fr.rqndomhax.cardbot.cards.monsters.CardMonster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public class ImageMaker {

    private final int width = 1065;
    private final int height = 414;
    private final Setup setup;

    public ImageMaker(Setup setup) {
        this.setup = setup;
    }

    public Image createMonsterCardImage(CardMonster card) throws IOException {
        Image cardBackground;
        Image monsterImage;
        BufferedImage bufferedImage = new BufferedImage(347, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        cardBackground = ImageIO.read(new File(card.getCardBackground().getImagePath()));
        cardBackground.getScaledInstance(347,400, Image.SCALE_SMOOTH);
        monsterImage = ImageIO.read(new URL(card.getMonsters().getImageURL()));
        monsterImage.getScaledInstance(272, 400, Image.SCALE_SMOOTH);
        graphics.drawImage(cardBackground, 0, 0, 347, 400, null);
        graphics.drawImage(monsterImage, 0, 0, null);
        return bufferedImage;
    }

    public void createDropImage(HashSet<CardMonster> cards) throws IOException {
        int index = 0;
        Image image;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.DARK_GRAY);
        for (CardMonster card : cards) {
            image = createMonsterCardImage(card);
            switch (index) {
                case 0:
                    graphics.drawImage(image, 0, 7, null);
                    break;
                case 1:
                    graphics.drawImage(image, 347+8, 7, null);
                    break;
                case 2:
                    graphics.drawImage(image, 672, 7, null);
                    break;
                default:
                    break;
            }
            index++;
        }
        ImageIO.write(newImage, "png", new File("card.png"));
    }
}
