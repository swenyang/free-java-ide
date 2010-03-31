import java.io.*;
/*
名称：	编译运行器Build.java
功能：	负责创建线程进行编译和运行一个文件
*/

class Build implements Runnable
{
	//编译器线程
	Thread compiler=null; 
	//运行器线程
	Thread run_prom=null;
	//路径
	String path=null;
	//文件名
	String flie_name=null;
	//编译产生的信息流
	StringBuffer comp_str=new StringBuffer();
	//运行产生的信息流
	StringBuffer dos_out_str=new StringBuffer();

	//构造函数，线程初始化
	Build()
	{
      compiler=new Thread(this);
      run_prom=new Thread(this);
	}

	//编译过程
	public void compile(String comp_flie_name)
	{
		flie_name=new String(comp_flie_name);
		if(!(compiler.isAlive()))
		{
			compiler=new Thread(this);
		}
		try{
			compiler.start();
		}
        catch(Exception eee){}
	}
	
	//运行过程
	public void prom_run(String run_path)
	{ 
		path=new String(run_path);
		if(!(run_prom.isAlive()))
		{
			run_prom =new Thread(this);
		}
        try{
			run_prom.start();
		}
        catch(Exception eee){}
	}
	
	//获取编译信息流
	public String getCompileStr()
	{
		String temp=new String(comp_str.substring(0));
		return temp;
	}
	
	//获取运行信息流
	public String getDosOutStr()
	{
		String temp=new String(dos_out_str.substring(0));
		return temp;
	}
	
	//重载的run函数，是线程主过程，具体实现编译和运行
	public void run()
	{  
		//当前的任务是编译
		if(Thread.currentThread()==compiler)
        { 
			comp_str=new StringBuffer();
			try{
				Runtime ce=Runtime.getRuntime();
				//执行编译，并截取错误流信息
                InputStream in=ce.exec("javac "+flie_name).getErrorStream();
                BufferedInputStream bin=new BufferedInputStream(in);
                byte shuzu[]=new byte[100];
                int n;
				boolean bn=true;
				//将错误流信息保存到comp_str
                while((n=bin.read(shuzu,0,100))!=-1)
				{
					String s=null;
					s=new String(shuzu,0,n);
					comp_str.append(s);
					if(s!=null) bn=false;
                }
                if(bn) 
				{  
					comp_str.append("编译正确"); 
				} 
			} 
			catch(IOException e1){} 
		}
		//当前的任务是运行
		else if(Thread.currentThread()==run_prom)
		{  
			dos_out_str=new StringBuffer();			
			try
			{
				File dir = new File( path.substring(0,path.lastIndexOf('\\')) );
				Runtime ce=Runtime.getRuntime();
				//运行程序，并截取输出流
				OutputStream in=ce.exec("java "+path.substring(path.lastIndexOf('\\')+1,path.length()),
					null,
					dir).getOutputStream();
				BufferedOutputStream bin=new BufferedOutputStream(in);
                byte zu[]=new byte[15000];
                int m;String s=null;
                //保存到comp_str
                bin.write(zu,0,15000);
					s=new String(zu,0,15000);
					dos_out_str.append(s);
            }
            catch(IOException e1){} 
        } 
   }
}