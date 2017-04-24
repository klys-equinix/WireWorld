package gamelogic;

/**
 * Created by Konrad on 24.04.2017.
 */
public class ORgate extends Component {
    public ORgate(int rotation,int[] loc){
        this.loc = loc;
        this.rotation=rotation;
        switch (rotation){
            case 0:
                this.structure= new int [][] {{3,0},
                        {0,3},
                        {3,3},{0,3},{3,0}};
                this.input= new int[][] {{0,0},{4,0}};
                this.output= new int[][] {{2,1}};
                break;
            case 1:
                this.structure= new int [][] {{3,0,3,0,3},
                        {0,3,3,3,0}};
                this.input = new int[][] {{0,0},{0,4}};
                this.output = new int[][] {{1,2}};
                break;
            case 2:
                this.structure= new int [][] {{0,3},
                        {3,0},
                        {3,3},{3,0},{0,3}};
                this.input= new int[][] {{0,1},{4,1}};
                this.output= new int[][] {{2,0}};
                break;
            case 3:
                this.structure= new int [][] {{0,3,3,3,0},
                        {3,0,3,0,3}};
                this.input = new int[][] {{1,0},{1,4}};
                this.output = new int[][] {{0,2}};
                break;
        }

    }
}
