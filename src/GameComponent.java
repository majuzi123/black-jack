import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GameComponent extends JComponent implements MouseListener {

    public BufferedImage backgroundImage;
    public BufferedImage logo;
    public BufferedImage chip;
    private ArrayList<Card> house;
    private ArrayList<Card> player;
    private int houseScore;//庄家的分数
    private int playerScore;//玩家的分数
    public boolean faceDown=true;
    public static boolean betMode=false;//玩家是否已经进入了投注模式
    private int currentBalance;//玩家现在的余额
    public static int currentBet;//现在的赌金
    public String[] options = new String[] {"20", "40", "60", "80", "100"};
    public GameComponent(ArrayList<Card> ho,ArrayList<Card> pl){
        house=ho;
        player=pl;
        houseScore=0;
        playerScore=0;
        currentBalance=1000;
        addMouseListener(this);
    }
    public void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D) g;
        try {
        backgroundImage= ImageIO.read(new File("images/background.png"));
            logo = ImageIO.read(new File("images/blackjackLogo.png"));
            chip = ImageIO.read(new File("images/chip.png"));

        } catch (IOException e){}
        g2.drawImage(backgroundImage,0,0,null);
        g2.drawImage(logo,510,200,null);
        g2.drawImage(chip,50,300,null);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("宋体",Font.BOLD,35));//设置字体
        g2.drawString("庄家",530,50);
        g2.drawString("玩家", 530, 380);
        g2.drawString("庄家赢了: ", 35, 100);
        g2.drawString(Integer.toString(houseScore), 300, 100); //展示得分
        g2.drawString("玩家赢了: ", 35, 150);
        g2.drawString(Integer.toString(playerScore), 300, 150);

        g2.setFont(new Font("宋体", Font.BOLD, 17));
        g2.drawString("！每一回合开始您需要先下注！", 45, 250);
        g2.drawString("请点击筹码图标选择您要下注的金额", 35, 270);

        g2.setFont(new Font("宋体", Font.BOLD, 25));
        g2.drawString("您的余额: " + currentBalance, 45, 570);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        g2.drawString(sdf.format(cal.getTime()),980,20);//显示时间
        try{//庄家回合
            for(int i=0;i<house.size();i++){
                if(i==0){
                   if(faceDown){//检查第一张牌是否朝下
                       house.get(i).printCard(g2,true,true,i);
                   }
                   else {
                       house.get(i).printCard(g2,true,false,i);
                   }
                }
                else {
                    house.get(i).printCard(g2,true,false,i);
                }
            }
        }catch (IOException e){}
        try{//玩家回合
          for(int i=0;i<player.size();i++){
              player.get(i).printCard(g2,false,false,i);
          }
        } catch (IOException e) {

        }
    }
    public void refresh(int cB,int pS,int hS,boolean fD){
        currentBalance=cB;
        playerScore=pS;
        houseScore=hS;
        faceDown=fD;
        this.repaint();
    }
    public void mouseClicked(MouseEvent mouseEvent) {

    }

public void setResponse(int response){
        currentBet= Integer.parseInt(options[response]);
        currentBalance-= Integer.parseInt(options[response]);
}
    public void mousePressed(MouseEvent mouseEvent) {//鼠标点击事件
     int mouseX=mouseEvent.getX();
     int mouseY=mouseEvent.getY();
     if(mouseX>=50 && mouseX<=200 &&mouseY>=300 && mouseY<=450){
         betMode=true;
        /*JLabel input=new JLabel("Input:");
         input.setSize(20,55);
         Font font = new Font("Comic Sans MS",Font.PLAIN,20);
         input.setFont(font);
         JTextField maskText=new JTextField(18);
         maskText.setPreferredSize(new Dimension(20,28));

         JButton button1 = new JButton("Start");
         button1.setSize(100, 55);
         button1.setFont(font);
         button1.setBackground((new Color(247, 238, 214, 255)));

         JPanel buttonPanel = new JPanel();
         buttonPanel.setBackground((new Color(160, 148, 231, 255)));
         buttonPanel.setLayout(new FlowLayout());
         buttonPanel.add(input);
         buttonPanel.add(maskText);
         buttonPanel.add(button1);*/

         int response = JOptionPane.showOptionDialog(null, "请点击您的投注金额", "开始",
                 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
         if(response>=0 && response<=4){
         setResponse(response);}
         else{//如果用户什么都没选，且关闭了窗口，我们将投注金额设置为1
             currentBet=20;
             currentBalance-=20;
             System.out.println("您未选择金额，因此，投注金额默认设置为20.");
         }
         JOptionPane.showMessageDialog(this,"您的赌注是: " + currentBet + "元，赔率1:1\n" + "如果您打败了庄家，您将获得: " + currentBet*2 +
                 "元; 如果庄家打败您，您将输掉赌注. ","游戏开始",JOptionPane.INFORMATION_MESSAGE);
         System.out.println("您的赌注是: " + currentBet + "元，赔率1:1\n" + "如果您打败了庄家，您将获得: " + currentBet*2 +
                 "元; 如果庄家打败您，您将输掉赌注. ");
         Tester.newGame.startGame();
     }
    }


    public void mouseReleased(MouseEvent mouseEvent) {

    }


    public void mouseEntered(MouseEvent mouseEvent) {

    }


    public void mouseExited(MouseEvent mouseEvent) {

    }
}
