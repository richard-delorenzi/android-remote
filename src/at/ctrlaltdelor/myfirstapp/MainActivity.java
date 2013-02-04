package at.ctrlaltdelor.myfirstapp;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        //android.content.Intent service = new android.content.Intent(this, Service.class);
        //this.startService(service); 
        
        Thread thread = new Thread()
        {
            @Override
            public void run() {
            	try {
         			Thread.sleep(20000);
         		} catch (InterruptedException e) {
         			// TODO Auto-generated catch block
         			e.printStackTrace();
         		}
            	
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:123456789"));
                startActivity(callIntent);
            }
        };
       
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
