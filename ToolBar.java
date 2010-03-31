/*
名称：	工具条ToolBar.java
功能：	ToolBar是组件JToolBar的子类
	定义了新建、打开、保存、保存全部、编译、运行、撤销、重做、复制、粘贴十一个按钮
	从components静态全局组件集合里Action部分获取按钮的属性
	从components资源包rb中获取提示信息，并设置图标
	最后分为四组加入工具条中。
*/

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Insets;

public class ToolBar extends JToolBar
{
	//声明JButton类型成员变量
	JButton createNewFileButton;
	JButton openFileButton;
	JButton saveFileButton;
	JButton saveAllFileButton;
	JButton compileButton;
	JButton runButton;
	JButton undoButton;
	JButton redoButton;
	JButton copyButton;
	JButton pasteButton;
	JButton cutButton;
		
	//构造函数为空
	ToolBar(){}

	//初始化函数
	public void initialize()
	{
		//创建11个按钮，其属性从components里的 Action部分获取
		createNewFileButton = new JButton(Components.createNewFileAction);
		openFileButton = new JButton(Components.openFileAction);
		saveFileButton = new JButton(Components.saveFileAction);
		saveAllFileButton = new JButton(Components.saveAllFileAction);
		compileButton = new JButton(Components.compileAction);
		runButton = new JButton(Components.runAction);
		undoButton = new JButton(Components.undoAction);
		redoButton = new JButton(Components.redoAction);
		copyButton = new JButton(Components.copyAction);
		pasteButton = new JButton(Components.pasteAction);
		cutButton = new JButton(Components.cutAction);

		//按钮的文本为空
		createNewFileButton.setText(null);
		createNewFileButton.setText(null);
		openFileButton.setText(null);
		saveFileButton.setText(null);
		saveAllFileButton.setText(null);
		compileButton.setText(null);
		runButton.setText(null);
		undoButton.setText(null);
		redoButton.setText(null);
		copyButton.setText(null);
		pasteButton.setText(null);
		cutButton.setText(null);
		
		//通过images文件夹里的图片，设置按钮的图标
		createNewFileButton.setIcon(new ImageIcon("images/createNewFileButton.gif"));
		openFileButton.setIcon(new ImageIcon("images/openFileButton.gif"));
		saveFileButton.setIcon(new ImageIcon("images/saveFileButton.gif"));
		saveAllFileButton.setIcon(new ImageIcon("images/saveAllFileButton.gif"));
		compileButton.setIcon(new ImageIcon("images/compileButton.gif"));
		runButton.setIcon(new ImageIcon("images/runButton.gif"));
		undoButton.setIcon(new ImageIcon("images/undoButton.gif"));
		redoButton.setIcon(new ImageIcon("images/redoButton.gif"));
		copyButton.setIcon(new ImageIcon("images/copyButton.gif"));
		pasteButton.setIcon(new ImageIcon("images/pasteButton.gif"));
		cutButton.setIcon(new ImageIcon("images/cutButton.gif"));
		
		//通过全局资源包rb，设置按钮的提示信息
		createNewFileButton.setToolTipText(Components.rb.getString("Create\u0020new\u0020file"));
		openFileButton.setToolTipText(Components.rb.getString("Open\u0020files"));
		saveFileButton.setToolTipText(Components.rb.getString("Save\u0020a\u0020file"));
		saveAllFileButton.setToolTipText(Components.rb.getString("Save\u0020all\u0020files"));
		compileButton.setToolTipText(Components.rb.getString("Compile\u0020file"));
		runButton.setToolTipText(Components.rb.getString("Run\u0020file"));
		undoButton.setToolTipText(Components.rb.getString("Undo"));
		redoButton.setToolTipText(Components.rb.getString("Redo"));
		copyButton.setToolTipText(Components.rb.getString("Copy"));
		pasteButton.setToolTipText(Components.rb.getString("Paste"));
		cutButton.setToolTipText(Components.rb.getString("Cut"));
		
		//设定按钮的边距
		Insets _0 = new Insets(0,0,0,0);
		createNewFileButton.setMargin(_0);
		openFileButton.setMargin(_0);
		saveFileButton.setMargin(_0);
		saveAllFileButton.setMargin(_0);
		compileButton.setMargin(_0);
		runButton.setMargin(_0);
		undoButton.setMargin(_0);
		redoButton.setMargin(_0);
		copyButton.setMargin(_0);
		pasteButton.setMargin(_0);
		cutButton.setMargin(_0);
		
		//据全局静态组建集合Components记录各项按钮的变量，设定按钮可见性
		createNewFileButton.setVisible(Components.VisibleCreateNew);
		openFileButton.setVisible(Components.VisibleOpenFile);
		saveFileButton.setVisible(Components.VisibleSaveFile);
		saveAllFileButton.setVisible(Components.VisibleSaveAll);
		compileButton.setVisible(Components.VisibleCompile);
		runButton.setVisible(Components.VisibleRun);
		undoButton.setVisible(Components.VisibleUndo);
		redoButton.setVisible(Components.VisibleRedo);
		copyButton.setVisible(Components.VisibleCopy);
		pasteButton.setVisible(Components.VisiblePaste);
		cutButton.setVisible(Components.VisibleCut);
		
		//分为四组加入工具条
		add(createNewFileButton);
		add(openFileButton);
		addSeparator();
		add(saveFileButton);
		add(saveAllFileButton);
		addSeparator();
		add(copyButton);
		add(pasteButton);
		add(cutButton);
		addSeparator();
		add(compileButton);
		add(runButton);
		addSeparator();
		add(undoButton);
		add(redoButton);
		
		//据全局静态组建集合Components记录工具条可见性的变量，设定Components可见性
		setVisible(Components.ToolBarVisible);
	}
}