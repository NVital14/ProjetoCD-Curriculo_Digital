/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blockchain.utils;

import java.math.BigInteger;
import java.math.MathContext;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noemi
 */
public class MinerPar {

    private static class MinerThr extends Thread {

        AtomicInteger myTicket;
        AtomicInteger nonce;
        int dificulty;
        String data;

        public MinerThr(AtomicInteger ticket, AtomicInteger n, int dificulty, String data) {
            this.myTicket = ticket;
            this.nonce = n;
            this.dificulty = dificulty;
            this.data = data;

        }

        public void run() {
            String zeros = String.format("%0" + dificulty + "d", 0);
            while (nonce.get() == 0) {
                int n = myTicket.getAndIncrement();
                String hash = Hash.getHash(n + data);
                if (hash.endsWith(zeros)) {
                    nonce.set(n);
                }
            }
        }
    }

    public static int getNonce(String data, int dificulty) {
        int numCores = Runtime.getRuntime().availableProcessors();
        MinerThr[] thr = new MinerThr[numCores];
        AtomicInteger sharedNonce = new AtomicInteger(0);
        AtomicInteger trueNonce = new AtomicInteger(0);
        //------- construir e pôr as threads a correr ------
        for (int i = 0; i < thr.length; i++) {
            thr[i] = new MinerThr(sharedNonce, trueNonce, dificulty, data);
            thr[i].start();
        }
         try {
            //esperar que a primeira termine == todas terminarem
            thr[0].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MinerPar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trueNonce.get();
    }   
}
