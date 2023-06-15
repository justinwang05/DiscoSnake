import java.util.Random;
import java.awt.Color;

public class Fruit {
    private int colorInt; //0 = red, 1 = yellow, 2 = blue, 3 = green
    private Color color;
    private int x;
    private int y;
    Random random;

    Fruit(int c){
        colorInt = c;

        switch(c){
            case 0: //RED
                color = new Color(255, 0, 127);
                break;
            case 1: //YELLOW
                color = new Color(255, 255, 102);
                break;
            case 2: //BLUE
                color = new Color(0, 204, 204);
                break;
            case 3: //GREEN
                color = new Color(153, 255, 51);
                break;
        }
    }

    public void newFruitLocation(){
        random = new Random();
        x = random.nextInt((int) GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE) * GamePanel.UNIT_SIZE;
		y = 3 * GamePanel.UNIT_SIZE + ((random.nextInt((int) (GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE - 3)) * GamePanel.UNIT_SIZE));
        //System.out.println("New Fruit at " + x / GamePanel.UNIT_SIZE + ", " + y / GamePanel.UNIT_SIZE);
    }

    public boolean sameLocation(int x, int y){
        return (this.x == x) && (this.y == y);
    }

    public boolean sameColor(String c){
        return c.equals(this.getColorName());
    }


    public String getColorName() {
        switch (colorInt) {
            case 0: // RED
                return "RED";
            case 1: // YELLOW
                return "YELLOW";
            case 2: // BLUE
                return "BLUE";
            case 3: // GREEN
                return "GREEN";
        }

        return null;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Color getColor(){
        return color;
    }
}
