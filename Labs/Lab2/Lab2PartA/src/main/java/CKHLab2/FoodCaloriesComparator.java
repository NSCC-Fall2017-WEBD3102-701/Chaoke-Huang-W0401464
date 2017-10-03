package CKHLab2;

import java.util.Comparator;

public class FoodCaloriesComparator implements Comparator<Food> {

    @Override
    public int compare(Food o1, Food o2) {
        return o1.getTotalCalories() - o2.getTotalCalories();
    }


}
