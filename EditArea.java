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
�༭����EditArea.java
�������ܣ�	�ǳ��������Ĳ���
			�����˲˵���������������ǩҳ��������Ĵ󲿷�
			ʵ�ּ򵥵����ֱ༭���ܣ����ơ����С�ճ��������������
			ʵ�ּ򵥵Ĵ��빦�ܣ��ؼ�����ע�Ͳ�ɫ��ʾ�����롢����
*/

public class EditArea extends JFrame
{
	//��ǩҳ��������ʾ�����ļ�
	JTabbedPane m_TabbedPane;	
	//���������ʾ���롢����ʱ�������Ϣ
	public OutputArea m_OutputArea= new OutputArea();	
	//��ǶEJE�����Ի�ȡ���ǩҳ
	EJE m_eje;	

	//���캯������ʼ������װ�������
	EditArea()
	{
		//�༭������
		super("FreeJava beta 1.0");
		m_eje= EJE.getEJE();
		//��ñ�ǩҳ
		m_TabbedPane = m_eje.tabbedPane;
		m_TabbedPane.setMinimumSize(new Dimension(0,300));
		//��ӱ�ǩҳ�������
		add( new JSplitPane( JSplitPane.VERTICAL_SPLIT,m_TabbedPane, m_OutputArea) );
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    //��ʼ����������װ�˵����͹��������Լ��Ҽ��˵�
    public void initialize()
    {
		//�Ҽ��˵�
		JMenuItem closeTab=new JMenuItem(Components.rb.getString("Close"));
		//�˵���
		Components.comp_EditArea.setJMenuBar(Components.comp_MenuBar);
		//������
		Components.comp_EditArea.add(Components.comp_ToolBar, "North");
	
		//�Թرն�������Ӧ
		addWindowListener( new WindowAdapter()
		{
			//��Ӧ����:���������ļ����˳�����
			public void windowClosing(WindowEvent e)
			{
				Components.comp_Config.saveConfig();
				System.exit(0);
			}
		
		});
		//���������ļ����ô����λ�úʹ�С
		setLocation(Components.leftOfEditArea, Components.topOfEditArea);
		setSize(Components.rightOfEditArea - Components.leftOfEditArea, Components.bottomOfEditArea - Components.topOfEditArea);
    }
	
	//�´��ļ�����ǩҳ
	public void addFile( File GetFile )
	{
		JavaFile file= new JavaFile( GetFile.getAbsolutePath() );
		m_eje.processOpenAction( file );
	}

	//���ص�ǰ������ı�ǩҳ��Ӧ���ļ�
	public File getCurrentFile()
	{
		return m_eje.ejeTab.javaFile;
	}
	
	//���浱ǰ��Ծ״̬���ļ�
	public void saveCurrentFile()
	{
		m_eje.processSaveAction();
	}
	
	//�����Ѿ��򿪵������ļ�
	public void saveAllFile()
	{
		m_eje.saveAll();
	}
	
	//�����༭������һ�β���
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
	
	//���������Ķ���
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

	//����ѡ�е��ַ�
	public void copy()
	{
		m_eje.ejeTab.textArea.copy();
	}
	
	//���ñ༭��������
	public void setFont( Font f )
	{
		if(m_eje!=null)
			if(m_eje.ejeTab!=null)
				if(m_eje.ejeTab.textArea!=null)
					m_eje.ejeTab.textArea.setFont(f);
	}

	//ճ�����а��е��ַ����༭��������ڴ�
	public void paste()
	{
		m_eje.ejeTab.textArea.paste();
	}
	
	//���б�ѡ�е��ַ�
	public void cut()
	{
		m_eje.ejeTab.textArea.cut();
	}
	
	//���ñ�����ı�ǩҳ����
	public void setSelected( String FileName )
	{
		SingleSelectionModel ssm=m_TabbedPane.getModel();
		ssm.setSelectedIndex( m_TabbedPane.indexOfTab( FileName ) );
		m_TabbedPane.setModel( ssm );
	}
	
	//�������ڱ�������
	Build build = new Build();

	//���뵱ǰ�ļ�
	public void compile()
	{
		build.compile(m_eje.ejeTab.javaFile.getAbsolutePath());
		//�����Ϣ�����Ա���ʾ��IDE��ָ������Ϣ��
		m_OutputArea.m_content.setText( build.getCompileStr() );
		validate();
	}
	
	//���е�ǰ�ļ�
	public void run()
	{
		String path=m_eje.ejeTab.javaFile.getAbsolutePath();
		build.prom_run(	path.substring(0, path.indexOf(".java")));
		//�����Ϣ�����Ա���ʾ��IDE��ָ������Ϣ��
		m_OutputArea.m_content.setText( build.getDosOutStr() );
		validate();
	}

}

/*
�������OutputArea
�������ܣ���һ����Ҫ��ʾ��༭�����½ǵ���Ϣ��
*/
class OutputArea extends JPanel
{
	//ֻ��JTextPane
	public JTextArea m_content= new JTextArea();
	//���캯�������OutputArea�����ô�Сλ�õ�ƫ��
	OutputArea()
	{
		add( new JScrollPane(m_content) );
		setMaximumSize( new Dimension(2000, 150) );
		setMinimumSize( new Dimension(0, 50) );
		setPreferredSize( new Dimension(100, 50) );
	}
}