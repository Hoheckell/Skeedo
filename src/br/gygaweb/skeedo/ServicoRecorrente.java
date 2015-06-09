package br.gygaweb.skeedo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import br.gygaweb.skeedo.dao.ReuniaoDAO;
import br.gygaweb.skeedo.entities.Reuniao;

public class ServicoRecorrente extends Service{
	
	private List<Reuniao> listaReuniao;
	private Reuniao reuniao;
	private String datareuniao;
	private String horareuniao;
	Integer dift;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate(){
		super.onCreate();
		Log.i("INICIADO","Serviço iniciado");
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags,int startId){
		Log.i("DISPARADO","Serviço disparado");
		checkTimes();
		return START_STICKY;
	}
	
	
	@Override
	  public void onDestroy() {
		Log.i("PARADO","Serviço parado");
	  }
	
	public void checkTimes(){

		ReuniaoDAO rdao = new ReuniaoDAO(this);
		listaReuniao = rdao.recuperarRegistros();
		if(!listaReuniao.isEmpty()){
			for (int i=0; i<listaReuniao.size(); i++){
				reuniao = listaReuniao.get(i);
				datareuniao = reuniao.getDtReuniao();
				horareuniao = reuniao.getHrReuniao();
				dift = checkDifferenceMinutes(getSystemTime(),datareuniao+" "+horareuniao+":00");
				
				if(dift <= 5 && dift > 1){
					if(reuniao.despertar > 0){
						geraNotification(reuniao,dift);
					}
				}
				
				
			}
			rdao.close();
		}else{
			rdao.close();
			Log.i("Vazia","Lista vazia");
		}
	}
		
	private void geraNotification(Reuniao reuniao,Integer dif){
		String[] texto;
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent notifyIntent = new Intent(this,MapasAtividade.class);
		Parcelable parceable = (Parcelable) reuniao;
		notifyIntent.setAction(Intent.ACTION_MAIN);
		notifyIntent.addCategory(Intent.CATEGORY_DEFAULT);
		notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notifyIntent.putExtra("reuniao",parceable);
		PendingIntent p = PendingIntent.getActivity(this,1,notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);	
		
		NotificationCompat.Builder bilder = new NotificationCompat.Builder(this);
		bilder.setTicker("Aviso antecipado");
		bilder.setContentTitle("Notificação do Skeedo");
		bilder.setContentText("Aviso de Reunião");
		NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
		
		if(reuniao.getDescricao() != null){
			texto= new String[]{reuniao.getDescricao(),reuniao.getEndereco(),"faltam " + dif.toString() + " minutos"};
			
		}else{
			texto= new String[]{reuniao.getEndereco(),"faltam " + dif.toString() + " minutos"};
		}
		
		for(int i=0; i<texto.length;i++){
			style.addLine(texto[i]);
		}
		bilder.setStyle(style);
		bilder.setSmallIcon(R.drawable.ic_launcher);
		bilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.task));
		bilder.setContentIntent(p);
		
		Notification n =  bilder.build();
		
		n.vibrate =  new long[]{150,300,150,600};
		
		nm.notify(R.drawable.ic_launcher,n);
		
		try{
			
			Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone toque = RingtoneManager.getRingtone(this, som);
			toque.play();
		
			
			
		}catch(Exception e){
			
		}
	}


	public String getSystemTime(){

		Calendar calander = Calendar.getInstance(); 
	    Integer cDay = calander.get(Calendar.DAY_OF_MONTH);
	    Integer cMonth = calander.get(Calendar.MONTH) + 1;
	    Integer cYear = calander.get(Calendar.YEAR);
	    Integer cHour = calander.get(Calendar.HOUR);
	    Integer cMinute = calander.get(Calendar.MINUTE);
	    Integer periodo = calander.get(Calendar.AM_PM); 
	    
	    if(periodo == 1){
	    	switch(cHour){
	    	case 1:
	    		cHour = 13;
	    		break;
	    	case 2:
	    		cHour = 14;
	    		break;
	    	case 3:
	    		cHour = 15;
	    		break;
	    	case 4:
	    		cHour = 16;
	    		break;
	    	case 5:
	    		cHour = 17;
	    		break;
	    	case 6:
	    		cHour = 18;
	    		break;
	    	case 7:
	    		cHour = 19;
	    		break;
	    	case 8:
	    		cHour = 20;
	    		break;
	    	case 9:
	    		cHour = 21;
	    		break;
	    	case 10:
	    		cHour = 22;
	    		break;
	    	case 11:
	    		cHour = 23;
	    		break;
	    	case 12:
	    		cHour = 00;
	    		break;
	    		
	    	}
	    }
	    
	    String dia = cDay.toString();
	    String mes = cMonth.toString();
	    String ano = cYear.toString();
	    String hora = cHour.toString();
	    String minuto = cMinute.toString();
	    if(cHour < 10){
	    	hora = "0"+hora;
	    }
	    if(cDay < 10){
	    	dia = "0"+dia;
	    }
	    if(cMonth < 10){
	    	mes = "0"+mes;
	    }
	    if(cMinute < 10){
	    	minuto = "0"+minuto;
	    }
	    
		return dia+"/"+mes+"/"+ano+" "+hora+":"+minuto+":00";
	}
	
	public Integer checkDifferenceMinutes(String s1,String s2){

		long diffMinutes = 0;
		long diff = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",new Locale("pt", "BR"));
 
		Date d1 = null;
		Date d2 = null;

		Log.i("Inicial",s1);
		Log.i("Final",s2);
		
		try {
			d1 = format.parse(s1);
			d2 = format.parse(s2);
 
			//in milliseconds
			diff = d2.getTime() - d1.getTime();
 
			 diffMinutes = diff / (60 * 1000);
 
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (int) diffMinutes;
		
	}
	
	
}
