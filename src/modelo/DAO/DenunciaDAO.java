package modelo.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import controle.util.JpaDAO;
import modelo.Denuncia;

public class DenunciaDAO extends JpaDAO<Denuncia>{

	public DenunciaDAO() {
		super();
	}
	
	public DenunciaDAO(EntityManager manager){
		super(manager);
	}
	
	public List<Denuncia> denuncias(){
		return this.getEntityManager().createQuery("select d from Denuncia d",Denuncia.class).getResultList();

	}
}
