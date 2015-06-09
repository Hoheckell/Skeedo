package br.gygaweb.skeedo.helpers;

import br.gygaweb.skeedo.R;
import br.gygaweb.skeedo.ReuniaoDescAtividade;
import br.gygaweb.skeedo.entities.Reuniao;
import android.widget.Button;
import android.widget.EditText;

public class ReuniaoDescHelper {

	private EditText editTextDescReuniao;
	private Button buttonGravadesc;
	private Reuniao reuniao;
	
	public EditText getEditTextDescReuniao() {
		return editTextDescReuniao;
	}

	public void setEditTextDescReuniao(EditText editTextDescReuniao) {
		this.editTextDescReuniao = editTextDescReuniao;
	}

	public Button getButtonGravadesc() {
		return buttonGravadesc;
	}

	public void setButtonGravadesc(Button buttonGravadesc) {
		this.buttonGravadesc = buttonGravadesc;
	}

	public void setReuniao(Reuniao reuniao) {
		this.reuniao = reuniao;
	}

	public ReuniaoDescHelper(ReuniaoDescAtividade atividade, Reuniao r){
		
		this.editTextDescReuniao = (EditText) atividade.findViewById(R.id.editTextDescReuniao);
		this.buttonGravadesc = (Button) atividade.findViewById(R.id.buttonGravadesc);
		this.reuniao = this.getReuniao(r);
		
	}
	
	public Reuniao getReuniao(Reuniao r){		
		Reuniao reuniao = new Reuniao();
		
		reuniao.setId(r.getId());
		reuniao.setHrReuniao(r.getHrReuniao());
		reuniao.setEndereco(r.getEndereco());
		reuniao.setDtReuniao(r.getDtReuniao());
		reuniao.setDescricao(editTextDescReuniao.getText().toString());
		
		return reuniao;
	
	}
	
}
