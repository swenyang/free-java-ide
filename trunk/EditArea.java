import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cdsc.eje.entities.CompilableFile;
import com.cdsc.eje.entities.JavaFile;
import com.cdsc.eje.entities.ProjectFile;
import com.cdsc.eje.gui.options.EJEOptionsDialog;
import com.cdsc.eje.gui.options.EditorOptionsPanel;
import com.cdsc.eje.gui.options.JavaOptionsPanel;
import com.cdsc.eje.gui.search.ReplaceDialog;
import com.cdsc.eje.gui.search.SearchDialog;
import com.cdsc.eje.gui.search.SearchFactory;
import com.cdsc.eje.gui.utilities.JavaFileView;
import com.cdsc.eje.gui.utilities.JavaFilter;
import com.cdsc.eje.jdk.Compiler;
import com.cdsc.eje.jdk.JDKApplication;
import com.cdsc.eje.jdk.JVM;
import com.cdsc.eje.jdk.utilities.FileValidator;
import com.cdsc.eje.gui.*;

/*
编辑区类EditArea.java
基本功能：	是程序中最大的部分
			集合了菜单栏、工具栏、标签页、输出区四大部分
			实现简单的文字编辑功能：复制、剪切、粘贴、撤销、重做
			实现简单的代码功能：关键字与注释彩色显示、编译、运行
*/

public class EditArea extends JFrame
{
	//标签页，用于显示各个文件
	JTabbedPane m_TabbedPane;	
	//输出区，显示编译、运行时的输出信息
	public OutputArea m_OutputArea= new OutputArea();	
	//内嵌EJE对象，以获取其标签页
	EJE m_eje;	

	//构造函数，初始化、组装部分组件
	EditArea()
	{
		//编辑区标题
		super("FreeJava beta 1.0");
		m_eje= EJE.getEJE();
		//获得标签页
		m_TabbedPane = m_eje.tabbedPane;
		m_TabbedPane.setMinimumSize(new Dimension(0,300));
		//添加标签页和输出区
		add( new JSplitPane( JSplitPane.VERTICAL_SPLIT,m_TabbedPane, m_OutputArea) );
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    //初始化函数，组装菜单栏和工具栏，以及右键菜单
    public void initialize()
    {
		//右键菜单
		JMenuItem closeTab=new JMenuItem(Components.rb.getString("Close"));
		//菜单栏
		Components.comp_EditArea.setJMenuBar(Components.comp_MenuBar);
		//工具栏
		Components.comp_EditArea.add(Components.comp_ToolBar, "North");
	
		//对关闭动作的响应
		addWindowListener( new WindowAdapter()
		{
			//响应方法:保存配置文件，退出程序
			public void windowClosing(WindowEvent e)
			{
				Components.comp_Config.saveConfig();
				System.exit(0);
			}
		
		});
		//根据配置文件设置窗体的位置和大小
		setLocation(Components.leftOfEditArea, Components.topOfEditArea);
		setSize(Components.rightOfEditArea - Components.leftOfEditArea, Components.bottomOfEditArea - Components.topOfEditArea);
    }
	
	//新打开文件到标签页
	public void addFile( File GetFile )
	{
		JavaFile file= new JavaFile( GetFile.getAbsolutePath() );
		m_eje.processOpenAction( file );
	}

	//返回当前被激活的标签页对应的文件
	public File getCurrentFile()
	{
		return m_eje.ejeTab.javaFile;
	}
	
	//保存当前活跃状态的文件
	public void saveCurrentFile()
	{
		m_eje.processSaveAction();
	}
	
	//保存已经打开的所有文件
	public void saveAllFile()
	{
		m_eje.saveAll();
	}
	
	//撤销编辑区的上一次操作
	public void undo()
	{
		try
		{
			m_eje.ejeTab.undo.undo();
		} 
		catch (Exception eex) {}
		m_eje.ejeTab.undoAction.update();
		m_eje.ejeTab.redoAction.update();
		m_eje.ejeTab.isDirty = true;
		m_eje.updateSaveFileState(m_eje.ejeTab);
		m_eje.updateSaveAllFilesState();
	}
	
	//重做撤销的动作
	public void redo()
	{
		try 
		{
			m_eje.ejeTab.undo.redo();
		} 
		catch (Exception ex) {}
		m_eje.ejeTab.redoAction.update();
		m_eje.ejeTab.undoAction.update();
		m_eje.ejeTab.isDirty = true;
		m_eje.updateSaveFileState(m_eje.ejeTab);
		m_eje.updateSaveAllFilesState();
	}

	//复制选中的字符
	public void copy()
	{
		m_eje.ejeTab.textArea.copy();
	}
	
	//设置编辑区的字体
	public void setFont( Font f )
	{
		if(m_eje!=null)
			if(m_eje.ejeTab!=null)
				if(m_eje.ejeTab.textArea!=null)
					m_eje.ejeTab.textArea.setFont(f);
	}

	//粘贴剪切板中的字符到编辑区光标所在处
	public void paste()
	{
		m_eje.ejeTab.textArea.paste();
	}
	
	//剪切被选中的字符
	public void cut()
	{
		m_eje.ejeTab.textArea.cut();
	}
	
	//设置被激活的标签页索引
	public void setSelected( String FileName )
	{
		SingleSelectionModel ssm=m_TabbedPane.getModel();
		ssm.setSelectedIndex( m_TabbedPane.indexOfTab( FileName ) );
		m_TabbedPane.setModel( ssm );
	}
	
	//以下用于编译运行
	Build build = new Build();

	//编译当前文件
	public void compile()
	{
		build.compile(m_eje.ejeTab.javaFile.getAbsolutePath());
		//获得消息流，以便显示到IDE中指定的消息区
		m_OutputArea.m_content.setText( build.getCompileStr() );
		validate();
	}
	
	//运行当前文件
	public void run()
	{
		String path=m_eje.ejeTab.javaFile.getAbsolutePath();
		build.prom_run(	path.substring(0, path.indexOf(".java")));
		//获得消息流，以便显示到IDE中指定的消息区
		m_OutputArea.m_content.setText( build.getDosOutStr() );
		validate();
	}

}

/*
输出区类OutputArea
基本功能：是一个将要显示与编辑区右下角的消息框
*/
class OutputArea extends JPanel
{
	//只含JTextPane
	public JTextArea m_content= new JTextArea();
	//构造函数：组合OutputArea，设置大小位置等偏好
	OutputArea()
	{
		add( new JScrollPane(m_content) );
		setMaximumSize( new Dimension(2000, 150) );
		setMinimumSize( new Dimension(0, 50) );
		setPreferredSize( new Dimension(100, 50) );
	}
}