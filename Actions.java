/*
���ƣ�	����Actions.java
���ܣ�	���������ж�������ʵ��
	����15��ܣ�
	�½����򿪡����桢�������С��˳�������������������
	���С�ѡ����������ڡ����ơ�ճ��������
	15���඼��AbstractAction�����ࡣ
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

//Actions��Ϊ��
public class Actions{}

//�½�
class CreateNewFileAction extends AbstractAction
{
	public CreateNewFileAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("New")+"(N)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
		//��ʾ�Ի�����ʾ�����ļ���
		String fileName = JOptionPane.showInputDialog(Components.comp_EditArea.getRootPane(), Components.rb.getString("Input\u0020new\u0020file's\u0020name"), Components.rb.getString("Untitled.java"));
		File newFile = new File(fileName);
		boolean success = true;
		//���쳣������ƣ����Ƿ��½��ɹ�
		try{
			success = newFile.createNewFile();
		}
		catch(IOException exc){
		}
		//����½��ɹ�����comp_TreeViewer
		if (success){
				Components.comp_TreeViewer.fileTree.updateUI();
		}
		else{
		}	
		Components.comp_EditArea.addFile(newFile);//�༭����������½��ļ�
		TreeViewer.fileTree.initializeRoot();//���³�ʼ���ṹ��ͼ
	}
}

//��
class OpenFileAction extends AbstractAction
{
	public OpenFileAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Open")+"(O)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
		//ͨ�� JFileChooser �ඨ��fileChooser������֧�ֶ�ѡ
		JFileChooser fileChooser= new JFileChooser();
		fileChooser.setMultiSelectionEnabled( true );
		
		//�����ڲ���JavaFileFilterΪ FileFilter������
		class JavaFileFilter extends FileFilter
		{
		    	//�жϽ�β�Ƿ�Ϊ.java�ɽ���
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
		
		//returnValueΪ�Ƿ���ڱ༭���򿪣�������ԣ����ڱ༭���򿪡�
		if(returnValue == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fileChooser.getSelectedFiles();
			for(File SingleFile : files)
				Components.comp_EditArea.addFile(SingleFile);
		}
	}
}

//����
class SaveFileAction extends AbstractAction
{
	public SaveFileAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Save")+"(S)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�������ñ༭����saveCurrentFile()
	{
		Components.comp_EditArea.saveCurrentFile();
	}
}

//��������
class SaveAllFileAction extends AbstractAction
{
	public SaveAllFileAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("SaveAll")+"(A)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
	}
	public void actionPerformed(ActionEvent e)//�¼�������ñ༭����saveAllFile()
	{
		Components.comp_EditArea.saveAllFile();
	}
}

//�˳�
class ExitAction extends AbstractAction
{
	public ExitAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Exit")+"(Q)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
		Components.comp_Config.saveConfig();//����������Ϣ
		System.exit(0);//�˳�
	}
}

//����
class UndoAction extends AbstractAction
{
	public UndoAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Undo")+"(Z)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�������ñ༭����undo()
	{
		Components.comp_EditArea.undo();
	}
}

//����
class RedoAction extends AbstractAction
{
	public RedoAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Redo")+"(Y)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�������ñ༭����redo()
	{
		Components.comp_EditArea.redo();
	}
}

//����
class CompileAction extends AbstractAction
{
	public CompileAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Compile")+"(C)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�������ñ༭����compile()
	{
		Components.comp_EditArea.compile();
	}
}

//����
class RunAction extends AbstractAction
{
	public RunAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Run")+"(J)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('J', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�������ñ༭����run()
	{
		Components.comp_EditArea.run();
	}
}

//ͳ��
class StatisticsAction extends AbstractAction
{
	public StatisticsAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Statistics") + "(S)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{/*
		File file = Components.comp_EditArea.getCurrentFile();
	    long fileSize = file.length();
	    JOptionPane.showMessageDialog(Components.comp_EditArea.getRootPane(), "��ǰ�ļ�����ͳ��" + String.valueOf(fileSize), "�ļ�ͳ��", JOptionPane.INFORMATION_MESSAGE);
	*/
	}
}

//ѡ��
class OptionAction extends AbstractAction
{
	public OptionAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Option")+"(O)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
	    	//�Ի���JDialog������������
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
		//�����������������˳�ʱ����Components���е����������¼��ֵMagneticDistance
		dialog.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
			Components.MagneticDistance = slider.getValue();
		    }
		});
	}
}

//����
class LanguageAction extends AbstractAction
{
	public LanguageAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Language")+"(L)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
	}
	public void actionPerformed(ActionEvent e)//�¼�����-֧����������
	{
	    	//���ɰ������ġ�English���ձ��Z������ĶԻ���JDialog
		final JComboBox box = new JComboBox();
		box.addItem("����");
		box.addItem("English");
		box.addItem("�ձ��Z");
		JPanel panel = new JPanel();
		panel.add(box);
		JDialog dialog = new JDialog(Components.comp_EditArea, "����");
		dialog.add(panel);
		dialog.pack();
		dialog.setLocation(500, 400);
		dialog.setVisible(true);
		//�����������������˳�ʱ���������ļ��м�¼���Ե���Ϣ
		dialog.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
			int tmp = box.getSelectedIndex();//tmp��ȡѡ��
				Locale l;
				if(tmp==0)//ѡ0-cn-����
				{
					l=new Locale("cn");
					Components.comp_Config.setProperty("locale", "cn");
				}
				else if(tmp==1)//ѡ1-en-Ӣ��
				{
					l=new Locale("en");
					Components.comp_Config.setProperty("locale", "en");
				}
				else//ja-����
				{
					l=new Locale("ja");
					Components.comp_Config.setProperty("locale", "ja");
				}
				Components.rb=ResourceBundle.getBundle("mess",l);
		    }
		});
	}
}

//��������
class FontAction extends AbstractAction
{
	public FontAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Font")+"(F)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
		//��ѡ��box�趨��������
		final JComboBox box = new JComboBox();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontsName[] = ge.getAvailableFontFamilyNames();
		for(int i = 0;i < fontsName.length;i++)
			box.addItem(fontsName[i]);
			
		//��ѡ��box2�趨�����С6-51
		final JComboBox box2 = new JComboBox();
		for(int i = 6;i < 51;i++)
			box2.addItem(i);
		
		//��ѡ��box3�趨�����Ƿ�Ӵ�
		final JComboBox box3 = new JComboBox();
		box3.addItem("Plain");
		box3.addItem("Bold");
		
		//��ư�����ʾ��Ϣ�ĶԻ���
		JPanel panel = new JPanel();
		panel.add(box);
		panel.add(box2);
		panel.add(box3);
		
		JDialog dialog = new JDialog(Components.comp_EditArea, "����");
		dialog.add(panel);
		dialog.pack();
		dialog.setLocation(500, 400);
		dialog.setVisible(true);
		
		//�������������������˳�ʱ�趨���ñ༭��setFont�����趨���塢���ͣ��Ƿ�Ӵ֣�����С
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

//����
class HelpAction extends AbstractAction
{
	public HelpAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Help")+"(H)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
	    	//���쳣�������ִ��hh jdk150.chm�����ĵ�
		try{
	   		Runtime.getRuntime().exec("hh jdk150.chm");
		}
		catch( Exception ex ){}
	}
}

//����
class AboutAction extends AbstractAction
{
	public AboutAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("About")+"(A)");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
	}
	public void actionPerformed(ActionEvent e)//�¼�����
	{
	    	//���ɰ����汾��Ϣ�ĶԻ���
		String message = new String("Java\u0020IDE\u0020beta\u00201.0\nAuthor:\u0020Xiao\u0020Xiangquan,\u0020Liu\u0020Kainan,\u0020Shi\u0020Qinqing,\u0020Yang\u0020Wenxin\nMay\u00202009");
		JOptionPane.showMessageDialog(Components.comp_EditArea.getRootPane(), message, "About Java IDE", JOptionPane.INFORMATION_MESSAGE); 
	}
}

//����
class CopyAction extends AbstractAction
{
	public CopyAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Copy")+"(C)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�����-���ñ༭����copy()
	{
		Components.comp_EditArea.copy();
	}
}

//ճ��
class PasteAction extends AbstractAction
{
	public PasteAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Paste")+"(V)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�����-���ñ༭����paste()
	{
		Components.comp_EditArea.paste();
	}
}

//����
class CutAction extends AbstractAction
{
	public CutAction()//���캯�����ɲ˵����ӿ�ݼ�
	{
		putValue(Action.NAME, Components.rb.getString("Cut")+"(X)");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent e)//�¼�����-���ñ༭����cut()
	{
		Components.comp_EditArea.cut();
	}
}