package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Denuncia implements EntityIdSequencial, Serializable{
/* Aluno faz a denúncia de um comentário*/
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "DENUNCIA_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "DENUNCIA_ID", sequenceName = "SEQ_DENUNCIA", allocationSize = 1)
	private Long id;

	private String texto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Aluno aluno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Comentario comentario;

	public Denuncia() {
		super();
		this.aluno = new Aluno();
		this.comentario = new Comentario();
	}

	public Denuncia(String texto) {
		super();
		this.texto = texto;
		this.aluno = new Aluno();
		this.comentario = new Comentario();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		Denuncia other = (Denuncia) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}

}
