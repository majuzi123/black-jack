import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OptionComponent extends JComponent implements ActionListener {
    private JButton btnPlay=new JButton("开始游戏");
    private JButton btnExit=new JButton("退出游戏");
    private JButton btnHelp=new JButton("帮助");
    private static BufferedImage backgroundImage;
    public OptionComponent(){
        btnPlay.addActionListener(this);
        btnExit.addActionListener(this);
        btnHelp.addActionListener(this);
    }
    public  void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D) g;
        try{
            backgroundImage= ImageIO.read(new File("images/background.png"));
        }catch (IOException e){}
        g2.drawImage(backgroundImage,0,0,null);

        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 100)); //In these codes, we will add the title of our game and its font and color.
        g2.setColor(Color.WHITE);
        g2.drawString("Welcome", 380, 100);
        g2.drawString("to", 530, 185);
        g2.drawString("BLACKJACK!", 290, 265);
        g2.setFont(new Font("宋体", Font.BOLD, 25));
        g2.drawString("点击开始，让我们进入游戏吧...", 400, 580);

        btnPlay.setBounds(500, 320, 140, 60);
        btnExit.setBounds(500, 395, 140, 60);
        btnHelp.setBounds(500, 470, 140, 60);


        btnPlay.setFont(new Font("宋体", Font.BOLD, 25));
        btnExit.setFont(new Font("宋体", Font.BOLD, 25));
        btnHelp.setFont(new Font("宋体", Font.BOLD, 25));

        super.add(btnPlay);
        super.add(btnExit);
        super.add(btnHelp);

    }
    public void actionPerformed(ActionEvent e) {//点击按钮行为
     JButton selectedButton=(JButton) e.getSource();//被选中的按钮
        if(selectedButton==btnExit){
            System.exit(0);
        }
        else if(selectedButton==btnPlay){
           UIController.currentState= UIController.STATE.GAME;
           UIController.menuFrame.dispose();
           UIController.gameRefreshThread.start();
           UIController.gameCheckThread.start();
        }
        else if(selectedButton==btnHelp){
         JOptionPane.showMessageDialog(this,"21点的目标是在不超过21点的情况下击败庄家。" +
                 "\nA的值为1或11，J、Q、K的值为10，其余的牌即自己的牌面值，谁最后的值好谁就赢。" +
                 "\n每个玩家都有两张牌开始，其中一张庄家的牌一直隐藏到最后" +
                 "\n点击“要一张牌”可以再获得一张牌。点击“开牌”是保持总得分并结束回合。" +
                 "\n如果你超过21点，你就会爆掉，不管庄家的牌是多少，庄家都会赢。" +
                 "\n如果你从一开始就被发21分（把A当成10分），你就有21点，你就可以获胜。","游戏规则",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
