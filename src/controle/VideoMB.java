package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;

import controle.util.JSFUtil;
import modelo.Video;
import modelo.DAO.AlunoDAO;
import modelo.DAO.VideoDAO;
import net.bootsfaces.expressions.ThisExpressionResolver;

@ManagedBean(name = "videoMB")
@SessionScoped
public class VideoMB {

	private Video video = new Video();
	private VideoDAO videoDao = new VideoDAO();

	private ComentarioMB comentmb;
	private NotificacaoMB notifymb;
	private Long id;

	private boolean value1;
	private boolean value2;

	private String campoBusca;

	private List<Video> videos = null;
	private List<Video> maisAcessados = null;
	private List<Video> videosInteressantes = null;
	private List<Video> buscaTag = null;
	private static List<String> listaTag = new ArrayList<String>();

	static {
		listaTag.add("Banco de Dados");
		listaTag.add("Front-end");
		listaTag.add("Programação");
		listaTag.add("Redes");
		listaTag.add("Outros");
	}

	public List<String> getListaTag() {
		return VideoMB.listaTag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCampoBusca() {
		return campoBusca;
	}

	public void setCampoBusca(String campoBusca) {
		this.campoBusca = campoBusca;
	}

	public List<Video> getBuscaTag(String campoBusca) {
		this.buscaTag = this.videoDao.buscaTag(campoBusca);
		return this.buscaTag;
	}

	public void setBuscaTag(List<Video> buscaTag) {
		this.buscaTag = buscaTag;
	}

	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;

	/**
	 * @return the loginMB
	 */
	public LoginMB getLoginMB() {
		return loginMB;
	}

	/**
	 * @param loginMB
	 *            the loginMB to set
	 */
	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public ComentarioMB getComentmb() {
		return comentmb;
	}

	public void setComentmb(ComentarioMB comentmb) {
		this.comentmb = comentmb;
	}

	public List<Video> getVideos(Long id) {
		this.videos = this.videoDao.videos(id);
		return this.videos;
	}

	public List<Video> getMaisAcessados() {
		if (this.maisAcessados == null)
			this.maisAcessados = this.videoDao.maisAcessados();
		return this.maisAcessados;
	}

	// Lista de vídeos que entrarão na seção 'videos q talvez goste'
	public List<Video> getVideosInteressantes() {
		this.videosInteressantes = this.videoDao.videosInteressantes();
		return videosInteressantes;
	}

	/* Carrega o video para outra página */
	public void carregarVideo() {
		this.video = videoDao.lerPorId(this.id);
		increVisualizacao();
	}

	public String acaoAlterar() {
		Long id = JSFUtil.getParametroLong("itemId");
		Video objetoDoBanco = this.videoDao.lerPorId(id);
		this.setVideo(objetoDoBanco);

		return "editarVideo.xhtml?faces-redirect=true";
	}

	public void salvarAlteracoes() throws IOException {
		AlunoDAO alunoDAO = new AlunoDAO();

		// salvando objeto com chave estrangeira
		this.getVideo().setAutor(alunoDAO.lerPorEmail(this.getLoginMB().getAluno().getEmail()));

		this.videoDao.salvar(this.getVideo());
		this.video = null;
		this.setVideo(new Video());
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		JSFUtil.retornarMensagemInfo("Alteração realizada com sucesso", null, null);
		context.getExternalContext().redirect("meusVideos.xhtml");
	}

	public void acaoSalvar() throws IOException {
		AlunoDAO alunoDAO = new AlunoDAO();

		if (this.acaoValidarLink(this.getVideo().getLink()) != null)
			this.getVideo().setLink(this.acaoValidarLink(this.getVideo().getLink()));

		this.getVideo().setImgVideo(this.acaoImagemVideo(this.getVideo().getLink()));

		// salvando objeto com chave estrangeira
		this.getVideo().setAutor(alunoDAO.lerPorEmail(this.getLoginMB().getAluno().getEmail()));

		this.videoDao.salvar(this.getVideo());
		this.video = null;
		this.setVideo(new Video());
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		JSFUtil.retornarMensagemInfo("Seu vídeo será analisado por um administrador", null, null);
		context.getExternalContext().redirect("meusVideos.xhtml");
	}

	public String acaoCancelar() {
		// limpar o objeto da página
		this.setVideo(new Video());
		return "meusVideos.xhtml?faces-redirect=true";
	}

	// Excluir tudo o que pertence ao usuário
	public void acaoExcluir() throws ServletException {
		try {
			Long id = JSFUtil.getParametroLong("itemId");
			this.videoDao.excluir(getVideo());
			Video objetoDoBanco = this.videoDao.lerPorId(id);
			this.videoDao.excluir(objetoDoBanco);
			JSFUtil.retornarMensagemInfo("Vídeo excluído com sucesso", null, null);
		} catch (IllegalArgumentException e) {
			// redirecionar para uma página de erro
			JSFUtil.retornarMensagemInfo("Video excluído com sucesso", null, null);
		} catch (PersistenceException e) {
			// redirecionar para uma página de erro
			JSFUtil.retornarMensagemInfo("Video excluído com sucesso", null, null);
		} catch (GenericJDBCException e) {
			// redirecionar para uma página de erro
			JSFUtil.retornarMensagemInfo("Video excluído com sucesso", null, null);
		}

	}
	// Ação para validar qualquer tipo de link que o usuário coloque

	public String acaoValidarLink(String link) {
		String linkvalidado;

		if (link.startsWith("https://www.youtube.com/watch?v=")) {
			String id = link.substring(32);
			linkvalidado = "https://www.youtube.com/embed/" + id;
			return linkvalidado;
		} else if (link.startsWith("https://youtu.be/")) {
			String id = link.substring(16);
			linkvalidado = "https://www.youtube.com/embed/" + id;
			return linkvalidado;
		} else
			return null;
	}

	// Ação para alterar as miniaturas dos vídeos salvos
	public String acaoImagemVideo(String link) {
		String imagemlink;

		String id = link.substring(30);
		imagemlink = "http://img.youtube.com/vi/" + id + "/hqdefault.jpg";
		return imagemlink;

	}

	// Método de incremento do numero de visualizaçoes do vídeo
	public void increVisualizacao() {
		this.getVideo().setVisualizacoes(this.getVideo().getVisualizacoes() + 1);
		this.videoDao.salvar(this.getVideo());
	}

	// Método de incremento do numero de curtidas positivas do vídeo
	public void increment() {
		if (this.value1 == false)
			this.getVideo().setPositivo(this.getVideo().getPositivo() + 1);
		this.videoDao.salvar(this.getVideo());
	}

	// Método de incremento do numero de curtidas negativas do vídeo
	public void decrement() {
		if (this.value2 == false)
			this.getVideo().setNegativo(this.getVideo().getNegativo() + 1);
		this.videoDao.salvar(this.getVideo());
	}

	public boolean isValue2() {
		return value2;
	}

	public void setValue2(boolean value2) {
		this.value2 = value2;
	}

	public boolean isValue1() {
		return value1;
	}

	public void setValue1(boolean value1) {
		this.value1 = value1;
	}

	public String acaoBuscar() throws IOException {
		VideoDAO dao = new VideoDAO();

		try {
			Video videoNoBanco = dao.buscarVideo(this.campoBusca);

			if (videoNoBanco.getTitulo().equals(this.campoBusca))
				System.out.println(videoNoBanco.getId());
			this.setVideo(videoNoBanco);
			return "paginaVideo.xhtml";

		} catch (NullPointerException e) {
			System.out.println("Vídeo não encontrado");
			return "paginaBancodeDados.xhtml";
		}

	}

	public NotificacaoMB getNotifymb() {
		return notifymb;
	}

	public void setNotifymb(NotificacaoMB notifymb) {
		this.notifymb = notifymb;
	}
}
