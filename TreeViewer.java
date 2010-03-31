import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.ToolTipManager;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;

/*�ļ�����ͼ��ܣ���Dialogʵ����Ҫ����һ��TabbedPane����ʾ�ļ���
����Config�м�¼�����λ�ô�С����������ͼ�Ի����λ�ô�С
*/
public class TreeViewer extends JDialog
{
	public static FileTree fileTree= new FileTree();
	//ClassTreeSystem classTree;
	public TreeViewer()
	{
		super(Components.comp_EditArea, Components.rb.getString("StructureView"));
	}
	public void initialize()
	{
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		ImageIcon icon = new ImageIcon("images/TabbedPane.gif");
		ToolTipManager.sharedInstance().registerComponent(fileTree);
		
		tabbedPane.addTab(Components.rb.getString("FileSystem"), icon, new JScrollPane(fileTree), Components.rb.getString("Looking\u0020all\u0020files\u0020under\u0020temp\u0020dir"));
		tabbedPane.setSelectedIndex(0);
		add(tabbedPane);

		setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
		setSize(Components.rightOfTreeViewer - Components.leftOfTreeViewer, Components.bottomOfTreeViewer - Components.topOfTreeViewer);
		setVisible(Components.TreeViewerVisible);
	}
}