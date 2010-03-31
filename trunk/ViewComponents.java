/*
名称：	组件可见性ViewComponents.java
功能：	ViewComponents.java 是JMenu 的子类
	实现了 ItemListener接口
	实现菜单(察看-组件)中对工具栏、结构视图、团队交流区、输出区的复选框可见性控制
*/

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ViewComponents extends JMenu implements ItemListener
{
    	//声明了复选框类型的四个成员变量：工具栏、结构视图、团队交流区、输出区
	JCheckBoxMenuItem viewToolBar;
	JCheckBoxMenuItem viewTreeViewer;
	JCheckBoxMenuItem viewCommunicateArea;
	JCheckBoxMenuItem viewOutputArea;
	
	//构造函数为JMenu含名称的构造函数
	ViewComponents()
	{
		super(Components.rb.getString("Component"));
	}
	
	//初始化函数
	public void initialize()
	{
	    	//给四个复选框分配内存空间创建带有指定文本和选择状态的复选框项
		viewToolBar = new JCheckBoxMenuItem(Components.rb.getString("ToolBar"), Components.ToolBarVisible);
		viewTreeViewer = new JCheckBoxMenuItem(Components.rb.getString("FileViewer"), Components.TreeViewerVisible);
		viewCommunicateArea = new JCheckBoxMenuItem(Components.rb.getString("CommunicateArea"), Components.CommunicateAreaVisible);
		viewOutputArea = new JCheckBoxMenuItem(Components.rb.getString("OutputArea"), Components.OutputAreaVisible);
		//并将本类作为监听器
		viewToolBar.addItemListener(this);
		viewTreeViewer.addItemListener(this);
		viewCommunicateArea.addItemListener(this);
		viewOutputArea.addItemListener(this);
		//加入菜单项（查看-组件）中
		add(viewToolBar);
		add(viewTreeViewer);
		add(viewCommunicateArea);
		add(viewOutputArea);
	}
	
	//是否选定事件处理
	public void itemStateChanged(ItemEvent e)
	{
	    	/*根据不同的事件来源
	    	  更新Components里对应的记录组建可见性的变量
	    	  并更新相对应组件里的可见性，实现可见或不可见*/
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