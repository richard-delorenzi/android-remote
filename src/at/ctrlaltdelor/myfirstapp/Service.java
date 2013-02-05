package at.ctrlaltdelor.myfirstapp;

import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import android.content.Intent;
import android.os.IBinder;

public class Service extends android.app.Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		java.lang.Thread thread = new java.lang.Thread(new Worker(this));
		thread.start();		
        
	    return Service.START_NOT_STICKY;
	}	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

class Worker implements java.lang.Runnable {

	Worker(android.content.Context context)
	{
		this.context = context;
	}
	
	@Override
	public void run() {
		try {
			Listen();
			while (true) {
				Accept();
				Call();
				
				Read();			
				String input = CurrentInput();
				
				if (input.equalsIgnoreCase("hello")) {
					Write("hello world\n");
					out.flush();
				}
				
				Close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private android.content.Context context;
	private java.net.ServerSocket listener;
	private java.net.Socket  service;
	private java.io.OutputStream out;
	private java.io.InputStream  in;
	private String currentInput;
	private int indexOfEnd= -1;
	
	void Listen() throws IOException
	{
		listener = new java.net.ServerSocket(60000);
	}
	void Accept() throws IOException
	{
		service = listener.accept();
		out = service.getOutputStream();
		in  = service.getInputStream();
	}
	void Close() throws IOException 
	{
		out.flush();
		service.close();
	}	
	void Write(String msg) throws IOException 
	{
		out.write(msg.getBytes("utf8"));	
	}
	void Read() throws IOException
	{
		if (indexOfEnd >= 0)
		{
			currentInput = currentInput.substring(indexOfEnd+1);
		}
		indexOfEnd = -1;

		while (indexOfEnd < 0)
		{
			byte[] buf = new byte[in.available()];
			int len = in.read(buf);
			String input = new java.lang.String(buf, 0, len, "utf8");
			currentInput = currentInput + input;
			indexOfEnd=currentInput.indexOf("\n");
		}		
	}
	String CurrentInput()
	{
		return currentInput.substring(0, indexOfEnd).trim();
	}	
	void Call()
	{
		android.content.Intent intent = 
	     		new android.content.Intent(
	       			android.content.Intent.ACTION_CALL, 
	       			android.net.Uri.parse("tel:+447782333123"));
		intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);		
	}
}
