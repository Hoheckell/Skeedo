package br.gygaweb.skeedo.helpers;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import br.gygaweb.skeedo.R;
import br.gygaweb.skeedo.TarefaItemAtividade;
import br.gygaweb.skeedo.entities.Tarefa;

public class EditaTarefaHelper {
	
	private EditText editTextEditaDescT;
	private EditText editTextEditaDTT;
	private Button buttonEditaGravaT,buttonEditaSairT;
	private Integer idt;
	private Integer concluida;
	Tarefa tarefa;
	
	public EditaTarefaHelper(TarefaItemAtividade tarefaatividade,Tarefa tarefa){
		Log.i("Tarefa 2",tarefa.toString());
		this.editTextEditaDescT = (EditText) tarefaatividade.findViewById(R.id.editTextEditaDescT);
		this.editTextEditaDTT = (EditText) tarefaatividade.findViewById(R.id.editTextEditaDTT);
		this.buttonEditaGravaT = (Button) tarefaatividade.findViewById(R.id.buttonEditaGravaT);
		this.buttonEditaSairT = (Button) tarefaatividade.findViewById(R.id.buttonEditaSairT);
		
		idt = tarefa.getId();
		concluida = tarefa.getConcluida();
		
		editTextEditaDescT.setText(tarefa.getDescricao());
		editTextEditaDTT.setText(tarefa.getDtTarefa());

		
		tarefa = this.getTarefa();
		
		
	}
	
	public Tarefa getTarefa(){
		
		Tarefa tareF = new Tarefa();
		tareF.setId(idt);
		tareF.setDescricao(editTextEditaDescT.getText().toString());
		tareF.setDtTarefa(editTextEditaDTT.getText().toString());
		tareF.setConcluida(concluida);
		return tareF;
		
	}

	public EditText getEditTextEditaDescT() {
		return editTextEditaDescT;
	}

	public void setEditTextEditaDescT(EditText editTextEditaDescT) {
		this.editTextEditaDescT = editTextEditaDescT;
	}

	public EditText getEditTextEditaDTT() {
		return editTextEditaDTT;
	}

	public void setEditTextEditaDTT(EditText editTextEditaDTT) {
		this.editTextEditaDTT = editTextEditaDTT;
	}

	public Button getButtonEditaGravaT() {
		return buttonEditaGravaT;
	}

	public void setButtonEditaGravaT(Button buttonEditaGravaT) {
		this.buttonEditaGravaT = buttonEditaGravaT;
	}

	public Button getButtonEditaSairT() {
		return buttonEditaSairT;
	}

	public void setButtonEditaSairT(Button buttonEditaSairT) {
		this.buttonEditaSairT = buttonEditaSairT;
	}

	public Integer getIdt() {
		return idt;
	}

	public void setIdt(Integer idt) {
		this.idt = idt;
	}

}
