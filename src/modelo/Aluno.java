package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Aluno extends Usuario {

	private static final long serialVersionUID = 1L;

	@Column(length = 500, nullable = true)
	private String fotoPerfil;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String localizacao;
	private String curso = "Sistemas de Informação";

	@OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
	private List<Video> video;

	@OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
	private List<Comentario> comentario;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<Denuncia> denuncia;

	public Aluno(String nome, String sobrenome, String senha, String datanasc, String sexo, Date criacaoConta,
			String fotoPerfil, String email, String localizacao, String curso) {
		super(nome, sobrenome, senha, datanasc, sexo, criacaoConta);
		this.fotoPerfil = fotoPerfil;
		this.email = email;
		this.localizacao = localizacao;
		this.video = new ArrayList<Video>();
		this.comentario = new ArrayList<Comentario>();
		this.denuncia = new ArrayList<Denuncia>();
	}

	public Aluno() {
		super();
		this.video = new ArrayList<Video>();
		this.comentario = new ArrayList<Comentario>();
		this.denuncia = new ArrayList<Denuncia>();
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public List<Video> getVideo() {
		return video;
	}

	public void setVideo(List<Video> video) {
		this.video = video;
	}

	public List<Comentario> getComentario() {
		return comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}

	public List<Denuncia> getDenuncia() {
		return denuncia;
	}

	public void setDenuncia(List<Denuncia> denuncia) {
		this.denuncia = denuncia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((denuncia == null) ? 0 : denuncia.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fotoPerfil == null) ? 0 : fotoPerfil.hashCode());
		result = prime * result + ((localizacao == null) ? 0 : localizacao.hashCode());
		result = prime * result + ((video == null) ? 0 : video.hashCode());
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
		Aluno other = (Aluno) obj;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (denuncia == null) {
			if (other.denuncia != null)
				return false;
		} else if (!denuncia.equals(other.denuncia))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fotoPerfil == null) {
			if (other.fotoPerfil != null)
				return false;
		} else if (!fotoPerfil.equals(other.fotoPerfil))
			return false;
		if (localizacao == null) {
			if (other.localizacao != null)
				return false;
		} else if (!localizacao.equals(other.localizacao))
			return false;
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		return true;
	}
}