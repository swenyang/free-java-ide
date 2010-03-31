import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/*
���ƣ�	ȫ������Config.java
���ܣ�	���Ǹ�����Ҫ��¼����Ϣ
		�������ø�����Լ����������״̬��
		Ҳ���ڱ��浽���̲����¶���
*/

public class Config extends Properties
{
//�����ļ�������
	private String CONFIG_FILE = "config.prop";

	Config()
	{
		super();
	}
	
	//��ʼ��
	public void initialize()
	{
		//��ʼ��������������ļ����ڣ���ֱ�Ӷ�ȡ
	    if(new File(CONFIG_FILE).exists())
		{
			readConfig();
			return;
		}
	
		//��������б������г�ʼ��ֵ

		//��Ļ��С������������Ķ�λ
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(new Frame().getGraphicsConfiguration());
		
		//��ʼ��ֵ
		setProperty("leftOfTreeViewer", "0");//TreeViewerλ�ô�С��Ϣ
		setProperty("topOfTreeViewer", "0");
		setProperty("rightOfTreeViewer", "300");
		setProperty("bottomOfTreeViewer", "500");
	
		setProperty("leftOfCommunicateArea", "0");//������λ�ô�С��Ϣ
		setProperty("topOfCommunicateArea", "500");
		setProperty("rightOfCommunicateArea", "300");
		setProperty("bottomOfCommunicateArea", String.valueOf(dimension.height - screenInsets.top - screenInsets.bottom));

		setProperty("leftOfEditArea", "300");//�༭��λ�ô�С��Ϣ
		setProperty("topOfEditArea", "0");
		setProperty("rightOfEditArea", String.valueOf(dimension.width));
		setProperty("bottomOfEditArea", String.valueOf(dimension.height - screenInsets.top - screenInsets.bottom));

		setProperty("ToolBarVisible", "true");//���������ļ���ͼ����������������ɼ���
		setProperty("TreeViewerVisible", "true");
		setProperty("OutputAreaVisible", "true");
		setProperty("CommunicateAreaVisible", "true");
			
		setProperty("VisibleCreateNew", "true");//�������еĸ���ť�Ŀɼ���
		setProperty("VisibleOpenFile", "true");
		setProperty("VisibleSaveFile", "true");
		setProperty("VisibleSaveAll", "true");
		setProperty("VisibleCopy", "true");
		setProperty("VisiblePaste", "true");
		setProperty("VisibleCut", "true");
		setProperty("VisibleCompile", "true");
		setProperty("VisibleRun", "true");
		setProperty("VisibleRedo", "true");
		setProperty("VisibleUndo", "true");
			
		setProperty("MagneticDistance", "0");//���Ծ���
		setProperty("locale","cn");//����
		//д���ļ�
		try
    	{
			FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
			store(fos, "IDEConfig");
			fos.close();
	  	}
		catch(Exception e){}
		//���¶�ȡ
		readConfig();
	}
	
	//��ȡ�����ļ�	
	public void readConfig()
	{
		try
		{
			FileInputStream fis = new FileInputStream(CONFIG_FILE);
			load(fis);
			fis.close();
		}
		catch(Exception e){}
		
		//���ݶ�ȡ��������ϢΪ������ֵ
		Components.leftOfTreeViewer = Integer.parseInt(getProperty("leftOfTreeViewer"));
		Components.topOfTreeViewer = Integer.parseInt(getProperty("topOfTreeViewer"));
		Components.rightOfTreeViewer = Integer.parseInt(getProperty("rightOfTreeViewer"));
		Components.bottomOfTreeViewer = Integer.parseInt(getProperty("bottomOfTreeViewer"));
	
		Components.leftOfCommunicateArea = Integer.parseInt(getProperty("leftOfCommunicateArea"));
		Components.topOfCommunicateArea = Integer.parseInt(getProperty("topOfCommunicateArea"));
		Components.rightOfCommunicateArea = Integer.parseInt(getProperty("rightOfCommunicateArea"));
		Components.bottomOfCommunicateArea = Integer.parseInt(getProperty("bottomOfCommunicateArea"));
		
		Components.leftOfEditArea = Integer.parseInt(getProperty("leftOfEditArea"));
		Components.topOfEditArea = Integer.parseInt(getProperty("topOfEditArea"));
		Components.rightOfEditArea = Integer.parseInt(getProperty("rightOfEditArea"));
		Components.bottomOfEditArea = Integer.parseInt(getProperty("bottomOfEditArea"));

		Components.ToolBarVisible = ((String)getProperty("ToolBarVisible")).equals("true");
		Components.TreeViewerVisible = ((String)getProperty("TreeViewerVisible")).equals("true");
		Components.OutputAreaVisible = ((String)getProperty("OutputAreaVisible")).equals("true");
		Components.CommunicateAreaVisible = ((String)getProperty("CommunicateAreaVisible")).equals("true");
		Components.VisibleCreateNew = ((String)getProperty("VisibleCreateNew")).equals("true");
		Components.VisibleOpenFile = ((String)getProperty("VisibleOpenFile")).equals("true");
		Components.VisibleSaveFile = ((String)getProperty("VisibleSaveFile")).equals("true");
		Components.VisibleSaveAll = ((String)getProperty("VisibleSaveAll")).equals("true");
		Components.VisibleCopy = ((String)getProperty("VisibleCopy")).equals("true");
		Components.VisiblePaste = ((String)getProperty("VisiblePaste")).equals("true");
		Components.VisibleCut = ((String)getProperty("VisibleCut")).equals("true");
		Components.VisibleCompile = ((String)getProperty("VisibleCompile")).equals("true");
		Components.VisibleRun = ((String)getProperty("VisibleRun")).equals("true");
		Components.VisibleRedo = ((String)getProperty("VisibleRedo")).equals("true");
		Components.VisibleUndo = ((String)getProperty("VisibleUndo")).equals("true");
			
		Components.MagneticDistance = Integer.parseInt(getProperty("MagneticDistance"));
	}

	//���������ļ������õ������������˳�ʱ
	public void saveConfig()
	{
		setProperty("leftOfTreeViewer", String.valueOf(Components.comp_TreeViewer.getX()));
		setProperty("topOfTreeViewer",  String.valueOf(Components.comp_TreeViewer.getY()));
		setProperty("rightOfTreeViewer",  String.valueOf((Components.comp_TreeViewer.getX() + Components.comp_TreeViewer.getWidth())));
		setProperty("bottomOfTreeViewer",  String.valueOf((Components.comp_TreeViewer.getY() + Components.comp_TreeViewer.getHeight())));
		
		setProperty("leftOfCommunicateArea", String.valueOf(Components.comp_CommunicateArea.getX()));
		setProperty("topOfCommunicateArea", String.valueOf(Components.comp_CommunicateArea.getY()));
		setProperty("rightOfCommunicateArea", String.valueOf((Components.comp_CommunicateArea.getX() + Components.comp_CommunicateArea.getWidth())));
		setProperty("bottomOfCommunicateArea", String.valueOf((Components.comp_CommunicateArea.getY() + Components.comp_CommunicateArea.getHeight())));
		
		setProperty("leftOfEditArea", String.valueOf(Components.comp_EditArea.getX()));
		setProperty("topOfEditArea", String.valueOf(Components.comp_EditArea.getY()));
		setProperty("rightOfEditArea", String.valueOf((Components.comp_EditArea.getX() + Components.comp_EditArea.getWidth())));
		setProperty("bottomOfEditArea", String.valueOf((Components.comp_EditArea.getY() + Components.comp_EditArea.getHeight())));

		setProperty("ToolBarVisible", String.valueOf(Components.ToolBarVisible));
		setProperty("TreeViewerVisible", String.valueOf(Components.TreeViewerVisible));
		setProperty("OutputAreaVisible", String.valueOf(Components.OutputAreaVisible));
		setProperty("CommunicateAreaVisible", String.valueOf(Components.CommunicateAreaVisible));
		
		setProperty("VisibleCreateNew", String.valueOf(Components.VisibleCreateNew));
		setProperty("VisibleOpenFile", String.valueOf(Components.VisibleOpenFile));
		setProperty("VisibleSaveFile", String.valueOf(Components.VisibleSaveFile));
		setProperty("VisibleSaveAll", String.valueOf(Components.VisibleSaveAll));
		setProperty("VisibleCopy", String.valueOf(Components.VisibleCopy));
		setProperty("VisiblePaste", String.valueOf(Components.VisiblePaste));
		setProperty("VisibleCut", String.valueOf(Components.VisibleCut));
		setProperty("VisibleCompile", String.valueOf(Components.VisibleCompile));
		setProperty("VisibleRun", String.valueOf(Components.VisibleRun));
		setProperty("VisibleRedo", String.valueOf(Components.VisibleRedo));
		setProperty("VisibleUndo", String.valueOf(Components.VisibleUndo));
		
		setProperty("MagneticDistance", String.valueOf(Components.MagneticDistance));
		setProperty("locale", getProperty("locale"));
		try
		{
			FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
			store(fos, "IDEConfig");
			fos.close();
		}
		catch(Exception e){}
	}
}