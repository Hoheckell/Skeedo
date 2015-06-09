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
import br.gygaweb.skeedo.entities.Reuniao;

public class ReuniaoDAO extends SQLiteOpenHelper {

	public static final int VERSAO = 1;
	public static final String TABELA = "reunioes";
	public static final String DATABASE = "BD_SKEEDO";

	private static final String TAG_I = "INSERIR_REUNIAO";
	private static final String TAG_L = "LISTAR_REUNIAO";
	private static final String TAG_R = "REMOVER_REUNIAO";

	public ReuniaoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA 
				+ "('id' INTEGER PRIMARY KEY NOT NULL"
				+ ", 'descricao' TEXT NULL"
				+ ", 'endereco' TEXT NOT NULL"
				+ ", 'dtreuniao' DATETIME NOT NULL"
				+ ", 'hrreuniao' TEXT NOT NULL"
				+ ", 'despertar' INTEGER NULL"
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
	public void registrarReuniao(Reuniao reuniao){

		ContentValues valores = new ContentValues();
		
		valores.put("endereco", reuniao.getEndereco().toString());
		valores.put("dtreuniao",reuniao.getDtReuniao().toString());
		valores.put("hrreuniao", reuniao.getHrReuniao().toString());
		valores.put("despertar",reuniao.despertar);

		getWritableDatabase().insert(TABELA, null, valores);

		Log.i(TAG_I, "Registro realizado: "+ reuniao.getEndereco());

	}

	//UPDATE
	public void atualizarRegistroReuniao(Reuniao reuniao){

		ContentValues valores = new ContentValues();

		valores.put("endereco", reuniao.getEndereco());
		valores.put("dtreuniao", reuniao.getDtReuniao());
		valores.put("hrreuniao", reuniao.getHrReuniao());
		valores.put("descricao", reuniao.getDescricao());
		valores.put("despertar", reuniao.despertar);

		String[] args = new String[]{reuniao.getId().toString()};

		getWritableDatabase().update(TABELA, valores, "id=?", args);

		Log.i(TAG_I, "Reuniao atualizada: "+ reuniao.getDescricao());

	}
	
	public void atualizarRegistroDescricao(Reuniao reuniao){

		ContentValues valores = new ContentValues();
		Log.i(TAG_I, "Reuniao Descricao atualizada: "+ reuniao.getEndereco());

		valores.put("descricao", reuniao.getDescricao().toString());

		String[] args = new String[]{Long.toString(reuniao.getId())};

		try{
			
			getWritableDatabase().update(TABELA, valores, "id=?", args);
			
		}catch(Exception e){

			Log.i(TAG_I, "Erro ao atualizar... " + e.getMessage());
		}

	}

	//SELECT *
	public List<Reuniao> recuperarRegistros(){

		List<Reuniao> listaReuniao = new ArrayList<Reuniao>();

		String sql = "Select * from reunioes";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try{
			
			while(cursor.moveToNext()){

				Reuniao reuniao = new Reuniao();

				reuniao.setId(cursor.getInt(0));
				reuniao.setDescricao(cursor.getString(1));
				reuniao.setEndereco(cursor.getString(2));
				reuniao.setDtReuniao(cursor.getString(3));
				reuniao.setHrReuniao(cursor.getString(4));
				reuniao.despertar = cursor.getInt(5);
				
				listaReuniao.add(reuniao);
			}
			
		}catch(SQLException sqle){
			Log.e(TAG_L, sqle.getMessage());
		}finally{
			cursor.close();
		}

		return listaReuniao;
	}
	
	//SELECT WHERE
	public Reuniao recuperaItem(Integer id){

		String sql = "Select * from reunioes where id = " + id ;

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		Reuniao reuniao = new Reuniao();
			
		while(cursor.moveToNext()){
				

			reuniao.setId(cursor.getInt(0));
			reuniao.setDescricao(cursor.getString(1));
			reuniao.setEndereco(cursor.getString(2));
			reuniao.setDtReuniao(cursor.getString(3));
			reuniao.setHrReuniao(cursor.getString(4));
			reuniao.despertar = cursor.getInt(5);

				
		}
		Log.i(TAG_L, "Reunião recuperada: "+ reuniao.getEndereco());
		return reuniao;			
		
	}

	public void removerRegistroReuniao(Reuniao reuniao){
		String [] args = {reuniao.getId().toString()};

		getWritableDatabase().delete(TABELA, "id=?", args);

		Log.i(TAG_R, "Reunião removida: "+ reuniao.getEndereco());
	}
	
}
