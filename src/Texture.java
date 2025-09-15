public class Texture
{
    //current frame
    //all the different textures
    public static Texture black = new Texture(64, "black");
    public static Texture bricks = new Texture(64, "bricks");
    public static Texture coolpattern = new Texture(64, "coolpattern");
    public static Texture grass = new Texture(64, "grass");
    public int SIZE;
    public int[] pixels;

    public Texture(int size, String texName)
    {
        //size of texture (64)
        SIZE = size;
        //all pixels in texture
        pixels = new int[SIZE * SIZE];
        //what design there is for each texture
        switch (texName) {
            case "black" -> {
                for (int i = 0; i < SIZE * SIZE; i++) {
                    pixels[i] = rgbNum(0, 0, 0);
                }
            }
            case "bricks" -> {
                for (int i = 0; i < SIZE * SIZE; i++) {
                    int backgroundColor = rgbNum(128, 128, 128);
                    int brickColor = rgbNum(128, 32, 0);
                    if (i < 3 * SIZE || i >= (SIZE - 3) * SIZE)
                        pixels[i] = backgroundColor;
                    else if (i / SIZE % 10 == 1 || i / SIZE % 10 == 2)
                        pixels[i] = backgroundColor;
                    else if (i / SIZE % 20 >= 3 && i / SIZE % 20 <= 10 && (((i % SIZE % 20 == 1 || i % SIZE % 20 == 2) && !(i % SIZE < 3 || i % SIZE >= SIZE - 3)) || (i % SIZE < 1 || i % SIZE >= SIZE - 1)))
                        pixels[i] = backgroundColor;
                    else if ((i / SIZE % 20 >= 13 || i / SIZE % 20 == 0) && (i % SIZE % 20 == 11 || i % SIZE % 20 == 12))
                        pixels[i] = backgroundColor;
                    else if (i % SIZE % 10 < 5)
                        pixels[i] = brickColor;
                    else
                        pixels[i] = brickColor;
                }
            }
            case "coolpattern" -> {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        int color;
                        if (i < SIZE / 2 && j < SIZE / 2)
                            color = i * j;
                        else if (i < SIZE / 2)
                            color = i * (SIZE - j);
                        else if (j < SIZE / 2)
                            color = (SIZE - i) * j;
                        else
                            color = (SIZE - i) * (SIZE - j);
                        color *= 276;
                        pixels[i * SIZE + j] = color;
                    }
                }
            }
            case "grass" -> {
                int color = rgbNum(0, 128, 0);
                for (int i = 0; i < SIZE * SIZE; i++) {
                    double random = Math.random();
                    int newColor = rgbNum((int) (random * getR(color)), (int) (random * getG(color)), (int) (random * getB(color)));
                    pixels[i] = newColor;
                }
            }
        }
    }

    private int rgbNum(int r, int g, int b)
    {
        //gets rgb decimal value from rgb input
        return r * 65536 + g * 256 + b;
    }

    private int getR(int color)
    {
        //gets r value from rgb decimal input
        return color/65536;
    }

    private int getG(int color)
    {
        //gets g value from rgb decimal input
        return color % 65536/256;
    }

    private int getB(int color)
    {
        //gets b value from rgb decimal input
        return color % 65536 % 256;
    }
}