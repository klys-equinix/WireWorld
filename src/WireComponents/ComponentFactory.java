package WireComponents;

/**
 * Created by Konrad on 24.04.2017.
 */
public class ComponentFactory {
    public Component getComponent(String compType,int[] loc ,int rotation,boolean connected){
            switch (compType){
                case "Diode":
                    return new Diode(rotation,loc,connected);
                case "ORgate":
                    return new ORgate(rotation,loc,connected);
                case "ExORgate":
                    return new ExORgate(rotation,loc,connected);
                case "ClockGen":
                    return new ClockGen(rotation,loc,connected);
            }
            return null;
    };
}
