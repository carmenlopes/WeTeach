package modelo.DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import controle.util.JpaDAO;
import modelo.Administrador;

public class AdminDAO extends JpaDAO<Administrador> {
	
	
	public AdminDAO() {
		super();
	}

	public AdminDAO(EntityManager manager) {
		super(manager);
	}

	public Administrador lerPorCPF(String cpf) {
		Administrador resultado;

		Query consulta = this.getEntityManager().createQuery("from Administrador ad where ad.cpf =:cpf");
		consulta.setParameter("cpf", cpf);

		try {
			resultado = (Administrador) consulta.getSingleResult();
		} catch (NoResultException e) {
			resultado = null;
		}

		return resultado;
	}
}
