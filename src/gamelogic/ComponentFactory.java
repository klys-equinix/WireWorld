package gamelogic;

/**
 * Created by Konrad on 24.04.2017.
 */
public class ComponentFactory {
    public Component getComponent(String compType,int[] loc ,int rotation){
            switch (compType){
                case "Diode":
                    return new Diode(rotation,loc);
                case "ORgate":
                    return new ORgate(rotation,loc);
                case "ExORgate":
                    return new ExORgate(rotation,loc);
                case "ClockGen":
                    return new ClockGen(rotation,loc);
            }
            return null;
    };
}
