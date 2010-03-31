/*
���ƣ�	���� Main.java
���ܣ�	ֱ�ӵ��ó�ʼ������
	��ʼ�����������ɲ˵���comp_MenuBar
	��ӽṹ��ͼ���༭�����Ŷӽ�����������
	���ε�������ȫ�־�̬���comp_**�ĳ�ʼ������
	�γ�Free Java�������ļ��ɿ�����������
*/

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main
{
	public static void main(String args[])
	{
	    initialize();//���ó�ʼ������
	}
	
	static void initialize()
	{
	    	//���ɲ˵���comp_MenuBar
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

		//���ṹ��ͼcomp_TreeViewer���༭��comp_EditArea���Ŷӽ�����comp_CommunicateArea���λ�øı������
		Components.comp_TreeViewer.addComponentListener(Components.adjustListener);
		Components.comp_EditArea.addComponentListener(Components.adjustListener);
		Components.comp_CommunicateArea.addComponentListener(Components.adjustListener);
	
		/*���������ļ���Ϣcomp_Config��������comp_ToolBar���ṹ��ͼcomp_TreeViewer 
		  ������comp_CommunicateArea������ɼ���viewComponents���������ɼ���viewToolBarComponents
		  �༭��comp_EditArea�ĳ�ʼ������*/
		Components.comp_Config.initialize();
		Components.comp_ToolBar.initialize();
		Components.comp_TreeViewer.initialize();
		Components.comp_CommunicateArea.initialize();
		Components.viewComponents.initialize();
		Components.viewToolBarComponents.initialize();
		Components.comp_EditArea.initialize();
	}
}