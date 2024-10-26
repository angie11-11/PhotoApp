import java.awt.Image;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 * The Photo class represents an image file with metadata including its name,
 * file path, date added, and file size. It provides access to these properties
 * and a method to generate a thumbnail of the image for display purposes.
 */
public class Photo {
    private final String name;      
    private final String filePath;  
    private final Date dateAdded;   
    private final long fileSize;    

    /**
     * Constructs a Photo instance with the specified name, file path, date added, and file size.
     *
     * @param name      the name of the photo
     * @param filePath  the path to the photo file
     * @param dateAdded the date the photo was added to the album
     * @param fileSize  the size of the photo file in bytes
     */
    public Photo(String name, String filePath, Date dateAdded, long fileSize) {
        this.name = name;
        this.filePath = filePath;
        this.dateAdded = dateAdded;
        this.fileSize = fileSize;
    }

    /**
     * Retrieves the name of the photo.
     *
     * @return the name of the photo
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the file path of the photo.
     *
     * @return the file path of the photo
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Retrieves the date the photo was added.
     *
     * @return the date the photo was added to the album
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Retrieves the file size of the photo in bytes.
     *
     * @return the size of the photo file in bytes
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Generates a thumbnail image for the photo, scaled to 50x50 pixels.
     *
     * @return an ImageIcon of the photo thumbnail
     */
    public ImageIcon getThumbnail() {
        ImageIcon icon = new ImageIcon(filePath);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 
        return new ImageIcon(img);
    }

    /**
     * Returns a string representation of the photo, including its name,
     * date added, and file size.
     *
     * @return a string representation of the photo
     */
    @Override
    public String toString() {
        return name + " - " + dateAdded + " (" + fileSize + " bytes)"; 
    }
}
