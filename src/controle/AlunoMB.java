package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import controle.util.JSFUtil;
import modelo.Aluno;
import modelo.DAO.AlunoDAO;

@ManagedBean(name = "alunoMB")
@SessionScoped
public class AlunoMB {

	private Aluno aluno = new Aluno();
	private AlunoDAO dao = new AlunoDAO();
	private List<Aluno> alunos = null;
	// private boolean skip;
	private Long id;
	private Part file;
	
	private static List<String> listaInteresse = new ArrayList<String>();

	static {
		listaInteresse.add("Ui Design");
		listaInteresse.add("PostgreSQL");
		listaInteresse.add("Java");
		listaInteresse.add("PHP");
		listaInteresse.add("Gestão/ Governança");
		listaInteresse.add("Redes de Computadores");

	}

	public List<String> getListaInteresse() {
		return AlunoMB.listaInteresse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		if (this.alunos == null)
			this.alunos = this.dao.lerTodos();

		return this.alunos;
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

	public String acaoAbrirAlteracao() {
		Long id = JSFUtil.getParametroLong("itemId");
		Aluno objetoDoBanco = this.dao.lerPorId(id);
		this.setAluno(objetoDoBanco);

		return "editarDados.xhtml";
	}

	public String salvarAlteracoes() throws IOException {
		/**
		 * Deve limpar o ID com valor zero, pois o JSF sempre converte o campo
		 * vazio para um LONG = 0.
		 */
		if ((this.getAluno().getId() != null) && (this.getAluno().getId().longValue() == 0))
			this.getAluno().setId(null);

		if (this.getFile() != null) {
			this.getAluno().setFotoPerfil(fileUpload());
		}

		this.dao.salvar(this.getAluno());
		// limpa a lista
		this.alunos = null;
		// limpar o objeto da página
		this.setAluno(new Aluno());
		JSFUtil.retornarMensagemAviso("Os dados alterados aparecerão no proximo logon", null, null);

		return "perfil.xhtml";
	}

	public String acaoSalvar() throws IOException {
		this.getAluno().setFotoPerfil(fileUpload());

		this.dao.salvar(this.getAluno());

		// limpa a lista
		this.alunos = null;

		// limpar o objeto da página
		this.setAluno(new Aluno());
		JSFUtil.retornarMensagemInfo("Aluno foi cadastrado com sucesso", null, null);

		// executa a ação listar e retorna a sua página
		return "login.jsf";
	}

	public String acaoCancelar() {
		// limpar o objeto da página
		this.setAluno(new Aluno());

		return "perfil.xhtml";
	}

	// Excluir tudo o que pertence ao usuário, chamar todas as classes
	public String acaoExcluir() {
		dao.excluir(getAluno());
		Aluno aluno = dao.lerPorEmail(getAluno().getEmail());
		if (aluno.getEmail() != null) {
			return "Erro";
		}

		return "login";
	}

	/* Carrega a pagina de perfil do aluno selecionado */
	public void carregarPaginaUsuario() {
		this.aluno = dao.lerPorId(this.id);
	}

	/*
	 * public boolean isSkip() { return skip; }
	 * 
	 * public void setSkip(boolean skip) { this.skip = skip; }
	 * 
	 * public String onFlowProcess(FlowEvent event) { if (skip) { skip = false;
	 * // reset in case user goes back return "confirm"; } else { return
	 * event.getNewStep(); } }
	 */
}