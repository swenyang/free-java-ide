/*
名称：	动作Actions.java
功能：	集成了所有动作及其实现
	包括15项功能：
	新建、打开、保存、保存所有、退出、撤销、重做、编译
	运行、选项、帮助、关于、复制、粘贴、剪切
	15个类都是AbstractAction的子类。
*/
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import javax.swing.JSlider;
import javax.swing.JDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.*;

//Actions类为空
public class Actions{}

//新建
class CreateNewFileAction extends AbstractAction
{
	public CreateNewFileAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("New")+"(N)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
		//显示对话框，提示输入文件名
		String fileName = JOptionPane.showInputDialog(Components.comp_EditArea.getRootPane(), Components.rb.getString("Input\u0020new\u0020file's\u0020name"), Components.rb.getString("Untitled.java"));
		File newFile = new File(fileName);
		boolean success = true;
		//用异常处理机制，看是否新建成功
		try{
			success = newFile.createNewFile();
		}
		catch(IOException exc){
		}
		//如果新建成功更新comp_TreeViewer
		if (success){
				Components.comp_TreeViewer.fileTree.updateUI();
		}
		else{
		}	
		Components.comp_EditArea.addFile(newFile);//编辑区中中添加新建文件
		TreeViewer.fileTree.initializeRoot();//重新初始化结构视图
	}
}

//打开
class OpenFileAction extends AbstractAction
{
	public OpenFileAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Open")+"(O)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
		//通过 JFileChooser 类定义fileChooser，可以支持多选
		JFileChooser fileChooser= new JFileChooser();
		fileChooser.setMultiSelectionEnabled( true );
		
		//定义内部类JavaFileFilter为 FileFilter的子类
		class JavaFileFilter extends FileFilter
		{
		    	//判断结尾是否为.java可接受
			public boolean accept(File file)
			{
				if( file.isDirectory() )
					return true;
				String tmp = file.getName().toLowerCase();
				if ( tmp.endsWith(".java") )
					return true;
				return false;
			}
			public String getDescription()
			{
				return "java\u0020source\u0020files";
			}
		}
		fileChooser.addChoosableFileFilter(new JavaFileFilter());
		int returnValue = fileChooser.showOpenDialog(Components.comp_EditArea);
		
		//returnValue为是否可在编辑区打开，如果可以，则在编辑区打开。
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fileChooser.getSelectedFiles();
			for(File SingleFile : files)
				Components.comp_EditArea.addFile(SingleFile);
		}
	}
}

//保存
class SaveFileAction extends AbstractAction
{
	public SaveFileAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Save")+"(S)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理调用编辑区的saveCurrentFile()
	{
		Components.comp_EditArea.saveCurrentFile();
	}
}

//保存所有
class SaveAllFileAction extends AbstractAction
{
	public SaveAllFileAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("SaveAll")+"(A)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
	}
	public void actionPerformed(ActionEvent e)//事件处理调用编辑区的saveAllFile()
	{
		Components.comp_EditArea.saveAllFile();
	}
}

//退出
class ExitAction extends AbstractAction
{
	public ExitAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Exit")+"(Q)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
		Components.comp_Config.saveConfig();//保存配置信息
		System.exit(0);//退出
	}
}

//撤销
class UndoAction extends AbstractAction
{
	public UndoAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Undo")+"(Z)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理调用编辑区的undo()
	{
		Components.comp_EditArea.undo();
	}
}

//重做
class RedoAction extends AbstractAction
{
	public RedoAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Redo")+"(Y)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理调用编辑区的redo()
	{
		Components.comp_EditArea.redo();
	}
}

//编译
class CompileAction extends AbstractAction
{
	public CompileAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Compile")+"(C)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理调用编辑区的compile()
	{
		Components.comp_EditArea.compile();
	}
}

//运行
class RunAction extends AbstractAction
{
	public RunAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Run")+"(J)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('J', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理调用编辑区的run()
	{
		Components.comp_EditArea.run();
	}
}

//统计
class StatisticsAction extends AbstractAction
{
	public StatisticsAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Statistics") + "(S)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{/*
		File file = Components.comp_EditArea.getCurrentFile();
	    long fileSize = file.length();
	    JOptionPane.showMessageDialog(Components.comp_EditArea.getRootPane(), "当前文件字数统计" + String.valueOf(fileSize), "文件统计", JOptionPane.INFORMATION_MESSAGE);
	*/
	}
}

//选项
class OptionAction extends AbstractAction
{
	public OptionAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Option")+"(O)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
	    	//对话框JDialog设置吸附距离
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, Components.MagneticDistance);
		slider.setMinorTickSpacing(2);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		JDialog dialog = new JDialog(Components.comp_EditArea, Components.rb.getString("MagneticDistance"));
		dialog.add(slider);
		dialog.pack();
		dialog.setLocation(500, 400);
		dialog.setVisible(true);
		//添加匿名类监听器，退出时更新Components类中的吸附距离记录数值MagneticDistance
		dialog.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
			Components.MagneticDistance = slider.getValue();
		    }
		});
	}
}

//语言
class LanguageAction extends AbstractAction
{
	public LanguageAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Language")+"(L)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
	}
	public void actionPerformed(ActionEvent e)//事件处理-支持三种语言
	{
	    	//生成包含中文、English、日本語下拉框的对话框JDialog
		final JComboBox box = new JComboBox();
		box.addItem("中文");
		box.addItem("English");
		box.addItem("日本語");
		JPanel panel = new JPanel();
		panel.add(box);
		JDialog dialog = new JDialog(Components.comp_EditArea, "语言");
		dialog.add(panel);
		dialog.pack();
		dialog.setLocation(500, 400);
		dialog.setVisible(true);
		//添加匿名类监听器，退出时更新配置文件中记录语言的信息
		dialog.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
			int tmp = box.getSelectedIndex();//tmp获取选项
				Locale l;
				if(tmp==0)//选0-cn-中文
				{
					l=new Locale("cn");
					Components.comp_Config.setProperty("locale", "cn");
				}
				else if(tmp==1)//选1-en-英文
				{
					l=new Locale("en");
					Components.comp_Config.setProperty("locale", "en");
				}
				else//ja-日文
				{
					l=new Locale("ja");
					Components.comp_Config.setProperty("locale", "ja");
				}
				Components.rb=ResourceBundle.getBundle("mess",l);
		    }
		});
	}
}

//字体设置
class FontAction extends AbstractAction
{
	public FontAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Font")+"(F)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
		//多选框box设定字体类型
		final JComboBox box = new JComboBox();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontsName[] = ge.getAvailableFontFamilyNames();
		for(int i = 0;i < fontsName.length;i++)
			box.addItem(fontsName[i]);
			
		//多选框box2设定字体大小6-51
		final JComboBox box2 = new JComboBox();
		for(int i = 6;i < 51;i++)
			box2.addItem(i);
		
		//多选框box3设定字体是否加粗
		final JComboBox box3 = new JComboBox();
		box3.addItem("Plain");
		box3.addItem("Bold");
		
		//设计包含提示信息的对话框
		JPanel panel = new JPanel();
		panel.add(box);
		panel.add(box2);
		panel.add(box3);
		
		JDialog dialog = new JDialog(Components.comp_EditArea, "语言");
		dialog.add(panel);
		dialog.pack();
		dialog.setLocation(500, 400);
		dialog.setVisible(true);
		
		//添加匿名类监听器，在退出时设定调用编辑区setFont函数设定字体、类型（是否加粗）、大小
		dialog.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
			String name = (String)box.getSelectedItem();
			int size = (int)box2.getSelectedIndex() + 6;
			int kind;
			if(box3.getSelectedIndex() == 0)
				kind = Font.PLAIN;
			else
				kind = Font.BOLD;
			Font f = new Font(name, kind, size);
			Components.comp_EditArea.setFont(f);
			
		    }
		});
	}
}

//帮助
class HelpAction extends AbstractAction
{
	public HelpAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Help")+"(H)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
	    	//用异常处理机制执行hh jdk150.chm帮助文档
		try{
	   		Runtime.getRuntime().exec("hh jdk150.chm");
		}
		catch( Exception ex ){}
	}
}

//关于
class AboutAction extends AbstractAction
{
	public AboutAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("About")+"(A)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
	}
	public void actionPerformed(ActionEvent e)//事件处理
	{
	    	//生成包含版本信息的对话框
		String message = new String("Java\u0020IDE\u0020beta\u00201.0\nAuthor:\u0020Xiao\u0020Xiangquan,\u0020Liu\u0020Kainan,\u0020Shi\u0020Qinqing,\u0020Yang\u0020Wenxin\nMay\u00202009");
		JOptionPane.showMessageDialog(Components.comp_EditArea.getRootPane(), message, "About Java IDE", JOptionPane.INFORMATION_MESSAGE); 
	}
}

//复制
class CopyAction extends AbstractAction
{
	public CopyAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Copy")+"(C)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理-调用编辑区的copy()
	{
		Components.comp_EditArea.copy();
	}
}

//粘贴
class PasteAction extends AbstractAction
{
	public PasteAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Paste")+"(V)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理-调用编辑区的paste()
	{
		Components.comp_EditArea.paste();
	}
}

//剪切
class CutAction extends AbstractAction
{
	public CutAction()//构造函数生成菜单名加快捷键
	{
		putValue(Action.NAME, Components.rb.getString("Cut")+"(X)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//事件处理-调用编辑区的cut()
	{
		Components.comp_EditArea.cut();
	}
}