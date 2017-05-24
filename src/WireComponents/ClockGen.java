package WireComponents;

/**
 * <h1>ClockGen</h1>
 * One components, pulsar
 */
public class ClockGen extends Component {
    public ClockGen(int rotation, int[] loc, boolean connected) {
        this.wire = connected;
        this.loc = loc;
        this.rotation = rotation;
        this.input = new int[][]{};
        switch (rotation) {
            case 0:
                this.structure = new int[][]{{0, 2, 1, 3, 3, 0},
                        {3, 0, 0, 0, 0, 3},
                        {0, 3, 3, 3, 3, 0}};
                this.output = new int[][]{{1, 5}};
                break;
            case 1:
                this.structure = new int[][]{{0, 3, 0}, {3, 0, 2}, {3, 0, 1}, {3, 0, 3}, {3, 0, 3}, {0, 3, 0}
                };
                this.output = new int[][]{{5, 1}};
                break;
            case 2:
                this.structure = new int[][]{{0, 2, 1, 3, 3, 0},
                        {3, 0, 0, 0, 0, 3},
                        {0, 3, 3, 3, 3, 0}};
                this.output = new int[][]{{1, 0}};
                break;
            case 3:
                this.structure = new int[][]{{0, 3, 0}, {3, 0, 2}, {3, 0, 1}, {3, 0, 3}, {3, 0, 3}, {0, 3, 0}
                };
                this.output = new int[][]{{0, 1}};
                break;
        }

    }
}
