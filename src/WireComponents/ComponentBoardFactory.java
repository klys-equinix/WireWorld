package WireComponents;

/**
 * <h1>ComponentBoardFactory</h1>
 * Factory of Component elements, handling production of components
 */
public class ComponentBoardFactory extends ComponentFactory {

    /**
     * <h1>getBoardComponent</h1>
     * Function producing components
     * @param compType
     * @return a component of specified type, wiring, and rotation
     */
    public Board getBoardComponent(String compType){
        Component newComponent = null;
        int[] loc={0,0};
        int rotation = 0;
        boolean connected = false;
        switch (compType){
            case "Diode":
                newComponent = new Diode(rotation,loc,connected);
                break;
            case "ORgate":
                newComponent = new ORgate(rotation,loc,connected);
                break;
            case "ExORgate":
                newComponent = new ExORgate(rotation,loc,connected);
                break;
            case "ClockGen":
                newComponent = new ClockGen(rotation,loc,connected);
                break;
        }
        if(newComponent!=null){
            return new Board(newComponent.structure,newComponent.structure.length,newComponent.structure[0].length);
        }else{
            return null;
        }
    }
}
