/*
名称：	可见位移处理监听器AdjustListener.java
功能：	AdjustListener是ComponentListener 的子类
	实现了ComponentListener接口
	实现编辑区、结构视图、团队交流区发生被关闭时的事件处理
	发生移动时在吸附范围内吸附效果的实现
*/

import java.awt.event.ComponentEvent;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ComponentListener;

public class AdjustListener implements ComponentListener
{
    	//定义私有成员int型变量offsetX1，offsetX2，offsetY1，offsetY2
	private int offsetX1;
	private int offsetY1;
	private int offsetX2;
	private int offsetY2;
	//dimension获取屏幕信息
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	//构造函数为空
	AdjustListener()
	{
	}
	//处理关闭事件函数
	public void componentHidden(ComponentEvent e)
	{
	    if(e.getComponent() instanceof EditArea)//编辑区关闭
	    {
		    Components.comp_Config.saveConfig();//保存配置信息
		    System.exit(0);//退出
	    }
	    if(e.getComponent() instanceof TreeViewer)//结构视图关闭
	    {
		Components.TreeViewerVisible = false;//更新全局结构视图可见性为假
		Components.viewComponents.viewTreeViewer.setState(false);//可见性控制组件对应项假
		Components.comp_TreeViewer.setVisible(false);//隐藏结构视图
	    }
	    if(e.getComponent() instanceof CommunicateArea)//交流区关闭
	    {
		Components.CommunicateAreaVisible = false;//更新交流区可见性为假
		Components.viewComponents.viewCommunicateArea.setState(false);//可见性控制组件对应项假
		Components.comp_CommunicateArea.setVisible(false);//隐藏交流区
	    }
	}	
	public void componentMoved(ComponentEvent e)//如果发生移动
	{
	    if(Components.MagneticDistance <= 0)//吸附距离<=0不处理、返回
		return;
		//定义局部int型变量（left，top）
		int left = e.getComponent().getX();
		int top = e.getComponent().getY();
		if(left == -4 && top == -4)
		    return;
		if (top <= 0 + Components.MagneticDistance) {
		  top = 0;
		}
		if(e.getComponent() instanceof EditArea)//编辑区发生移动事件
		{
			if(Components.TreeViewerVisible)//如果结构视图可见，针对二者四种可能发生吸附的位置关系，分别计算并实现吸附效果
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

			if(Components.CommunicateAreaVisible)//如果交流区可见，则针对二者四种可能发生吸附的位置关系，分别计算并实现吸附效果
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

		
		
		
		else if(e.getComponent() instanceof TreeViewer)//结构视图区发生移动事件
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
		
		
		
		
		
		else if(e.getComponent() instanceof CommunicateArea)//交流区发生移动事件
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
	public void componentResized(ComponentEvent e)//发生大小改变事件
	{
	    //针对事件源：编辑区、结构视图区、交流区，分别更新Components里的坐标（left*，top*），（right*，bottom*）
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
	public void componentShown(ComponentEvent e)//发生显示事件
	{
	    if(e.getComponent() instanceof EditArea)
	    {
	    }
	    if(e.getComponent() instanceof TreeViewer)//若为结构视图区
	    {
		Components.TreeViewerVisible = true;//更新Components里结构视图区可见性变量为真
		Components.viewComponents.viewTreeViewer.setState(true);//更新viewComponents里对应项为真
	    }
	    if(e.getComponent() instanceof CommunicateArea)//若为交流区区
	    {
		Components.CommunicateAreaVisible = true;//更新Components里交流区可见性变量为真
		Components.viewComponents.viewCommunicateArea.setState(true);//更新viewComponents里对应项为真
	    }
	}
 }