package modelo.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import controle.util.JpaDAO;
import modelo.Comentario;
import modelo.Moderacao;

public class NotificacaoDAO extends JpaDAO<Moderacao> {

	public NotificacaoDAO() {
		super();
	}

	public NotificacaoDAO(EntityManager manager) {
		super(manager);
	}

	public List<Moderacao> notificacoes(Long id) {
		return this.getEntityManager().createQuery("select m from Moderacao m where m.aluno.id =:id", Moderacao.class)
				.setParameter("id", id).setMaxResults(5).getResultList();

	}

}
