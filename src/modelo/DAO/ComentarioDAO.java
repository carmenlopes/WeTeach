package modelo.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import controle.util.JpaDAO;
import modelo.Comentario;

public class ComentarioDAO extends JpaDAO<Comentario> {
	public ComentarioDAO() {
		super();
	}
	
	public ComentarioDAO(EntityManager manager){
		super(manager);
	}
	
	
	public List<Comentario> comentarios(Long id){
		return this.getEntityManager().createQuery("select c from Comentario c where c.video.id =:id", Comentario.class).setParameter("id", id)
                .getResultList();

	}
}
