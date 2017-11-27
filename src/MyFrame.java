import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class MyFrame extends JFrame implements ActionListener,KeyListener,Runnable{
	JTextPane textShow;
	JTextArea textInput;
	JLabel label1,label2,label3,label4,label5,label6;
	JTextField textField1,textField2,textField3,textField4;
	JButton button;
	JScrollPane scrollInput,scrollShow;
	JComboBox jComboBox,jComboBoxCh;
	SimpleAttributeSet sendSet,receiveSet;
	String[] face = {"(。ì _ í。)","(^ω^)ノ","(>^ω^<)","('^Д.)」","(*-︶-*)","(●--●)","(╯﹏╰）","(^ω^)"};
	String[] Ch = {"12","15","17","20","24","30","40","60"};
	JPanel imagePanel;
	ImageIcon background;
	public void setBackgunrod(){
		background = new ImageIcon("background.jpg");		// 背景图片
		JLabel label = new JLabel(background);							//把背景图片显示在一个标签里面
		label.setBounds(0,0,750,650);				//大小等于窗口大小
		imagePanel = (JPanel)this.getContentPane();					// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel.setOpaque(false);									//设置内容窗格透明
		imagePanel.setLayout(null);									//设置布局
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));		  // 把背景图片添加到窗格的最底层作为背景
}
	public MyFrame(String port){
		setTitle("Talk what you want to talk to me");
		setBackgunrod();
		setBounds(450,100,750,650);
		setLayout(null);
		init(port);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void init(String port){
		textShow = new JTextPane();
		textShow.setBounds(10,10,550,420);
		add(textShow);																				//将文本框应用于滚动框
		scrollShow = new JScrollPane(textShow);
		add(scrollShow);
		scrollShow.setBounds(10,10,550,420);
		scrollShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);			//总是显示垂直滚动框

		label5 = new JLabel("表情");
		label5.setBounds(10,435,50,20);
		label5.setFont(new Font("宋体",Font.BOLD,15));
		add(label5);

		jComboBox = new JComboBox(face);
		jComboBox.setBounds(50,435,80,20);
		jComboBox.addActionListener(this);
		add(jComboBox);

		label6 = new JLabel("字体");
		label6.setBounds(150,435,50,20);
		label6.setFont(new Font("宋体",Font.BOLD,15));
		add(label6);

		jComboBoxCh = new JComboBox(Ch);
		jComboBoxCh.setBounds(190,435,80,20);
		jComboBoxCh.addActionListener(this);
		jComboBoxCh.setEditable(true);										//可自己编辑
		add(jComboBoxCh);

		textInput = new JTextArea();
		textInput.setBounds(10,460,550,100);
		textInput.addKeyListener(this);
		add(textInput);
		textInput.setLineWrap(true);
		scrollInput = new JScrollPane(textInput);												//将文本框应用于滚动框
		add(scrollInput);
		scrollInput.setBounds(10,460,550,100);
		scrollInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		//总是显示垂直滚动框


		
		label1 = new JLabel("昵称:");
		label1.setBounds(580,30,140,30);
		label1.setFont(new Font("宋体",Font.BOLD,25));
		add(label1);
		
		textField1 = new JTextField();
		textField1.setBounds(580,80,140,40);
		textField1.setFont(new Font("宋体",Font.BOLD,20));
		add(textField1);
		
		label2 = new JLabel("目标IP:");
		label2.setBounds(580,150,140,30);
		label2.setFont(new Font("宋体",Font.BOLD,25));
		add(label2);
		
		textField2 = new JTextField();
		textField2.setBounds(580,200,140,40);
		textField2.setFont(new Font("宋体",Font.BOLD,20));
		add(textField2);
		
		label3 = new JLabel("目标端口:");
		label3.setBounds(580,270,140,30);
		label3.setFont(new Font("宋体",Font.BOLD,25));
		add(label3);
		
		textField3 = new JTextField();
		textField3.setBounds(580,320,140,40);
		textField3.setFont(new Font("宋体",Font.BOLD,20));
		add(textField3);

		label4 = new JLabel("接收端口:");
		label4.setBounds(580,390,140,30);
		label4.setFont(new Font("宋体",Font.BOLD,25));
		add(label4);

		textField4 = new JTextField(port);
		textField4.setBounds(580,440,140,40);
		textField4.setFont(new Font("宋体",Font.BOLD,20));
		textField4.setEditable(false);
		add(textField4);
		
		button = new JButton("发送");
		button.setBounds(580,500,140,40);
		button.setFont(new Font("宋体",Font.BOLD,20));
		button.addActionListener(this);
		add(button);
	}

	public void sendMessage(){
		if(!textInput.getText().equals("")){
			String meText = textField1.getText() + "\n" + new Date() + "\n" + textInput.getText() + "\n";	//姓名+时间+输入

			sendSet = new SimpleAttributeSet();
			StyleConstants.setAlignment(sendSet, StyleConstants.ALIGN_RIGHT);				//设置为右侧
			StyleConstants.setFontSize(sendSet, Integer.parseInt(jComboBoxCh.getSelectedItem().toString()));		//设置字体
			StyledDocument mydoc = textShow.getStyledDocument();													//设置格式
			mydoc.setParagraphAttributes(mydoc.getLength(),  meText.length(),sendSet,false);				//设置段落输入
			try{
				mydoc.insertString(mydoc.getLength(),meText,sendSet);												//插入要输入的文字
			}catch (Exception e){

			}
			byte data[] = (textField1.getText() + "\n" + new Date() + "\n" + textInput.getText() + "\n").getBytes();
			try{
				String Ip = textField2.getText();
				InetAddress address = InetAddress.getByName(Ip);
				String port = textField3.getText();
				DatagramPacket sendPacket = new DatagramPacket(data,data.length,address, Integer.parseInt(port));
				DatagramSocket post = new DatagramSocket();
				post.send(sendPacket);
				textInput.setText("");
			}catch (Exception e1){

			}
		}
	}



	public void run(){
		byte data[] = new byte[1024];
		String receivePort = textField4.getText();
		try{
			DatagramSocket recv = new DatagramSocket(Integer.parseInt(receivePort));
			DatagramPacket pack = new DatagramPacket(data,data.length);

			while (true){
				recv.receive(pack);
				String s = new String(pack.getData(),0,pack.getLength());

				receiveSet = new SimpleAttributeSet();
				StyleConstants.setAlignment(receiveSet, StyleConstants.ALIGN_LEFT);										//设置为左侧
				StyleConstants.setFontSize(receiveSet, Integer.parseInt(jComboBoxCh.getSelectedItem().toString()));		//设置字体
				StyledDocument mydoc = textShow.getStyledDocument();														//设置格式
				mydoc.setParagraphAttributes(mydoc.getLength(),s.length(),receiveSet,false);						//设置段落输入
				try{
					mydoc.insertString(mydoc.getLength(),s,receiveSet);													//插入要输入的文字
				}catch (Exception e){

				}
				textShow.setSelectionStart(textShow.getText().length());
				JScrollBar vertical = scrollShow.getVerticalScrollBar();													//始终将滚动框在最下方
				vertical.setValue( vertical.getMaximum());
			}
		}catch (Exception e1){

		}
	}


	public void actionPerformed(ActionEvent e){
		if(e.getSource() == button) {
			sendMessage();
		}
		if(e.getSource() == jComboBox){
			textInput.setText(textInput.getText() + jComboBox.getSelectedItem().toString());			//响应下拉框，添加颜文字
		}
	}


	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			sendMessage();
		}
	}

	public void keyReleased(KeyEvent e) {

	}


}
