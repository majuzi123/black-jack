import javax.swing.*;
import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private int size;
    public Hand(){
        hand=new ArrayList<Card>();
        size=0;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getSize() {
        return hand.size();
    }

    public int getSumOfHand(){//计算手牌值
        if(hasAceInHand()){//如果有A
            if(getSumWithHighAce()<=21){
                return getSumWithHighAce();
            }
            else {//把A的值看成1

                for (int i = 0; i <aceCountInHand() ; i++){
                    int handSum = getSumWithHighAce()-(i+1)*10;
                    if(handSum<=21){
                        return handSum;
                    }
                }

            }
        }
        else{//如果没有A，直接计算
            int handSum = 0;
            for (int i = 0; i < hand.size(); i++){
                handSum = handSum + hand.get(i).getValue();
            }
            return handSum;
        }
        return 22;//默认设置为爆掉的返回
    }

    public boolean hasAceInHand(){
        for(int i=0;i<hand.size();i++){
            if(hand.get(i).getRank()==0){
                return true;
            }
        }
        return false;
    }
    public int getSumWithHighAce() {//如果A被算成11，计算总和
        int handSum = 0;
        for (int i = 0; i < hand.size(); i++){
            handSum = handSum + hand.get(i).getValue();
        }
        return handSum;
    }
    public int aceCountInHand(){//数数有几个A
        int aceCount = 0;
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i).getRank() == 0) {
                aceCount++;
            }
        }
        return aceCount;
    }

}
