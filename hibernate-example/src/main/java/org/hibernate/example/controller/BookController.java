package org.hibernate.example.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.example.model.Author;
import org.hibernate.example.model.Book;
import org.hibernate.example.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {
	
	@Resource(name="bookServiceImpl")
	private BookService bookService;
	
	@RequestMapping("/query/{start}/{pagesize}")
	public ModelAndView query(@PathVariable(value="start")int start,@PathVariable(value="pagesize")int pagesize){
		List<Book> lists = null;
		try {
			lists = bookService.query(start, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("list");
		modelAndView.addObject("lists", lists);
		return modelAndView;
	}
	
	
	
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable(value="id")int id){
		
		bookService.delete(id);
		
		return query(0, 5);
		
	}
	
	
	@RequestMapping("/save")
	public void save(){
		Book book = new Book();
		
		book.setName("��ĥ���ģʽ");
		book.setPublicationDate(new Date());
		book.setDescription("����ĥ���ģʽ����������gof������23�����ģʽ������ϸϸ��ĥ���������ݴӻ������𣬰���ÿ��ģʽ�Ķ��塢���ܡ�˼·���ṹ������ʵ�֡����е���˳�򡢻���Ӧ��ʾ���ȣ��ö�����ϵͳ��������׼ȷ������ÿ��ģʽ��������ȷ�ġ���ƹۡ����и߼�����������̽����������Щģʽ������ģʽ���̺�ʲô�������˼�룬ģʽ�ı�����ʲô��ģʽ��ν��ʵ��Ӧ�ã�ģʽ����ȱ���Լ�������ģʽ�Ĺ�ϵ�ȣ������ö��߾���ȥ��������ÿ�����ģʽ�ľ������ڡ�");
		
		Set<Author> authors=new HashSet<Author>();
		
		authors.add(new Author("�³�"));
		authors.add(new Author("����"));
		
		book.setAuthors(authors);
		
		bookService.add(book);
		
	}
	
	
	
	@RequestMapping("/modify/{id}")
	public void modify(@PathVariable(value="id")int id){
		Book book = bookService.load(id);
		
		book.setName("��ĥ���ģʽ");
		book.setPublicationDate(new Date());
		book.setDescription("����ĥ���ģʽ����������gof������23�����ģʽ������ϸϸ��ĥ���������ݴӻ������𣬰���ÿ��ģʽ�Ķ��塢���ܡ�˼·���ṹ������ʵ�֡����е���˳�򡢻���Ӧ��ʾ���ȣ��ö�����ϵͳ��������׼ȷ������ÿ��ģʽ��������ȷ�ġ���ƹۡ����и߼�����������̽����������Щģʽ������ģʽ���̺�ʲô�������˼�룬ģʽ�ı�����ʲô��ģʽ��ν��ʵ��Ӧ�ã�ģʽ����ȱ���Լ�������ģʽ�Ĺ�ϵ�ȣ������ö��߾���ȥ��������ÿ�����ģʽ�ľ������ڡ�");
		
		Set<Author> authors=book.getAuthors();
		
		authors.add(new Author("�³�"));
		authors.add(new Author("����"));
		
		book.setAuthors(authors);
		
		bookService.update(book);
		
	}
	
}
