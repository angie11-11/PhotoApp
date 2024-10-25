import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByName implements SortingStrategy {
    @Override
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, Comparator.comparing(Photo::getName));
        return photos;
    }
}
