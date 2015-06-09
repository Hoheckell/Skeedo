package br.gygaweb.skeedo.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Reuniao implements Parcelable {
	
	private Integer id;
	private String descricao;
	private String endereco;
	private String dtReuniao;
	private String hrReuniao;
	public Integer despertar;	

	
	public Reuniao(Parcel in) {
		id = in.readInt();
		descricao = in.readString();
		endereco = in.readString();
		dtReuniao = in.readString();
		hrReuniao = in.readString();
		despertar = in.readInt();
		// TODO Auto-generated constructor stub
	}
	public Reuniao() {
		// TODO Auto-generated constructor stub
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
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getDtReuniao() {
		return dtReuniao;
	}
	public void setDtReuniao(String dtReuniao) {

			this.dtReuniao = dtReuniao;
		
	}
	public String getHrReuniao() {
		return hrReuniao;
	}
	public void setHrReuniao(String hrReuniao) {

			this.hrReuniao = hrReuniao;		
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(descricao);
		dest.writeString(endereco);
		dest.writeString(dtReuniao);
		dest.writeString(hrReuniao);
		dest.writeInt(despertar);
		
	}
	
	private void readFromParcelable(Parcel in) {
		id = in.readInt();
		descricao = in.readString();
		endereco = in.readString();
		dtReuniao = in.readString();
		hrReuniao = in.readString();
		despertar = in.readInt();
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {  
		public Reuniao createFromParcel(Parcel in) {  
			return new Reuniao(in);  
		}  

		public Reuniao[] newArray(int size) {  
			return new Reuniao[size];  
		}  
	};  
	
	@Override
    public String toString() {
		String des;
		if(this.despertar == 1){
			des = "Sim";
		}else if(this.despertar == 0){
			des = "Não";
		}else{
			des = "NULO";
		}
        return  this.endereco +" " + this.dtReuniao + " - " + this.hrReuniao + " Despertar: " + des;
    }

}
