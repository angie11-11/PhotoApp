import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * SortByDate is a concrete implementation of the SortingStrategy interface
 * that sorts a list of photos based on their date added in ascending order.
 */
public class SortByDate implements SortingStrategy {

    /**
     * Sorts the provided list of photos by the date they were added in ascending order.
     *
     * @param photos the list of Photo objects to be sorted by date added
     * @return a list of Photo objects sorted by their date added
     */
    @Override
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, Comparator.comparing(Photo::getDateAdded));
        return photos;
    }
}
