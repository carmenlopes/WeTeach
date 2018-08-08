package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.internal.NotNull;

@Entity
public class Video implements EntityIdSequencial, Serializable, Comparable<Video> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "VIDEO_ID", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "VIDEO_ID", sequenceName = "SEQ_VIDEO", allocationSize = 1)
	private Long id;
	private String titulo;
	private String link;
	private String imgVideo;
	private String tag;
	private String descricao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpload = new Date();
	private int visualizacoes;
	private int positivo=1;
	private int negativo=1;
	private String situacao = "Em verificação";
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Aluno autor;
	
/*	@OneToOne(mappedBy = "video", cascade = CascadeType.REMOVE)
	private Moderacao moderacao;*/
	
	@OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade={CascadeType.ALL,CascadeType.REMOVE})
	private List<Comentario> comentario;
	
	
	public Video() {
		this.autor = new Aluno();
	//	this.moderacao = new Moderacao();
		this.comentario = new ArrayList<Comentario>();
	}


	public Video(String titulo, String link, String imgVideo, String tag, String descricao, Date dataUpload,
			int visualizacoes, String situacao) {
		super();
		this.titulo = titulo;
		this.link = link;
		this.imgVideo = imgVideo;
		this.tag = tag;
		this.descricao = descricao;
		this.dataUpload = dataUpload;
		this.visualizacoes = visualizacoes;
		this.situacao = situacao;
		this.autor = new Aluno();
	//	this.moderacao = new Moderacao();
		this.comentario = new ArrayList<Comentario>();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getImgVideo() {
		return imgVideo;
	}


	public void setImgVideo(String imgVideo) {
		this.imgVideo = imgVideo;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Date getDataUpload() {
		return dataUpload;
	}


	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}


	public int getVisualizacoes() {
		return visualizacoes;
	}


	public void setVisualizacoes(int visualizacoes) {
		this.visualizacoes = visualizacoes;
	}

	public int getPositivo() {
		return positivo;
	}

	public void setPositivo(int positivo) {
		this.positivo = positivo;
	}

	public int getNegativo() {
		return negativo;
	}

	public void setNegativo(int negativo) {
		this.negativo = negativo;
	}


	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}


	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public Aluno getAutor() {
		return autor;
	}


	public void setAutor(Aluno autor) {
		this.autor = autor;
	}


/*	public Moderacao getModeracao() {
		return moderacao;
	}


	public void setModeracao(Moderacao moderacao) {
		this.moderacao = moderacao;
	}
*/
	public List<Comentario> getComentario() {
		return comentario;
	}


	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((dataUpload == null) ? 0 : dataUpload.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imgVideo == null) ? 0 : imgVideo.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
	//	result = prime * result + ((moderacao == null) ? 0 : moderacao.hashCode());
		result = prime * result + negativo;
		result = prime * result + positivo;
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + visualizacoes;
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
		Video other = (Video) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (dataUpload == null) {
			if (other.dataUpload != null)
				return false;
		} else if (!dataUpload.equals(other.dataUpload))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imgVideo == null) {
			if (other.imgVideo != null)
				return false;
		} else if (!imgVideo.equals(other.imgVideo))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (negativo != other.negativo)
			return false;
		if (positivo != other.positivo)
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (visualizacoes != other.visualizacoes)
			return false;
		return true;
	}


	@Override
	public int compareTo(Video video) {
		if (this.id == video.getId()) {
			return 1;

		} else {
			return 0;

		}
	}
}