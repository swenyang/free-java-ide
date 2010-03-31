/*
名称：	全局静态组件集合Components.java
功能：	集成所有基本的、相互影响的全局静态组件（以comp_**命名）或变量
	这样便于功能区之间进行交流、完成一系列基本功能
	静态组件包括：配置文件、功能区、工具条、目录条、位置调整监听器、16项动作、菜单条
	记录基本信息的全局静态变量：位置信息、组建是否可见信息、是否吸附、吸附距离
*/

import javax.swing.JMenu;
import java.util.*;
import javax.swing.JMenuBar;

public class Components
{
	//定义记录配置文件组件comp_Config 并初始化资源包ResourceBundle rb
	public static Config comp_Config = new Config();
	
	public static ResourceBundle rb;
	static
	{
		comp_Config.initialize();
		if( comp_Config.getProperty("locale")==null )
			comp_Config.setProperty("locale", "cn");
		Locale l=new Locale(comp_Config.getProperty("locale"));
		rb=ResourceBundle.getBundle("mess",l);
	}

	/*定义基本功能类静态组件：
		编辑区comp_EditArea、结构视图区comp_TreeViewer
		工具条comp_ToolBar、交流区comp_CommunicateArea
		位置调整监听器adjustListener、目录条comp_MenuBar*/
	public static EditArea comp_EditArea = new EditArea();
	public static TreeViewer comp_TreeViewer = new TreeViewer();
	public static ToolBar comp_ToolBar = new ToolBar();
	public static CommunicateArea comp_CommunicateArea = new  CommunicateArea();
	public static AdjustListener adjustListener = new AdjustListener();
	public static JMenuBar comp_MenuBar = new JMenuBar();

	/*定义基本动作变量：
		新建createNewFileAction、打开openFileAction、保存saveFileAction
		保存全部saveAllFileAction、退出exitAction、撤销undoAction
		重做redoAction、复制copyAction、粘贴pasteAction、剪切cutAction*/
	public static CreateNewFileAction createNewFileAction = new CreateNewFileAction();
	public static OpenFileAction openFileAction = new OpenFileAction();
	public static SaveFileAction saveFileAction = new SaveFileAction();
	public static SaveAllFileAction saveAllFileAction = new SaveAllFileAction();
	public static ExitAction exitAction = new ExitAction();
	public static UndoAction undoAction = new UndoAction();
	public static RedoAction redoAction = new RedoAction();
	public static CopyAction copyAction = new CopyAction();
	public static PasteAction pasteAction = new PasteAction();
	public static CutAction cutAction = new CutAction();

	//组建可见性控制全局组件viewComponents、工具条可见性控制全局组件viewToolBarComponents
	public static ViewComponents viewComponents = new ViewComponents();
	public static ViewToolBarComponents viewToolBarComponents = new ViewToolBarComponents();
	
	//定义全局变量编译compileAction、运行runAction
	public static CompileAction compileAction = new CompileAction();
	public static RunAction runAction = new RunAction();
  
	/*定义附加功能动作：
		统计statisticsAction、选项optionAction
		语言设置languageAction、字体设置fontAction
		帮助helpAction、关于aboutAction*/
	public static StatisticsAction statisticsAction = new StatisticsAction();
	public static OptionAction optionAction = new OptionAction();
	public static LanguageAction languageAction = new LanguageAction();
	public static FontAction fontAction = new FontAction();
	public static HelpAction helpAction = new HelpAction();
	public static AboutAction aboutAction = new AboutAction();
	
	/*定义菜单栏五项菜单
		文件fileMenu、编辑editMenu、查看viewMenu
		组件buildMenu、工具toolMenu、帮助helpMenu*/
	public static JMenu fileMenu = new JMenu(rb.getString("File"));
	public static JMenu editMenu = new JMenu(rb.getString("Edit"));
	public static JMenu viewMenu = new JMenu(rb.getString("View"));
	public static JMenu buildMenu = new JMenu(rb.getString("Build"));
	public static JMenu toolMenu = new JMenu(rb.getString("Tool"));	
	public static JMenu helpMenu = new JMenu(rb.getString("Help"));

	/*针对结构视图、编辑区、团队交流区，定义记录位置信息int型类变量：		
		左上角（leftOf**，topOf**）
		右下角坐标（rightOf**，bottomOf**）*/	
	public static int leftOfTreeViewer;
	public static int topOfTreeViewer;
	public static int rightOfTreeViewer;
	public static int bottomOfTreeViewer;

	public static int leftOfEditArea;
	public static int topOfEditArea;
	public static int rightOfEditArea;
	public static int bottomOfEditArea;

	public static int leftOfCommunicateArea;
	public static int topOfCommunicateArea;
	public static int rightOfCommunicateArea;
	public static int bottomOfCommunicateArea;

	//定义boolean型结构视图-编辑区、交流区-编辑区是否吸附类变量
	public static boolean isAdheredTreeViewerAndEditArea;
	public static boolean isAdheredCommunicateAreaAndEditArea;
	
	//定义boolean型工具栏、文件视图、输出区、交流区可见性控制类变量
	public static boolean ToolBarVisible;
	public static boolean TreeViewerVisible;
	public static boolean OutputAreaVisible;
	public static boolean CommunicateAreaVisible;
	
	/*定义工具栏可见性控制类变量：
		新建、打开、保存、保存所有、复制
		黏贴、剪切、编译、运行、撤销、重做*/
	public static boolean VisibleCreateNew;
	public static boolean VisibleOpenFile;
	public static boolean VisibleSaveFile;
	public static boolean VisibleSaveAll;
	public static boolean VisibleCopy;
	public static boolean VisiblePaste;
	public static boolean VisibleCut;
	public static boolean VisibleCompile;
	public static boolean VisibleRun;
	public static boolean VisibleRedo;
	public static boolean VisibleUndo;
	
	//定义记录吸附距离的int型类变量MagneticDistance
	public static int MagneticDistance;
}