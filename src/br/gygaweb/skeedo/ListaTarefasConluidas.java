package br.gygaweb.skeedo;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.gygaweb.skeedo.dao.TarefaDAO;
import br.gygaweb.skeedo.entities.Tarefa;

public class ListaTarefasConluidas extends ActionBarActivity {
	
	public static final String TAG_S = "SELECAO_TAREFA";
	private Tarefa tarefaSelecionada;
	private ListView listViewConcluidas ;
	private List<Tarefa> listaTarefaconc;
	private ArrayAdapter<Tarefa> adaptadorTarefa;
	private int adaptadorLayoutt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefas_conluidas);
		
		listViewConcluidas = (ListView) findViewById(R.id.listViewConcluidas);
		TarefaDAO tarefaDao =  new TarefaDAO(ListaTarefasConluidas.this);
		
		listaTarefaconc = tarefaDao.recuperarRegistrosConcluidos();
		
		if (listaTarefaconc.isEmpty()){
			
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			AlertDialog dialog = builder2.create();
			dialog.setTitle("Não Existem Tarefas Concluidas");
			dialog.show();

		}else{
			adaptadorLayoutt = android.R.layout.simple_expandable_list_item_1;
			adaptadorTarefa = new ArrayAdapter<Tarefa> (this,adaptadorLayoutt,listaTarefaconc);
			adaptadorTarefa.notifyDataSetChanged();
			listViewConcluidas.setAdapter(adaptadorTarefa);
			
			registerForContextMenu(listViewConcluidas);			
			
			listViewConcluidas.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int posicao, long id) {
					tarefaSelecionada = (Tarefa) adaptadorTarefa.getItem(posicao);

					Log.i(TAG_S, "Reuniao Selecionada ListView.longClick"
							+ tarefaSelecionada.getDescricao());
					return false;
				}
			});
			
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, view, menuInfo);
			getMenuInflater().inflate(R.menu.context_menu_concluidas, menu);

	}

	public boolean onContextItemSelected(MenuItem item){
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.itemConcluidasRemover:
			
			removerRegistroTarefa();
			
			break;
		case R.id.itemConcluidasDesfazer:
			
			desmarcarConcluida();
			
			break;
		case R.id.itemConcluidasSair:
			finish();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	public void desmarcarConcluida(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Desmarcar: "+ tarefaSelecionada.getDescricao());
		builder.setPositiveButton("sim", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				TarefaDAO tarefaDAO = new TarefaDAO(ListaTarefasConluidas.this);
				tarefaDAO.desconcluiTarefa(tarefaSelecionada);
				tarefaDAO.close();
				tarefaDAO.recuperarRegistrosConcluidos();
				tarefaSelecionada = null;
				adaptadorTarefa.notifyDataSetChanged();
				finish();
				startActivity(getIntent());
			}
		});

		builder.setNegativeButton("Não", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("Confirmar operação");
		dialog.show();
	}
	
	public void removerRegistroTarefa(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Tarefa removida: "+ tarefaSelecionada.getDescricao());
		builder.setPositiveButton("sim", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				TarefaDAO tarefaDAO = new TarefaDAO(ListaTarefasConluidas.this);
				tarefaDAO.removerRegistroTarefa(tarefaSelecionada);
				tarefaDAO.close();
				tarefaDAO.recuperarRegistrosConcluidos();

				listaTarefaconc.remove(tarefaSelecionada);
				tarefaSelecionada = null;
				adaptadorTarefa.notifyDataSetChanged();
				finish();
				startActivity(getIntent());
			}
		});

		builder.setNegativeButton("Não", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("Confirmar operação");
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_tarefas_conluidas, menu);
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
