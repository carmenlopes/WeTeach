package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Administrador extends Usuario {

	private static final long serialVersionUID = 1L;
	
	private String cpf;
	
	@Column(length = 500, nullable = true)
	private String fotoPerfil;
	
	@OneToMany(mappedBy = "administrador", fetch = FetchType.EAGER)
	private List<Moderacao> moderacao;

	public Administrador(String nome, String sobrenome, String senha, String datanasc, String sexo, Date criacaoConta,
			String cpf,String fotoPerfil) {
		super(nome, sobrenome, senha, datanasc, sexo, criacaoConta);
		this.cpf = cpf;
		this.fotoPerfil = fotoPerfil;
		this.moderacao = new ArrayList<Moderacao>();
	}

	public Administrador() {
		super();
		this.moderacao = new ArrayList<Moderacao>();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public List<Moderacao> getModeracao() {
		return moderacao;
	}

	public void setModeracao(List<Moderacao> moderacao) {
		this.moderacao = moderacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((fotoPerfil == null) ? 0 : fotoPerfil.hashCode());
		result = prime * result + ((moderacao == null) ? 0 : moderacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Administrador other = (Administrador) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (fotoPerfil == null) {
			if (other.fotoPerfil != null)
				return false;
		} else if (!fotoPerfil.equals(other.fotoPerfil))
			return false;
		if (moderacao == null) {
			if (other.moderacao != null)
				return false;
		} else if (!moderacao.equals(other.moderacao))
			return false;
		return true;
	}
}