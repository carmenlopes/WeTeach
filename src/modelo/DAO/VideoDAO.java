package modelo.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import controle.util.JpaDAO;
import modelo.Aluno;
import modelo.Video;

public class VideoDAO extends JpaDAO<Video> {
	public VideoDAO() {
		super();
	}

	public VideoDAO(EntityManager manager) {
		super(manager);
	}

	public List<Video> maisAcessados() {
		return this.getEntityManager()
				.createQuery("select v from Video v order by v.visualizacoes desc and v.situacao LIKE 'Aprovado'",
						Video.class)
				.setMaxResults(6).getResultList();
	}

	public List<Video> videos(Long id) {
		return this.getEntityManager()
				.createQuery("select v from Video v where v.autor.id =:id and v.situacao LIKE 'Aprovado'", Video.class)
				.setParameter("id", id).getResultList();
	}

	public List<Video> verificarSituacao() {
		return this.getEntityManager()
				.createQuery("select v from Video v where v.situacao LIKE 'Em verificação%'", Video.class)
				.getResultList();
	}

	public List<Video> videosInteressantes() {
		return this.getEntityManager()
				.createQuery("select v from Video v where v.situacao LIKE 'Aprovado'", Video.class).setMaxResults(5)
				.getResultList();
	}

	public Video buscarVideo(String campoBusca) {
		Video resultado;

		Query consulta = this.getEntityManager().createQuery("from Video v where v.titulo = :campoBusca");
		consulta.setParameter("campoBusca", campoBusca);

		try {
			resultado = (Video) consulta.getSingleResult();
		} catch (NoResultException e) {
			resultado = null;
		}

		return resultado;

	}

	public List<Video> buscaTag(String campoBusca) {
		
		return this.getEntityManager().createQuery("select v from Video v where v.tag = :campoBusca and v.situacao LIKE 'Aprovado'",Video.class)
				.setParameter("campoBusca", campoBusca).getResultList();
	}
}