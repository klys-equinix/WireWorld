package WireComponents;

/**
 * Abstract Factory for Component class elements
 */
public class ComponentFactory {//Abstract Factory of Component class elements
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
