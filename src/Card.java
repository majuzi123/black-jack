import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {
    private int suit;//表示4个花色
    private int rank;//表示从A到K的13个牌
    private int value;//表示牌在这个游戏中所代表的的值（从1到11）
    private int xPosition;//表示图像的横坐标
    private int yPosition;//表示图像的纵坐标
    public Card(){
        suit=0;
        rank=0;
        value=0;
    }
    public Card(int s, int r, int v){
        suit=s;
        rank=r;
        value=v;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    public void printCard(Graphics2D g2,boolean houseTurn,boolean faceDown,int cardNumber) throws IOException{
        BufferedImage deckImg= ImageIO.read(new File("images/cardSpriteSheet.png"));
        int imgWidth=950;
        int imgHeight=392;
        BufferedImage[][] cardPictures=new BufferedImage[4][13];
        BufferedImage backOfACard=ImageIO.read(new File("images/backsideOfACard.jpg"));
        for(int c=0;c<4;c++){
            for (int r=0;r<13;r++){
                cardPictures[c][r]=deckImg.getSubimage(r*imgWidth/13, c*imgHeight/4, imgWidth/13, imgHeight/4);
            }
        }
        if(houseTurn){
            yPosition=75;
        }
        else{
            yPosition=400;
        }
        xPosition=500+75*cardNumber;
        if(faceDown){
            g2.drawImage(backOfACard,xPosition,yPosition,null);
        }
        else{
            g2.drawImage(cardPictures[suit][rank],xPosition,yPosition,null);
        }
    }
}
