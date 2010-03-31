import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import java.io.FilenameFilter;

//文件树，即资源管理器，显示工程下的文件
public class FileTree extends JTree
{
	//文件树构造函数
	public FileTree()
	{
		super(new DefaultTreeModel(new DefaultMutableTreeNode("root")));
		fileTreeModel = (DefaultTreeModel)treeModel;
		showHiddenFiles = false;

		initializeComponents();
		initializeListeners();
	}

	public DefaultTreeModel getFileTreeModel()
	{
		return fileTreeModel;
	}
	
	//初始化组件
	private void initializeComponents()
	{
		fileSystemView = FileSystemView.getFileSystemView();
		initializeRoot();
		setCellRenderer(new FileTreeCellRenderer());
		setEditable(false);
	}

	//设置监听器
	public void initializeListeners()
	{
		addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
			}
			public void treeExpanded(TreeExpansionEvent event) {
				TreePath path = event.getPath();
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				treeNode.removeAllChildren();
				showSubTree(treeNode);
				fileTreeModel.nodeStructureChanged(treeNode);
			}
		});

		FileTreeListener ftl = new FileTreeListener(this);
		addMouseListener(ftl);
	}
	
	//设置根结点
	public void initializeRoot()
	{
		File root = null;
		root = new File(System.getProperty("user.dir"));
		rootNode = new DefaultMutableTreeNode(new FileTreeNode(root));
		showSubTree(rootNode);
		fileTreeModel.setRoot(rootNode);
	}


	//显示子结点，当点击一个树结点时把该结点下的所有子文件作为结点加入，按照文件名排序
	public void showSubTree(DefaultMutableTreeNode node)
	{
		Object userObject = node.getUserObject();
		if(userObject instanceof FileTreeNode)
		{
			FileTreeNode fileTreeNode = (FileTreeNode)userObject;
			File []files = fileTreeNode.file.listFiles(new TreeFileFilter());
			Arrays.sort(files, new Comparator<File>() {
				public int compare(File f1, File f2) {
					boolean f1IsDir = f1.isDirectory();
					boolean f2IsDir = f2.isDirectory();

					if(f1IsDir == f2IsDir)
						return f1.compareTo(f2);
					if(f1IsDir && !f2IsDir)
						return -1;
					return 1;
				}
			});

			//加入子结点
			for(File file:files)
			{
				if(!showHiddenFiles && file.isHidden())
					continue;
				FileTreeNode subFile = new FileTreeNode(file);
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subFile);
				if(file.isDirectory())
				{
					subNode.add(new DefaultMutableTreeNode(""));
				}
				node.add(subNode);
			}
		}
	}

	//设置是否显示隐藏文件，IDE中暂时没有提供选项操作
	public void setShowHiddenFiles(boolean showHiddenFiles)
	{
		if(showHiddenFiles != this.showHiddenFiles)
		{
			this.showHiddenFiles = showHiddenFiles;
			initializeRoot();
		}
	}


	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel fileTreeModel;
	protected FileSystemView fileSystemView;     //用于获取系统图标
	protected boolean showHiddenFiles;

	//结点翻译模式，设置结点的ToolTip和图标
	private class FileTreeCellRenderer extends DefaultTreeCellRenderer
	{

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
			if(userObject instanceof FileTreeNode)
			{
				FileTreeNode fileTreeNode = (FileTreeNode)userObject;
				if(fileTreeNode.file.isDirectory()){
				    setToolTipText(Components.rb.getString("Dir")+ "\"" + fileTreeNode.toString() + "\"");
				}
				else{
				    setToolTipText(Components.rb.getString("File")+"\"" + fileTreeNode.toString() + "\"");
				}
				try{
					setIcon(fileSystemView.getSystemIcon(fileTreeNode.file));
				}
				catch(Exception e){
				}
			}
			return this;
		}
	}
	
	//文件过滤器，过滤掉所有.class文件
	private class TreeFileFilter implements FilenameFilter
	{
		public boolean accept(File dir, String str)
		{
		    str.toLowerCase();
		    if(str.endsWith(".class"))
			return false;
		    return true;
		}
	}
}

/*文件树结点，作为DefaultMutableTreeNode的UserObject使用
内含一个成员变量File记录结点的文件
*/
class FileTreeNode
{
	public FileTreeNode(File file)
	{
		this.file = file;
	}
	public String toString()
	{
		String name = file.getName();
		if(name.length() == 0)
			return file.getPath();

		if(name.equals(WINDOWS_MYCOMPUTER))
			return Components.rb.getString("Computer");
		if(name.equals(WINDOWS_MYNETWORKPLACES))
			return Components.rb.getString("Network\u0020neighbors");

		return name;
	}
	
	public static void rename(FileTreeNode node, String newName)
	{
	    if(node != null){
		    File temp = node.file;
		    temp.renameTo(new File(newName));
	    }
	}

	public File file;
	public static final String WINDOWS_MYCOMPUTER = "::{20D04FE0-3AEA-1069-A2D8-08002B30309D}";
	public static final String WINDOWS_MYNETWORKPLACES = "::{208D2C60-3AEA-1069-A2D7-08002B30309D}";
}



/*
文件树监听器，主要负责监听鼠标事件
左键双击文件时打开文件/展开目录
右键点击时弹出右键菜单，提供新建等操作
*/
class FileTreeListener extends MouseAdapter
{
	public FileTreeListener(FileTree fileTree)
	{
		this.fileTree = fileTree;       //将文件树关联
	}

	public void mousePressed(MouseEvent e)   //判断是否右键或者双击文件
	{
		if(e.getButton() == MouseEvent.BUTTON3)
			rightClick(e.getX(), e.getY());
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2)
		    doubleClick(e.getX(), e.getY());
	}

	private void rightClick(int x, int y)      //右键点击，弹出菜单
	{
		TreePath treePath = fileTree.getPathForLocation(x, y);
		if(treePath == null)
			return;

		JPopupMenu popup = new JPopupMenu();     //将动作加入弹出菜单
		popup.add(new OpenSelectedFile(treePath));
		popup.add(new CreateNewFile(treePath));
		popup.add(new RenameAction(treePath));
		popup.add(new DeleteFileAction(treePath));
		popup.addSeparator();
		popup.add(new RefreshAction(treePath));
		popup.add(new GetPropertyAction(treePath));
		popup.show(fileTree, x, y);       //显示
	}
	
	private void doubleClick(int x, int y)     //鼠标双击
	{
	    TreePath treePath = fileTree.getPathForLocation(x, y);
	    if(treePath == null)
			return;
	    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
	    FileTreeNode fileTreeNode = (FileTreeNode)treeNode.getUserObject();
	    if(fileTreeNode.file.isDirectory())       //文件夹则返回
			return;
		String n = fileTreeNode.file.getName();
		n.toLowerCase();
		//支持打开特定类型的文件
		if(n.endsWith(".java") || n.endsWith(".prop") || n.endsWith(".xml") || n.endsWith(".txt") || n.endsWith(".properties") || n.endsWith(".cpp") || n.endsWith(".c"))
			Components.comp_EditArea.addFile(fileTreeNode.file);
		return;
	}

	private FileTree fileTree;
	
	
	//新建文件动作，先获取绝对路径再新建文件，建立后父节点重新载入子结点
	private class CreateNewFile extends AbstractAction
	{
		public CreateNewFile(TreePath treePath)
		{
			this.treePath = treePath;

			putValue(Action.NAME, Components.rb.getString("New")+"(N)");
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

			treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			fileTreeNode = (FileTreeNode)treeNode.getUserObject();
			
			if(fileTreeNode.file.isFile())
				setEnabled(false);
		}

		public void actionPerformed(ActionEvent e)
		{
		    String fileName = JOptionPane.showInputDialog(fileTree.getRootPane(), Components.rb.getString("Input\u0020new\u0020file's\u0020name"), Components.rb.getString("Untitled\u002Ejava"));
		   
		    String pathString = new String();
		    DefaultMutableTreeNode parent = (DefaultMutableTreeNode)treePath.getPathComponent(0);
		    FileTreeNode root = (FileTreeNode)parent.getUserObject();
		    pathString += root.file.getAbsolutePath() + File.separator;
		    for(int i = 1; i < treePath.getPathCount(); i++)
			pathString += treePath.getPathComponent(i) + File.separator;
		    pathString += fileName;
		    
		    File newFile = new File(pathString);
		    try{
			 newFile.createNewFile();
		    }
		    catch(Exception exc){}
		    FileTreeNode newNode = new FileTreeNode(newFile);
		    treeNode.removeAllChildren();
		    fileTree.showSubTree(treeNode);
		    fileTree.updateUI();
		    Components.comp_EditArea.addFile(newFile);
		}

		private TreePath treePath;
		private DefaultMutableTreeNode treeNode;
		private FileTreeNode fileTreeNode;
	}

	//打开文件动作，在编辑区打开文件
	private class OpenSelectedFile extends AbstractAction
	{
		public OpenSelectedFile(TreePath treePath)
		{
			this.treePath = treePath;

			putValue(Action.NAME, Components.rb.getString("Open")+"(O)");
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			fileTreeNode = (FileTreeNode)treeNode.getUserObject();
			String n = fileTreeNode.file.getName();
			if(n.endsWith(".java") || n.endsWith(".prop") || n.endsWith(".xml") || n.endsWith(".txt") || n.endsWith(".properties") || n.endsWith(".cpp") || n.endsWith(".c"))
			    setEnabled(true);
			else setEnabled(false);
		}

		public void actionPerformed(ActionEvent e)
		{
		    Components.comp_EditArea.addFile(fileTreeNode.file);
		}

		private TreePath treePath;
		private FileTreeNode fileTreeNode;
	}
	
	
	//刷新动作，重新载入根结点
	private class RefreshAction extends AbstractAction
	{
		public RefreshAction(TreePath treePath)
		{
			this.treePath = treePath;

			putValue(Action.NAME, Components.rb.getString("Refresh")+"(E)");
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		}
		public void actionPerformed(ActionEvent e)
		{
			fileTree.initializeRoot();
		}
		private TreePath treePath;
	}

	
	//重命名文件动作，先获取绝对路径后进行重命名
	private class RenameAction extends AbstractAction
	{
		public RenameAction(TreePath treePath)
		{
			this.treePath = treePath;

			putValue(Action.NAME, Components.rb.getString("Rename")+"(R)");
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);

			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			fileTreeNode = (FileTreeNode)treeNode.getUserObject();
			if(!fileTreeNode.file.canWrite())
				setEnabled(false);
		}

		public void actionPerformed(ActionEvent e)
		{
			if (true)
			{
			    TreePath path = fileTree.getSelectionPath();
			    
			    DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
			    FileTreeNode oldNode = (FileTreeNode)node.getUserObject();
			    DefaultMutableTreeNode parentFile = (DefaultMutableTreeNode)node.getParent();
			    if (oldNode != null)
			    {
				String name = oldNode.file.getName();
				String newName = JOptionPane.showInputDialog(fileTree.getRootPane(), Components.rb.getString("Input\u0020new\u0020file's\u0020name"), name);
				
				String pathString = new String();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode)path.getPathComponent(0);
				FileTreeNode root = (FileTreeNode)parent.getUserObject();
				pathString += root.file.getAbsolutePath() + File.separator;
				for(int i = 1; i < path.getPathCount() - 1; i++)
				    pathString += path.getPathComponent(i) + File.separator;
				pathString += newName;
				
				if (newName != null)
				    FileTreeNode.rename(oldNode, pathString);
				
				parentFile.removeAllChildren();
				fileTree.showSubTree(parentFile);
				fileTree.updateUI();
			    }
			}
		}

		private TreePath treePath;
		private FileTreeNode fileTreeNode;
	}

	//删除文件动作，为确保安全弹出确认对话框。
	private class DeleteFileAction extends AbstractAction
	{
		public DeleteFileAction(TreePath treePath)
		{
			this.treePath = treePath;
			putValue(Action.NAME, Components.rb.getString("Cancel")+"(D)");
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			fileTreeNode = (FileTreeNode)treeNode.getUserObject();
			if(!fileTreeNode.file.canWrite())
				setEnabled(false);
		}
		public void actionPerformed(ActionEvent e)
		{
			int choice = JOptionPane.showConfirmDialog(fileTree.getRootPane(),
				Components.rb.getString("Are\u0020you\u0020sure\u0020to\u0020CANCEL\u0020the\u0020file")+"“" + fileTreeNode.file.getName()+"”吗？",
				Components.rb.getString("Sure\u0020to\u0020cancel"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
			if(choice == 1)
				return;

			boolean success = false;
			if(fileTreeNode.file.isDirectory())
				success = deleteDirectory(fileTreeNode.file);
			else
				success = fileTreeNode.file.delete();

			if(success)
			{
				fileTree.getFileTreeModel().removeNodeFromParent(
					(DefaultMutableTreeNode)treePath.getLastPathComponent());
			}

		}
		//删除目录下全部子文件
		private boolean deleteDirectory(File dir)
		{
			if(dir == null || !dir.exists() || !dir.isDirectory())
				return false;

			boolean success = true;
			File[] list = dir.listFiles();
			for(File file:list)
			{
				if(file.isDirectory())
				{
					if(!deleteDirectory(file))
						success = false;
				}
				else
				{
					if(!file.delete())
						success = false;
				}
			}
			if(!dir.delete())
				success = false;
			return success;
		}
		private TreePath treePath;
		private FileTreeNode fileTreeNode;
	}
	
	//获取文件属性动作，可以查看文件的位置、大小、类型、最后修改时间等
	private class GetPropertyAction extends AbstractAction
	{
		public GetPropertyAction(TreePath treePath)
		{
			this.treePath = treePath;
			putValue(Action.NAME, Components.rb.getString("Properties")+"(P)");
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			fileTreeNode = (FileTreeNode)treeNode.getUserObject();
		}
		public void actionPerformed(ActionEvent e)
		{
			//获取文件位置和大小
			long fileSize = fileTreeNode.file.length() / 1024;
			String message = Components.rb.getString("File\u0020Location")
				+": "+ fileTreeNode.file.getAbsolutePath()+ "\n"
				+ Components.rb.getString("File\u0020Size")
				+": "+ fileSize+ " KB" + "\n"
				+ Components.rb.getString("File\u0020Type")+": ";
			
			//获取文件类型
			if((fileTreeNode.file.getName().lastIndexOf(46) > 0) && (fileTreeNode.file.isFile()))
			{
			    int pos = fileTreeNode.file.getName().lastIndexOf(46);
			    String type = fileTreeNode.file.getName().substring(pos + 1);
			    message += type + " "+Components.rb.getString("File");
			}
			else if(fileTreeNode.file.isDirectory()){
			    message += Components.rb.getString("Dir");
			}
			else{
			    message += Components.rb.getString("Unknown\u0020type");
			}
			
			//获取最后修改时间
			long longTime = fileTreeNode.file.lastModified();
			Date date = new Date(longTime);
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String updateTime = simpleFormat.format(date);
			message += "\n"+Components.rb.getString("Update\u0020time")+": " + updateTime;
			JOptionPane.showMessageDialog(fileTree.getRootPane(), message, "文件属性", JOptionPane.INFORMATION_MESSAGE); 
		}
		private TreePath treePath;
		private FileTreeNode fileTreeNode;
	}
}
