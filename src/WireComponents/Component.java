package WireComponents;

/**
 * Created by Konrad on 24.04.2017.
 */
public abstract class Component {
        public boolean wire ;
        public int rotation;
        public int[] loc;
        public int[][] input;
        public int[][] output;
        public int[][] structure;
        public static Board getElement(){
                return new Board(0,0);
        }
}
