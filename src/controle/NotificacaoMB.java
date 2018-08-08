package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import controle.util.JSFUtil;
import modelo.Comentario;
import modelo.Moderacao;
import modelo.Video;
import modelo.DAO.AdminDAO;
import modelo.DAO.AlunoDAO;
import modelo.DAO.NotificacaoDAO;
import modelo.DAO.VideoDAO;

@ManagedBean(name = "notifyMB")
@SessionScoped
public class NotificacaoMB {
	private Moderacao notificar = new Moderacao();
	private NotificacaoDAO dao = new NotificacaoDAO();

	private List<Moderacao> notificacoes = null;

	public List<Moderacao> getNotificacoes(Long id) {
		this.notificacoes = this.dao.notificacoes(id);
		return this.notificacoes;
	}

	public void setNotificacoes(List<Moderacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public Moderacao getNotificar() {
		return notificar;
	}

	public void setNotificar(Moderacao notificar) {
		this.notificar = notificar;
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

	public void msgAprovadoVideo() {
		AdminDAO adminDao = new AdminDAO();
		AlunoDAO alunoDao = new AlunoDAO();
		VideoDAO videoDao = new VideoDAO();
		Long id = JSFUtil.getParametroLong("itemId");
		Long idAluno = JSFUtil.getParametroLong("alunoId");
		Video objetoDoBanco = videoDao.lerPorId(id);
		this.getNotificar().setTexto("Seu Vídeo " + objetoDoBanco.getTitulo() + " foi aprovado");
		this.getNotificar().setAdministrador(adminDao.lerPorCPF(this.getLoginMB().getAdmin().getCpf()));
		this.getNotificar().setVideo(videoDao.lerPorId(id));
		this.getNotificar().setAluno(alunoDao.lerPorId(idAluno));
		this.dao.salvar(this.getNotificar());
	}

	public void msgRecusadoVideo() {
		AdminDAO adminDao = new AdminDAO();
		AlunoDAO alunoDao = new AlunoDAO();
		VideoDAO videoDao = new VideoDAO();
		Long id = JSFUtil.getParametroLong("itemId");
		Long idAluno = JSFUtil.getParametroLong("alunoId");
		Video objetoDoBanco = videoDao.lerPorId(id);
		this.getNotificar().setTexto("Seu Vídeo " + objetoDoBanco.getTitulo() + " Foi recusado");
		this.getNotificar().setAdministrador(adminDao.lerPorCPF(this.getLoginMB().getAdmin().getCpf()));
		this.getNotificar().setVideo(videoDao.lerPorId(id));
		this.getNotificar().setAluno(alunoDao.lerPorId(idAluno));
		this.dao.salvar(this.getNotificar());
	}

	
	public String excluirNotificacao() {
		Long id = JSFUtil.getParametroLong("notiId");
		try {
			this.dao.excluir(getNotificar());
			Moderacao notifiDoBanco = this.dao.lerPorId(id);
			this.dao.excluir(notifiDoBanco);
			
		} catch (IllegalArgumentException e) {
			//redirecionar para uma página de erro
			System.out.println("Deu erro");
		}
		return "meusVideos.xhtml?faces-redirect=true";
	}
}
