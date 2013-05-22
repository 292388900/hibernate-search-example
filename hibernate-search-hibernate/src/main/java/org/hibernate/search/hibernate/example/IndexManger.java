package org.hibernate.search.hibernate.example;


import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.InitializingBean;

/**
 * ����Spring�����ĵĴ�������������
 * @author Administrator
 *
 */
public class IndexManger implements InitializingBean{
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//�ؽ�����
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		
		fullTextSession.createIndexer().start();
	}
}
