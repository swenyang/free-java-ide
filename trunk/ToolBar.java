/*
���ƣ�	������ToolBar.java
���ܣ�	ToolBar�����JToolBar������
	�������½����򿪡����桢����ȫ�������롢���С����������������ơ�ճ��ʮһ����ť
	��components��̬ȫ�����������Action���ֻ�ȡ��ť������
	��components��Դ��rb�л�ȡ��ʾ��Ϣ��������ͼ��
	����Ϊ������빤�����С�
*/

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Insets;

public class ToolBar extends JToolBar
{
	//����JButton���ͳ�Ա����
	JButton createNewFileButton;
	JButton openFileButton;
	JButton saveFileButton;
	JButton saveAllFileButton;
	JButton compileButton;
	JButton runButton;
	JButton undoButton;
	JButton redoButton;
	JButton copyButton;
	JButton pasteButton;
	JButton cutButton;
		
	//���캯��Ϊ��
	ToolBar(){}

	//��ʼ������
	public void initialize()
	{
		//����11����ť�������Դ�components��� Action���ֻ�ȡ
		createNewFileButton = new JButton(Components.createNewFileAction);
		openFileButton = new JButton(Components.openFileAction);
		saveFileButton = new JButton(Components.saveFileAction);
		saveAllFileButton = new JButton(Components.saveAllFileAction);
		compileButton = new JButton(Components.compileAction);
		runButton = new JButton(Components.runAction);
		undoButton = new JButton(Components.undoAction);
		redoButton = new JButton(Components.redoAction);
		copyButton = new JButton(Components.copyAction);
		pasteButton = new JButton(Components.pasteAction);
		cutButton = new JButton(Components.cutAction);

		//��ť���ı�Ϊ��
		createNewFileButton.setText(null);
		createNewFileButton.setText(null);
		openFileButton.setText(null);
		saveFileButton.setText(null);
		saveAllFileButton.setText(null);
		compileButton.setText(null);
		runButton.setText(null);
		undoButton.setText(null);
		redoButton.setText(null);
		copyButton.setText(null);
		pasteButton.setText(null);
		cutButton.setText(null);
		
		//ͨ��images�ļ������ͼƬ�����ð�ť��ͼ��
		createNewFileButton.setIcon(new ImageIcon("images/createNewFileButton.gif"));
		openFileButton.setIcon(new ImageIcon("images/openFileButton.gif"));
		saveFileButton.setIcon(new ImageIcon("images/saveFileButton.gif"));
		saveAllFileButton.setIcon(new ImageIcon("images/saveAllFileButton.gif"));
		compileButton.setIcon(new ImageIcon("images/compileButton.gif"));
		runButton.setIcon(new ImageIcon("images/runButton.gif"));
		undoButton.setIcon(new ImageIcon("images/undoButton.gif"));
		redoButton.setIcon(new ImageIcon("images/redoButton.gif"));
		copyButton.setIcon(new ImageIcon("images/copyButton.gif"));
		pasteButton.setIcon(new ImageIcon("images/pasteButton.gif"));
		cutButton.setIcon(new ImageIcon("images/cutButton.gif"));
		
		//ͨ��ȫ����Դ��rb�����ð�ť����ʾ��Ϣ
		createNewFileButton.setToolTipText(Components.rb.getString("Create\u0020new\u0020file"));
		openFileButton.setToolTipText(Components.rb.getString("Open\u0020files"));
		saveFileButton.setToolTipText(Components.rb.getString("Save\u0020a\u0020file"));
		saveAllFileButton.setToolTipText(Components.rb.getString("Save\u0020all\u0020files"));
		compileButton.setToolTipText(Components.rb.getString("Compile\u0020file"));
		runButton.setToolTipText(Components.rb.getString("Run\u0020file"));
		undoButton.setToolTipText(Components.rb.getString("Undo"));
		redoButton.setToolTipText(Components.rb.getString("Redo"));
		copyButton.setToolTipText(Components.rb.getString("Copy"));
		pasteButton.setToolTipText(Components.rb.getString("Paste"));
		cutButton.setToolTipText(Components.rb.getString("Cut"));
		
		//�趨��ť�ı߾�
		Insets _0 = new Insets(0,0,0,0);
		createNewFileButton.setMargin(_0);
		openFileButton.setMargin(_0);
		saveFileButton.setMargin(_0);
		saveAllFileButton.setMargin(_0);
		compileButton.setMargin(_0);
		runButton.setMargin(_0);
		undoButton.setMargin(_0);
		redoButton.setMargin(_0);
		copyButton.setMargin(_0);
		pasteButton.setMargin(_0);
		cutButton.setMargin(_0);
		
		//��ȫ�־�̬�齨����Components��¼���ť�ı������趨��ť�ɼ���
		createNewFileButton.setVisible(Components.VisibleCreateNew);
		openFileButton.setVisible(Components.VisibleOpenFile);
		saveFileButton.setVisible(Components.VisibleSaveFile);
		saveAllFileButton.setVisible(Components.VisibleSaveAll);
		compileButton.setVisible(Components.VisibleCompile);
		runButton.setVisible(Components.VisibleRun);
		undoButton.setVisible(Components.VisibleUndo);
		redoButton.setVisible(Components.VisibleRedo);
		copyButton.setVisible(Components.VisibleCopy);
		pasteButton.setVisible(Components.VisiblePaste);
		cutButton.setVisible(Components.VisibleCut);
		
		//��Ϊ������빤����
		add(createNewFileButton);
		add(openFileButton);
		addSeparator();
		add(saveFileButton);
		add(saveAllFileButton);
		addSeparator();
		add(copyButton);
		add(pasteButton);
		add(cutButton);
		addSeparator();
		add(compileButton);
		add(runButton);
		addSeparator();
		add(undoButton);
		add(redoButton);
		
		//��ȫ�־�̬�齨����Components��¼�������ɼ��Եı������趨Components�ɼ���
		setVisible(Components.ToolBarVisible);
	}
}