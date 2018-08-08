package controle;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import controle.util.JSFUtil;
import modelo.Administrador;
import modelo.Aluno;
import modelo.DAO.AdminDAO;
import modelo.DAO.AlunoDAO;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean autenticado = false;
	private Aluno aluno = new Aluno();
	private Administrador admin = new Administrador();

	private String email;
	private String senha;
	private String cpf;
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String acaoAutenticar() {

		AlunoDAO dao = new AlunoDAO();

		Aluno usuarioDoBanco = dao.lerPorEmail(this.getEmail());

		if (usuarioDoBanco == null) {
			JSFUtil.retornarMensagemErro("Usu�rio n�o existe.", null, null);
			return "login.jsf?faces-redirect=true";
		} else if (usuarioDoBanco.senhaCorreta(this.getSenha())) {

			this.setAluno(usuarioDoBanco);
			this.autenticado = true;

			return "aluno/perfil.jsf?faces-redirect=true";
		} else {
			JSFUtil.retornarMensagemErro("Senha inv�lida.", null, null);
			return "login.jsf?faces-redirect=true";
		}
	}

	public String autenticarAdmin() {
		AdminDAO adminDao = new AdminDAO();
		Administrador buscaDoBanco = adminDao.lerPorCPF(this.getCpf());

		if (buscaDoBanco == null) {
			JSFUtil.retornarMensagemAviso("Usu�rio n�o encontrado", null, null);
			return "admin/loginAdmin.jsf?faces-redirect=true";
		} else if (buscaDoBanco.senhaCorreta(this.getSenha())) {
			this.setAdmin(buscaDoBanco);
			this.autenticado = true;
			JSFUtil.retornarMensagemAviso("Usu�rio autenticado", null, null);
			return "admin/dashboard.jsf?faces-redirect=true";
		} else {
			JSFUtil.retornarMensagemErro("Senha inv�lida", null, null);
			return "admin/loginAdmin.jsf?faces-redirect=true";
		}
	}

	public void Logout() throws IOException {
		this.aluno = new Aluno();
		this.autenticado = false;
		this.email = null;
		this.cpf = null;
		this.senha = null;
		// encerrar a sess�o atual
		JSFUtil.getHttpSession().invalidate();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		JSFUtil.retornarMensagemInfo("Sess�o Encerrada!", null, null);
		context.getExternalContext().redirect("../login.xhtml");
		}
}