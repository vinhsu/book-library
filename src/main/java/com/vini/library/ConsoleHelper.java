package com.vini.library;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.vini.library.views.BookView;

/**
 * Created by vin on 7/14/16.
 */
public final class ConsoleHelper {

    /**
     * start console
     */
    public void start(){
        boolean isExit = false;
        while (!isExit) {
            int option = menu();
            if (option == 5)
                isExit = true;
        }
    }

    /**
     * contorller to assign which module
     * @return
     */
    private int menu() {
        Scanner scan = new Scanner(System.in);

        boolean validData = false;
        int number = 0;
        do {
            BookView.INSTANCE.bookManager();
            
            try{
                number = scan.nextInt();

                switch (number){
                    case 1:
                        BookView.INSTANCE.viewAllBooks();
                        break;
                    case 2:
                    	BookView.INSTANCE.addBook();
                        break;
                    case 3:
                    	BookView.INSTANCE.editBook();
                        break;
                    case 4:
                    	BookView.INSTANCE.searchBook();
                        break;
                    case 5:
                        System.out.println("Library saved.");
                        validData = true;
                        break;
                }

                if (number <= 0 || number > 5)
                    System.out.println("choose number only 1 to 5");

            }catch(InputMismatchException e){
                System.out.println("Input has to be a number. ");
            }
        }while(!validData);
        
        return number;
    }

    /**
     * close EntityMnager transaction
     */
    public void end() {
        EmUtility.close();
    }
}
