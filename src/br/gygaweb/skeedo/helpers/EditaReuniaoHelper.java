package br.gygaweb.skeedo.helpers;

import java.text.ParseException;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import br.gygaweb.skeedo.R;
import br.gygaweb.skeedo.entities.Reuniao;

public class EditaReuniaoHelper {

	private EditText editTextEditaEndereco;
	private EditText editTextEditaDTReuniao;
	private EditText editTexteditaHrReuniao;
	private RadioGroup radioGroupEdita;
	private RadioButton radioEditaN,radioEditaS;
	private Button buttonEditaAlterar,buttonEditaSair;
	
	Integer Idp,despertar;
	Reuniao reuniao;
	
	public EditaReuniaoHelper(Activity atividade,Reuniao reuniao) throws ParseException{

		
		this.editTextEditaEndereco = (EditText) atividade.findViewById(R.id.editTextEditaEndereco);
		this.editTextEditaDTReuniao = (EditText) atividade.findViewById(R.id.editTextEditaDTReuniao);
		this.editTexteditaHrReuniao = (EditText) atividade.findViewById(R.id.editTexteditaHrReuniao);
		this.radioEditaN = (RadioButton) atividade.findViewById(R.id.radioEditaN);
		this.radioEditaS = (RadioButton) atividade.findViewById(R.id.radioEditaS);
		this.radioGroupEdita = (RadioGroup) atividade.findViewById(R.id.radioGroupEdita);
		this.buttonEditaAlterar =  (Button) atividade.findViewById(R.id.buttonEditaAlterar);
		this.buttonEditaSair =  (Button) atividade.findViewById(R.id.buttonEditaSair);
		
		
		Idp = reuniao.getId();	
		editTextEditaEndereco.setText(reuniao.getEndereco());
		editTextEditaDTReuniao.setText(reuniao.getDtReuniao());
		editTexteditaHrReuniao.setText(reuniao.getHrReuniao());

			despertar = reuniao.despertar;
			if(despertar == 0){
				radioEditaN.setChecked(true);
			}else{
				radioEditaS.setChecked(true);
			}

		
		reuniao = this.getReuniao();

	}
	
	public Reuniao getReuniao() {

		Reuniao reuniao = new Reuniao();

				if(radioEditaN.isChecked()) {
					despertar = 0;
				} else if(radioEditaS.isChecked()){
					despertar = 1;
				} 
				
				
		reuniao.setId(Idp);
		reuniao.setEndereco(editTextEditaEndereco.getText().toString());
		reuniao.setDtReuniao(editTextEditaDTReuniao.getText().toString());
		reuniao.setHrReuniao(editTexteditaHrReuniao.getText().toString());		
		reuniao.despertar = this.despertar;
		
		return reuniao;
	}

	public Button getButtonEditaSair() {
		return buttonEditaSair;
	}

	public void setButtonEditaSair(Button buttonEditaSair) {
		this.buttonEditaSair = buttonEditaSair;
	}

	public EditText getEditTextEditaEndereco() {
		return editTextEditaEndereco;
	}

	public void setEditTextEditaEndereco(EditText editTextEditaEndereco) {
		this.editTextEditaEndereco = editTextEditaEndereco;
	}

	public EditText getEditTextEditaDTReuniao() {
		return editTextEditaDTReuniao;
	}

	public void setEditTextEditaDTReuniao(EditText editTextEditaDTReuniao) {
		this.editTextEditaDTReuniao = editTextEditaDTReuniao;
	}

	public EditText getEditTexteditaHrReuniao() {
		return editTexteditaHrReuniao;
	}

	public void setEditTexteditaHrReuniao(EditText editTexteditaHrReuniao) {
		this.editTexteditaHrReuniao = editTexteditaHrReuniao;
	}

	public RadioGroup getRadioGroupEdita() {
		return radioGroupEdita;
	}

	public void setRadioGroupEdita(RadioGroup radioGroupEdita) {
		this.radioGroupEdita = radioGroupEdita;
	}

	public RadioButton getRadioEditaN() {
		return radioEditaN;
	}

	public void setRadioEditaN(RadioButton radioEditaN) {
		this.radioEditaN = radioEditaN;
	}

	public RadioButton getRadioEditaS() {
		return radioEditaS;
	}

	public void setRadioEditaS(RadioButton radioEditaS) {
		this.radioEditaS = radioEditaS;
	}

	public Button getButtonEditaAlterar() {
		return buttonEditaAlterar;
	}

	public void setButtonEditaAlterar(Button buttonEditaAlterar) {
		this.buttonEditaAlterar = buttonEditaAlterar;
	}
	
	
	
}
