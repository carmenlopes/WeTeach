package controle;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import controle.util.JSFUtil;
import modelo.Administrador;
import modelo.Aluno;
import modelo.Moderacao;
import modelo.Video;
import modelo.DAO.AdminDAO;
import modelo.DAO.VideoDAO;

@ManagedBean(name = "adminMB")
@SessionScoped
public class AdministradorMB {

	private Video video = new Video();
	private Administrador admin = new Administrador();
	private Moderacao notify = new Moderacao();

	private NotificacaoMB notifyMB;
	private VideoDAO daoVideo = new VideoDAO();
	private AdminDAO adminDao = new AdminDAO();

	private Long id;
	private Part file;
	private List<Video> verificarSituacao = null;
	
	private List <Video> listaProblemas = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	/**
	 * Obtém o nome do Arquivo usado durante o UPLOAD.
	 * 
	 * @param file
	 * @return o nome do arquivo.
	 */
	public String getFilename(Part file) {
		if (getFile() == null) {
			System.out.println("Foto não foi");
		}
		for (String cd : file.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}

	/**
	 * Método que realiza a inserção da foto na pasta.
	 */
	public String fileUpload() throws IOException {
		/*
		 * 1- Salvo foto na pasta na indicada
		 */
		this.getFile()
				.write("C:\\Users\\carme\\workspace\\WeTeach\\WebContent\\fotos\\fotosDePerfil\\" + getFilename(file));
		/*
		 * 2- Retorno o caminho relativo em que ela foi adicionada.
		 */
		return "..\\fotos\\fotosDePerfil\\" + getFilename(file);
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public NotificacaoMB getNotifyMB() {
		return notifyMB;
	}

	public void setNotifyMB(NotificacaoMB notifyMB) {
		this.notifyMB = notifyMB;
	}

	public Moderacao getNotify() {
		return notify;
	}

	public void setNotify(Moderacao notify) {
		this.notify = notify;
	}

	public List<Video> getVerificarSituacao() {
		this.verificarSituacao = this.daoVideo.verificarSituacao();
		return this.verificarSituacao;
	}

	// Salvar dados do administrador
	public String acaoSalvar() throws IOException {
		this.getAdmin().setFotoPerfil(fileUpload());
		this.adminDao.salvar(this.getAdmin());
		this.admin = null;
		this.setAdmin(new Administrador());
		JSFUtil.retornarMensagemInfo("Conta novo administrador foi criada", null, null);
		return "admin/dashboard.xhtml";

	}

	// Alterar dados do administrador
	public String acaoAlterar() {
		Long id = JSFUtil.getParametroLong("itemId");
		Administrador objeto = this.adminDao.lerPorId(id);
		this.setAdmin(objeto);
		return "editarPerfil.xhtml";
	}

	// Método que irá alterar a situação do vídeo para Aprovado
	public void videoAprovado() {
		Long id = JSFUtil.getParametroLong("itemId");
		Video objetoDoBanco = this.daoVideo.lerPorId(id);
		this.setVideo(objetoDoBanco);
		this.getVideo().setSituacao("Aprovado");
		this.daoVideo.salvar(this.getVideo());
		JSFUtil.retornarMensagemInfo("Vídeo aprovado", null, null);

	}

	public void videoNegado() {
		Long id = JSFUtil.getParametroLong("itemId");
		Video objetoDoBanco = this.daoVideo.lerPorId(id);
		this.setVideo(objetoDoBanco);
		this.getVideo().setSituacao("Negado");
		this.daoVideo.salvar(this.getVideo());
		JSFUtil.retornarMensagemInfo("Seu vídeo foi negado", null, null);

	}

	public String acaoCancelar() {
		// limpar o objeto da página
		this.setAdmin(new Administrador());

		return "dashboard.xhtml";
	}
	
	public void excluirUsuario(){
		JSFUtil.retornarMensagemAviso("Conta do aluno será excluída em breve.", null, null);
	}

	private LineChartModel lineModel2;
	private PieChartModel pieModel1;

	@PostConstruct
	public void init() {
		createLineModels();
		createPieModels();
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	private void createPieModels() {
		createPieModel1();
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();

		pieModel1.set("Redes", 15);
		pieModel1.set("Banco de dados", 20);
		pieModel1.set("Front-end", 32);
		pieModel1.set("Programação", 50);
		pieModel1.set("Outros", 5);
		pieModel1.setLegendPosition("w");
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	private void createLineModels() {

		lineModel2 = initCategoryModel();
		lineModel2.setLegendPosition("e");
		lineModel2.setShowPointLabels(true);
		lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Mês"));
		Axis yAxis = lineModel2.getAxis(AxisType.Y);
		yAxis.setLabel("Vídeos");
		yAxis.setMin(0);
		yAxis.setMax(100);
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		ChartSeries videos = new ChartSeries();
		videos.setLabel("Videos");
		videos.set("Ago", 5);
		videos.set("Set", 10);
		videos.set("Out", 15);
		videos.set("Nov", 50);
		videos.set("Dez", 30);

		model.addSeries(videos);

		return model;
	}

	public List <Video> getListaProblemas() {
		return listaProblemas;
	}

	public void setListaProblemas(List <Video> listaProblemas) {
		this.listaProblemas = listaProblemas;
	}
}