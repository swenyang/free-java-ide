/*
���ƣ�	������CommunicateArea.java
���ܣ�	CommunicateArea��JDialog������
	ʵ���� Runnable, ActionListener�ӿ�
	���Է��ͼ�ʱ��Ϣ����ʾ����ʱ��
	֧����ʱ����������ݡ����ڶ���Ч��������ID
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
	//�����˵��������������͡����ա���ݰ�ť����ID�ĳ�Ա����
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
	
	//���캯��Ϊ����Ĵ��б���������ߴ������ģʽ�Ի���
	CommunicateArea()
	{ 
		super(Components.comp_EditArea,Components.rb.getString("CommunicateArea"));
	}
	//��ʼ������
	public void initialize()
	{
		//���ɴ��з��͡����������������ID�Ĳ˵�����ӿ�ݼ�
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
		//���ĸ��˵�����ӱ���Ϊ������
		item_send.addActionListener(this);
		item_clear.addActionListener(this);
		item_shake.addActionListener(this);
		item_id.addActionListener(this);
		
		//���������������ɸġ�����ɫΪ�ס��ɻ���
		in_message.setEditable(false);
		in_message.setBackground(new Color(255,255,255));
		in_message.setLineWrap(true);
		
		//�����������20�С��ɻ���
		out_message.setColumns(20);
		out_message.setLineWrap(true);
		
		//����ݰ�ť��ӱ���Ϊ������
		b.addActionListener(this);
		
		//�������롢���ȥ��������������ʽΪ��Ҫʱ����ʾ
		in_scroll=new JScrollPane(in_message);
		out_scroll=new JScrollPane(out_message);
		in_scroll.setSize(new Dimension(getWidth(), 2));                    //���� JScrollPane ��20,��200
		out_scroll.setSize(new Dimension(getWidth(), 2));
		in_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		out_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		in_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		out_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//��������ΪBorderLayout����������������Ϳ�ݰ�ť�ֱ�λ�ڱ����С���
		setLayout(new BorderLayout());
		add(in_scroll, "North");
		add(out_scroll, "Center");
		add(b,"South");
		
		//����ȫ�־�̬�齨������λ����Ϣ���趨λ�á���С
		setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
		setSize(Components.rightOfCommunicateArea - Components.leftOfCommunicateArea, Components.bottomOfCommunicateArea - Components.topOfCommunicateArea);
		
		//����ȫ�־�̬�齨�����н������ɼ��Ա����趨�Ƿ�ɼ�
		setVisible(Components.CommunicateAreaVisible);
		validate();
		
		//�����̣߳��������ݰ�
		Thread thread=new Thread(this);
		thread.start();                   
	}
	
	//���ڶ������ܵ�ʵ��
	public void shake()
	{
		//(x,y)��ȡ����λ��
		int x=getLocation().x;
		int y=getLocation().y;
		//��λ����d��¼����λ����
		int d[]={0,0};
		for(int i=0; i<20; i++)//ѭ��20��
		{
			d[i%2]=i%4<2?0:4;
			setLocation(x+d[0],y+d[1]);//���´���λ��
			for(int j=0; j<10000000; j++){}//��ʱһ��ʱ��
		}
		setLocation(x,y);//�������趨�������λ��
	}
	
	//���ͽ������ݰ��¼�����
	public void actionPerformed(ActionEvent e)         
	{ 
		if(e.getSource()==b||e.getSource()==item_send)//������ͻ򰴿�ݼ�
		{
		    	//���ͻ�����buffer 
			byte buffer[]=out_message.getText().trim().getBytes();
			try{
				InetAddress address=InetAddress.getByName("127.0.0.1");
				DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length, address,888);
				DatagramSocket mail_data=new DatagramSocket();
				//�����������Ϣ�����ID��ʱ��
				Date nowTime=new Date();                           //��ʾʱ��
				SimpleDateFormat matter2=new SimpleDateFormat("HH:mm:ss ");
				String s=new String(matter2.format(nowTime).toString());
				in_message.append(id + ": "+s+out_message.getText().trim()+"\n");
				mail_data.send(data_pack);//�������ݰ�
				out_message.setText(null);//��������
			}
			catch(Exception e2){}  
		}
		else if(e.getSource()==item_clear)//��Ϣ���-��ˢ��
		{
			//������������������
			in_message.setText(null);
			out_message.setText(null);
		}
		else if(e.getSource()==item_shake)//���Ͷ��������Է�����
		{
			shake();//�¼��󣬵������е�shake����������ȡ���ڵ�ǰ���꣬ʵ�ֶ���Ч��
			byte buffer[]="/s".getBytes();//Ϊ���ڶ����趨�����ַ�����/s�������͵���һ̨����
			try{
			    	//�������������Ϣ����ʾ��������һ�����ڶ�����
				InetAddress address=InetAddress.getByName("127.0.0.1");
				DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length, address,888);
				DatagramSocket mail_data=new DatagramSocket();
				Date nowTime=new Date();                           //��ʾʱ��
				SimpleDateFormat matter2=new SimpleDateFormat("HH:mm:ss ");
				String s=new String(matter2.format(nowTime).toString());
				in_message.append(id + ": "+s+Components.rb.getString("sent\u0020a\u0020shake")+"\n");
				mail_data.send(data_pack);//�������ݰ�
				out_message.setText(null);//��������
			}
			catch(Exception e2){}  
		}
		else if(e.getSource() == item_id)//����ID-��showInputDialog����ȡ���趨��ID
		{
		    String name = JOptionPane.showInputDialog(this.getRootPane(), Components.rb.getString("Input\u0020your\u0020nick"), id);
		    if(name != null)//�ǿռ�����id
			id = name;
		}
	} 
	public void run()//�������ݰ�
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
			if(mail_data==null) break;//����Ϊ�����˳�
			else
               try{ 
					//��ʾ������Ϣ
					mail_data.receive(pack);
					int length=pack.getLength();					
					Date nowTime=new Date();
					SimpleDateFormat matter2=new SimpleDateFormat("HH:mm:ss ");
					String s=new String(matter2.format(nowTime).toString());
					String message=new String(pack.getData(),0,length);
                    if(pack.getData()[0]=='/' && pack.getData()[1]=='s')//�ж��յ��ǲ��������ַ�����/s��
					{
						shake();//�������ͬ������shake��������
						//�������������Ϣ����ʾ��������һ�����ڶ�����
						in_message.append("There: "+s+Components.rb.getString("sent\u0020a\u0020shake")+"\n");
					}
					else
						in_message.append("There: "+s+message+"\n");
				}
               catch(Exception e){}
         } 
   }
}