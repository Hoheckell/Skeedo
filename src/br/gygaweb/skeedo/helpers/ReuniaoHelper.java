package br.gygaweb.skeedo.helpers;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import br.gygaweb.skeedo.R;
import br.gygaweb.skeedo.ReuniaoAtividade;
import br.gygaweb.skeedo.entities.Reuniao;

public class ReuniaoHelper {

	private EditText editTextEndereco;
	private EditText editTextDtReuniao;
	private EditText editTextHrReuniao;
	private Button buttonSalvar,buttonSair;
	private RadioGroup radioGroupEdita;
	private RadioButton radioNao;
	private RadioButton radioSim;
	private Integer despertar;	
	private Reuniao reuniao;
	
	public ReuniaoHelper(ReuniaoAtividade reuniaoAtividade){
		
		this.editTextEndereco = (EditText) reuniaoAtividade.findViewById(R.id.editTextEndereco);
		this.editTextDtReuniao = (EditText) reuniaoAtividade.findViewById(R.id.editTextDtReuniao);
		this.editTextHrReuniao = (EditText) reuniaoAtividade.findViewById(R.id.editTextHrReuniao);
		this.radioGroupEdita = (RadioGroup) reuniaoAtividade.findViewById(R.id.radioGroupEdita);
		this.radioNao = (RadioButton) reuniaoAtividade.findViewById(R.id.radioNao);
		this.radioSim = (RadioButton) reuniaoAtividade.findViewById(R.id.radioSim);
		this.buttonSalvar = (Button) reuniaoAtividade.findViewById(R.id.buttonSalvar);
		this.buttonSair = (Button) reuniaoAtividade.findViewById(R.id.buttonSair);
		this.despertar = reuniaoAtividade.despertar;
		this.reuniao = this.getReuniao();
		
	}
	
	public Button getButtonSalvar() {
		return buttonSalvar;
	}

	public void setButtonSalvar(Button buttonSalvar) {
		this.buttonSalvar = buttonSalvar;
	}

	public Button getButtonSair() {
		return buttonSair;
	}

	public void setButtonSair(Button buttonSair) {
		this.buttonSair = buttonSair;
	}

	public Reuniao getReuniao(){
		Reuniao reuniao = new Reuniao();		
		
		reuniao.setEndereco(editTextEndereco.getText().toString());
		reuniao.setDtReuniao(editTextDtReuniao.getText().toString());
		reuniao.setHrReuniao(editTextHrReuniao.getText().toString());
		
			if(radioNao.isChecked()){
				despertar = 0;
			}else if(radioSim.isChecked()){
				despertar = 1;
			}
			
		reuniao.despertar = this.despertar;
		return reuniao;
	
	}

	public EditText getEditTextEndereco() {
		return editTextEndereco;
	}

	public void setEditTextEndereco(EditText editTextEndereco) {
		this.editTextEndereco = editTextEndereco;
	}

	public EditText getEditTextDtReuniao() {
		return editTextDtReuniao;
	}

	public void setEditTextDtReuniao(EditText editTextDtReuniao) {
		this.editTextDtReuniao = editTextDtReuniao;
	}

	public EditText getEditTextHrReuniao() {
		return editTextHrReuniao;
	}

	public void setEditTextHrReuniao(EditText editTextHrReuniao) {
		this.editTextHrReuniao = editTextHrReuniao;
	}

	public RadioGroup getRadioGroupEdita() {
		return radioGroupEdita;
	}

	public void setRadioGroupEdita(RadioGroup radioGroupEdita) {
		this.radioGroupEdita = radioGroupEdita;
	}

	public RadioButton getRadioNao() {
		return radioNao;
	}

	public void setRadioNao(RadioButton radioNao) {
		this.radioNao = radioNao;
	}

	public RadioButton getRadioSim() {
		return radioSim;
	}

	public void setRadioSim(RadioButton radioSim) {
		this.radioSim = radioSim;
	}

	public Integer getDespertar() {
		return despertar;
	}

	public void setDespertar(Integer despertar) {
		this.despertar = despertar;
	}
	
	
}
