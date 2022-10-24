import javax.swing.*;

public class Player {
    private static int balance;

    public Player(){//初始化拥有1000块钱
        balance=1000;
    }

    public static void doubleBet(){
        GameComponent.currentBalance-=GameComponent.currentBet;
        GameComponent.currentBet*=2;
        balance-= GameComponent.currentBet;
    }

    public static void makeBet(){
        int response = JOptionPane.showOptionDialog(null, "请点击您的投注金额", "开始",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, GameComponent.options, GameComponent.options[0]);
        if(response>=0 && response<=4){
            GameComponent.currentBet= Integer.parseInt(GameComponent.options[response]);
            GameComponent.currentBalance-= Integer.parseInt(GameComponent.options[response]);
            balance-=GameComponent.currentBalance;
        }
        else{//如果用户什么都没选，且关闭了窗口，我们将投注金额设置为1
            GameComponent.currentBet=20;
            GameComponent.currentBalance-=20;
            balance-=20;
            System.out.println("您未选择金额，因此，投注金额默认设置为20.");
        }
    }
}
