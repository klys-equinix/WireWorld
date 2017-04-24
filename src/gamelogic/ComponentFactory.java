package gamelogic;

/**
 * Created by Konrad on 24.04.2017.
 */
public class ComponentFactory {
    public Component getComponent(String compType,int[] loc ,int rotation){
            switch (compType){
                case "Diode":
                    return new Diode(rotation,loc);
            }
            return null;
    };
}
