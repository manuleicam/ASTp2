/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp2;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Manuel
 */
public class BuscarPrecos implements Runnable,Serializable {

    private HashMap<String, Utilizador> utilizadores;
    private HashMap<String, Ativos> activos;
    private HashMap<String, Double> vals;

    public BuscarPrecos(HashMap<String, Utilizador> utilizadores, HashMap<String, Ativos> activos, HashMap<String, Double> vals) {
        this.utilizadores = utilizadores;
        this.activos = activos;
        this.vals = vals;
    }

    public void getFromAPI() {
        String[] parts;
        String nome;
        double value;
        String url = "http://download.finance.yahoo.com/d/quotes.csv?s=GC=F,SI=F,CL=F,NVDA,IBM,GOOG,AAPL&f=sl1d1t1c1ohgv&columns='symbol,price'&e=.csv";
        try {
            URL yahooData = new URL(url);
            URLConnection data = yahooData.openConnection();
            Scanner input = new Scanner(data.getInputStream());
            String line = input.nextLine();
            while (input.hasNextLine()) {
                parts = line.split(",");
                nome = parts[0];
                nome = nome.replace("\"", "");
                value = Double.parseDouble(parts[1]);
                atualizaPrecos(nome, value);
                line = input.nextLine();
            }
            parts = line.split(",");
            nome = parts[0];
            value = Double.parseDouble(parts[1]);
            atualizaPrecos(nome, value);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void run() {
        double maxaux, minaux;
        while (true) {
            
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ex) {
            }
            for (Ativos a : activos.values()) {
                maxaux = a.getActualValue() + 60;
                minaux = a.getActualValue() - 30;
                double result = Math.random() * (maxaux - minaux) + minaux;
                a.setOldValue(a.getActualValue());
                a.setActualValue(result);
                a.setDif();
                vals.put(a.getNome(),result);
                //actValue(a.getNome(), result);
            }
            
        }
    }



    public void atualizaPrecos(String code, double valor) {
        for (Ativos a : activos.values()) {
            if (a.getCode().equals(code)) {
                a.setOldValue(a.getActualValue());
                a.setActualValue(valor);
                a.setDif();
            }
        }
    }

}
