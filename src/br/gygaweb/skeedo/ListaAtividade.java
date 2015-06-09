package br.gygaweb.skeedo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.gygaweb.skeedo.dao.ReuniaoDAO;
import br.gygaweb.skeedo.entities.Reuniao;
import br.gygaweb.skeedo.entities.Tarefa;

public class ListaAtividade extends Activity{
	public static final String TAG_S = "SELECAO_REUNIAO";
	private Reuniao reuniaoSelecionada;
	private ListView listViewReunioes ;
	private List<Reuniao> listaReuniao;
	private ArrayAdapter<Reuniao> adaptadorReuniao;
	private int adaptadorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_atividade);
		
		listViewReunioes = (ListView) findViewById(R.id.listViewReunioes);
		ReuniaoDAO reuniaoDao =  new ReuniaoDAO(ListaAtividade.this);
		
		listaReuniao =  reuniaoDao.recuperarRegistros();
		
		if (listaReuniao.isEmpty()){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			AlertDialog dialog = builder.create();
			dialog.setTitle("Não Existem Reuniões Cadastradas");
			dialog.show();

		}else{
			adaptadorLayout = android.R.layout.simple_expandable_list_item_1;
			adaptadorReuniao = new ArrayAdapter<Reuniao> (this,adaptadorLayout,listaReuniao);
			
			listViewReunioes.setAdapter(adaptadorReuniao);
			adaptadorReuniao.notifyDataSetChanged();
			registerForContextMenu(listViewReunioes);

			

			listViewReunioes.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Intent intent = new Intent(ListaAtividade.this, ReuniaoItemAtividade.class);
					
					Parcelable parceable = (Parcelable) listViewReunioes.getItemAtPosition(position);					
					intent.putExtra("reuniao",parceable);
					startActivity(intent);
					
				}		
			});

			listViewReunioes.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int posicao, long id) {
					reuniaoSelecionada = (Reuniao) adaptadorReuniao.getItem(posicao);

					Log.i(TAG_S, "Reuniao Selecionada ListView.longClick"
							+ reuniaoSelecionada.getId());
					return false;
				}
			});
		}
	}
		@Override
		public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
			super.onCreateContextMenu(menu, view, menuInfo);
			getMenuInflater().inflate(R.menu.menu_contexto, menu);
		}

		public boolean onContextItemSelected(MenuItem item){
			Intent intent = null;
			switch (item.getItemId()) {
			case R.id.itemEnviarpEmail:
				
				enviarReuniaoPorEmail(intent,this.reuniaoSelecionada);
				
				break;
			case R.id.itemAlterarReuniao:

				atualizarRegistroReuniao(intent);
				break;				
			case R.id.itemInserirDescricao:
				
				intent = new Intent(ListaAtividade.this, ReuniaoDescAtividade.class);
				Parcelable parceable = (Parcelable) this.reuniaoSelecionada;
				intent.putExtra("reuniao",parceable);
				startActivity(intent);
				
				break;
			case R.id.itemRemoverReuniao:
				
				removerRegistroReuniao();
				
				break;
			
			case R.id.itemSair:
				finish();
				break;
				
			case R.id.itemLerDesc:
				showDesc();
				break;

			default:
				break;
			}
			return super.onContextItemSelected(item);
		}
		
		private void enviarReuniaoPorEmail(Intent intent,Reuniao reuniao){
			
			SharedPreferences sp = getSharedPreferences("email",Context.MODE_PRIVATE);
			String e_mail = sp.getString("e_mail", "");
			String remetente = e_mail;
			Log.i("Email paciente",remetente);
			String assunto = "Relatório de Exame";
			String desperta = (reuniao.despertar == 0) ? "Não" : "Sim";
			String descricao = (reuniao.getDescricao() != null || reuniao.getDescricao() != "")?reuniao.getDescricao() : "Sem descrição";
			String corpoMensagem = "Endereçom da reuniao: "+ reuniao.getEndereco() +"\n Descrição da Reuniao: "+ descricao + "\n Data da Reuniao: " + reuniao.getDtReuniao() + "\n Horário da reunião:" + reuniao.getDtReuniao() + "\n Programada para despertar:" + desperta ;

			intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] { remetente });
			intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
			intent.putExtra(Intent.EXTRA_TEXT, corpoMensagem);

			intent.setType("message/rfc822");

			startActivity(Intent.createChooser(intent, "Escolha o Cliente de Email"));

		}
		
		
		private void showDesc(){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if(reuniaoSelecionada.getDescricao() == "" || reuniaoSelecionada.getDescricao() == null){
				builder.setMessage("Nenhuma descrição para esta reunião");
			}else{
				builder.setMessage("Descrição: "+ reuniaoSelecionada.getDescricao());
			}
			builder.setPositiveButton("sim", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

				}
			});

			AlertDialog dialog = builder.create();
			dialog.setTitle("Descrição da reunião");
			dialog.show();
			
		}

		private void atualizarRegistroReuniao(Intent intent){

			intent = new Intent(ListaAtividade.this, ReuniaoItemAtividade.class);
			Parcelable parceable = (Parcelable) this.reuniaoSelecionada;
			intent.putExtra("reuniao",parceable);
			startActivity(intent);
		}
		
		
		public void removerRegistroReuniao(){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Reuniao removida: "+ reuniaoSelecionada.getEndereco());

			builder.setPositiveButton("sim", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ReuniaoDAO reuniaoDAO = new ReuniaoDAO(ListaAtividade.this);
					reuniaoDAO.removerRegistroReuniao(reuniaoSelecionada);
					reuniaoDAO.close();
					reuniaoDAO.recuperarRegistros();

					listaReuniao.remove(reuniaoSelecionada);
					reuniaoSelecionada = null;
					adaptadorReuniao.notifyDataSetChanged();
				}
			});

			builder.setNegativeButton("Não", null);
			AlertDialog dialog = builder.create();
			dialog.setTitle("Confirmar operação");
			dialog.show();
		}
		
		
	}
		
		



