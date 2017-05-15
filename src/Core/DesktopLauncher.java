package Core;

import WireComponents.Board;
import WireComponents.ComponentBoardFactory;

import javax.swing.*;

/**
 * Created by Szymon on 12.05.2017.
 */
public class DesktopLauncher {
    public static void main(String args[]){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        ComponentBoardFactory fact = new ComponentBoardFactory();//Przykład użycia, tworzy Boarda z wydrukowaną diodą, zerową rotacją i bez podłączenia
        Board blabla = fact.getBoardComponent("Diode");




        WireWorld ww = new WireWorld();
        ww.runControl();
    }
}
