/*
���ƣ�	����ɼ���ViewComponents.java
���ܣ�	ViewComponents.java ��JMenu ������
	ʵ���� ItemListener�ӿ�
	ʵ�ֲ˵�(�쿴-���)�жԹ��������ṹ��ͼ���Ŷӽ�������������ĸ�ѡ��ɼ��Կ���
*/

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ViewComponents extends JMenu implements ItemListener
{
    	//�����˸�ѡ�����͵��ĸ���Ա���������������ṹ��ͼ���Ŷӽ������������
	JCheckBoxMenuItem viewToolBar;
	JCheckBoxMenuItem viewTreeViewer;
	JCheckBoxMenuItem viewCommunicateArea;
	JCheckBoxMenuItem viewOutputArea;
	
	//���캯��ΪJMenu�����ƵĹ��캯��
	ViewComponents()
	{
		super(Components.rb.getString("Component"));
	}
	
	//��ʼ������
	public void initialize()
	{
	    	//���ĸ���ѡ������ڴ�ռ䴴������ָ���ı���ѡ��״̬�ĸ�ѡ����
		viewToolBar = new JCheckBoxMenuItem(Components.rb.getString("ToolBar"), Components.ToolBarVisible);
		viewTreeViewer = new JCheckBoxMenuItem(Components.rb.getString("FileViewer"), Components.TreeViewerVisible);
		viewCommunicateArea = new JCheckBoxMenuItem(Components.rb.getString("CommunicateArea"), Components.CommunicateAreaVisible);
		viewOutputArea = new JCheckBoxMenuItem(Components.rb.getString("OutputArea"), Components.OutputAreaVisible);
		//����������Ϊ������
		viewToolBar.addItemListener(this);
		viewTreeViewer.addItemListener(this);
		viewCommunicateArea.addItemListener(this);
		viewOutputArea.addItemListener(this);
		//����˵���鿴-�������
		add(viewToolBar);
		add(viewTreeViewer);
		add(viewCommunicateArea);
		add(viewOutputArea);
	}
	
	//�Ƿ�ѡ���¼�����
	public void itemStateChanged(ItemEvent e)
	{
	    	/*���ݲ�ͬ���¼���Դ
	    	  ����Components���Ӧ�ļ�¼�齨�ɼ��Եı���
	    	  ���������Ӧ�����Ŀɼ��ԣ�ʵ�ֿɼ��򲻿ɼ�*/
		if(e.getSource() == viewToolBar)
		{
			Components.ToolBarVisible = viewToolBar.getState();
			Components.comp_ToolBar.setVisible(Components.ToolBarVisible);
		}
		if(e.getSource() == viewTreeViewer)
		{
			Components.comp_TreeViewer.setVisible(viewTreeViewer.getState());
			Components.TreeViewerVisible = viewTreeViewer.getState();
		}
		if(e.getSource() == viewCommunicateArea)
		{
			Components.comp_CommunicateArea.setVisible(viewCommunicateArea.getState());
			Components.CommunicateAreaVisible = viewCommunicateArea.getState();
		}
		if(e.getSource() == viewOutputArea)
		{
			//Components.comp_EditArea.OutputArea.setVisible(viewOutputArea.getState());
			Components.CommunicateAreaVisible = viewCommunicateArea.getState();
		}
	}
}