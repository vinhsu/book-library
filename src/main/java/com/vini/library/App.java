package com.vini.library;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vini.library.entity.Book;


public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
        initData();
        ConsoleHelper console = new ConsoleHelper();
        console.start();
        console.end();
    }

	private static void initData() {
		//Initialize data
        Book b1 = new Book("The Hobbit","J. R. R. Tolkien.", "best juvenile fiction");
        Book b2 = new Book("Harry Potter", "J. K. Rowling", "Fantasy, drama, young-adult fiction");
        Book b3 = new Book("The Hunger Games", "Suzanne Collins", "	Adventure, science fiction");
        Book b4 = new Book("Moby Dick", "Herman Melville", "Novel, adventure fiction, epic, sea story");
        Book b5 = new Book("Lord of the Rings", "J. R. R. Tolkien", "Fantasy novel  Adventure");
        
        List<Book> data = new ArrayList<Book>();
        data.add(b1);
        data.add(b2);
        data.add(b3);
        data.add(b4);
        data.add(b5);

        EntityManager em = EmUtility.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Book b : data) {
                em.persist(b);
                em.flush();
                em.clear();
            }
            tx.commit();
        } catch (Exception ex) {
        	logger.error(ex.getMessage());
        	if (tx.isActive())
        		tx.rollback();
        } 
    }
	
}
