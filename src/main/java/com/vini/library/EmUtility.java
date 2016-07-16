package com.vini.library;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * EntityManager SessionFactory connection
 */
public class EmUtility {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("librarydb");
    private static EntityManager em = emf.createEntityManager();
    
    public static EntityManager getEntityManager(){
    	return em;
    }
    
    public static void close() {
    	if (em != null) 
    		em.close();
    	if (emf != null) 
    		emf.close();
    }
}
