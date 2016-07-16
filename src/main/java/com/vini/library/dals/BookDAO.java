package com.vini.library.dals;

import com.vini.library.ConsoleHelper;
import com.vini.library.EmUtility;
import com.vini.library.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BookDAO {
    private static BookDAO ourInstance = new BookDAO();
	private EntityManager em = EmUtility.getEntityManager();
	
    private final Logger logger = LoggerFactory.getLogger(BookDAO.class);
    
    public static BookDAO getInstance() {
        return ourInstance;
    }

    private BookDAO() {}

    public List<Book> findAllBooks(){
        List<Book> books = null;
        try {
            Query query = em.createQuery("Select b from Book b");
            books = query.getResultList();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return books;
    }

	public Book addBook(Book book) {
    	Book ret = null;
    	EntityTransaction tx = em.getTransaction();
        try {
        	tx.begin( );
        	em.persist(book);
        	tx.commit(); 
        	ret = book;
        }catch (Exception ex){
        	System.out.println(ex.getMessage());
        	if (tx.isActive()) {
        		tx.rollback();
        	}
        }
        return ret;
	}

	public Book editBook(Book book) {
		Book ret = null;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin( );
			Book udateBook = em.find(Book.class, book.getId());
			udateBook.setTitle(book.getTitle());
			udateBook.setAuthor(book.getAuthor());
			udateBook.setDescription(book.getDescription());
			tx.commit(); 
			ret = udateBook;
		}catch (Exception ex){
			System.out.println(ex.getMessage());
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return ret;
	}

	public List<Book> searchBook(String key) {
		List<Book> ret = null;
		EntityTransaction tx = em.getTransaction();
		try {
			Query query = em.createQuery("Select book from Book book where book.title LIKE :keyword");
			query.setParameter("keyword", "%"+key+"%");
			ret = query.getResultList();
		}catch (Exception ex){
			System.out.println(ex.getMessage());
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return ret;
	}
    
	public Book findById(int id) {
		EntityTransaction tx = em.getTransaction();
		Book book = null;
		try {
			book = em.find(Book.class, id);
		}catch (Exception ex){
			System.out.println(ex.getMessage());
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return book;
	}
}
