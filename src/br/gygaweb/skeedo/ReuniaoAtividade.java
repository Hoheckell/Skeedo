package br.gygaweb.skeedo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import br.gygaweb.skeedo.dao.ReuniaoDAO;
import br.gygaweb.skeedo.entities.Reuniao;
import br.gygaweb.skeedo.helpers.ReuniaoHelper;

public class ReuniaoAtividade extends ActionBarActivity {
	
	public Integer despertar;
	ReuniaoHelper reuniaoHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reuniao_atividade);

		reuniaoHelper =  new ReuniaoHelper(this);
		
		reuniaoHelper.getButtonSair().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				finish();
				
			}
			
		});
		
		reuniaoHelper.getButtonSalvar().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Reuniao reuniao =  reuniaoHelper.getReuniao();
				ReuniaoDAO reuniaoDAO = new ReuniaoDAO(ReuniaoAtividade.this);
				reuniaoDAO.registrarReuniao(reuniao);
				reuniaoDAO.close();
				finish();
				
			}			
			
			
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reuniao_atividade, menu);
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
