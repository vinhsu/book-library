package com.vini.library.services;

import com.vini.library.dals.BookDAO;
import com.vini.library.entity.Book;

import java.util.List;

/**
 * Created by vin on 7/14/16.
 */
public class BookServiceImpl implements BookService{
	private BookDAO dao = BookDAO.getInstance();
	
    @Override
    public List<Book> findAllBooks() {
        return dao.findAllBooks();
    }

    @Override
    public Book addBook(Book book) {
    	return dao.addBook(book);
    }

    @Override
    public Book editBook(Book book) {
        return dao.editBook(book);
    }

    @Override
    public List<Book> searchBook(String key) {
        return dao.searchBook(key);
    }

	@Override
	public Book findById(int id) {
        return dao.findById(id);
	}
}
