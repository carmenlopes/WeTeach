package modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Exibe mensagens de aprovado e negado de vídeos e comentários
@Entity
@Table(name = "notificacao")
public class Moderacao {

	@Id
	@GeneratedValue(generator = "NOTIFY_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "NOTIFY_ID", sequenceName = "SEQ_NOTIFY", allocationSize = 1)
	private Long id;
	private String texto;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNotificado = new Date();
	@ManyToOne(fetch = FetchType.EAGER)
	private Administrador administrador;

	@OneToOne(fetch = FetchType.EAGER)
	private Video video;

	@OneToOne(fetch = FetchType.EAGER)
	private Aluno aluno;

	public Moderacao() {
		super();

	}

	public Moderacao(String texto, Date dataNotificado) {
		super();
		this.texto = texto;
		this.dataNotificado = dataNotificado;
		this.administrador = new Administrador();
		this.video = new Video();
		this.aluno = new Aluno();
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

	public Date getDataNotificado() {
		return dataNotificado;
	}

	public void setDataNotificado(Date dataNotificado) {
		this.dataNotificado = dataNotificado;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((administrador == null) ? 0 : administrador.hashCode());
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((dataNotificado == null) ? 0 : dataNotificado.hashCode());
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
		Moderacao other = (Moderacao) obj;
		if (administrador == null) {
			if (other.administrador != null)
				return false;
		} else if (!administrador.equals(other.administrador))
			return false;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (dataNotificado == null) {
			if (other.dataNotificado != null)
				return false;
		} else if (!dataNotificado.equals(other.dataNotificado))
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
