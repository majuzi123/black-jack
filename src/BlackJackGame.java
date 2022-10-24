import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackJackGame {
    Dealer dealer;
    static Hand house;//庄家手牌
    static Hand  player;//玩家手牌
    static boolean faceDown;//true即为面朝上
    static boolean houseWon;//true即为庄家赢了
    public volatile static boolean roundOver;//true即为回合结束，volatile的作用是作为指令关键字，确保本条指令不会因编译器的优化而省略，且要求每次直接读值。
    static JFrame frame;
    static Deck deck;
    GameComponent atmosphereComponent;
    GameComponent cardComponent;
    JButton btnHit;
    JButton btnStand;
    JButton btnDouble;
    JButton btnExit;
    static int isfirstAdd;
    public BlackJackGame(JFrame f){
        deck=new Deck();
        deck.shuffleDeck();//洗牌
        house=new Hand();
        player=new Hand();
        dealer = new Dealer();
        atmosphereComponent=new GameComponent(house,player);
        frame=f;
        faceDown=true;
        houseWon=true;
        roundOver=false;
    }
    public void formRound(){//创建游戏背景
        System.out.println("游戏开始");
        frame.setTitle("BLACKJACK!");
        frame.setSize(1130,665);
        frame.setLocationRelativeTo(null);//把游戏摆在正中央
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnHit=new JButton("再要一张");
        btnHit.setBounds(400, 550, 120, 50); //We set their bounds.
        btnHit.setFont(new Font("宋体", Font.BOLD, 18));  //We set their font.
        btnStand = new JButton("开牌");
        btnStand.setBounds(540, 550, 120, 50);
        btnStand.setFont(new Font("宋体", Font.BOLD, 18));
        btnDouble = new JButton("加倍");
        btnDouble.setBounds(680, 550, 120, 50);
        btnDouble.setFont(new Font("宋体", Font.BOLD, 18));
        btnExit = new JButton("退出游戏");
        btnExit.setBounds(940, 240, 180, 50);
        btnExit.setFont(new Font("宋体", Font.BOLD, 18));

        frame.add(btnHit);
        frame.add(btnStand);
        frame.add(btnDouble);
        frame.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"你带着"+ UIController.currentBalance+"元离开了游戏");
                System.exit(0);
            }
        });
        atmosphereComponent=new GameComponent(house,player);
        atmosphereComponent.setBounds(0,0,1130,665);
        frame.add(atmosphereComponent);
        frame.setVisible(true);
    }

    public static void rest() {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {}
    }

    public void startRound(){//开始游戏
        isfirstAdd=0;
        for(int i=0;i<2;i++){
            house.getHand().add(deck.getCard(i));//首先给庄家发两张牌
        }
        for(int i=2;i<4;i++){
            player.getHand().add(deck.getCard(i));//给玩家发两张牌
        }
        for (int i=0;i<4;i++){//将4张牌从牌堆中移出去
            deck.removeCard(0);
        }
        cardComponent=new GameComponent(house,player);
        cardComponent.setBounds(0,0,1130,665);
        frame.add(cardComponent);
        frame.setVisible(true);

        dealer.checkHand(house);
        dealer.checkHand(player);
        actionMapper();
    }

    public void compareHand(){
        dealer.judgeWinner();
    }

    public void actionMapper(){
        btnHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dealer.addCard(player);
                dealer.checkHand(player);
                if(player.getSumOfHand()<17 && house.getSumOfHand()<17){
                    dealer.addCard(house);
                    dealer.checkHand(house);
                }
            }
        });
        btnDouble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isfirstAdd==0)
                {
                    Player.doubleBet();
                    JOptionPane.showMessageDialog(null,"赌注加倍！现在赌注是: " + GameComponent.currentBet + "元，赔率1:1\n" + "如果您打败了庄家，您将获得: " + GameComponent.currentBet*2 +
                            "元; 如果庄家打败您，您将输掉赌注.","提示",JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("赌注加倍！现在赌注是: " + GameComponent.currentBet + "元，赔率1:1\n" + "如果您打败了庄家，您将获得: " + GameComponent.currentBet*2 +
                            "元; 如果庄家打败您，您将输掉赌注.");
                    isfirstAdd++;
                }
                else {
                    JOptionPane.showMessageDialog(null,"游戏已经开始，您无法再加倍","提示",JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("游戏已经开始，您无法再加倍");
                }
            }
        });
        btnStand.addActionListener(new ActionListener() {//开牌
            @Override
            public void actionPerformed(ActionEvent e) {
                while (house.getSumOfHand()<17){
                    dealer.addCard(house);
                    BlackJackGame.faceDown=true;//默认卡牌不显示
                    BlackJackGame.isfirstAdd++;
                    dealer.checkHand(house);
                }
                compareHand();
            }
        });
    }



}
