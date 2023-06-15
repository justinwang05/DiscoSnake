import java.awt.*;
import java.util.Random;

public class Snake {
    private int bodyParts;
	private int fruitsEaten;

    private String colorName;
	private Color headColor;
    private Color bodyColor1;
    private Color bodyColor2;

    Random random;
    
    Snake(int bp, String c){
        bodyParts = bp;
        colorName = c;

        assignColors(colorName);
    }

    public void addFruitsEaten(int n){
        fruitsEaten += n;
    }

    public void addBodyParts(int n){
        bodyParts += n;
    }

    public void assignColors(String c){
        switch(c){
            case "RED":
                headColor = new Color(171, 51, 51);
                bodyColor1 = new Color(236, 12, 12);
                bodyColor2 = new Color(230, 90, 90);
                break;
            case "YELLOW":
                headColor = new Color(255, 191, 13);
                bodyColor1 = new Color(255, 250, 67);
                bodyColor2 = new Color(255, 252, 151);
                break;
            case "BLUE":
                headColor = new Color(0, 0, 255);
                bodyColor1 = new Color(74, 129, 255);
                bodyColor2 = new Color(107, 151, 255);
                break;
            case "GREEN":
                headColor = new Color(0, 153, 0);
                bodyColor1 = new Color(92, 223, 101);
                bodyColor2 = new Color(129, 250, 137);
                break;

        }
    }

    public int getColorInt(){
        switch(colorName){
            case "RED": return 0;
            case "YELLOW": return 1;
            case "BLUE": return 2;
            case "GREEN": return 3;
        }
        return -1;
    }
    public void setRandomColor(){
        random = new Random();
        int n = random.nextInt(4);

        switch(n){
            case 0: colorName = "RED"; break;
            case 1: colorName = "YELLOW"; break;
            case 2: colorName = "BLUE"; break;
            case 3: colorName = "GREEN"; break;
        }

        assignColors(colorName);
    }

    public int getFruitsEaten(){
        return fruitsEaten;
    }

    public int getBodyParts(){
        return bodyParts;
    }

    public String getColorName(){
        return colorName;
    }

    public Color getHeadColor(){
        return headColor;
    }

    public Color getBodyColor1(){
        return bodyColor1;
    }

    public Color getBodyColor2(){
        return bodyColor2;
    }


}
