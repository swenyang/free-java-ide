import java.io.*;

/*
��FreeJava��������FreeJava
*/
public class FreeJava
{
	//����Main����ȡ�������Ȼ����������
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