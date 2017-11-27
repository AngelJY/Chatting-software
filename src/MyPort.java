import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

 public class MyPort extends JFrame implements ActionListener{
	JLabel label;
	JTextField textField;
	JButton button;
	String port;
//	 int flag = 0;
	public MyPort(){
		setTitle("��һ�������ñ��ؽ��ܶ˿�");
		setBounds(450,200,400,500);
		setLayout(null);
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void init()
	{
		label = new JLabel("�����뱾��Ҫ���ܵĶ˿�:");
		label.setBounds(50,100,300,30);
		label.setFont(new Font("����",Font.BOLD,25));
		add(label);

		textField = new JTextField();
		textField.setBounds(120,200,150,40);
		textField.setFont(new Font("����",Font.BOLD,20));
		add(textField);

		button = new JButton("ȷ��");
		button.setBounds(120,300,140,40);
		button.setFont(new Font("����",Font.BOLD,20));
		button.addActionListener(this);
		add(button);
	}

	public void actionPerformed(ActionEvent e){
		port = textField.getText();			//�õ�����Ķ˿ں�
		MyFrame myFrame = new MyFrame(port);		//���˿ںŴ���������Frame
		Thread readData = new Thread(myFrame);		//�������濪���߳�
		readData.start();							//��ʼ��������
		this.dispose();							//�˳���ʼ������
	}
 }



