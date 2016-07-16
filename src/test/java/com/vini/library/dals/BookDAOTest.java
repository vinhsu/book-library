package com.vini.library.dals;

import com.vini.library.entity.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by vini on 7/15/16.
 */
public class BookDAOTest {
    private static BookDAO mockedBookDAO;
    private static Book b1;
    private static Book b2;

    @Before
    public void steup() {
        mockedBookDAO = mock(BookDAO.class);
        //find all
        b1 = new Book("The Hobbit","J. R. R. Tolkien.", "best juvenile fiction");
        b2 = new Book("Harry Potter", "J. K. Rowling", "Fantasy, drama, young-adult fiction");
        when(mockedBookDAO.findAllBooks()).thenReturn(Arrays.asList(b1,b2));
        //add book
        Book newBook = new Book("Moby Dick", "Herman Melville", "Novel, adventure fiction, epic, sea story");
        when(mockedBookDAO.addBook(newBook)).thenReturn(newBook);
    }

    @Test
    public void testFindAllBooks() throws Exception {
        List<Book> mockedBooks = mockedBookDAO.findAllBooks();
        assertEquals(2, mockedBooks.size());
        Book m1 = mockedBooks.get(0);
        assertEquals(b1, m1);
        Book m2 = mockedBooks.get(1);
        assertEquals(b2, m2);
    }

    @Test
    public void testAddBook() throws Exception {
        Book b = new Book("Moby Dick", "Herman Melville", "Novel, adventure fiction, epic, sea story");
        assertEquals(b, mockedBookDAO.addBook(b));

    }
}