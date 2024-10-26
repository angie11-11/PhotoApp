import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * SortBySize is a concrete implementation of the SortingStrategy interface
 * that sorts a list of photos based on their file size, in ascending order.
 */
public class SortBySize implements SortingStrategy {

    /**
     * Sorts the provided list of photos by file size in ascending order.
     *
     * @param photos the list of Photo objects to be sorted by file size
     * @return a list of Photo objects sorted by their file size
     */
    @Override
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, Comparator.comparingLong(Photo::getFileSize));
        return photos;
    }
}
