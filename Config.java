import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/*
名称：	全局配置Config.java
功能：	涵盖各种需要记录的信息
		用于设置各组件以及整个程序的状态等
		也用于保存到磁盘并重新读入
*/

public class Config extends Properties
{
//配置文件的名称
	private String CONFIG_FILE = "config.prop";

	Config()
	{
		super();
	}
	
	//初始化
	public void initialize()
	{
		//初始创建，如果配置文件存在，则直接读取
	    if(new File(CONFIG_FILE).exists())
		{
			readConfig();
			return;
		}
	
		//否则对所有变量进行初始赋值

		//屏幕大小可以用于组件的定位
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(new Frame().getGraphicsConfiguration());
		
		//初始赋值
		setProperty("leftOfTreeViewer", "0");//TreeViewer位置大小信息
		setProperty("topOfTreeViewer", "0");
		setProperty("rightOfTreeViewer", "300");
		setProperty("bottomOfTreeViewer", "500");
	
		setProperty("leftOfCommunicateArea", "0");//交流区位置大小信息
		setProperty("topOfCommunicateArea", "500");
		setProperty("rightOfCommunicateArea", "300");
		setProperty("bottomOfCommunicateArea", String.valueOf(dimension.height - screenInsets.top - screenInsets.bottom));

		setProperty("leftOfEditArea", "300");//编辑区位置大小信息
		setProperty("topOfEditArea", "0");
		setProperty("rightOfEditArea", String.valueOf(dimension.width));
		setProperty("bottomOfEditArea", String.valueOf(dimension.height - screenInsets.top - screenInsets.bottom));

		setProperty("ToolBarVisible", "true");//工具栏、文件视图、输出区、交流区可见性
		setProperty("TreeViewerVisible", "true");
		setProperty("OutputAreaVisible", "true");
		setProperty("CommunicateAreaVisible", "true");
			
		setProperty("VisibleCreateNew", "true");//工具栏中的各按钮的可见性
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
			
		setProperty("MagneticDistance", "0");//磁性距离
		setProperty("locale","cn");//语言
		//写入文件
		try
    	{
			FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
			store(fos, "IDEConfig");
			fos.close();
	  	}
		catch(Exception e){}
		//重新读取
		readConfig();
	}
	
	//读取配置文件	
	public void readConfig()
	{
		try
		{
			FileInputStream fis = new FileInputStream(CONFIG_FILE);
			load(fis);
			fis.close();
		}
		catch(Exception e){}
		
		//根据读取的配置信息为变量赋值
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

	//保存配置文件，调用点是整个程序退出时
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