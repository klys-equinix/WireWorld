package WireComponents;

/**
 * <h1>ComponentBoardFactory</h1>
 * Abstract Factory of Component elements, handling production of components
 */
public class ComponentFactory {//Abstract Factory of Component class elements
    /**
     * <h1>getBoardComponent</h1>
     * Function producing components
     * @param compType
     * @return a component of specified type, wiring, and rotation
     */
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
