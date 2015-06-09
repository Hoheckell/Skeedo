package br.gygaweb.skeedo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.gygaweb.skeedo.entities.Tarefa;

public class TarefaDAO extends SQLiteOpenHelper  {
	
	public static final int VERSAO = 1;
	public static final String TABELA = "tarefas";
	public static final String DATABASE = "BD_SKEEDOS";

	private static final String TAG_I = "INSERIR_TAREFA";
	private static final String TAG_L = "LISTAR_TAREFA";
	private static final String TAG_R = "REMOVER_TAREFA";

	public TarefaDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA 
				+ "('id' INTEGER PRIMARY KEY NOT NULL"
				+ ", 'descricao' TEXT NOT NULL"
				+ ", 'dttarefa' TEXT NOT NULL"
				+ ", 'concluida' INTEGER NOT NULL"
				+ ")";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sql = "DROP TABLE IF EXISTS "+ TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	//INSERT
	public void registrarTarefa(Tarefa tarefa){

		ContentValues valores = new ContentValues();
		tarefa.setConcluida(0);
		valores.put("descricao", tarefa.getDescricao().toString());
		valores.put("dttarefa",tarefa.getDtTarefa().toString());
		valores.put("concluida",tarefa.getConcluida());

		getWritableDatabase().insert(TABELA, null, valores);

		Log.i(TAG_I, "Registro realizado: "+ tarefa.getDescricao());

	}
	
	//UPDATE
	public void atualizarRegistroTarefa(Tarefa tarefa){

		ContentValues valores = new ContentValues();

		valores.put("descricao", tarefa.getDescricao().toString());
		valores.put("dttarefa",tarefa.getDtTarefa().toString());

		String[] args = new String[]{Long.toString(tarefa.getId())};

		getWritableDatabase().update(TABELA, valores, "id=?", args);

		Log.i(TAG_I, "Tarefa atualizada: "+ tarefa.getDescricao());

	}
		
	//SELECT *
	public List<Tarefa> recuperarRegistrosNaoConcluidos(){

		List<Tarefa> listaTarefa = new ArrayList<Tarefa>();

		String sql = "Select * from tarefas where concluida = 0";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try{
			while(cursor.moveToNext()){

				Tarefa tarefa = new Tarefa();

				tarefa.setId(cursor.getInt(0));
				tarefa.setDescricao(cursor.getString(1));
				tarefa.setDtTarefa(cursor.getString(2));
				tarefa.setConcluida(cursor.getInt(3));

				listaTarefa.add(tarefa);
			}
		}catch(SQLException sqle){
			Log.e(TAG_L, sqle.getMessage());
		}finally{
			cursor.close();
		}

		return listaTarefa;
	}
	
	public List<Tarefa> recuperarRegistrosConcluidos(){

		List<Tarefa> listaTarefa = new ArrayList<Tarefa>();

		String sql = "Select * from tarefas where concluida = 1";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try{
			while(cursor.moveToNext()){

				Tarefa tarefa = new Tarefa();

				tarefa.setId(cursor.getInt(0));
				tarefa.setDescricao(cursor.getString(1));
				tarefa.setDtTarefa(cursor.getString(2));
				tarefa.setConcluida(cursor.getInt(3));

				listaTarefa.add(tarefa);
			}
		}catch(SQLException sqle){
			Log.e(TAG_L, sqle.getMessage());
		}finally{
			cursor.close();
		}

		return listaTarefa;
	}
		
	//SELECT WHERE
	public Tarefa recuperaItem(Integer id){

		String sql = "Select * from tarefas where id = " + id ;

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		Tarefa tarefa = new Tarefa();
			
		while(cursor.moveToNext()){

			tarefa.setId(cursor.getInt(0));
			tarefa.setDescricao(cursor.getString(1));
			tarefa.setDtTarefa(cursor.getString(2));
			tarefa.setConcluida(cursor.getInt(3));
				
		}
		
		Log.i(TAG_L, "Tarefa recuperada: "+ tarefa.getDescricao());
		return tarefa;			
		
	}
		

	public void removerRegistroTarefa(Tarefa tarefa){
		String [] args = {
				tarefa.getId().toString()
				};

		getWritableDatabase().delete(TABELA, "id=?", args);

		Log.i(TAG_R, "Tarefa removida: "+ tarefa.getDescricao());
	}

	public void concluiTarefa(Tarefa tarefa) {

		ContentValues valores = new ContentValues();
		tarefa.setConcluida(1);
		valores.put("concluida", tarefa.getConcluida());

		String[] args = new String[]{Long.toString(tarefa.getId())};

		getWritableDatabase().update(TABELA, valores, "id=?", args);

		Log.i(TAG_I, "Tarefa Concluida: "+ tarefa.getDescricao());
		
	}
	
	public void desconcluiTarefa(Tarefa tarefa) {

		ContentValues valores = new ContentValues();
		tarefa.setConcluida(0);
		valores.put("concluida", tarefa.getConcluida());

		String[] args = new String[]{Long.toString(tarefa.getId())};

		getWritableDatabase().update(TABELA, valores, "id=?", args);

		Log.i(TAG_I, "Desmarcada Tarefa: "+ tarefa.getDescricao());
		
	}

}
