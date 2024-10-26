import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

/**
 * PhotoAlbumView is the view component of the photo album application, responsible
 * for displaying the user interface, managing input fields, buttons, and photo
 * display areas. It interacts with the controller by providing methods to
 * retrieve input data, set display properties, and add action listeners.
 */
public class PhotoAlbumView {
    private final JFrame frame;
    private final JTextField photoNameField; 
    private final JTextField filePathField; 
    private final JButton addButton, deleteButton;
    private final JButton sortByNameButton, sortByDateButton, sortBySizeButton;
    private final JButton nextButton, previousButton;
    private final JList<Photo> photoList; 
    private final JLabel currentPhotoLabel; // Label to show the current photo name
    private final JLabel photoDisplayLabel; // Label to display the current photo (thumbnail or full image)
    private final DefaultListModel<Photo> photoListModel; // Model for the photo list

    /**
     * Constructs the PhotoAlbumView, initializing the frame and its components.
     */
    public PhotoAlbumView() {
        // Initialize the frame
        frame = new JFrame("Photo Album Manager");
        frame.setLayout(new BorderLayout());

        // Initialize text fields for photo name and file path
        photoNameField = new JTextField(15);
        filePathField = new JTextField(15);

        // Initialize buttons
        addButton = new JButton("Add Photo");
        deleteButton = new JButton("Delete Photo");
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        sortByNameButton = new JButton("Sort By Name");
        sortByDateButton = new JButton("Sort By Date");
        sortBySizeButton = new JButton("Sort By Size");

        // Initialize photo list and model
        photoListModel = new DefaultListModel<>();
        photoList = new JList<>(photoListModel);
        photoList.setCellRenderer(new PhotoCellRenderer()); // Set custom cell renderer
        currentPhotoLabel = new JLabel("Current Photo: None");
        photoDisplayLabel = new JLabel(); // To display the current photo
        photoDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the photo

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Photo Name:"));
        inputPanel.add(photoNameField);
        inputPanel.add(new JLabel("File Path:"));
        inputPanel.add(filePathField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        // Panel for displaying the current photo
        JPanel currentPhotoPanel = new JPanel(new BorderLayout());
        currentPhotoPanel.add(currentPhotoLabel, BorderLayout.NORTH);
        currentPhotoPanel.add(photoDisplayLabel, BorderLayout.CENTER);

        // Main panel configuration
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(photoList), BorderLayout.WEST); // Photo list on the left
        mainPanel.add(currentPhotoPanel, BorderLayout.CENTER); // Current photo on the right

        // Ensure the current photo takes up 80% of the panel
        currentPhotoPanel.setPreferredSize(new Dimension(0, 0));
        currentPhotoPanel.setMinimumSize(new Dimension(0, 0));
        currentPhotoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Panel for sort and navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByDateButton);
        buttonPanel.add(sortBySizeButton);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        // Adding panels to the frame
        frame.add(inputPanel, BorderLayout.NORTH); // Input panel at the top
        frame.add(mainPanel, BorderLayout.CENTER); // Main panel in the center
        frame.add(buttonPanel, BorderLayout.SOUTH); // Button panel at the bottom

        // Frame settings
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Custom cell renderer for displaying photo information in the JList.
     */
    private class PhotoCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Photo photo = (Photo) value;

            // Create a panel to hold the title and thumbnail
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout

            // Create a label for the photo title
            String text = photo.getName() + " - " + photo.getDateAdded() + " (" + photo.getFileSize() + " bytes)";
            JLabel titleLabel = new JLabel(text);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title label

            // Load and set the thumbnail image
            ImageIcon icon = new ImageIcon(photo.getFilePath());
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Resize to thumbnail
            JLabel thumbnailLabel = new JLabel(new ImageIcon(img));
            thumbnailLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the thumbnail

            // Add title and thumbnail to the panel
            panel.add(titleLabel);
            panel.add(thumbnailLabel);

            // Set the panel properties based on selection
            if (isSelected) {
                panel.setBackground(list.getSelectionBackground());
                titleLabel.setForeground(list.getSelectionForeground());
            } else {
                panel.setBackground(list.getBackground());
                titleLabel.setForeground(list.getForeground());
            }

            return panel; // Return the panel containing title and thumbnail
        }
    }

    /**
     * Retrieves the photo name entered by the user.
     *
     * @return the photo name as a String
     */
    public String getPhotoName() {
        return photoNameField.getText();
    }


    /**
     * Retrieves the file path entered by the user.
     *
     * @return the file path as a String
     */
    public String getFilePath() {
        return filePathField.getText();
    }

    /**
     * Clears the input fields for photo name and file path.
     */
    public void clearInputFields() {
        photoNameField.setText("");
        filePathField.setText("");
    }

    /**
     * Adds a photo to the list model, updating the JList.
     *
     * @param photo the Photo object to add to the list model
     */
    public void addPhotoToList(Photo photo) {
        photoListModel.addElement(photo);
    }

    /**
     * Removes a photo from the list model.
     *
     * @param photo the Photo object to remove from the list model
     */
    public void removePhotoFromList(Photo photo) {
        photoListModel.removeElement(photo);
    }

    /**
     * Sets the current photo in the display area, showing its thumbnail.
     *
     * @param photo the Photo object to display
     */
    public void setCurrentPhoto(Photo photo) {
        if (photo != null) {
            currentPhotoLabel.setText("Current Photo: " + photo.getName());
            // Load the image
            ImageIcon icon = new ImageIcon(photo.getFilePath());
            Image img = icon.getImage(); // transform it
            Image newimg = img.getScaledInstance(700, 700, Image.SCALE_SMOOTH); // scale it smoothly
            photoDisplayLabel.setIcon(new ImageIcon(newimg)); // transform it back
        } else {
            currentPhotoLabel.setText("Current Photo: None");
            photoDisplayLabel.setIcon(null);
        }
    }

    /**
     * Adds an ActionListener for the add photo button.
     *
     * @param listenForAddButton the ActionListener to add
     */
    public void addAddPhotoListener(ActionListener listenForAddButton) {
        addButton.addActionListener(listenForAddButton);
    }


    /**
     * Adds an ActionListener for the delete photo button.
     *
     * @param listenForDeleteButton the ActionListener to add
     */
    public void addDeletePhotoListener(ActionListener listenForDeleteButton) {
        deleteButton.addActionListener(listenForDeleteButton);
    }

    /**
     * Adds an ActionListener for the "Next" button.
     *
     * @param listenForNextButton the ActionListener to add for the "Next" button
     */
    public void addNextPhotoListener(ActionListener listenForNextButton) {
        nextButton.addActionListener(listenForNextButton);
    }

    /**
     * Adds an ActionListener for the "Previous" button.
     *
     * @param listenForPreviousButton the ActionListener to add for the "Previous" button
     */
    public void addPreviousPhotoListener(ActionListener listenForPreviousButton) {
        previousButton.addActionListener(listenForPreviousButton);
    }

    /**
     * Adds an ActionListener for the "Sort By Name" button.
     *
     * @param listenForSortByNameButton the ActionListener to add for the "Sort By Name" button
     */
    public void addSortByNameListener(ActionListener listenForSortByNameButton) {
        sortByNameButton.addActionListener(listenForSortByNameButton);
    }

    /**
     * Adds an ActionListener for the "Sort By Date" button.
     *
     * @param listenForSortByDateButton the ActionListener to add for the "Sort By Date" button
     */
    public void addSortByDateListener(ActionListener listenForSortByDateButton) {
        sortByDateButton.addActionListener(listenForSortByDateButton);
    }

    /**
     * Adds an ActionListener for the "Sort By Size" button.
     *
     * @param listenForSortBySizeButton the ActionListener to add for the "Sort By Size" button
     */
    public void addSortBySizeListener(ActionListener listenForSortBySizeButton) {
        sortBySizeButton.addActionListener(listenForSortBySizeButton);
    }

    /**
     * Retrieves the currently selected photo from the photo list.
     *
     * @return the selected Photo object from the photo list, or null if no photo is selected
     */
    public Photo getSelectedPhoto() {
        return photoList.getSelectedValue();
    }


    /**
     * Updates the photo list model with a new list of photos and repaints the JList.
     *
     * @param photos the List of Photo objects to display
     */
    public void updatePhotoListModel(List<Photo> photos) {
        photoListModel.clear(); // Clear existing photos
        for (Photo photo : photos) {
            photoListModel.addElement(photo); // Add photos back to the model
        }
        photoList.repaint(); // Refresh the display
    }

    /**
     * Displays a message dialog with the specified message and title.
     *
     * @param message the message to display
     * @param title the title of the dialog
     */
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
