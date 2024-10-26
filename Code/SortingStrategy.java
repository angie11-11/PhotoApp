import java.util.List;

/**
 * SortingStrategy is an interface for sorting strategies that can be applied 
 * to a collection of photos. It provides a method to sort a list of Photo objects
 * based on a specific criterion, such as name, date, or size.
 */
public interface SortingStrategy {

    /**
     * Sorts the provided list of photos according to the implemented sorting strategy.
     *
     * @param photos the list of Photo objects to be sorted
     * @return a sorted list of Photo objects
     */
    List<Photo> sort(List<Photo> photos);
}
