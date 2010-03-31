import java.io.*;

/*
类FreeJava用于运行FreeJava
*/
public class FreeJava
{
	//启动Main，截取输出流，然后立即结束
	public static void main( String args[] )
	{
		Runtime ce=Runtime.getRuntime();
		try
		{
			OutputStream out=ce.exec("java Main").getOutputStream();
		}
		catch( IOException e ){}
	}
}