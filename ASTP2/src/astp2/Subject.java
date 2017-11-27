/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp2;

import java.util.HashMap;

/**
 *
 * @author Manuel
 */
public interface Subject {
    
    public void notifyObserver();
    public void registarObserver(String nome, String pass, double dinh);
    public void removeObserver();
    
}
