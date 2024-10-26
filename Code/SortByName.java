import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * SortByName is a concrete implementation of the SortingStrategy interface
 * that sorts a list of photos based on their name in ascending alphabetical order.
 */
public class SortByName implements SortingStrategy {

    /**
     * Sorts the provided list of photos by name in ascending alphabetical order.
     *
     * @param photos the list of Photo objects to be sorted by name
     * @return a list of Photo objects sorted by their name
     */
    @Override
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, Comparator.comparing(Photo::getName));
        return photos;
    }
}
