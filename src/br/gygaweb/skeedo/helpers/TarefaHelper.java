package br.gygaweb.skeedo.helpers;

import android.widget.Button;
import android.widget.EditText;
import br.gygaweb.skeedo.R;
import br.gygaweb.skeedo.TarefaAtividade;
import br.gygaweb.skeedo.entities.Tarefa;

public class TarefaHelper {
	
	private EditText editTextDesc;
	private EditText editTextDtTarefa;
	private Button buttonGravar2,buttonSair2;
	
	public Button getButtonGravar2() {
		return buttonGravar2;
	}

	public void setButtonGravar2(Button buttonGravar2) {
		this.buttonGravar2 = buttonGravar2;
	}

	public Button getButtonSair2() {
		return buttonSair2;
	}

	public void setButtonSair2(Button buttonSair2) {
		this.buttonSair2 = buttonSair2;
	}

	public EditText getEditTextDesc() {
		return editTextDesc;
	}

	public void setEditTextDesc(EditText editTextDesc) {
		this.editTextDesc = editTextDesc;
	}

	public EditText getEditTextDtTarefa() {
		return editTextDtTarefa;
	}

	public void setEditTextDtTarefa(EditText editTextDtTarefa) {
		this.editTextDtTarefa = editTextDtTarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	private Tarefa tarefa;
	
	public TarefaHelper(TarefaAtividade tarefaAtividade){
		
		this.editTextDesc = (EditText) tarefaAtividade.findViewById(R.id.editTextDesc);
		this.editTextDtTarefa = (EditText) tarefaAtividade.findViewById(R.id.editTextDtTarefa);
		this.buttonGravar2 = (Button) tarefaAtividade.findViewById(R.id.buttonGravar2);
		this.buttonSair2 = (Button) tarefaAtividade.findViewById(R.id.buttonSair2);
		
		this.tarefa = this.getTarefa();
		
	}

	public Tarefa getTarefa() {
		Tarefa tarefa = new Tarefa();	
	
		tarefa.setDescricao(editTextDesc.getText().toString());
		tarefa.setDtTarefa(editTextDtTarefa.getText().toString());
		return tarefa;
	}

}
