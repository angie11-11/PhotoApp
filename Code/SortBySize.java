import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortBySize implements SortingStrategy {
    @Override
    public List<Photo> sort(List<Photo> photos) {
        Collections.sort(photos, Comparator.comparingLong(Photo::getFileSize));
        return photos;
    }
}
