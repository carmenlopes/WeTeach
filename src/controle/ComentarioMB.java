package controle;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import controle.util.JSFUtil;
import modelo.Comentario;
import modelo.Video;
import modelo.DAO.AlunoDAO;
import modelo.DAO.ComentarioDAO;
import modelo.DAO.VideoDAO;

@ManagedBean(name = "comentarioMB")
@SessionScoped
public class ComentarioMB {

	private Video video = new Video();
	private Comentario comentario = new Comentario();
	private ComentarioDAO dao = new ComentarioDAO();

	private List<Comentario> comentarios = null;
	private Long id;

	public List<Comentario> getComentarios(Long id) {
		this.comentarios = this.dao.comentarios(id);
		return this.comentarios;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	@ManagedProperty(value = "#{videoMB}")
	private VideoMB videoMB;

	public void acaoSalvar(Long id) {
		AlunoDAO alunoDAO = new AlunoDAO();
		VideoDAO videoDAO = new VideoDAO();

		getComentario().setAutor(alunoDAO.lerPorEmail(this.getLoginMB().getAluno().getEmail()));
		getComentario().setVideo(videoDAO.lerPorId(this.getVideoMB().getVideo().getId()));

		this.dao.salvar(this.getComentario());

		this.comentario = null;
		this.setComentario(new Comentario());
		JSFUtil.retornarMensagemInfo("Comentário salvo", null, null);
		
	}

	public void acaoExcluir() {
		Long id = JSFUtil.getParametroLong("itemId");
		this.dao.excluir(getComentario());
		Comentario objetoDoBanco = this.dao.lerPorId(id);
		this.dao.excluir(objetoDoBanco);

		JSFUtil.retornarMensagemInfo("Comentário excluído!", null, null);

	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	/**
	 * @return the videoMB
	 */
	public VideoMB getVideoMB() {
		return videoMB;
	}

	/**
	 * @param videoMB
	 *            the videoMB to set
	 */
	public void setVideoMB(VideoMB videoMB) {
		this.videoMB = videoMB;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void carregarComentario() {
		this.comentario = dao.lerPorId(this.id);
	}
}
