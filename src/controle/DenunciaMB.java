package controle;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import controle.util.JSFUtil;
import modelo.Denuncia;
import modelo.DAO.AlunoDAO;
import modelo.DAO.ComentarioDAO;
import modelo.DAO.DenunciaDAO;

@ManagedBean(name = "denunciaMB")
@SessionScoped
public class DenunciaMB {
	private Denuncia denuncia = new Denuncia();
	private DenunciaDAO denunciaDAO = new DenunciaDAO();

	private List<Denuncia> ListaDenuncias = null;

	private static List<String> listaMsg = new ArrayList<String>();

	static {
		listaMsg.add("É Spam");
		listaMsg.add("É abusivo ou nocivo");
		listaMsg.add("Viola a Política de Regras");
		listaMsg.add("Outros");
	}

	public List<Denuncia> getListaDenuncias() {
		this.ListaDenuncias = this.denunciaDAO.denuncias();
		return ListaDenuncias;
	}

	public void setListaDenuncias(List<Denuncia> listaDenuncias) {
		ListaDenuncias = listaDenuncias;
	}

	public List<String> getListaMsg() {
		return DenunciaMB.listaMsg;
	}

	public Denuncia getDenuncia() {
		return denuncia;
	}

	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}

	@ManagedProperty(value = "#{loginMB}")
	private LoginMB loginMB;

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB) {
		this.loginMB = loginMB;
	}

	@ManagedProperty(value = "#{comentarioMB}")
	private ComentarioMB comentMB;

	public ComentarioMB getComentMB() {
		return comentMB;
	}

	public void setComentMB(ComentarioMB comentMB) {
		this.comentMB = comentMB;
	}

	public void acaoDenunciar(Long id) {
		AlunoDAO alunoDAO = new AlunoDAO();
		ComentarioDAO comentDao = new ComentarioDAO();

		this.getDenuncia().setAluno(alunoDAO.lerPorEmail(this.getLoginMB().getAluno().getEmail()));
		this.getDenuncia().setComentario(comentDao.lerPorId(id));
		this.denunciaDAO.salvar(this.getDenuncia());
		this.denuncia = null;
		this.setDenuncia(new Denuncia());
		JSFUtil.retornarMensagemInfo("Sua denúncia foi registrada! Obrigado!", null, null);
	}

	public void permitirComentario() {
		Long id = JSFUtil.getParametroLong("itemId");
		try {
			this.denunciaDAO.excluir(getDenuncia());
			Denuncia objetoDoBanco = this.denunciaDAO.lerPorId(id);
			this.denunciaDAO.excluir(objetoDoBanco);
		} catch (IllegalArgumentException e) {
			JSFUtil.retornarMensagemInfo("Denúcia descartada", null, null);

		}
		JSFUtil.retornarMensagemInfo("Denúcia descartada", null, null);

	}

}
