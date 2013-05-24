package org.hibernate.search.jpa.example.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.hibernate.search.jpa.example.dao.BookDao;
import org.hibernate.search.jpa.example.model.Author;
import org.hibernate.search.jpa.example.model.Book;
import org.hibernate.search.jpa.example.model.QueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class BookDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource(name="bookDaoImpl")
	private BookDao bookDao ;
	
	@Test
	public void query(){
		List<Book> list = bookDao.query(5, 5);
		for (Book book : list) {
			System.out.println(book.getName());
		}
	}
	
	
	@Test
	public void delete(){
		bookDao.delete(11);
	}
	
	@Test
	public void save(){
		Book book = new Book();
		book.setName("΢��Ӫ������:�ƶ�������ʱ����Ӫ������");
		book.setPublicationDate(new Date());
		book.setDescription("��΢��Ӫ������:�ƶ�������ʱ����Ӫ��������������΢��Ӫ��ר�ҡ�΢��Ӫ�������ߡ�΢��Ӫ�����۵��������׫д�����ݻ�������ҵ�͸�����΢��Ӫ�������󣬴����۲����΢��Ӫ���ı��ʡ�Ҫ�塢���ļ�ֵ�����������̽�֣�ϵͳ�ܽ���΢��Ӫ����ԭ�򡢷��������衢���ɣ��Լ�Ӫ��Ч����������������������ʵ�ٲ����10�����ҵ��΢��Ӫ��ǰ��������ȫ��Ľ���������˽����������13���ɹ��ľ���΢��Ӫ��������ʵʩ���̽������������������΢��Ӫ��������Ӫ��ý������Ͻ����˲�����������������Ϳɲ����ԡ�");
		
		Set<Author> authors=new HashSet<Author>();
		authors.add(new Author("�����"));
		authors.add(new Author("��С��"));
		book.setAuthors(authors);
		bookDao.add(book);
	}
	
	@Test
	public void search(){
		int start=0;
		int pagesize=5;
		Analyzer analyzer=new PaodingAnalyzer();
		String[] field=new String[]{"name","description","authors.name"};
		QueryResult<Book> queryResult= null;
		try {
			queryResult = bookDao.query("ʵս", start, pagesize, analyzer, field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("��������["+queryResult.getSearchresultsize()+"]����¼!");
		
		for (Book book : queryResult.getSearchresult()) {
			System.out.println("����:"+book.getName()+"\n����:"+book.getDescription()+"\n��������:"+book.getPublicationDate());
			System.out.println("----------------------------------------------------------");
		}
	}
	
}
