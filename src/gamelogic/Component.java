package gamelogic;

/**
 * Created by Konrad on 24.04.2017.
 */
abstract class Component {
        boolean wire ;
        int rotation;
        int[] loc;
        int[][] input;
        int[][] output;
        int[][] structure;
        public static Board getElement(){
                return new Board(0,0);
        }
}
