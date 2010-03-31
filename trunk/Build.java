import java.io.*;
/*
���ƣ�	����������Build.java
���ܣ�	���𴴽��߳̽��б��������һ���ļ�
*/

class Build implements Runnable
{
	//�������߳�
	Thread compiler=null; 
	//�������߳�
	Thread run_prom=null;
	//·��
	String path=null;
	//�ļ���
	String flie_name=null;
	//�����������Ϣ��
	StringBuffer comp_str=new StringBuffer();
	//���в�������Ϣ��
	StringBuffer dos_out_str=new StringBuffer();

	//���캯�����̳߳�ʼ��
	Build()
	{
      compiler=new Thread(this);
      run_prom=new Thread(this);
	}

	//�������
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
	
	//���й���
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
	
	//��ȡ������Ϣ��
	public String getCompileStr()
	{
		String temp=new String(comp_str.substring(0));
		return temp;
	}
	
	//��ȡ������Ϣ��
	public String getDosOutStr()
	{
		String temp=new String(dos_out_str.substring(0));
		return temp;
	}
	
	//���ص�run���������߳������̣�����ʵ�ֱ��������
	public void run()
	{  
		//��ǰ�������Ǳ���
		if(Thread.currentThread()==compiler)
        { 
			comp_str=new StringBuffer();
			try{
				Runtime ce=Runtime.getRuntime();
				//ִ�б��룬����ȡ��������Ϣ
                InputStream in=ce.exec("javac "+flie_name).getErrorStream();
                BufferedInputStream bin=new BufferedInputStream(in);
                byte shuzu[]=new byte[100];
                int n;
				boolean bn=true;
				//����������Ϣ���浽comp_str
                while((n=bin.read(shuzu,0,100))!=-1)
				{
					String s=null;
					s=new String(shuzu,0,n);
					comp_str.append(s);
					if(s!=null) bn=false;
                }
                if(bn) 
				{  
					comp_str.append("������ȷ"); 
				} 
			} 
			catch(IOException e1){} 
		}
		//��ǰ������������
		else if(Thread.currentThread()==run_prom)
		{  
			dos_out_str=new StringBuffer();			
			try
			{
				File dir = new File( path.substring(0,path.lastIndexOf('\\')) );
				Runtime ce=Runtime.getRuntime();
				//���г��򣬲���ȡ�����
				OutputStream in=ce.exec("java "+path.substring(path.lastIndexOf('\\')+1,path.length()),
					null,
					dir).getOutputStream();
				BufferedOutputStream bin=new BufferedOutputStream(in);
                byte zu[]=new byte[15000];
                int m;String s=null;
                //���浽comp_str
                bin.write(zu,0,15000);
					s=new String(zu,0,15000);
					dos_out_str.append(s);
            }
            catch(IOException e1){} 
        } 
   }
}