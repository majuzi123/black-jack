import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    //一副扑克牌
    private ArrayList<Card> deck;

    public Deck(){ 
        deck=new ArrayList<Card>();

        for(int s=0;s<4;s++){//四种花色
            for(int r=0;r<13;r++){//13个数字
                if(r==0){
                    Card card=new Card(s,r,11);
                    deck.add(card);
                }
                else if(r>=9){
                    Card card=new Card(s,r,11);
                    deck.add(card);
                }
                else {
                    Card card=new Card(s,r,r+1);
                    deck.add(card);
                }
            }
        }
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;

    }
    public int getDeckSize(){
        return deck.size();
    }
    public ArrayList<Card> getDeck() {
        return deck;
    }
    public Card getCard(int i) { //This method returns the ith (index) card of the deck.
        return deck.get(i);
    }
    public Card removeCard(int i) { //This method removes the ith (index) card of the deck.
        return deck.remove(i);
    }
    public void shuffleDeck() { //This method shuffles the deck to make the alignment of cards random.
        Collections.shuffle(deck);
    }//洗牌函数
}
