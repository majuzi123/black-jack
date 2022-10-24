import javax.swing.*;

public class UIController {
    public static JFrame menuFrame=new JFrame();
    public static JFrame gameFrame=new JFrame();
    private static int playScore=0;
    private static int houseScore=0;
    public static int currentBalance=1000;
    public static BlackJackGame newGame=new BlackJackGame(gameFrame);
    private static boolean isFirstTime=true;//判断是否为第一次进入游戏

    public static enum STATE{
        MENU,
        GAME
    }
    public static STATE currentState=STATE.MENU;
    public static void main(String[] args) throws InterruptedException{
        if(currentState==STATE.MENU){
            openMenu();
        }
    }

    private static void openMenu() {
        menuFrame.setTitle("BLACKJACK!");
        menuFrame.setSize(1130, 665);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);

        OptionComponent beginningComponent = new OptionComponent();
        menuFrame.add(beginningComponent);
        menuFrame.setVisible(true);
    }

    public static Thread gameRefreshThread = new Thread() {
        public void run(){
            while(true){
                newGame.atmosphereComponent.refresh(currentBalance,playScore,houseScore-1,newGame.faceDown);
            }
        }
    };
    public static Thread gameCheckThread=new Thread() {
        public void run(){
           while (true){
               if(isFirstTime||newGame.roundOver){
                   System.out.println("刷新游戏");
                   if(newGame.houseWon){
                       houseScore++;
                       currentBalance-=GameComponent.currentBet;
                   }
                   else {
                       playScore++;
                       currentBalance+=GameComponent.currentBet*2;
                   }
                   gameFrame.getContentPane().removeAll();
                   newGame=new BlackJackGame(gameFrame);
                   newGame.formRound();
                   isFirstTime=false;
               }
           }
        }
    };
}
