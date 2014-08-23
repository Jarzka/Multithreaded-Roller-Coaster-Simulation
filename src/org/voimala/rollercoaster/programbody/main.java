package org.voimala.rollercoaster.programbody;


public class main {
    public static void main(String[] args) {
        try {
            RollerCoasterApp.getInstance().run(args);
        } catch (Exception e) {
            System.out.println("Serious error occurred and the execution can not continue.");
            System.out.println(e.getMessage());
        }
    }
}
