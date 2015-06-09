package br.gygaweb.skeedo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailSettingsAtividade extends Activity {
	
	private Button buttonEmaillSave;
	private EditText editTextEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emaill_settings_layout);
		
		buttonEmaillSave = (Button) findViewById(R.id.buttonEmaillSave);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		
		buttonEmaillSave.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				save(editTextEmail.getText().toString());
				finish();
			}
			
		});
		
	}
	
	public void save(String emailAddress){
		
		SharedPreferences sp = getSharedPreferences("email",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("e_mail", emailAddress);
		editor.commit();
		
		Toast.makeText(this,"Email salvo com sucesso",Toast.LENGTH_LONG);
	}

}
