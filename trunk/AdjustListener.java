/*
���ƣ�	�ɼ�λ�ƴ��������AdjustListener.java
���ܣ�	AdjustListener��ComponentListener ������
	ʵ����ComponentListener�ӿ�
	ʵ�ֱ༭�����ṹ��ͼ���Ŷӽ������������ر�ʱ���¼�����
	�����ƶ�ʱ��������Χ������Ч����ʵ��
*/

import java.awt.event.ComponentEvent;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ComponentListener;

public class AdjustListener implements ComponentListener
{
    	//����˽�г�Աint�ͱ���offsetX1��offsetX2��offsetY1��offsetY2
	private int offsetX1;
	private int offsetY1;
	private int offsetX2;
	private int offsetY2;
	//dimension��ȡ��Ļ��Ϣ
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	//���캯��Ϊ��
	AdjustListener()
	{
	}
	//����ر��¼�����
	public void componentHidden(ComponentEvent e)
	{
	    if(e.getComponent() instanceof EditArea)//�༭���ر�
	    {
		    Components.comp_Config.saveConfig();//����������Ϣ
		    System.exit(0);//�˳�
	    }
	    if(e.getComponent() instanceof TreeViewer)//�ṹ��ͼ�ر�
	    {
		Components.TreeViewerVisible = false;//����ȫ�ֽṹ��ͼ�ɼ���Ϊ��
		Components.viewComponents.viewTreeViewer.setState(false);//�ɼ��Կ��������Ӧ���
		Components.comp_TreeViewer.setVisible(false);//���ؽṹ��ͼ
	    }
	    if(e.getComponent() instanceof CommunicateArea)//�������ر�
	    {
		Components.CommunicateAreaVisible = false;//���½������ɼ���Ϊ��
		Components.viewComponents.viewCommunicateArea.setState(false);//�ɼ��Կ��������Ӧ���
		Components.comp_CommunicateArea.setVisible(false);//���ؽ�����
	    }
	}	
	public void componentMoved(ComponentEvent e)//��������ƶ�
	{
	    if(Components.MagneticDistance <= 0)//��������<=0����������
		return;
		//����ֲ�int�ͱ�����left��top��
		int left = e.getComponent().getX();
		int top = e.getComponent().getY();
		if(left == -4 && top == -4)
		    return;
		if (top <= 0 + Components.MagneticDistance) {
		  top = 0;
		}
		if(e.getComponent() instanceof EditArea)//�༭�������ƶ��¼�
		{
			if(Components.TreeViewerVisible)//����ṹ��ͼ�ɼ�����Զ������ֿ��ܷ���������λ�ù�ϵ���ֱ���㲢ʵ������Ч��
			{
				if( (left >= Components.rightOfTreeViewer) &&
					(left - Components.rightOfTreeViewer <= Components.MagneticDistance) &&
					((top <= Components.bottomOfTreeViewer && top + Components.comp_EditArea.getHeight() >=  Components.bottomOfTreeViewer) ||
					    (top <= Components.topOfTreeViewer && top + Components.comp_EditArea.getHeight() >= Components.topOfTreeViewer) ||
					    (top >= Components.topOfTreeViewer && top + Components.comp_EditArea.getHeight() <= Components.bottomOfTreeViewer))
					)
				{
					Components.leftOfEditArea = Components.rightOfTreeViewer;
					Components.topOfEditArea = top;
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfEditArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredTreeViewerAndEditArea = true;
					return;
				}
				if( (left + Components.comp_EditArea.getWidth() <= Components.leftOfTreeViewer) &&
					(Components.leftOfTreeViewer - left - Components.comp_EditArea.getWidth() <= Components.MagneticDistance) &&
					((top <= Components.bottomOfTreeViewer && top + Components.comp_EditArea.getHeight() >=  Components.bottomOfTreeViewer) ||
					    (top <= Components.topOfTreeViewer && top + Components.comp_EditArea.getHeight() >= Components.topOfTreeViewer) ||
					    (top >= Components.topOfTreeViewer && top + Components.comp_EditArea.getHeight() <= Components.bottomOfTreeViewer))
					)
				{
					Components.leftOfEditArea = Components.leftOfTreeViewer - Components.comp_EditArea.getWidth();
					Components.topOfEditArea = top;
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfEditArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredTreeViewerAndEditArea = true;
					return;
				}
				if( (top + Components.comp_EditArea.getHeight() <= Components.topOfTreeViewer) &&
					(Components.topOfTreeViewer - top - Components.comp_EditArea.getHeight() <= Components.MagneticDistance) &&
					((left <= Components.leftOfTreeViewer && left + Components.comp_EditArea.getWidth() >=  Components.leftOfTreeViewer) ||
					    (left <= Components.rightOfTreeViewer && left + Components.comp_EditArea.getWidth() >= Components.rightOfTreeViewer) ||
					    (left >= Components.leftOfTreeViewer && left + Components.comp_EditArea.getWidth() <= Components.rightOfTreeViewer))
					)
				{
					Components.leftOfEditArea = left;
					Components.topOfEditArea = Components.topOfTreeViewer - Components.comp_EditArea.getHeight();
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfEditArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredTreeViewerAndEditArea = true;
					return;
				}
				if( (top >= Components.bottomOfTreeViewer) &&
					(top - Components.bottomOfTreeViewer <= Components.MagneticDistance) &&
					((left <= Components.leftOfTreeViewer && left + Components.comp_EditArea.getWidth() >=  Components.leftOfTreeViewer) ||
					    (left <= Components.rightOfTreeViewer && left + Components.comp_EditArea.getWidth() >= Components.rightOfTreeViewer) ||
					    (left >= Components.leftOfTreeViewer && left + Components.comp_EditArea.getWidth() <= Components.rightOfTreeViewer))
					)
				{
					Components.leftOfEditArea = left;
					Components.topOfEditArea = Components.bottomOfTreeViewer;
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfEditArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					return;
				}
			}

			if(Components.CommunicateAreaVisible)//����������ɼ�������Զ������ֿ��ܷ���������λ�ù�ϵ���ֱ���㲢ʵ������Ч��
			{
				if( (left >= Components.rightOfCommunicateArea) &&
					(left - Components.rightOfCommunicateArea <= Components.MagneticDistance) &&
					((top <= Components.bottomOfCommunicateArea && top + Components.comp_EditArea.getHeight() >=  Components.bottomOfCommunicateArea) ||
					    (top <= Components.topOfCommunicateArea && top + Components.comp_EditArea.getHeight() >= Components.topOfCommunicateArea) ||
					    (top >= Components.topOfCommunicateArea && top + Components.comp_EditArea.getHeight() <= Components.bottomOfCommunicateArea))
					)
				{
					Components.leftOfEditArea = Components.rightOfCommunicateArea;
					Components.topOfEditArea = top;
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfCommunicateArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredCommunicateAreaAndEditArea = true;
					return;
				}
				if( (left + Components.comp_EditArea.getWidth() <= Components.leftOfCommunicateArea) &&
					(Components.leftOfCommunicateArea - left - Components.comp_EditArea.getWidth() <= Components.MagneticDistance) &&
					((top <= Components.bottomOfCommunicateArea && top + Components.comp_EditArea.getHeight() >=  Components.bottomOfCommunicateArea) ||
					    (top <= Components.topOfCommunicateArea && top + Components.comp_EditArea.getHeight() >= Components.topOfCommunicateArea)  ||
					    (top >= Components.topOfCommunicateArea && top + Components.comp_EditArea.getHeight() <= Components.bottomOfCommunicateArea))
					)
				{
					Components.leftOfEditArea = Components.leftOfCommunicateArea - Components.comp_EditArea.getWidth();
					Components.topOfEditArea = top;
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfCommunicateArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredCommunicateAreaAndEditArea = true;
					return;
				}
				if( (top + Components.comp_EditArea.getHeight() <= Components.topOfCommunicateArea) &&
					(Components.topOfCommunicateArea - top - Components.comp_EditArea.getHeight() <= Components.MagneticDistance) &&
					((left <= Components.leftOfCommunicateArea && left + Components.comp_EditArea.getWidth() >=  Components.leftOfCommunicateArea) ||
					    (left <= Components.rightOfCommunicateArea && left + Components.comp_EditArea.getWidth() >= Components.rightOfCommunicateArea) ||
					    (left >= Components.leftOfCommunicateArea && left + Components.comp_EditArea.getWidth() <= Components.rightOfCommunicateArea))
					)
				{
					Components.leftOfEditArea = left;
					Components.topOfEditArea = Components.topOfCommunicateArea - Components.comp_EditArea.getHeight();
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfCommunicateArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredCommunicateAreaAndEditArea = true;
					return;
				}
				if((top >= Components.bottomOfCommunicateArea) &&
					(top - Components.bottomOfCommunicateArea <= Components.MagneticDistance) &&
					((left <= Components.leftOfCommunicateArea && left + Components.comp_EditArea.getWidth() >=  Components.leftOfCommunicateArea) ||
					(left <= Components.rightOfCommunicateArea && left + Components.comp_EditArea.getWidth() >= Components.rightOfCommunicateArea) ||
					    (left >= Components.leftOfCommunicateArea && left + Components.comp_EditArea.getWidth() <= Components.rightOfCommunicateArea))
					)
				{
					Components.leftOfEditArea = left;
					Components.topOfEditArea = Components.bottomOfCommunicateArea;
					e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
					Components.rightOfEditArea = Components.leftOfCommunicateArea + Components.comp_EditArea.getWidth();
					Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
					//Components.isAdheredCommunicateAreaAndEditArea = true;
					return;
				}
			}
			
			    //Components.isAdheredCommunicateAreaAndEditArea = false;
			Components.leftOfEditArea = left;
			Components.topOfEditArea = top;
			e.getComponent().setLocation(Components.leftOfEditArea, Components.topOfEditArea);
			Components.rightOfEditArea = Components.leftOfCommunicateArea + Components.comp_EditArea.getWidth();
			Components.bottomOfEditArea = Components.topOfEditArea + Components.comp_EditArea.getHeight();
		}

		
		
		
		else if(e.getComponent() instanceof TreeViewer)//�ṹ��ͼ�������ƶ��¼�
		{
				if( (left >= Components.rightOfEditArea) &&
					(left - Components.rightOfEditArea <= Components.MagneticDistance) &&
					((top <= Components.bottomOfEditArea && top + Components.comp_TreeViewer.getHeight() >=  Components.bottomOfEditArea) ||
					    (top <= Components.topOfEditArea && top + Components.comp_TreeViewer.getHeight() >= Components.topOfEditArea) ||
					    (top >= Components.topOfEditArea && top + Components.comp_TreeViewer.getHeight() <= Components.bottomOfEditArea))
					)
				{
					Components.leftOfTreeViewer = Components.rightOfEditArea;
					Components.topOfTreeViewer = top;
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfEditArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredEditAreaAndTreeViewer = true;
					return;
				}
				if( (left + Components.comp_TreeViewer.getWidth() <= Components.leftOfEditArea) &&
					(Components.leftOfEditArea - left - Components.comp_TreeViewer.getWidth() <= Components.MagneticDistance) &&
					((top <= Components.bottomOfEditArea && top + Components.comp_TreeViewer.getHeight() >=  Components.bottomOfEditArea) ||
					    (top <= Components.topOfEditArea && top + Components.comp_TreeViewer.getHeight() >= Components.topOfEditArea)  ||
					    (top >= Components.topOfEditArea && top + Components.comp_TreeViewer.getHeight() <= Components.bottomOfEditArea))
					)
				{
					Components.leftOfTreeViewer = Components.leftOfEditArea - Components.comp_TreeViewer.getWidth();
					Components.topOfTreeViewer = top;
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfEditArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredEditAreaAndTreeViewer = true;
					return;
				}
				if( (top + Components.comp_TreeViewer.getHeight() <= Components.topOfEditArea) &&
					(Components.topOfEditArea - top - Components.comp_TreeViewer.getHeight() <= Components.MagneticDistance) &&
					((left <= Components.leftOfEditArea && left + Components.comp_TreeViewer.getWidth() >=  Components.leftOfEditArea) ||
					    (left <= Components.rightOfEditArea && left + Components.comp_TreeViewer.getWidth() >= Components.rightOfEditArea) ||
					    (left >= Components.leftOfEditArea && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfEditArea))
					)
				{
					Components.leftOfTreeViewer = left;
					Components.topOfTreeViewer = Components.topOfEditArea - Components.comp_TreeViewer.getHeight();
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfEditArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredEditAreaAndTreeViewer = true;
					return;
				}
				if((top >= Components.bottomOfEditArea) &&
					(top - Components.bottomOfEditArea <= Components.MagneticDistance) &&
					((left <= Components.leftOfEditArea && left + Components.comp_TreeViewer.getWidth() >=  Components.leftOfEditArea) ||
					    (left <= Components.rightOfEditArea && left + Components.comp_TreeViewer.getWidth() >= Components.rightOfEditArea) ||
					    (left >= Components.leftOfEditArea && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfEditArea))
					)
				{
					Components.leftOfTreeViewer = left;
					Components.topOfTreeViewer = Components.bottomOfEditArea;
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfEditArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredEditAreaAndTreeViewer = true;
					return;
				}
			
			if(Components.CommunicateAreaVisible){
				if( (left >= Components.rightOfCommunicateArea) &&
					(left - Components.rightOfCommunicateArea <= Components.MagneticDistance) &&
					((top <= Components.bottomOfCommunicateArea && top + Components.comp_TreeViewer.getHeight() >=  Components.bottomOfCommunicateArea) ||
					    (top <= Components.topOfCommunicateArea && top + Components.comp_TreeViewer.getHeight() >= Components.topOfCommunicateArea) ||
					    (top >= Components.topOfCommunicateArea && top + Components.comp_TreeViewer.getHeight() <= Components.bottomOfCommunicateArea))
					)
				{
					Components.leftOfTreeViewer = Components.rightOfCommunicateArea;
					Components.topOfTreeViewer = top;
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfCommunicateArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredCommunicateAreaAndTreeViewer = true;
					return;
				}
				if( (left + Components.comp_TreeViewer.getWidth() <= Components.leftOfCommunicateArea) &&
					(Components.leftOfCommunicateArea - left - Components.comp_TreeViewer.getWidth() <= Components.MagneticDistance) &&
					((top <= Components.bottomOfCommunicateArea && top + Components.comp_TreeViewer.getHeight() >=  Components.bottomOfCommunicateArea) ||
					    (top <= Components.topOfCommunicateArea && top + Components.comp_TreeViewer.getHeight() >= Components.topOfCommunicateArea) ||
					    (top >= Components.topOfCommunicateArea && top + Components.comp_TreeViewer.getHeight() <= Components.bottomOfCommunicateArea))
					)
				{
					Components.leftOfTreeViewer = Components.leftOfCommunicateArea - Components.comp_TreeViewer.getWidth();
					Components.topOfTreeViewer = top;
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfCommunicateArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredCommunicateAreaAndTreeViewer = true;
					return;
				}
				if((top + Components.comp_TreeViewer.getHeight() <= Components.topOfCommunicateArea) &&
					(Components.topOfCommunicateArea - top - Components.comp_TreeViewer.getHeight() <= Components.MagneticDistance) &&
					((left <= Components.leftOfCommunicateArea && left + Components.comp_TreeViewer.getWidth() >=  Components.leftOfCommunicateArea) ||
					    (left <= Components.rightOfCommunicateArea && left + Components.comp_TreeViewer.getWidth() >= Components.rightOfCommunicateArea) ||
					    (left >= Components.leftOfCommunicateArea && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfCommunicateArea))
					)
				{
					Components.leftOfTreeViewer = left;
					Components.topOfTreeViewer = Components.topOfCommunicateArea - Components.comp_TreeViewer.getHeight();
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfCommunicateArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredCommunicateAreaAndTreeViewer = true;
					return;
				}
				if( (top >= Components.bottomOfCommunicateArea) &&
					(top - Components.bottomOfCommunicateArea <= Components.MagneticDistance) &&
					((left <= Components.leftOfCommunicateArea && left + Components.comp_TreeViewer.getWidth() >=  Components.leftOfCommunicateArea) ||
					    (left <= Components.rightOfCommunicateArea && left + Components.comp_TreeViewer.getWidth() >= Components.rightOfCommunicateArea) ||
					    (left >= Components.leftOfCommunicateArea && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfCommunicateArea))
					)
				{
					Components.leftOfTreeViewer = left;
					Components.topOfTreeViewer = Components.bottomOfCommunicateArea;
					e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
					Components.rightOfTreeViewer = Components.leftOfCommunicateArea + Components.comp_TreeViewer.getWidth();
					Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
					//Components.isAdheredCommunicateAreaAndTreeViewer = true;
					return;
				}
			}
			
			    //Components.isAdheredCommunicateAreaAndTreeViewer = false;
			Components.leftOfTreeViewer = left;
			Components.topOfTreeViewer = top;
			e.getComponent().setLocation(Components.leftOfTreeViewer, Components.topOfTreeViewer);
			Components.rightOfTreeViewer = Components.leftOfCommunicateArea + Components.comp_TreeViewer.getWidth();
			Components.bottomOfTreeViewer = Components.topOfTreeViewer + Components.comp_TreeViewer.getHeight();
		}
		
		
		
		
		
		else if(e.getComponent() instanceof CommunicateArea)//�����������ƶ��¼�
		{
				if( (left >= Components.rightOfEditArea) &&
					(left - Components.rightOfEditArea <= Components.MagneticDistance) &&
					((top <= Components.bottomOfEditArea && top + Components.comp_CommunicateArea.getHeight() >=  Components.bottomOfEditArea) ||
					    (top <= Components.topOfEditArea && top + Components.comp_CommunicateArea.getHeight() >= Components.topOfEditArea) ||
					    (top >= Components.topOfEditArea && top + Components.comp_CommunicateArea.getHeight() <= Components.bottomOfEditArea))
					)
				{
					Components.leftOfCommunicateArea = Components.rightOfEditArea;
					Components.topOfCommunicateArea = top;
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfEditArea + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredEditAreaAndCommunicateArea = true;
					return;
				}
				if( (left + Components.comp_CommunicateArea.getWidth() <= Components.leftOfEditArea) &&
					(Components.leftOfEditArea - left - Components.comp_CommunicateArea.getWidth() <= Components.MagneticDistance) &&
					((top <= Components.bottomOfEditArea && top + Components.comp_CommunicateArea.getHeight() >=  Components.bottomOfEditArea) ||
					    (top <= Components.topOfEditArea && top + Components.comp_CommunicateArea.getHeight() >= Components.topOfEditArea) ||
					    (top >= Components.topOfEditArea && top + Components.comp_CommunicateArea.getHeight() <= Components.bottomOfEditArea))
					)
				{
					Components.leftOfCommunicateArea = Components.leftOfEditArea - Components.comp_CommunicateArea.getWidth();
					Components.topOfCommunicateArea = top;
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfEditArea + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredEditAreaAndCommunicateArea = true;
					return;
				}
				if( (top + Components.comp_CommunicateArea.getHeight() <= Components.topOfEditArea) &&
					(Components.topOfEditArea - top - Components.comp_CommunicateArea.getHeight() <= Components.MagneticDistance) &&
					((left <= Components.leftOfEditArea && left + Components.comp_CommunicateArea.getWidth() >=  Components.leftOfEditArea) ||
					    (left <= Components.rightOfEditArea && left + Components.comp_CommunicateArea.getWidth() >= Components.rightOfEditArea) ||
					    (left >= Components.leftOfEditArea && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfEditArea))
					)
				{
					Components.leftOfCommunicateArea = left;
					Components.topOfCommunicateArea = Components.topOfEditArea - Components.comp_CommunicateArea.getHeight();
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfEditArea + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredEditAreaAndCommunicateArea = true;
					return;
				}
				if( (top >= Components.bottomOfEditArea) &&
					(top - Components.bottomOfEditArea <= Components.MagneticDistance) &&
					((left <= Components.leftOfEditArea && left + Components.comp_CommunicateArea.getWidth() >=  Components.leftOfEditArea) ||
					    (left <= Components.rightOfEditArea && left + Components.comp_CommunicateArea.getWidth() >= Components.rightOfEditArea) ||
					    (left >= Components.leftOfEditArea && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfEditArea))
					)
				{
					Components.leftOfCommunicateArea = left;
					Components.topOfCommunicateArea = Components.bottomOfEditArea;
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfEditArea + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredEditAreaAndCommunicateArea = true;
					return;
				}
			
			if(Components.TreeViewerVisible)
			{
				if((left >= Components.rightOfTreeViewer) &&
					(left - Components.rightOfTreeViewer <= Components.MagneticDistance) &&
					((top <= Components.bottomOfTreeViewer && top + Components.comp_CommunicateArea.getHeight() >=  Components.bottomOfTreeViewer) ||
					(top <= Components.topOfTreeViewer && top + Components.comp_CommunicateArea.getHeight() >= Components.topOfTreeViewer) ||
					(top >= Components.topOfTreeViewer && top + Components.comp_CommunicateArea.getHeight() <= Components.bottomOfTreeViewer))
				    )
				{
					Components.leftOfCommunicateArea = Components.rightOfTreeViewer;
					Components.topOfCommunicateArea = top;
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfTreeViewer + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredTreeViewerAndCommunicateArea = true;
					return;
				}
				if( (left + Components.comp_CommunicateArea.getWidth() <= Components.leftOfTreeViewer) &&
					(Components.leftOfTreeViewer - left - Components.comp_CommunicateArea.getWidth() <= Components.MagneticDistance) &&
					((top <= Components.bottomOfTreeViewer && top + Components.comp_CommunicateArea.getHeight() >=  Components.bottomOfTreeViewer) ||
					    (top <= Components.topOfTreeViewer && top + Components.comp_CommunicateArea.getHeight() >= Components.topOfTreeViewer) ||
					    (top >= Components.topOfTreeViewer && top + Components.comp_CommunicateArea.getHeight() <= Components.bottomOfTreeViewer))
					)
				{
					Components.leftOfCommunicateArea = Components.leftOfTreeViewer - Components.comp_CommunicateArea.getWidth();
					Components.topOfCommunicateArea = top;
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfTreeViewer + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredTreeViewerAndCommunicateArea = true;
					return;
				}
				if( (top + Components.comp_CommunicateArea.getHeight() <= Components.topOfTreeViewer) &&
					(Components.topOfTreeViewer - top - Components.comp_CommunicateArea.getHeight() <= Components.MagneticDistance) &&
					((left <= Components.leftOfTreeViewer && left + Components.comp_CommunicateArea.getWidth() >=  Components.leftOfTreeViewer) ||
					    (left <= Components.rightOfTreeViewer && left + Components.comp_CommunicateArea.getWidth() >= Components.rightOfTreeViewer) ||
					    (left >= Components.leftOfTreeViewer && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfTreeViewer))
					)
				{
					Components.leftOfCommunicateArea = left;
					Components.topOfCommunicateArea = Components.topOfTreeViewer - Components.comp_CommunicateArea.getHeight();
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfTreeViewer + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredTreeViewerAndCommunicateArea = true;
					return;
				}
				if( (top >= Components.bottomOfTreeViewer) &&
					(top - Components.bottomOfTreeViewer <= Components.MagneticDistance) &&
					((left <= Components.leftOfTreeViewer && left + Components.comp_CommunicateArea.getWidth() >=  Components.leftOfTreeViewer) ||
					    (left <= Components.rightOfTreeViewer && left + Components.comp_CommunicateArea.getWidth() >= Components.rightOfTreeViewer) ||
					    (left >= Components.leftOfTreeViewer && left + Components.comp_TreeViewer.getWidth() <= Components.rightOfTreeViewer))
					)
				{
					Components.leftOfCommunicateArea = left;
					Components.topOfCommunicateArea = Components.bottomOfTreeViewer;
					e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
					Components.rightOfCommunicateArea = Components.leftOfTreeViewer + Components.comp_CommunicateArea.getWidth();
					Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
					//Components.isAdheredTreeViewerAndCommunicateArea = true;
					return;
				}
			}
			
			    //Components.isAdheredTreeViewerAndCommunicateArea = false;
			Components.leftOfCommunicateArea = left;
			Components.topOfCommunicateArea = top;
			e.getComponent().setLocation(Components.leftOfCommunicateArea, Components.topOfCommunicateArea);
			Components.rightOfCommunicateArea = Components.leftOfTreeViewer + Components.comp_CommunicateArea.getWidth();
			Components.bottomOfCommunicateArea = Components.topOfCommunicateArea + Components.comp_CommunicateArea.getHeight();
		}
	}
	public void componentResized(ComponentEvent e)//������С�ı��¼�
	{
	    //����¼�Դ���༭�����ṹ��ͼ�������������ֱ����Components������꣨left*��top*������right*��bottom*��
	    if(e.getComponent() instanceof EditArea)
	    {
		    Components.leftOfEditArea = Components.comp_EditArea.getX();
		    Components.topOfEditArea = Components.comp_EditArea.getY();
		    Components.rightOfEditArea = Components.comp_EditArea.getX() + Components.comp_EditArea.getWidth();
		    Components.bottomOfEditArea = Components.comp_EditArea.getY() + Components.comp_EditArea.getHeight();
	    }
	    if(e.getComponent() instanceof TreeViewer)
	    {
		Components.leftOfTreeViewer = Components.comp_TreeViewer.getX();
		    Components.topOfTreeViewer = Components.comp_TreeViewer.getY();
		    Components.rightOfTreeViewer = Components.comp_TreeViewer.getX() + Components.comp_TreeViewer.getWidth();
		    Components.bottomOfTreeViewer = Components.comp_TreeViewer.getY() + Components.comp_TreeViewer.getHeight();
	    }
	    if(e.getComponent() instanceof CommunicateArea)
	    {
		Components.leftOfCommunicateArea = Components.comp_CommunicateArea.getX();
		    Components.topOfCommunicateArea = Components.comp_CommunicateArea.getY();
		    Components.rightOfCommunicateArea = Components.comp_CommunicateArea.getX() + Components.comp_CommunicateArea.getWidth();
		    Components.bottomOfCommunicateArea = Components.comp_CommunicateArea.getY() + Components.comp_CommunicateArea.getHeight();
	    }
	}
	public void componentShown(ComponentEvent e)//������ʾ�¼�
	{
	    if(e.getComponent() instanceof EditArea)
	    {
	    }
	    if(e.getComponent() instanceof TreeViewer)//��Ϊ�ṹ��ͼ��
	    {
		Components.TreeViewerVisible = true;//����Components��ṹ��ͼ���ɼ��Ա���Ϊ��
		Components.viewComponents.viewTreeViewer.setState(true);//����viewComponents���Ӧ��Ϊ��
	    }
	    if(e.getComponent() instanceof CommunicateArea)//��Ϊ��������
	    {
		Components.CommunicateAreaVisible = true;//����Components�ｻ�����ɼ��Ա���Ϊ��
		Components.viewComponents.viewCommunicateArea.setState(true);//����viewComponents���Ӧ��Ϊ��
	    }
	}
 }