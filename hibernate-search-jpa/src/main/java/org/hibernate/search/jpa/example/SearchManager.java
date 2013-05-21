package org.hibernate.search.jpa.example;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.jpa.example.model.Author;
import org.hibernate.search.jpa.example.model.Book;

public class SearchManager {
	
	public static void main(String[] args) throws Exception{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate.search");
		EntityManager em = emf.createEntityManager();
		FullTextEntityManager fullTextEntityManager =Search.getFullTextEntityManager(em);
		
		//ʹ��Hibernate Search api��ѯ �Ӷ���ֶ�ƥ�� name��description��authors.name
//		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Book.class ).get();
//		Query luceneQuery = qb.keyword().onFields("name","description","authors.name").matching("�ƶ�������").createQuery();
		
		//ʹ��lucene api��ѯ �Ӷ���ֶ�ƥ�� name��description��authors.name
		//ʹ���Ҷ��ִ���
		MultiFieldQueryParser queryParser=new MultiFieldQueryParser(Version.LUCENE_36, new String[]{"name","description","authors.name"}, new PaodingAnalyzer());
		Query luceneQuery=queryParser.parse("ios");
		
		FullTextQuery fullTextQuery =fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);
		//����ÿҳ��ʾ������
		fullTextQuery.setMaxResults(5);
		//���õ�ǰҳ
		fullTextQuery.setFirstResult(0);
		
		//��������
		SimpleHTMLFormatter formatter=new SimpleHTMLFormatter("<b><font color='red'>", "<font/></b>");
		QueryScorer queryScorer=new QueryScorer(luceneQuery);
		Highlighter highlighter=new Highlighter(formatter, queryScorer);

		@SuppressWarnings("unchecked")
		List<Book> resultList = fullTextQuery.getResultList();
		System.out.println("�����ҵ�["+resultList.size()+"]����¼");
		for (Book book : resultList) {
			String highlighterString=null;
			Analyzer analyzer=new PaodingAnalyzer();
			try {
				//����name
				highlighterString=highlighter.getBestFragment(analyzer, "name", book.getName());
				if(highlighterString!=null){
					book.setName(highlighterString);
				}
				//����authors.name
				Set<Author> authors = book.getAuthors();
				for (Author author : authors) {
					highlighterString=highlighter.getBestFragment(analyzer, "authors.name", author.getName());
					if(highlighterString!=null){
						author.setName(highlighterString);
					}
				}
				//����description
				highlighterString=highlighter.getBestFragment(analyzer, "description", book.getDescription());
				if(highlighterString!=null){
					book.setDescription(highlighterString);
				}
			} catch (Exception e) {
			}
			
			System.out.println("����:"+book.getName()+"\n����:"+book.getDescription()+"\n��������:"+book.getPublicationDate());
			System.out.println("----------------------------------------------------------");
		}
		em.close();
	}
}
