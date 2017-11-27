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
		setTitle("第一步：设置本地接受端口");
		setBounds(450,200,400,500);
		setLayout(null);
		init();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void init()
	{
		label = new JLabel("请输入本地要接受的端口:");
		label.setBounds(50,100,300,30);
		label.setFont(new Font("宋体",Font.BOLD,25));
		add(label);

		textField = new JTextField();
		textField.setBounds(120,200,150,40);
		textField.setFont(new Font("宋体",Font.BOLD,20));
		add(textField);

		button = new JButton("确定");
		button.setBounds(120,300,140,40);
		button.setFont(new Font("宋体",Font.BOLD,20));
		button.addActionListener(this);
		add(button);
	}

	public void actionPerformed(ActionEvent e){
		port = textField.getText();			//拿到输入的端口号
		MyFrame myFrame = new MyFrame(port);		//带端口号创建主界面Frame
		Thread readData = new Thread(myFrame);		//对主界面开启线程
		readData.start();							//开始接收数据
		this.dispose();							//退出初始化界面
	}
 }



