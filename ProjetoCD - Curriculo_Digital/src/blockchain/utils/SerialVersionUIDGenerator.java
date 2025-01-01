/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blockchain.utils;

import curriculumdigital.core.Submission;
import java.io.ObjectStreamClass;

/**
 *
 * @author noemi
 */
public class SerialVersionUIDGenerator {
     public static void main(String[] args) {
        ObjectStreamClass osc = ObjectStreamClass.lookup(Submission.class);
        System.out.println("serialVersionUID: " + osc.getSerialVersionUID());
    }
}
