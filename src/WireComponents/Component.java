package WireComponents;

/**
 * Abstract class of components, elements simulating parts of circuits
 */
public abstract class Component {
    public boolean wire;
    public int rotation;
    public int[] loc;
    public int[][] input;
    public int[][] output;
    public int[][] structure;

    public static Board getElement() {
        return new Board(0, 0);
    }
}
