package br.gygaweb.skeedo;

import java.text.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import br.gygaweb.skeedo.dao.ReuniaoDAO;
import br.gygaweb.skeedo.entities.Reuniao;
import br.gygaweb.skeedo.helpers.EditaReuniaoHelper;

public class ReuniaoItemAtividade extends Activity {
	
	private EditaReuniaoHelper editareuniaohelper;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reuniao_item_atividade);
		
		Reuniao reuniao = (Reuniao) getIntent().getExtras().getParcelable("reuniao");

			try {
				editareuniaohelper = new EditaReuniaoHelper(ReuniaoItemAtividade.this,reuniao);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		editareuniaohelper.getButtonEditaAlterar().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Reuniao reuniao =  editareuniaohelper.getReuniao();	
				ReuniaoDAO reuniaoDAO = new ReuniaoDAO(ReuniaoItemAtividade.this);
				reuniaoDAO.atualizarRegistroReuniao(reuniao);
				reuniaoDAO.close();
				Intent o =  new Intent(ReuniaoItemAtividade.this,MainActivity.class);
				startActivity(o);
			}
			
		});
		
		editareuniaohelper.getButtonEditaSair().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				intent =  new Intent(ReuniaoItemAtividade.this,MainActivity.class);
				startActivity(intent);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reuniao_item_atividade, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
