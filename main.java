package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

public class main extends JFrame implements MouseListener{

	
	JPanel[] back1 = new JPanel[6];
	JButton[] image = new JButton[6];
	JLabel[] label = new JLabel[6];
	String[] menus = {"원조김밥", "참치김밥", "순두부찌개", "라면", "오므라이스", "돈까스"};
	int[] prices = {2000, 3000, 5000, 3500, 5500, 6000};
	public int count1, count2, count3, count4, count5, count6;

	JTabbedPane fpage = new JTabbedPane();	// 탭기능을 구현하는 소스
	JPanel full = new JPanel();//
	JPanel topPaenl = new JPanel();//
	JLabel topimage = new JLabel();
	JPanel main = new JPanel();
	JPanel bottomPanel = new JPanel();//
	
	public Kimbab first = null;
	public PogbaFood second = null;
	public zzige thard = null;
	public flourFood forth = null;
	public SeasonalMenu five = null;
	public static Cart six = null;
	
	static boolean mflag =true;
	public main() {
		setBounds(400, 70, 900, 900);
		full.setLayout(new BorderLayout());//
		
		topPaenl.setPreferredSize(new Dimension(900,150));//
		String top = String.format("./src/MainImage/toppanel.jpg");
		topimage.setIcon(new ImageIcon(top));
		topPaenl.add(topimage);
		full.add(topPaenl, BorderLayout.NORTH);//
		
		main.setLayout(new GridLayout(3, 2));
		for(int i=0 ; i<image.length ; i++) {
			back1[i] = new JPanel(new BorderLayout());
			image[i] = new JButton();
			image[i].setName(menus[i]);
			label[i] = new JLabel(menus[i] + " " + prices[i] + "원");
			label[i].setHorizontalAlignment(JLabel.CENTER);
			label[i].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));	// 외곽선의 모양을 바꾸는 명령어
			image[i].addMouseListener(this);
			back1[i].add(image[i]);
			back1[i].add(label[i], BorderLayout.SOUTH);
			main.add(back1[i]);
		}
		
		String filename = "";
		for(int i=0 ; i<image.length ; i++) {
			filename = String.format("./src/MainImage/%d.PNG", i + 1);
			image[i].setIcon(new ImageIcon(filename));
		}
		
		first = new Kimbab();	
		second = new PogbaFood();
		thard = new zzige();
		forth = new flourFood();
		five = new SeasonalMenu();
		six = new Cart();
		fpage.addTab("메인", main);		// 탭테이블에 클래스들을 추가한다.
		fpage.addTab("김밥", first);
		fpage.addTab("식사류", second);
		fpage.addTab("찌개류", thard);
		fpage.addTab("분식류", forth);
		fpage.addTab("계절 한정 메뉴", five);
		full.add(fpage); //
		six.setPreferredSize(new Dimension(900,250));//
		bottomPanel.add(six);//
        full.add(bottomPanel, BorderLayout.SOUTH);//
		add(full);  //
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {		
		main frame = new main();
		Thread thread = new Thread(six);
		thread.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		int val;
				
		switch (((JButton)obj).getName()) {
		
		case "원조김밥": 
			val = 2000;			
			if(mflag == false && count1 >0) {count1 = 0; mflag = true;}
			Cart.insert(((JButton)obj).getName(), val, ++count1);
			break;
			
		case "참치김밥":
			val = 3000;			
			if(mflag == false && count2 >0) {count2 = 0; mflag = true;}
			Cart.insert(((JButton)obj).getName(), val, ++count2);
			break;
			
		case "순두부찌개":
			val = 5000;		
			if(mflag == false && count3 >0) {count3 = 0; mflag = true;}		
			Cart.insert(((JButton)obj).getName(), val, ++count3);
			break;
			
		case "라면":
			val = 3000;			
			if(mflag == false && count4 >0) {count4 = 0; mflag = true;}				
			Cart.insert(((JButton)obj).getName(), val, ++count4);
			break;
			
		case "오므라이스":
			val = 5500;		
			if(mflag == false && count5 >0) {count5 = 0; mflag = true;}		
			Cart.insert(((JButton)obj).getName(), val, ++count5);
			break;
			
		case "돈까스":
			val = 6000;			
			if(mflag == false && count6 >0) {count6 = 0; mflag = true;}			
			Cart.insert(((JButton)obj).getName(), val, ++count6);
			break;		
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	public static void FlagMethod(boolean cflag) {	// 물품이 테이블에서 사라졌을때, 해당 수량을 초기화 하기위한 플래그
		mflag = cflag;		
	}
}
