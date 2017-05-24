package WireComponents;

/**
 * <h1>ExORgate</h1>
 * One of components, ExORGate
 */
public class ExORgate extends Component {

    public ExORgate(int rotation, int[] loc, boolean connected) {
        this.wire = connected;
        this.loc = loc;
        this.rotation = rotation;
        switch (rotation) {
            case 0:
                this.structure = new int[][]{{3, 0, 0, 0},
                        {0, 3, 0, 0},
                        {3, 3, 3, 3}, {3, 0, 0, 3}, {3, 3, 3, 3}, {0, 3, 0, 0}, {3, 0, 0, 0}};
                this.input = new int[][]{{0, 0}, {6, 0}};
                this.output = new int[][]{{3, 3}};
                break;
            case 1:
                this.structure = new int[][]{{3, 0, 3, 3, 3, 0, 3},
                        {0, 3, 3, 0, 3, 3, 0}, {0, 0, 3, 0, 3, 0, 0}, {0, 0, 3, 3, 3, 0, 0}};
                this.input = new int[][]{{0, 0}, {0, 6}};
                this.output = new int[][]{{3, 3}};
                break;
            case 2:
                this.structure = new int[][]{{0, 0, 0, 3},
                        {0, 0, 3, 0},
                        {3, 3, 3, 3}, {3, 0, 0, 3}, {3, 3, 3, 3}, {0, 0, 3, 0}, {0, 0, 0, 3}};
                this.input = new int[][]{{0, 3}, {6, 3}};
                this.output = new int[][]{{3, 0}};
                break;
            case 3:
                this.structure = new int[][]{{0, 0, 3, 3, 3, 0, 0},
                        {0, 0, 3, 0, 3, 0, 0}, {0, 3, 3, 0, 3, 3, 0}, {3, 0, 3, 3, 3, 0, 3}};
                this.input = new int[][]{{3, 0}, {3, 6}};
                this.output = new int[][]{{0, 3}};
                break;
        }

    }
}
