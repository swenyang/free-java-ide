/*
���ƣ�	ȫ�־�̬�������Components.java
���ܣ�	�������л����ġ��໥Ӱ���ȫ�־�̬�������comp_**�����������
	�������ڹ�����֮����н��������һϵ�л�������
	��̬��������������ļ�������������������Ŀ¼����λ�õ�����������16������˵���
	��¼������Ϣ��ȫ�־�̬������λ����Ϣ���齨�Ƿ�ɼ���Ϣ���Ƿ���������������
*/

import javax.swing.JMenu;
import java.util.*;
import javax.swing.JMenuBar;

public class Components
{
	//�����¼�����ļ����comp_Config ����ʼ����Դ��ResourceBundle rb
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

	/*������������ྲ̬�����
		�༭��comp_EditArea���ṹ��ͼ��comp_TreeViewer
		������comp_ToolBar��������comp_CommunicateArea
		λ�õ���������adjustListener��Ŀ¼��comp_MenuBar*/
	public static EditArea comp_EditArea = new EditArea();
	public static TreeViewer comp_TreeViewer = new TreeViewer();
	public static ToolBar comp_ToolBar = new ToolBar();
	public static CommunicateArea comp_CommunicateArea = new  CommunicateArea();
	public static AdjustListener adjustListener = new AdjustListener();
	public static JMenuBar comp_MenuBar = new JMenuBar();

	/*�����������������
		�½�createNewFileAction����openFileAction������saveFileAction
		����ȫ��saveAllFileAction���˳�exitAction������undoAction
		����redoAction������copyAction��ճ��pasteAction������cutAction*/
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

	//�齨�ɼ��Կ���ȫ�����viewComponents���������ɼ��Կ���ȫ�����viewToolBarComponents
	public static ViewComponents viewComponents = new ViewComponents();
	public static ViewToolBarComponents viewToolBarComponents = new ViewToolBarComponents();
	
	//����ȫ�ֱ�������compileAction������runAction
	public static CompileAction compileAction = new CompileAction();
	public static RunAction runAction = new RunAction();
  
	/*���帽�ӹ��ܶ�����
		ͳ��statisticsAction��ѡ��optionAction
		��������languageAction����������fontAction
		����helpAction������aboutAction*/
	public static StatisticsAction statisticsAction = new StatisticsAction();
	public static OptionAction optionAction = new OptionAction();
	public static LanguageAction languageAction = new LanguageAction();
	public static FontAction fontAction = new FontAction();
	public static HelpAction helpAction = new HelpAction();
	public static AboutAction aboutAction = new AboutAction();
	
	/*����˵�������˵�
		�ļ�fileMenu���༭editMenu���鿴viewMenu
		���buildMenu������toolMenu������helpMenu*/
	public static JMenu fileMenu = new JMenu(rb.getString("File"));
	public static JMenu editMenu = new JMenu(rb.getString("Edit"));
	public static JMenu viewMenu = new JMenu(rb.getString("View"));
	public static JMenu buildMenu = new JMenu(rb.getString("Build"));
	public static JMenu toolMenu = new JMenu(rb.getString("Tool"));	
	public static JMenu helpMenu = new JMenu(rb.getString("Help"));

	/*��Խṹ��ͼ���༭�����Ŷӽ������������¼λ����Ϣint���������		
		���Ͻǣ�leftOf**��topOf**��
		���½����꣨rightOf**��bottomOf**��*/	
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

	//����boolean�ͽṹ��ͼ-�༭����������-�༭���Ƿ����������
	public static boolean isAdheredTreeViewerAndEditArea;
	public static boolean isAdheredCommunicateAreaAndEditArea;
	
	//����boolean�͹��������ļ���ͼ����������������ɼ��Կ��������
	public static boolean ToolBarVisible;
	public static boolean TreeViewerVisible;
	public static boolean OutputAreaVisible;
	public static boolean CommunicateAreaVisible;
	
	/*���幤�����ɼ��Կ����������
		�½����򿪡����桢�������С�����
		��������С����롢���С�����������*/
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
	
	//�����¼���������int�������MagneticDistance
	public static int MagneticDistance;
}