package CKHLab2;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //Reader reader = new Reader();
        //reader.readFile();
        NutritionFileReader nutriReader = new NutritionFileReader();
        nutriReader.processFood();
    }
}
