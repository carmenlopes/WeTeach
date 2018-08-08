package modelo.DAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import controle.util.JpaDAO;
import modelo.Aluno;

public class AlunoDAO extends JpaDAO<Aluno> {
	public AlunoDAO() {
		super();
	}

	public AlunoDAO(EntityManager manager) {
		super(manager);
	}
	
	public Aluno lerPorEmail(String email) {
		Aluno resultado;

		Query consulta = this.getEntityManager().createQuery("from Aluno a where a.email = :email");
		consulta.setParameter("email", email);
		

		try {
			resultado = (Aluno) consulta.getSingleResult();
		} catch (NoResultException e) {
			resultado = null;
		}

		return resultado;
	}

}
