/*
名称：	工具条可见性ViewToolBarComponents.java
功能：	ViewToolBarComponents 是JMenu 的子类
	实现了 ItemListener接口
	实现菜单中对工具条十一个按钮的复选框可见性控制
*/

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ViewToolBarComponents extends JMenu implements ItemListener
{
    	//声明复选框类型的十一个成员变量view***，对应于ToolBar的十一个按钮
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
	
	//构造函数为JMenu含名称的构造函数
	ViewToolBarComponents()
	{
		super(Components.rb.getString("ToolBar"));
	}
	
	//初始化函数
	public void initialize()
	{
	    	//给十一个复选框类型的成员变量分配内存空间，创建带有指定文本和选择状态的复选框菜单项
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
		
		//将本类作为监听器
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
		
		//分组加入ViewToolBar中
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
	
	//是否选定事件处理
	public void itemStateChanged(ItemEvent e)
	{
	    	/*根据不同的事件来源
	    	  更新Components里对应的记录工具栏按钮可见性的变量
	    	  并更新comp_ToolBar对应项的可见性，实现可见或不可见*/
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