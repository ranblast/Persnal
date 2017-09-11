package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Cart extends JPanel implements ActionListener, Runnable{
	static String[] colName = {"음식 ", "가격", "수량"};
	static DefaultTableModel model = new DefaultTableModel(colName, 0);	// 선택한 음식을 보여줄 장바구니 테이블 모델
	
	static Date date1 = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일  HH시 mm분 ss초 E요일");
	static JLabel time = new JLabel(sdf.format(date1));
	
	JTable table = new JTable(model);	// 장바구니 테이블
	JPanel panel = new JPanel();
	JPanel botpanel = new JPanel();
	JButton card = new JButton("카드 결제");
	JPanel epanel = new JPanel();
	JButton minus = new JButton("-");
	JButton plus = new JButton("+");
	JTextField field = new JTextField(3);
	JPanel totalpanel = new JPanel();
	JLabel totallabel = new JLabel("총액 : ");
	static JTextField totalfield = new JTextField(8);
	static int total = 0;
	int Value = 0, position = 0, getmoney = 0, order = 1;
	boolean cflag;
	
	JPanel calPanel = new JPanel();						
	JPanel calcPanel = new JPanel(new BorderLayout());
	JButton[] calBtn = new JButton[12];
	JTextField calField = new JTextField(5);
	boolean maflag;
	
	public Cart() {
		setBounds(0, 0, 900, 900);
		setLayout(new BorderLayout());
		JScrollPane jsp = new JScrollPane(table);
		add(jsp);
		table.setGridColor(Color.black);
		botpanel.setLayout(new BorderLayout());	
		calPanel.setLayout(new GridLayout(4, 3));
		
		for(int i=0 ; i<calBtn.length-3 ; i++) {
			calBtn[i] = new JButton(i + 1 + "");
			calBtn[i].setName(i + 1 + "");
			calBtn[i].addActionListener(this);
			calPanel.add(calBtn[i]);
		}
		calBtn[9] = new JButton("←");
		calBtn[9].setName("지우기");
		calBtn[9].addActionListener(this);
		calPanel.add(calBtn[9]);
		calBtn[10] = new JButton("0");
		calBtn[10].setName("0");
		calBtn[10].addActionListener(this);
		calPanel.add(calBtn[10]);
		calBtn[11] = new JButton("ENTER");
		calBtn[11].setName("입력");
		calBtn[11].addActionListener(this);
		calPanel.add(calBtn[11]);	// 계산기 모양 버튼패널 제작
		
		
		calField.setText("0");
		calField.setHorizontalAlignment(JTextField.RIGHT);
		calField.setFont(new Font("Serif", Font.BOLD, 15));
		calField.addActionListener(this);	// 입력한 금액이 출력되는 텍스트필드 제작
		
		calcPanel.add(calField, BorderLayout.NORTH);
		calcPanel.add(calPanel);
		calcPanel.setPreferredSize(new Dimension(250, 150));
		calcPanel.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 5));	// 계산기 버튼패널과 금액필드패널을 올리고 오른쪽에 공백을 준다.
		
		add(calcPanel, BorderLayout.EAST);
		setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 5));
				
		minus.setName("마이너스");
		minus.addActionListener(this);
		epanel.add(minus);
		minus.setEnabled(false);		
		epanel.add(field);
		field.setEnabled(false);
		plus.setName("플러스");
		plus.addActionListener(this);
		epanel.add(plus);
		plus.setEnabled(false);
		epanel.setBorder(BorderFactory.createEmptyBorder(0 , 180 , 0 , 0));
		botpanel.add(epanel, BorderLayout.WEST);
		
		totalpanel.add(totallabel);
		totalfield.setEnabled(false);
		totalpanel.add(totalfield);
		totalpanel.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 80));
		botpanel.add(totalpanel, BorderLayout.EAST);
		
		panel.setLayout(new GridLayout());
		card.setName("카드");
		card.addActionListener(this);
		panel.add(card);
		botpanel.add(panel, BorderLayout.SOUTH);
		
		time.setBorder(BorderFactory.createEmptyBorder(0 , 60, 0 , 0));
		botpanel.add(time, BorderLayout.CENTER);		
		add(botpanel, BorderLayout.SOUTH);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로     
	    TableColumnModel tcm = table.getColumnModel() ; // 정렬할 테이블의 컬럼모델을 가져옴	테이블의 내용을 가운데 정렬
	     
	      for(int i = 0 ; i < tcm.getColumnCount() ; i++){
	      tcm.getColumn(i).setCellRenderer(dtcr);  }      
	      table.addMouseListener(new MouseAdapter() {
	    	  
	    	  @Override
	    	public void mouseClicked(MouseEvent e) {    // 테이블의 데이터를 클릭하면 수량이 텍스트 필드에 출력되게하는 코드		  
	    		  minus.setEnabled(true);
	    		  field.setEnabled(true);
	    		  plus.setEnabled(true);
	    		  position = table.getSelectedRow();  
	    		  Value = Integer.parseInt(model.getValueAt(position, 2).toString());
	    		  getmoney = Integer.parseInt(model.getValueAt(position, 1).toString());
	    		  field.setText(String.valueOf(model.getValueAt(position, 2)));
	    		  field.requestFocus(); 		 		
	    	}
		});
	      field.addActionListener(new ActionListener() {	// 물품의 수량을 입력하면 그에 해당하게 테이블의 수량을 변경하는 코드			
				@Override
				public void actionPerformed(ActionEvent e) {
				Value = Integer.parseInt(field.getText().trim());
				if(Value <= 0){
					JOptionPane.showMessageDialog(null, "물품이 없습니다.");
					total -= (Integer.parseInt(model.getValueAt(position, 2).toString()) * getmoney );
					field.setText("");
					field.requestFocus();
					model.removeRow(position);
					totalfield.setText(String.valueOf(total));
					cflag = false;
					main.FlagMethod(cflag);
					} else {
					cflag = true;
					field.requestFocus();
  				total += (getmoney * Value) - ((Integer.parseInt(model.getValueAt(position, 2).toString()) * getmoney ));
  				model.setValueAt(Value, position, 2);
  				totalfield.setText(String.valueOf(total));
					}
				}
			});		
	}
	public static void main(String[] args) {
		Cart frame = new Cart();
		Thread thread = new Thread(frame);
		thread.start();
	}
	public static void insert(String name, int val, int count) {	// 클릭된 메뉴를 받아서 테이블에 추가하는 메소드
		for(int i=model.getRowCount()-1 ; i>=0 ; i--) {
		if(model.getValueAt(i, 0).equals(name)){
			model.removeRow(i);
			}		
		}		
		total += val;
		String[] row = new String[3];
		row[0] = name;
		row[1] = String.valueOf(val);
		row[2] = String.valueOf(count);
		model.addRow(row);
		totalfield.setText(String.valueOf(total));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		switch (((JButton)obj).getName()) {
		case "카드":							// 카드 결제를 실행하는 코드
			if(table.getRowCount() <= 0) {
				break;
			} else{
			int result = JOptionPane.showConfirmDialog(null, "카드를 넣어주세요", "카드 결제창", JOptionPane.CANCEL_OPTION, 
					JOptionPane.PLAIN_MESSAGE, null);
			if(result == 0) {
				JOptionPane.showMessageDialog(null, "잠시만 기다려주세요");
				try {Thread.sleep(1000);} catch (InterruptedException e1) {e1.printStackTrace();}
				JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.  주문 번호 : " + order++);
				for(int i=model.getRowCount()-1 ; i>=0 ; i--) {
					model.removeRow(i);
					field.setText("");
					total = 0;
					totalfield.setText(String.valueOf(total));
					minus.setEnabled(false);
					field.setEnabled(false);
					plus.setEnabled(false);
					cflag = false;
					main.FlagMethod(cflag);
				}
				break;
			} else {
				JOptionPane.showMessageDialog(null, "결제가 취소 되었습니다.");
				break;
				}
			}
			
		case "마이너스":		// 테이블의 수량을 하나 줄이는 버튼 코드
			Value -= 1;
			if(Value <= 0){
				int result = JOptionPane.showConfirmDialog(null, "물품을 삭제하시겠습니까?", "확인 창", JOptionPane.YES_NO_OPTION, 
						JOptionPane.PLAIN_MESSAGE, null);
				if(result == 0) {
					model.removeRow(position);
					field.setText("");
					total -= getmoney;
					totalfield.setText(String.valueOf(total));
					cflag = false;
					main.FlagMethod(cflag);
					minus.setEnabled(false);
					field.setEnabled(false);
					plus.setEnabled(false);
				}
			} else {
				cflag = true;
				field.setText(String.valueOf(Value));
				total -= getmoney;			
				model.setValueAt(Value, position, 2); 						
				totalfield.setText(String.valueOf(total));
				}
			break;
			
		case "플러스":	// 테이블의 수량을 하나 늘리는 버튼 코드
			Value += 1;
				field.setText(String.valueOf(Value));
				total += getmoney;
				model.setValueAt(Value, position, 2);	 
				totalfield.setText(String.valueOf(total));
			break;
		}
	
		for(int i=0 ; i<calBtn.length-3 ; i++) {
			if(e.getSource() == calBtn[i]) {
				if(calField.getText().equals("0")) {
					calField.setText(i+1+"");
				} else {
					calField.setText(calField.getText() + (i+1));
					}
			}
		}
		if(e.getSource() == calBtn[10]) {
			if(calField.getText().equals("0")) {
				calField.setText("0");
			} else {
				calField.setText(calField.getText() + 0);
				}
			}
		if(e.getSource() == calBtn[9]) {
			String temp = "";
			temp = calField.getText();
			temp = temp.substring(0, temp.length()-1);
			calField.setText(temp);		// 계산기에 텍스트를 넣는 코드
			}
		
		if(table.getRowCount() > 0 ){		// 현금 결제를 실행하는 코드
			if(e.getSource() == calBtn[11] || e.getSource() == calField) {
				calField.getText();
				String value = calField.getText();
				if(Integer.parseInt(value) < total) {
					JOptionPane.showMessageDialog(null, "금액이 부족합니다.");
					calField.setText("");
					calField.requestFocus();
				} else if (Integer.parseInt(value) > total) {				
					JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.  거스름 돈 : " + (Integer.parseInt(value) - total)
							+ "주문 번호 : " + order++);
					for(int i=model.getRowCount()-1 ; i>=0 ; i--) {
						model.removeRow(i);					
						field.setText("");					
						total = 0;
						totalfield.setText(String.valueOf(total));
						calField.setText("0");
						
					}
					minus.setEnabled(false);
					field.setEnabled(false);
					plus.setEnabled(false);
					cflag = false;
					main.FlagMethod(cflag);
				} else {
				JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.  주문 번호 : " + order++);
				for(int i=model.getRowCount()-1 ; i>=0 ; i--) {
				model.removeRow(i);
				field.setText("");
				total = 0;
				totalfield.setText(String.valueOf(total));
				calField.setText("0");			
					}
				cflag = false;
				main.FlagMethod(cflag);
				minus.setEnabled(false);
				field.setEnabled(false);
				plus.setEnabled(false);
				}		
			}
		}
		
	}
	@Override
	public void run() {		// 현재 시간을 계속 표시하는 코드
		while(true){
			date1 = new Date();
			time.setText(sdf.format(date1));
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
	
}
