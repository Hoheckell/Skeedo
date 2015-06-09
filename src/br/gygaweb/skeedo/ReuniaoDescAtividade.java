package br.gygaweb.skeedo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import br.gygaweb.skeedo.dao.ReuniaoDAO;
import br.gygaweb.skeedo.entities.Reuniao;
import br.gygaweb.skeedo.helpers.ReuniaoDescHelper;

public class ReuniaoDescAtividade extends ActionBarActivity {
	
	ReuniaoDescHelper reuniaodescHelper;
	private Intent intent;
	private Reuniao re;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reuniao_desc_atividade);
		re = (Reuniao) getIntent().getParcelableExtra("reuniao");
		reuniaodescHelper =  new ReuniaoDescHelper(this,re);
		
		reuniaodescHelper.getButtonGravadesc().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Reuniao reuniao =  reuniaodescHelper.getReuniao(re);
				ReuniaoDAO rdao = new ReuniaoDAO(ReuniaoDescAtividade.this);
				rdao.atualizarRegistroDescricao(reuniao);
				rdao.close();
				startActivity(new Intent(ReuniaoDescAtividade.this,ListaAtividade.class));
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reuniao_desc_atividade, menu);
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
