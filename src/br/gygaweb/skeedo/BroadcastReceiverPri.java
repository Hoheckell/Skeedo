package br.gygaweb.skeedo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class BroadcastReceiverPri extends WakefulBroadcastReceiver{
	
	


	@Override
	public void onReceive(Context context, Intent intent) {

		Log.i("SCRIPT","Broadcast Recebido");
		Intent i = new Intent(context,ServicoRecorrente.class);
		context.startService(i);
	}


	

}
