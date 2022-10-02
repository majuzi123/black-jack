import javax.imageio.IIOException;
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
        g2.setFont(new Font("宋体", Font.BOLD, 100)); //In these codes, we will add the title of our game and its font and color.
        g2.setColor(Color.WHITE);
        g2.drawString("欢迎来到", 290, 140);
        g2.drawString("BLACKJACK!", 290, 260);


        btnPlay.setBounds(400, 300, 300, 80);
        btnExit.setBounds(400, 400, 300, 80);
        btnHelp.setBounds(400, 500, 300, 80);


        btnPlay.setFont(new Font("宋体", Font.BOLD, 40));
        btnExit.setFont(new Font("宋体", Font.BOLD, 40));
        btnHelp.setFont(new Font("宋体", Font.BOLD, 40));

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
           Tester.currentState=Tester.STATE.GAME;
           Tester.menuFrame.dispose();
           Tester.gameRefreshThread.start();
           Tester.gameCheckThread.start();
        }
        else if(selectedButton==btnPlay){
         JOptionPane.showMessageDialog(this,"21点的目标是在不超过21点的情况下击败庄家。" +
                 "\nA的值为1或11，J、Q、K的值为10，其余的牌即自己的牌面值，谁最后的值好谁就赢。" +
                 "\n每个玩家都有两张牌开始，其中一张庄家的牌一直隐藏到最后" +
                 "\n点击“要一张牌”可以再获得一张牌。点击“开牌”是保持总得分并结束回合。" +
                 "\n如果你超过21点，你就会爆掉，不管庄家的牌是多少，庄家都会赢。" +
                 "\n如果你从一开始就被发21分（把A当成10分），你就有21点，你就可以获胜。","游戏规则",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
