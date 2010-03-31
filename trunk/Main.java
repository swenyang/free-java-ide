/*
名称：	主类 Main.java
功能：	直接调用初始化函数
	初始化函数中生成菜单条comp_MenuBar
	添加结构视图、编辑区、团队交流区监听器
	依次调用所有全局静态组件comp_**的初始化函数
	形成Free Java的完整的集成开发环境界面
*/

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main
{
	public static void main(String args[])
	{
	    initialize();//调用初始化函数
	}
	
	static void initialize()
	{
	    	//生成菜单条comp_MenuBar
	    	Components.fileMenu.add(Components.createNewFileAction);
		Components.fileMenu.add(Components.openFileAction);
		Components.fileMenu.addSeparator();
		Components.fileMenu.add(Components.saveFileAction);
		Components.fileMenu.add(Components.saveAllFileAction);
		Components.fileMenu.addSeparator();
		Components.fileMenu.add(Components.exitAction);
	
		Components.editMenu.add(Components.undoAction);
		Components.editMenu.add(Components.redoAction);
		Components.editMenu.addSeparator();
		Components.editMenu.add(Components.copyAction);
		Components.editMenu.add(Components.pasteAction);
		Components.editMenu.add(Components.cutAction);

		Components.viewMenu.add(Components.viewComponents);
		Components.viewMenu.add(Components.viewToolBarComponents);
	
		Components.buildMenu.add(Components.compileAction);
		Components.buildMenu.add(Components.runAction);
	
		Components.toolMenu.add(Components.statisticsAction);
		Components.toolMenu.add(Components.optionAction);
		Components.toolMenu.add(Components.languageAction);
		Components.toolMenu.add(Components.fontAction);
	
		Components.helpMenu.add(Components.helpAction);
		Components.helpMenu.add(Components.aboutAction);
	
		Components.comp_MenuBar.add(Components.fileMenu);
		Components.comp_MenuBar.add(Components.editMenu);
		Components.comp_MenuBar.add(Components.viewMenu);
		Components.comp_MenuBar.add(Components.buildMenu);
		Components.comp_MenuBar.add(Components.toolMenu);
		Components.comp_MenuBar.add(Components.helpMenu);

		//给结构视图comp_TreeViewer、编辑区comp_EditArea、团队交流区comp_CommunicateArea添加位置改变监听器
		Components.comp_TreeViewer.addComponentListener(Components.adjustListener);
		Components.comp_EditArea.addComponentListener(Components.adjustListener);
		Components.comp_CommunicateArea.addComponentListener(Components.adjustListener);
	
		/*调用配置文件信息comp_Config、工具条comp_ToolBar、结构视图comp_TreeViewer 
		  交流区comp_CommunicateArea、组件可见性viewComponents、工具条可见性viewToolBarComponents
		  编辑区comp_EditArea的初始化函数*/
		Components.comp_Config.initialize();
		Components.comp_ToolBar.initialize();
		Components.comp_TreeViewer.initialize();
		Components.comp_CommunicateArea.initialize();
		Components.viewComponents.initialize();
		Components.viewToolBarComponents.initialize();
		Components.comp_EditArea.initialize();
	}
}