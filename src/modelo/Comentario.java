package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Comentario implements EntityIdSequencial, Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "COMENTARIO_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "COMENTARIO_ID", sequenceName = "SEQ_COMENTARIO", allocationSize = 1)
	private Long id;

	private String texto;

	private Date dataEnvio = new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	private Aluno autor;

	@ManyToOne(fetch = FetchType.EAGER)
	private Video video;

	@OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.REMOVE })
	private List<Denuncia> denuncia;

	public Comentario() {
		super();
		this.autor = new Aluno();
		this.video = new Video();
		this.denuncia = new ArrayList<Denuncia>();
	}

	public Comentario(String texto, Date dataEnvio) {
		super();
		this.texto = texto;
		this.dataEnvio = dataEnvio;
		this.autor = new Aluno();
		this.video = new Video();
		this.denuncia = new ArrayList<Denuncia>();
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

	public Aluno getAutor() {
		return autor;
	}

	public void setAutor(Aluno autor) {
		this.autor = autor;
	}

	public Date getdataEnvio() {
		return dataEnvio;
	}

	public void setdataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
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
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((dataEnvio == null) ? 0 : dataEnvio.hashCode());
		result = prime * result + ((denuncia == null) ? 0 : denuncia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + ((video == null) ? 0 : video.hashCode());
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
		Comentario other = (Comentario) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (dataEnvio == null) {
			if (other.dataEnvio != null)
				return false;
		} else if (!dataEnvio.equals(other.dataEnvio))
			return false;
		if (denuncia == null) {
			if (other.denuncia != null)
				return false;
		} else if (!denuncia.equals(other.denuncia))
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
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		return true;
	}

}