package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Usuario implements EntityIdSequencial, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USUARIO_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "USUARIO_ID", sequenceName = "SEQ_USUARIO", allocationSize = 1)
	private Long id;
	@Column(nullable=false)
	private String nome;
	private String sobrenome;
	@Column(nullable=false)
	private String senha;
	private String datanasc;
	@Column(nullable=false,length=9)
	private String sexo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date criacaoConta = new Date();

	public Usuario(String nome, String sobrenome, String senha, String datanasc, String sexo, Date criacaoConta) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.senha = senha;
		this.datanasc = datanasc;
		this.sexo = sexo;
		this.criacaoConta = criacaoConta;
	}

	public Usuario() {
		super();
	}

	public boolean senhaCorreta(String senhaDigitada) {
		if (this.senha.equals(senhaDigitada))
			return true;
		else
			return false;
	}

	public boolean emailCorreto(String emailDigitado) {
		if (this.senha.equals(emailDigitado))
			return true;
		else
			return false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getCriacaoConta() {
		return criacaoConta;
	}

	public void setCriacaoConta(Date criacaoConta) {
		this.criacaoConta = criacaoConta;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDatanasc() {
		return datanasc;
	}

	public void setDatanasc(String datanasc) {
		this.datanasc = datanasc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criacaoConta == null) ? 0 : criacaoConta.hashCode());
		result = prime * result + ((datanasc == null) ? 0 : datanasc.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((sobrenome == null) ? 0 : sobrenome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (criacaoConta == null) {
			if (other.criacaoConta != null)
				return false;
		} else if (!criacaoConta.equals(other.criacaoConta))
			return false;
		if (datanasc == null) {
			if (other.datanasc != null)
				return false;
		} else if (!datanasc.equals(other.datanasc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (sobrenome == null) {
			if (other.sobrenome != null)
				return false;
		} else if (!sobrenome.equals(other.sobrenome))
			return false;
		return true;
	}	
	
}