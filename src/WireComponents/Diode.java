package WireComponents;

/**
 * <h1>Diode</h1>
 * One of components, Diode
 */
public class Diode extends Component {
    public Diode(int rotation,int[] loc,boolean connected){
            this.wire=connected;
            this.loc = loc;
            this.rotation=rotation;
            switch (rotation){
                case 0:
                    this.structure= new int [][] {{3,3},
                            {3,0},
                            {3,3}};
                    this.input= new int[][] {{1,0}};
                    this.output= new int[][] {{1,1}};
                    break;
                case 1:
                    this.structure= new int [][] {{3,0,3},
                            {3,3,3}};
                    this.input = new int[][] {{0,1}};
                    this.output = new int[][] {{1,1}};
                    break;
                case 2:
                    this.structure= new int [][] {{3,3},
                            {0,3},
                            {3,3}};
                    this.input= new int[][] {{1,0}};
                    this.output= new int[][] {{1,1}};
                    break;
                case 3:
                    this.structure= new int [][] {{3,3,3},
                            {3,0,3}};
                    this.input = new int[][] {{0,1}};
                    this.output = new int[][] {{1,1}};
                    break;
            }

    }


}
