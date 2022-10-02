import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Game {
    ArrayList<Card> house;//电脑的牌
    ArrayList<Card> player;//玩家的牌
   public boolean faceDown;//true即为面朝上
   public boolean houseWon;//true即为庄家赢了
    public volatile boolean roundOver;//true即为回合结束，volatile的作用是作为指令关键字，确保本条指令不会因编译器的优化而省略，且要求每次直接读值。
    JFrame frame;
    Deck deck;
    GameComponent atmosphereComponent;
    GameComponent cardComponent;
    JButton btnHit;
    JButton btnStand;
    JButton btnDouble;
    JButton btnExit;
    public Game(JFrame f){
        deck=new Deck();
        deck.shuffleDeck();//洗牌
        house=new ArrayList<Card>();
        player=new ArrayList<Card>();
        atmosphereComponent=new GameComponent(house,player);
        frame=f;
        faceDown=true;
        houseWon=true;
        roundOver=false;
    }
    public void formGame(){//创建游戏背景
        System.out.println("游戏开始");
        frame.setTitle("BLACKJACK!");
        frame.setSize(1130,665);
        frame.setLocationRelativeTo(null);//把游戏摆在正中央
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnHit=new JButton("再要一张");
        btnHit.setBounds(330, 550, 150, 50); //We set their bounds.
        btnHit.setFont(new Font("宋体", Font.BOLD, 16));  //We set their font.
        btnStand = new JButton("开牌");
        btnStand.setBounds(520, 550, 100, 50);
        btnStand.setFont(new Font("宋体", Font.BOLD, 16));
        btnDouble = new JButton("加倍");
        btnDouble.setBounds(650, 550, 100, 50);
        btnDouble.setFont(new Font("宋体", Font.BOLD, 16));
        btnExit = new JButton("退出游戏");
        btnExit.setBounds(930, 240, 190, 50);
        btnExit.setFont(new Font("宋体", Font.BOLD, 16));

        frame.add(btnHit);
        frame.add(btnStand);
        frame.add(btnDouble);
        frame.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"你带着"+Tester.currentBalance+"元离开了游戏");
                System.exit(0);
            }
        });
      atmosphereComponent=new GameComponent(house,player);
      atmosphereComponent.setBounds(0,0,1130,665);
      frame.add(atmosphereComponent);
      frame.setVisible(true);

    }
    public boolean hasAceInHand(ArrayList<Card> hand){
        for(int i=0;i<hand.size();i++){
            if(hand.get(i).getRank()==0){
                return true;
            }
        }
        return false;
    }
    public int getSumWithHighAce(ArrayList<Card> hand) {//如果A被算成11，计算总和
        int handSum = 0;
        for (int i = 0; i < hand.size(); i++){
            handSum = handSum + hand.get(i).getValue();
        }
        return handSum;
    }
    public int aceCountInHand(ArrayList<Card> hand){//数数有几个A
        int aceCount = 0;
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i).getRank() == 0) {
                aceCount++;
            }
        }
        return aceCount;
    }
    public int getSumOfHand(ArrayList<Card> hand){//计算手牌值
        if(hasAceInHand(hand)){//如果有A
           if(getSumWithHighAce(hand)<=21){
               return getSumWithHighAce(hand);
           }
           else {//把A的值看成1

               for (int i = 0; i <aceCountInHand(hand) ; i++){
                  int handSum = getSumWithHighAce(hand)-(i+1)*10;
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
    public void checkHand(ArrayList<Card> hand){//测试两方的手牌
        if(hand.equals(player)){//玩家的手牌
            if(getSumOfHand(hand)==21){//玩家blackjack赢
                faceDown=false;
                houseWon=false;
                JOptionPane.showMessageDialog(frame,"您以BLACKJACK获胜！");
                rest();
                roundOver=true;
            }
            else if(getSumOfHand(hand)>21){//玩家爆掉
                faceDown=false;
                houseWon=true;
                JOptionPane.showMessageDialog(frame,"您已经爆掉！您输掉了比赛！");
                rest();
                roundOver=true;
            }
        }
        else{//庄家的手牌
            if(getSumOfHand(hand)==21){//庄家blackjack赢
                faceDown=false;
                houseWon=true;
                JOptionPane.showMessageDialog(frame,"庄家以BLACKJACK获胜！");
                rest();
                roundOver=true;
            }
            else if(getSumOfHand(hand)>21){//庄家爆掉
                faceDown=false;
                houseWon=false;
                JOptionPane.showMessageDialog(frame,"庄家已经爆掉！您获得胜利！");
                rest();
                roundOver=true;
            }
        }
    }

    private void rest() {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {}
    }
    public void addCard(ArrayList<Card> hand){//发牌到手中
        hand.add(deck.getCard(0));
        deck.removeCard(0);
        faceDown=true;
}

    public void startGame(){//开始游戏
        for(int i=0;i<2;i++){
            house.add(deck.getCard(i));//首先给庄家发两张牌
        }
        for(int i=2;i<4;i++){
            player.add(deck.getCard(i));//给玩家发两张牌
        }
        for (int i=0;i<4;i++){//将4张牌从牌堆中移出去
            deck.removeCard(0);
        }
        cardComponent=new GameComponent(house,player);
        cardComponent.setBounds(0,0,1130,665);
        frame.add(cardComponent);
        frame.setVisible(true);

        checkHand(house);
        checkHand(player);

        btnHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCard(player);
                checkHand(player);
                if(getSumOfHand(player)<17 && getSumOfHand(house)<17){
                    addCard(house);
                    checkHand(house);
                }
            }
        });
        btnDouble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnStand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (getSumOfHand(house)<17){
                    addCard(house);
                    checkHand(house);
                }
                if((getSumOfHand(house)<21) && (getSumOfHand(player)<21)){
                    if (getSumOfHand(player)>getSumOfHand(house)){
                        faceDown = false;
                        houseWon = false;
                        JOptionPane.showMessageDialog(frame, "恭喜您！您获胜了!");
                        rest();
                        roundOver = true;
                    }
                    else {
                        faceDown = false;
                        houseWon = true;
                        JOptionPane.showMessageDialog(frame, "很遗憾！庄家取胜!");
                        rest();
                        roundOver = true;
                    }
                }
            }
        });
    }
}
