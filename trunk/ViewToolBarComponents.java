/*
���ƣ�	�������ɼ���ViewToolBarComponents.java
���ܣ�	ViewToolBarComponents ��JMenu ������
	ʵ���� ItemListener�ӿ�
	ʵ�ֲ˵��жԹ�����ʮһ����ť�ĸ�ѡ��ɼ��Կ���
*/

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ViewToolBarComponents extends JMenu implements ItemListener
{
    	//������ѡ�����͵�ʮһ����Ա����view***����Ӧ��ToolBar��ʮһ����ť
	JCheckBoxMenuItem viewCreateNew;
	JCheckBoxMenuItem viewOpenFile;
	JCheckBoxMenuItem viewSaveFile;
	JCheckBoxMenuItem viewSaveAll;
	JCheckBoxMenuItem viewCopy;
	JCheckBoxMenuItem viewPaste;
	JCheckBoxMenuItem viewCut;
	JCheckBoxMenuItem viewCompile;
	JCheckBoxMenuItem viewRun;
	JCheckBoxMenuItem viewRedo;
	JCheckBoxMenuItem viewUndo;
	
	//���캯��ΪJMenu�����ƵĹ��캯��
	ViewToolBarComponents()
	{
		super(Components.rb.getString("ToolBar"));
	}
	
	//��ʼ������
	public void initialize()
	{
	    	//��ʮһ����ѡ�����͵ĳ�Ա���������ڴ�ռ䣬��������ָ���ı���ѡ��״̬�ĸ�ѡ��˵���
		viewCreateNew = new JCheckBoxMenuItem(Components.rb.getString("New"), Components.VisibleCreateNew);
		viewOpenFile = new JCheckBoxMenuItem(Components.rb.getString("Open"), Components.VisibleOpenFile);
		viewSaveFile = new JCheckBoxMenuItem(Components.rb.getString("Save"), Components.VisibleSaveFile);
		viewSaveAll = new JCheckBoxMenuItem(Components.rb.getString("SaveAll"), Components.VisibleSaveAll);
		viewCopy = new JCheckBoxMenuItem(Components.rb.getString("Copy"), Components.VisibleCopy);
		viewPaste = new JCheckBoxMenuItem(Components.rb.getString("Paste"), Components.VisiblePaste);
		viewCut = new JCheckBoxMenuItem(Components.rb.getString("Cut"), Components.VisibleCut);
		viewCompile = new JCheckBoxMenuItem(Components.rb.getString("Compile"), Components.VisibleCompile);
		viewRun = new JCheckBoxMenuItem(Components.rb.getString("Run"), Components.VisibleRun);
		viewRedo = new JCheckBoxMenuItem(Components.rb.getString("Redo"), Components.VisibleRedo);
		viewUndo = new JCheckBoxMenuItem(Components.rb.getString("Undo"), Components.VisibleUndo);
		
		//��������Ϊ������
		viewCreateNew.addItemListener(this);
		viewOpenFile.addItemListener(this);
		viewSaveFile.addItemListener(this);
		viewSaveAll.addItemListener(this);
		viewCopy.addItemListener(this);
		viewPaste.addItemListener(this);
		viewCut.addItemListener(this);
		viewCompile.addItemListener(this);
		viewRun.addItemListener(this);
		viewRedo.addItemListener(this);
		viewUndo.addItemListener(this);
		
		//�������ViewToolBar��
		add(viewCreateNew);
		add(viewOpenFile);
		addSeparator();
		add(viewSaveFile);
		add(viewSaveAll);
		addSeparator();
		add(viewCopy);
		add(viewPaste);
		add(viewCut);
		addSeparator();
		add(viewCompile);
		add(viewRun);
		addSeparator();
		add(viewUndo);
		add(viewRedo);
	}
	
	//�Ƿ�ѡ���¼�����
	public void itemStateChanged(ItemEvent e)
	{
	    	/*���ݲ�ͬ���¼���Դ
	    	  ����Components���Ӧ�ļ�¼��������ť�ɼ��Եı���
	    	  ������comp_ToolBar��Ӧ��Ŀɼ��ԣ�ʵ�ֿɼ��򲻿ɼ�*/
		if(e.getSource() == viewCreateNew)
		{
			Components.VisibleCreateNew = viewCreateNew.getState();
			Components.comp_ToolBar.getComponentAtIndex(0).setVisible(Components.VisibleCreateNew);
		}
		if(e.getSource() == viewOpenFile)
		{
			Components.VisibleOpenFile = viewOpenFile.getState();
			Components.comp_ToolBar.getComponentAtIndex(1).setVisible(Components.VisibleOpenFile);
		}
		if(e.getSource() == viewSaveFile)
		{
			Components.VisibleSaveFile = viewSaveFile.getState();
			Components.comp_ToolBar.getComponentAtIndex(3).setVisible(Components.VisibleSaveFile);
		}
		if(e.getSource() == viewSaveAll)
		{
			Components.VisibleSaveAll = viewSaveAll.getState();
			Components.comp_ToolBar.getComponentAtIndex(4).setVisible(Components.VisibleSaveAll);
		}
		if(e.getSource() == viewCopy)
		{
			Components.VisibleCopy = viewCopy.getState();
			Components.comp_ToolBar.getComponentAtIndex(6).setVisible(Components.VisibleCopy);
		}
		if(e.getSource() == viewPaste)
		{
			Components.VisiblePaste = viewPaste.getState();
			Components.comp_ToolBar.getComponentAtIndex(7).setVisible(Components.VisiblePaste);
		}
		if(e.getSource() == viewCut)
		{
			Components.VisibleCut = viewCut.getState();
			Components.comp_ToolBar.getComponentAtIndex(8).setVisible(Components.VisibleCut);
		}
		if(e.getSource() == viewCompile)
		{
			Components.VisibleCompile = viewCompile.getState();
			Components.comp_ToolBar.getComponentAtIndex(10).setVisible(Components.VisibleCompile);
		}
		if(e.getSource() == viewRun)
		{
			Components.VisibleRun = viewRun.getState();
			Components.comp_ToolBar.getComponentAtIndex(11).setVisible(Components.VisibleRun);
		}
		if(e.getSource() == viewRedo)
		{
			Components.VisibleRedo = viewRedo.getState();
			Components.comp_ToolBar.getComponentAtIndex(14).setVisible(Components.VisibleRedo);
		}
		if(e.getSource() == viewUndo)
		{
			Components.VisibleUndo = viewUndo.getState();
			Components.comp_ToolBar.getComponentAtIndex(13).setVisible(Components.VisibleUndo);
		}
	}
}