package br.gygaweb.skeedo;


import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

@SuppressLint("CommitTransaction")
public class MainActivity extends Activity{
	
	private ImageButton imagebuttonreuniao,imagebuttontarefa;
	private ImageButton imagebuttonlistar,imageButtonListTarefas;
	private ImageView imageViewEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		Intent myIntent = new Intent(this, BroadcastReceiverPri.class);
		  PendingIntent pendingIntent = PendingIntent.getBroadcast(this,  0, myIntent, 0);

		  AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTimeInMillis(System.currentTimeMillis());
		  calendar.add(Calendar.SECOND, 60); // first time
		  long frequency= 60 * 2000; // in ms 
		  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent); 
		
		imagebuttonreuniao = (ImageButton) findViewById(R.id.imageButton1);
		imagebuttontarefa = (ImageButton) findViewById(R.id.imageButton2);
		imagebuttonlistar = (ImageButton) findViewById(R.id.imageButtonListar);
		imageButtonListTarefas =  (ImageButton) findViewById(R.id.imageButtonListTarefas);
		imageViewEmail = (ImageView) findViewById(R.id.imageViewEmail);
		
		imagebuttonreuniao.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {

				Intent intent =  new Intent(MainActivity.this,ReuniaoAtividade.class);
				startActivity(intent);
				
			}
			
		});
		
		imageViewEmail.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(MainActivity.this,EmailSettingsAtividade.class);
				startActivity(intent);
				
			}
			
		});
		
		imagebuttontarefa.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Intent intent =  new Intent(MainActivity.this,TarefaAtividade.class);
				startActivity(intent);
				
			}
			
		});
		
		imagebuttonlistar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Intent intent =  new Intent(MainActivity.this,ListaAtividade.class);
				startActivity(intent);
				
			}
			
		});
		
		imageButtonListTarefas.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Intent intent =  new Intent(MainActivity.this,ListaTarefaAtividade.class);
				startActivity(intent);
				
			}
			
		});

		
		

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		/*
		Intent intent = new Intent("ALARME_DISPARADO");
		PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);
		
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarm.cancel(p);*/
		
	}

	
}
