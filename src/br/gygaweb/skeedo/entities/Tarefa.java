package br.gygaweb.skeedo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarefa implements Parcelable {

	private Integer id;
	private String descricao;
	private String dtTarefa;
	private Integer concluida;
	
	public Tarefa(Parcel in) {
		id = in.readInt();
		descricao = in.readString();
		dtTarefa = in.readString();
		concluida = in.readInt();

	}

	public Tarefa() {
	}

	public Integer getConcluida() {
		return concluida;
	}
	public void setConcluida(Integer concluida) {
		this.concluida = concluida;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDtTarefa() {
		return dtTarefa;
	}
	public void setDtTarefa(String dtTarefa) {
		this.dtTarefa = dtTarefa;
		
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(descricao);
		dest.writeString(dtTarefa);
		dest.writeInt(concluida);
		
	}
	
	private void readFromParcelable(Parcel in) {

		id = in.readInt();
		descricao = in.readString();
		dtTarefa = in.readString();
		concluida = in.readInt();
		
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {  
		public Tarefa createFromParcel(Parcel in) {  
			return new Tarefa(in);  
		}  

		public Tarefa[] newArray(int size) {  
			return new Tarefa[size];  
		}  
	};  
	
	@Override
    public String toString() {
		String des;
		if(this.getConcluida() == 1){
			des = "Sim";
		}else if(this.getConcluida() == 0){
			des = "Não";
		}else{
			des = "NULO";
		}
        return "Tarefa:"+this.descricao + " , " + this.dtTarefa + " Conluida:" + des;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
