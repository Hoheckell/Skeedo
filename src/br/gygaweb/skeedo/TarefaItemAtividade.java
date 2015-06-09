package br.gygaweb.skeedo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import br.gygaweb.skeedo.dao.TarefaDAO;
import br.gygaweb.skeedo.entities.Tarefa;
import br.gygaweb.skeedo.helpers.EditaTarefaHelper;

public class TarefaItemAtividade extends ActionBarActivity {


	private EditaTarefaHelper editatarefahelper;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarefa_item_atividade);
		
		Tarefa tarefa = (Tarefa) getIntent().getParcelableExtra("tarefa");
		Log.i("Tarefa 1",tarefa.toString());
		editatarefahelper =  new EditaTarefaHelper(TarefaItemAtividade.this, tarefa);
		
		editatarefahelper.getButtonEditaGravaT().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Tarefa t =  editatarefahelper.getTarefa();
				TarefaDAO tDao =  new TarefaDAO(TarefaItemAtividade.this);

				Log.i("Tarefa 3",t.toString());
				tDao.atualizarRegistroTarefa(t);
				tDao.close();
				intent =  new Intent(TarefaItemAtividade.this,ListaTarefaAtividade.class);
				startActivity(intent);
				
			}
			
		});
		
		editatarefahelper.getButtonEditaSairT().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarefa_item_atividade, menu);
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
