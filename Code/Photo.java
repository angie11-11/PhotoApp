import java.awt.Image;
import java.util.Date;
import javax.swing.ImageIcon;

public class Photo {
    private final String name;      // The name of the photo
    private final String filePath;  // The path to the photo file
    private final Date dateAdded;   // The date the photo was added
    private final long fileSize;     // The size of the photo file in bytes

    // Constructor
    public Photo(String name, String filePath, Date dateAdded, long fileSize) {
        this.name = name;
        this.filePath = filePath;
        this.dateAdded = dateAdded;
        this.fileSize = fileSize;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public long getFileSize() {
        return fileSize;
    }

    // Method to get a thumbnail image
    public ImageIcon getThumbnail() {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize for thumbnail
        return new ImageIcon(img);
    }

    @Override
    public String toString() {
        return name + " - " + dateAdded + " (" + fileSize + " bytes)"; // Display name and size
    }
}
