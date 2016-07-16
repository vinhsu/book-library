package com.vini.library.views;

import java.util.*;

import com.vini.library.services.BookService;
import com.vini.library.services.BookServiceImpl;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vini.library.entity.Book;

public enum BookView {   
	INSTANCE;
	
    private final BookService service;
    private final Logger logger = LoggerFactory.getLogger(BookView.class);
    
	BookView() {
		service = new BookServiceImpl();
	}
	
	public static BookView getInstance()
    {
        return INSTANCE;
    }

    /**
     * main menu
     */
    public void bookManager() {
    	System.out.println("==== Book Manager ====");
    	System.out.println("\t 1) View all books");
    	System.out.println("\t 2) Add a book");
    	System.out.println("\t 3) Edit a book");
    	System.out.println("\t 4) Search for a book");
    	System.out.println("\t 5) Save and exit");
    	System.out.print("Choose [1-5]: ");
	}

    /**
     * search all books from database and display simple ID, title
     */
	public void viewAllBooks() {
    	System.out.println("\n==== View Books ====");

        List<Book> books = service.findAllBooks();
    	listBooks(books);
        viewAllBookDetail(books);
    }

    /**
     * list simple format of books
     * @param books
     */
    private void listBooks(List<Book> books){
        if (books != null) {
            for (Book b : books) {
                System.out.println("\t ["+b.getId()+"] "+b.getTitle());
            }
        }
    }

    /**
     * display book's detail
     * @param books
     */
	public void viewAllBookDetail(List<Book> books) {
        if (books == null) {
            System.out.println("\t Library is empty.");
            return;
        }

		boolean isExit = false;
		do{
	        System.out.println("To view details enter the book ID, to return press <Enter>.\n");
	        System.out.print("Book ID: ");
	        try{
	            Scanner scan = new Scanner(System.in);
	            String reading = scan.nextLine();
	            if (reading.isEmpty()){
	            	isExit = true;
	            } else {
	            	if (NumberUtils.isDigits(reading)) {
                        Map<Integer, Book> bookMap = listToMap(books);
			            int id = Integer.parseInt(reading);
			            if (bookMap.keySet().contains(id)) {
					        Book b = bookMap.get(id);
                            showDetail(b);
			            } else {
			            	System.out.println("\t You choose ID:"+id+" is not at search list, please try again.");
			            }
	            	}else {
	            		System.out.println("\t You choose ID:"+reading+" is not number, please try again.");
	            	}
		        }
	        }catch(InputMismatchException e){
	            System.out.println("Input is wrong");
	            isExit = true;
	        }
		}while(!isExit);
	}

    /**
     * search book's tile by key word
     */
    public void searchBook() {
        System.out.println("\n==== Search Book ====");

        System.out.println("Type in one or more keywords to search for");
        System.out.print("\t Search: ");
        try{
            Scanner scan = new Scanner(System.in);
            String reading = scan.nextLine();
            List<Book> books = service.searchBook(reading);
            if (books != null && !books.isEmpty()) {
                System.out.println("The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");
                listBooks(books);
                viewAllBookDetail(books);
            } else {
                System.out.println("Sorry, we can't find a matched Book.\n");
            }
        }catch(InputMismatchException e){
            System.out.println("Input is wrong");
        }
    }

    /**
     * choose book from list of books to edit
     */
	public void editBook() {
    	System.out.println("\n==== Edit a Book ====");

        List<Book> books = service.findAllBooks();
    	listBooks(books);
    	if (books == null){
    		System.out.println("\t Library is empty.");
    	} else {
            Map<Integer, Book> bookMap = listToMap(books);
    		editBookDetail(bookMap);
    	}
	}

    /**
     *  utility to convert list<Book> to map<ID, Book>
     * @param books
     * @return
     */
    private Map<Integer, Book> listToMap(List<Book> books){
        Map<Integer, Book> bookMap = new HashMap<Integer, Book>();
        for (Book b : books) {
            bookMap.put(b.getId(), b);
        }
        return bookMap;
    }

    /**
     *  edit selected book
     * @param bookMap
     */
    private void editBookDetail(Map<Integer, Book> bookMap) {
		boolean isExit = false;
		do{
        	System.out.println("Enter the book ID of the book you want to edit; to return press <Enter>.\n");
	        System.out.print("Book ID: ");
	        try{
	            Scanner scan = new Scanner(System.in);
	            String reading = scan.nextLine();
	            if (reading.isEmpty()){
	            	isExit = true;
	            } else {
	            	if (NumberUtils.isDigits(reading)) {
	            		int id = Integer.parseInt(reading);
	            		if (bookMap.keySet().contains(id)) {
	            			Book b = bookMap.get(id);
	            			String title = valideEditInput(scan, "Title ", b.getTitle());
	            			String author = valideEditInput(scan, "Author ", b.getAuthor());
	            			String description = valideEditInput(scan, "Description ", b.getDescription());
	            			//update
	            			b.setTitle(title);
	            			b.setAuthor(author);
	            			b.setDescription(description);
	            			service.editBook(b);
                        } else {
	            			System.out.println("\t You choose ID:"+id+" is not at search list, please try again.");
	            		}
	            	} else {
	            		System.out.println("\t You choose ID:"+reading+" is not number, please try again.");
	            	}
		        }
	        }catch(InputMismatchException e){
	            System.out.println("Input is wrong");
	            isExit = true;
	        }
		}while(!isExit);
	}

    /**
     * add new book
     */
	public void addBook() {
    	System.out.println("\n==== Add a Book ====");
    	System.out.println("Please enter the following information:");
    	
    	Book newBook = null;
        try{
            Scanner scan = new Scanner(System.in);
            String title = valideNewInput(scan, "Title: ");
            String author = valideNewInput(scan, "Author: ");
            String description = valideNewInput(scan, "Description: ");
            newBook = new Book(title,author,description);
    		service.addBook(newBook);
        }catch(InputMismatchException e){
            System.out.println("Input is wrong");
        }

        System.out.println("Book ["+newBook.getId()+"] Saved");
	}

    /**
     * validate new book's input
     * @param scan
     * @param stat
     * @return
     */
	private String valideNewInput(Scanner scan, String stat){
		boolean isExit = false;
		String reading = null;
        do {
            System.out.println(stat);
    		reading = scan.nextLine();
 	        if (reading.isEmpty())
	        	System.out.println("Your input is empty, please try again");
	        else
	        	isExit = true;
        } while(!isExit);
        return reading;
	}

    /**
     * validate update book's input
     * @param scan
     * @param stat
     * @param orig
     * @return
     */
	private String valideEditInput(Scanner scan, String stat, String orig){
		String reading = null;
        System.out.print(stat+"["+orig+"]: ");
		reading = scan.nextLine();
        if (reading.isEmpty())
        	reading = orig;
        return reading;
	}

    /**
     * display book detail
     * @param b
     */
    private void showDetail(Book b) {
        System.out.println("\t ID: "+b.getId());
        System.out.println("\t Title: "+b.getTitle());
        System.out.println("\t Author: "+b.getAuthor());
        System.out.println("\t Description: "+b.getDescription());
    }
}
