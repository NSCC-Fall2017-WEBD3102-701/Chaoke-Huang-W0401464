package CKHLab2;

import java.util.Comparator;

public class FoodSodiumComparator implements Comparator<Food> {

    @Override
    public int compare(Food o1, Food o2) {
        return o1.getSodium() - o2.getSodium();
    }

}
