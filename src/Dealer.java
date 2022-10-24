import javax.swing.*;

public class Dealer {
    Hand house;//庄家手牌
    Hand player;//玩家手牌
    Deck deck;

    public Dealer(){
        house=new Hand();
        player=new Hand();
        deck=BlackJackGame.deck;
    }
    public void addCard(Hand hand){//发牌
        hand.getHand().add(deck.getCard(0));
        deck.removeCard(0);
    }

    public void judgeWinner(){
        house=BlackJackGame.house;
        player=BlackJackGame.player;
        if((house.getSumOfHand()<21) && (player.getSumOfHand()<21)){
            if (player.getSumOfHand()>house.getSumOfHand()){
                BlackJackGame.faceDown = false;
                BlackJackGame.houseWon = false;
                JOptionPane.showMessageDialog(BlackJackGame.frame, "恭喜您！您获胜了!","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                BlackJackGame.rest();
                BlackJackGame.roundOver = true;
            }
            else if(player.getSumOfHand()<house.getSumOfHand()){
                BlackJackGame.faceDown = false;
                BlackJackGame.houseWon = true;
                JOptionPane.showMessageDialog(BlackJackGame.frame, "很遗憾！庄家取胜!","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                BlackJackGame.rest();
                BlackJackGame.roundOver = true;
            }
            else{
                if(player.getSize()<house.getSize()){
                    BlackJackGame.faceDown = false;
                    BlackJackGame.houseWon = false;
                    JOptionPane.showMessageDialog(BlackJackGame.frame, "恭喜您！您获胜了!","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                    BlackJackGame.rest();
                    BlackJackGame.roundOver = true;
                }
                else if(player.getSize()>house.getSize()){
                    BlackJackGame.faceDown = false;
                    BlackJackGame.houseWon = true;
                    JOptionPane.showMessageDialog(BlackJackGame.frame, "很遗憾！庄家取胜!","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                    BlackJackGame.rest();
                    BlackJackGame.roundOver = true;
                }
                else {
                    BlackJackGame.faceDown = false;
                    BlackJackGame.houseWon = false;
                    JOptionPane.showMessageDialog(BlackJackGame.frame, "平局，您不会失去赌注！","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                    BlackJackGame.rest();
                    BlackJackGame.roundOver = true;
                }
            }
        }
    }

    public void checkHand(Hand hand){//监测两方手牌
        if(hand.equals(player)){//玩家的手牌
            if(hand.getSumOfHand()==21 && hand.getSize()==2){//玩家blackjack赢
                GameComponent.currentBet=GameComponent.currentBet+(int)(0.25*GameComponent.currentBet);
                JOptionPane.showMessageDialog(BlackJackGame.frame,"您以BLACKJACK获胜！赔率增加到1.5！","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                BlackJackGame.roundOver=true;
            }
            else if(hand.getSumOfHand()>21){//玩家爆掉
                BlackJackGame.faceDown=false;
                BlackJackGame.houseWon=true;
                JOptionPane.showMessageDialog(BlackJackGame.frame,"您已经爆掉！输掉了比赛！","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                BlackJackGame.rest();
                BlackJackGame.roundOver=true;
            }
        }
        else{//庄家的手牌
            if(hand.getSumOfHand()==21 && hand.getSize()==2){//庄家blackjack赢
                BlackJackGame.faceDown=false;
                BlackJackGame.houseWon=true;
                GameComponent.currentBet=GameComponent.currentBet+(int)(0.25*GameComponent.currentBet);
                JOptionPane.showMessageDialog(BlackJackGame.frame,"庄家以BLACKJACK获胜！赔率增加到1.5！","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                BlackJackGame.rest();
                BlackJackGame.roundOver=true;
            }
            else if(hand.getSumOfHand()>21){//庄家爆掉
                BlackJackGame.faceDown=false;
                BlackJackGame.houseWon=false;
                JOptionPane.showMessageDialog(BlackJackGame.frame,"庄家已经爆掉！您获得胜利！","游戏结果",JOptionPane.INFORMATION_MESSAGE);
                BlackJackGame.rest();
                BlackJackGame.roundOver=true;
            }
        }
    }
}
