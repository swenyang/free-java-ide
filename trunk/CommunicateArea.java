/*
名称：	交流区CommunicateArea.java
功能：	CommunicateArea是JDialog的子类
	实现了 Runnable, ActionListener接口
	可以发送即时信息、显示发送时间
	支持随时清除窗口内容、窗口抖动效果、设置ID
*/

import java.awt.BorderLayout;
import java.util.Date;
import javax.swing.text.JTextComponent.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class CommunicateArea extends JDialog implements Runnable, ActionListener
{  
	//声明菜单、滚动条、发送、接收、快捷按钮设置ID的成员变量
	JMenuBar menubar;
	JMenu menu;
	JMenuItem item_send;
	JMenuItem item_clear;
	JMenuItem item_shake;
	JMenuItem item_id;
	JScrollPane in_scroll=null;
	JScrollPane out_scroll=null;
	JTextArea in_message=new JTextArea(6, 8);
	JTextArea out_message=new JTextArea(2, 8);
	Button b=new Button("Send(ctrl+s)");
	private String id = "Here";
	
	//构造函数为父类的带有标题和所有者窗体的无模式对话框
	CommunicateArea()
	{ 
		super(Components.comp_EditArea,Components.rb.getString("CommunicateArea"));
	}
	//初始化函数
	public void initialize()
	{
		//生成带有发送、清除、抖动、设置ID的菜单并添加快捷键
		menubar=new JMenuBar();
		menu=new JMenu(Components.rb.getString("Menu"));
		item_send=new JMenuItem(Components.rb.getString("Send"));
		item_clear=new JMenuItem(Components.rb.getString("Clear"));
		item_shake=new JMenuItem(Components.rb.getString("Shake"));
		item_id = new JMenuItem(Components.rb.getString("Set\u0020ID"));
		item_send.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
		item_clear.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
		item_shake.setAccelerator(KeyStroke.getKeyStroke('K', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
		menu.add(item_send);
		menu.add(item_clear);
		menu.add(item_shake);
		menu.add(item_id);
		menubar.add(menu);
		setJMenuBar(menubar);
		//给四个菜单项添加本类为监听器
		item_send.addActionListener(this);
		item_clear.addActionListener(this);
		item_shake.addActionListener(this);
		item_id.addActionListener(this);
		
		//设置输入区：不可改、背景色为白、可换行
		in_message.setEditable(false);
		in_message.setBackground(new Color(255,255,255));
		in_message.setLineWrap(true);
		
		//设置输出区：20列、可换行
		out_message.setColumns(20);
		out_message.setLineWrap(true);
		
		//给快捷按钮添加本类为监听器
		b.addActionListener(this);
		
		//设置输入、输出去横竖滚动条，格式为需要时才显示
		in_scroll=new JScrollPane(in_message);
		out_scroll=new JScrollPane(out_message);
		in_scroll.setSize(new Dimension(getWidth(), 2));                    //设置 JScrollPane 宽20,高200
		out_scroll.setSize(new Dimension(getWidth(), 2));
		in_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		out_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		in_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		out_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//布局类型为BorderLayout：输入区、输出区和快捷按钮分别位于北、中、南
		setLayout(new BorderLayout());
		add(in_scroll, "North");
		add(out_scroll, "Center");
		add(b,"South");
		
		//根据全局静态组建集合中位置信息，设定位置、大小
		setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
		setSize(Components.rightOfCommunicateArea - Components.leftOfCommunicateArea, Components.bottomOfCommunicateArea - Components.topOfCommunicateArea);
		
		//根据全局静态组建集合中交流区可见性变量设定是否可见
		setVisible(Components.CommunicateAreaVisible);
		validate();
		
		//启动线程，接收数据包
		Thread thread=new Thread(this);
		thread.start();                   
	}
	
	//窗口抖动功能的实现
	public void shake()
	{
		//(x,y)获取窗口位置
		int x=getLocation().x;
		int y=getLocation().y;
		//二位数组d记录抖动位移量
		int d[]={0,0};
		for(int i=0; i<20; i++)//循环20次
		{
			d[i%2]=i%4<2?0:4;
			setLocation(x+d[0],y+d[1]);//更新窗口位置
			for(int j=0; j<10000000; j++){}//延时一段时间
		}
		setLocation(x,y);//将窗口设定回最初的位置
	}
	
	//发送接受数据包事件处理
	public void actionPerformed(ActionEvent e)         
	{ 
		if(e.getSource()==b||e.getSource()==item_send)//点击发送或按快捷键
		{
		    	//发送缓冲区buffer 
			byte buffer[]=out_message.getText().trim().getBytes();
			try{
				InetAddress address=InetAddress.getByName("127.0.0.1");
				DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length, address,888);
				DatagramSocket mail_data=new DatagramSocket();
				//往待输出的信息中添加ID及时间
				Date nowTime=new Date();                           //显示时间
				SimpleDateFormat matter2=new SimpleDateFormat("HH:mm:ss ");
				String s=new String(matter2.format(nowTime).toString());
				in_message.append(id + ": "+s+out_message.getText().trim()+"\n");
				mail_data.send(data_pack);//发送数据包
				out_message.setText(null);//输出区清空
			}
			catch(Exception e2){}  
		}
		else if(e.getSource()==item_clear)//信息清除-即刷屏
		{
			//将输入区和输出区清空
			in_message.setText(null);
			out_message.setText(null);
		}
		else if(e.getSource()==item_shake)//发送抖动，给对方提醒
		{
			shake();//事件后，调用类中的shake（）函数获取窗口当前坐标，实现抖动效果
			byte buffer[]="/s".getBytes();//为窗口抖动设定特殊字符串“/s”，发送到另一台机器
			try{
			    	//在输入区添加信息，显示“发送了一个窗口抖动”
				InetAddress address=InetAddress.getByName("127.0.0.1");
				DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length, address,888);
				DatagramSocket mail_data=new DatagramSocket();
				Date nowTime=new Date();                           //显示时间
				SimpleDateFormat matter2=new SimpleDateFormat("HH:mm:ss ");
				String s=new String(matter2.format(nowTime).toString());
				in_message.append(id + ": "+s+Components.rb.getString("sent\u0020a\u0020shake")+"\n");
				mail_data.send(data_pack);//发送数据包
				out_message.setText(null);//清空输出区
			}
			catch(Exception e2){}  
		}
		else if(e.getSource() == item_id)//设置ID-用showInputDialog来获取新设定的ID
		{
		    String name = JOptionPane.showInputDialog(this.getRootPane(), Components.rb.getString("Input\u0020your\u0020nick"), id);
		    if(name != null)//非空即更新id
			id = name;
		}
	} 
	public void run()//接收数据包
	{ 
		DatagramPacket pack=null;
		DatagramSocket mail_data=null;
		byte data[]=new byte[8192];
		try{
			pack=new DatagramPacket(data,data.length);
            mail_data=new DatagramSocket(666);
        }
		catch(Exception e){} 
		while(true)   
		{  
			if(mail_data==null) break;//接收为空则退出
			else
               try{ 
					//显示接收信息
					mail_data.receive(pack);
					int length=pack.getLength();					
					Date nowTime=new Date();
					SimpleDateFormat matter2=new SimpleDateFormat("HH:mm:ss ");
					String s=new String(matter2.format(nowTime).toString());
					String message=new String(pack.getData(),0,length);
                    if(pack.getData()[0]=='/' && pack.getData()[1]=='s')//判断收到是不是特殊字符串“/s”
					{
						shake();//如果是则同样调用shake（）函数
						//在输入区添加信息，显示“发送了一个窗口抖动”
						in_message.append("There: "+s+Components.rb.getString("sent\u0020a\u0020shake")+"\n");
					}
					else
						in_message.append("There: "+s+message+"\n");
				}
               catch(Exception e){}
         } 
   }
}