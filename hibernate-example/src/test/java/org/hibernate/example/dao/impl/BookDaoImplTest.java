package org.hibernate.example.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.example.dao.BookDao;
import org.hibernate.example.model.Author;
import org.hibernate.example.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager="hibernate4TransactionManager", defaultRollback=false)
@Transactional
public class BookDaoImplTest {
	
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
	public void update(){
		Book book = bookDao.load(11);
		book.setName("��ĥ���ģʽ");
		book.setPublicationDate(new Date());
		book.setDescription("����ĥ���ģʽ����������gof������23�����ģʽ������ϸϸ��ĥ���������ݴӻ������𣬰���ÿ��ģʽ�Ķ��塢���ܡ�˼·���ṹ������ʵ�֡����е���˳�򡢻���Ӧ��ʾ���ȣ��ö�����ϵͳ��������׼ȷ������ÿ��ģʽ��������ȷ�ġ���ƹۡ����и߼�����������̽����������Щģʽ������ģʽ���̺�ʲô�������˼�룬ģʽ�ı�����ʲô��ģʽ��ν��ʵ��Ӧ�ã�ģʽ����ȱ���Լ�������ģʽ�Ĺ�ϵ�ȣ������ö��߾���ȥ��������ÿ�����ģʽ�ľ������ڡ�");
		
		Set<Author> authors=new HashSet<Author>();
		authors.add(new Author("�³�"));
		authors.add(new Author("����"));
		book.setAuthors(authors);
		bookDao.update(book);
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
	
	
}
