package br.gygaweb.skeedo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import br.gygaweb.skeedo.dao.TarefaDAO;
import br.gygaweb.skeedo.entities.Tarefa;
import br.gygaweb.skeedo.helpers.TarefaHelper;

public class TarefaAtividade extends ActionBarActivity {
	

	private TarefaHelper tarefaHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarefa_atividade);
		tarefaHelper =  new TarefaHelper(this);
		
		tarefaHelper.getButtonSair2().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				finish();
				
			}
			
			
			
		});
		
		
		tarefaHelper.getButtonGravar2().setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Tarefa tarefa =  tarefaHelper.getTarefa();
				TarefaDAO tarefaDAO = new TarefaDAO(TarefaAtividade.this);
				tarefaDAO.registrarTarefa(tarefa);
				tarefaDAO.close();
				finish();
				
			}
			
			
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarefa_atividade, menu);
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
