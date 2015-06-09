package br.gygaweb.skeedo;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.gygaweb.skeedo.dao.TarefaDAO;
import br.gygaweb.skeedo.entities.Tarefa;

public class ListaTarefaAtividade extends ActionBarActivity {
	
	public static final String TAG_S = "SELECAO_TAREFA";
	private Tarefa tarefaSelecionada;
	private ListView listViewTarefas ;
	private List<Tarefa> listaTarefa;
	private ArrayAdapter<Tarefa> adaptadorTarefa;
	private int adaptadorLayoutt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefa_atividade);
		
		listViewTarefas = (ListView) findViewById(R.id.listViewTarefas);
		TarefaDAO tarefaDao =  new TarefaDAO(ListaTarefaAtividade.this);
		
		listaTarefa = tarefaDao.recuperarRegistrosNaoConcluidos();
		
		if (listaTarefa.isEmpty()){

			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage("Não Existem Tarefas Pendentes, deseja ver a lista de tarefas concluidas?");
			builder1.setPositiveButton("sim", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(ListaTarefaAtividade.this,ListaTarefasConluidas.class));
				}
			});
			builder1.setNegativeButton("Não", null);
			AlertDialog dialog = builder1.create();
			dialog.setTitle("Lista de Tarefas");
			dialog.show();

		}else{
			adaptadorLayoutt = android.R.layout.simple_expandable_list_item_1;
			adaptadorTarefa = new ArrayAdapter<Tarefa> (this,adaptadorLayoutt,listaTarefa);
			adaptadorTarefa.notifyDataSetChanged();
			listViewTarefas.setAdapter(adaptadorTarefa);
			
			registerForContextMenu(listViewTarefas);
			
			listViewTarefas.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Intent intent = new Intent(ListaTarefaAtividade.this, TarefaItemAtividade.class);
					Parcelable parceable = (Parcelable) listViewTarefas.getItemAtPosition(position);					
					intent.putExtra("tarefa",parceable);
					startActivity(intent);
					
				}		
			});

			listViewTarefas.setOnItemLongClickListener(new OnItemLongClickListener(){
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
			getMenuInflater().inflate(R.menu.menu_contexto_tarefas, menu);

	}

	public boolean onContextItemSelected(MenuItem item){
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.itemEnviarporEmail:
			
			enviarTarefaPorEmail(intent,tarefaSelecionada);
			
			break;
		case R.id.itemMostraConcluidas:
			
			intent = new Intent(ListaTarefaAtividade.this,ListaTarefasConluidas.class);
			startActivity(intent);
			
			break;
		case R.id.itemAlterarTarefa:

			atualizarRegistroTarefa(intent);
			
			break;				
		case R.id.itemRemoverTarefa:
			removerRegistroTarefa();
			
			break;
		case R.id.itemConcluirTarefa:
			
			concluirTarefa();
			
			break;
		case R.id.itemSair:
			finish();
			break;
			
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	private void atualizarRegistroTarefa(Intent intent){

		intent = new Intent(ListaTarefaAtividade.this, TarefaItemAtividade.class);
		Parcelable parceable = (Parcelable) this.tarefaSelecionada;
		intent.putExtra("tarefa",parceable);
		startActivity(intent);
	}
	
	private void enviarTarefaPorEmail(Intent intent,Tarefa tarefa){
		
		SharedPreferences sp = getSharedPreferences("email",Context.MODE_PRIVATE);
		String e_mail = sp.getString("e_mail", "");
		String remetente = e_mail;
		Log.i("Email paciente",remetente);
		String assunto = "Relatório de Exame";
		String concluida = (tarefa.getConcluida() == 0) ? "Não" : "Sim";
		String corpoMensagem = "Descrição da tarefa: "+tarefa.getDescricao() + "\n Data para execução da tarefa: " + tarefa.getDtTarefa() + "\n Tarefa Concluida:" + concluida ;

		intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { remetente });
		intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
		intent.putExtra(Intent.EXTRA_TEXT, corpoMensagem);

		intent.setType("message/rfc822");

		startActivity(Intent.createChooser(intent, "Escolha o Cliente de Email"));

	}
	
	public void removerRegistroTarefa(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Tarefa removida: "+ tarefaSelecionada.getDescricao());
		builder.setPositiveButton("sim", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				TarefaDAO tarefaDAO = new TarefaDAO(ListaTarefaAtividade.this);
				tarefaDAO.removerRegistroTarefa(tarefaSelecionada);
				tarefaDAO.close();
				tarefaDAO.recuperarRegistrosNaoConcluidos();

				listaTarefa.remove(tarefaSelecionada);
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
	
	public void concluirTarefa(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Tarefa concluida: "+ tarefaSelecionada.getDescricao());
		builder.setPositiveButton("sim", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				TarefaDAO tarefaDAO = new TarefaDAO(ListaTarefaAtividade.this);
				tarefaDAO.concluiTarefa(tarefaSelecionada);
				tarefaDAO.close();
				tarefaDAO.recuperarRegistrosNaoConcluidos();
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
		getMenuInflater().inflate(R.menu.lista_tarefa_atividade, menu);
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
