import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class PhotoAlbumView {
    private final JFrame frame;
    private final JTextField photoNameField; // Field to input photo name
    private final JTextField filePathField; // Field to input file path
    private final JButton addButton, deleteButton;
    private final JButton sortByNameButton, sortByDateButton, sortBySizeButton;
    private final JButton nextButton, previousButton;
    private final JList<Photo> photoList; // List to display photos
    private final JLabel currentPhotoLabel; // Label to show the current photo name
    private final JLabel photoDisplayLabel; // Label to display the current photo (thumbnail or full image)
    private final DefaultListModel<Photo> photoListModel; // Model for the photo list

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

    // Custom cell renderer to display the photo information
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

    // Method to get the photo name from the input field
    public String getPhotoName() {
        return photoNameField.getText();
    }

    // Method to get the file path from the input field
    public String getFilePath() {
        return filePathField.getText();
    }

    // Method to clear input fields
    public void clearInputFields() {
        photoNameField.setText("");
        filePathField.setText("");
    }

    // Method to add a photo to the list model
    public void addPhotoToList(Photo photo) {
        photoListModel.addElement(photo);
    }

    // Method to remove a photo from the list model
    public void removePhotoFromList(Photo photo) {
        photoListModel.removeElement(photo);
    }

    // Method to set the current photo label and display
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

    // Method to add listener for the add button
    public void addAddPhotoListener(ActionListener listenForAddButton) {
        addButton.addActionListener(listenForAddButton);
    }

    // Method to add listener for the delete button
    public void addDeletePhotoListener(ActionListener listenForDeleteButton) {
        deleteButton.addActionListener(listenForDeleteButton);
    }

    // Method to add listener for the next button
    public void addNextPhotoListener(ActionListener listenForNextButton) {
        nextButton.addActionListener(listenForNextButton);
    }

    // Method to add listener for the previous button
    public void addPreviousPhotoListener(ActionListener listenForPreviousButton) {
        previousButton.addActionListener(listenForPreviousButton);
    }

    // Method to add listener for sort by name button
    public void addSortByNameListener(ActionListener listenForSortByNameButton) {
        sortByNameButton.addActionListener(listenForSortByNameButton);
    }

    // Method to add listener for sort by date button
    public void addSortByDateListener(ActionListener listenForSortByDateButton) {
        sortByDateButton.addActionListener(listenForSortByDateButton);
    }

    // Method to add listener for sort by size button
    public void addSortBySizeListener(ActionListener listenForSortBySizeButton) {
        sortBySizeButton.addActionListener(listenForSortBySizeButton);
    }

    // Method to get the currently selected photo from the list
    public Photo getSelectedPhoto() {
        return photoList.getSelectedValue();
    }

    // Method to update the photo list model
    public void updatePhotoListModel(List<Photo> photos) {
        photoListModel.clear(); // Clear existing photos
        for (Photo photo : photos) {
            photoListModel.addElement(photo); // Add photos back to the model
        }
        photoList.repaint(); // Refresh the display
    }

    // Method to show a message dialog
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
