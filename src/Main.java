import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Main {
    private static final int COLS = 20;
    private static final int ROWS = 10;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Word Search Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 850);
        frame.setLayout(new BorderLayout());

        // ================= TIMER SECTION START =================
        String[] options = {"3 Minutes", "5 Minutes", "10 Minutes"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose your time limit:",
                "Time Limit",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        int minutes;
        if (choice == 0) {
            minutes = 3;
        } else if (choice == 1) {
            minutes = 5;
        } else {
            minutes = 10;
        }

        final int[] timeInSeconds = {minutes * 60};
        // ================= TIMER SECTION END =================

        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel lettersPanel = new JPanel(new GridLayout(ROWS, COLS, 2, 2));
        lettersPanel.setBackground(Color.WHITE);

        JTextField selectedTextField = new JTextField();
        selectedTextField.setEditable(false);
        selectedTextField.setFont(new Font("Monospaced", Font.BOLD, 24));
        selectedTextField.setPreferredSize(new Dimension(0, 40));
        selectedTextField.setHorizontalAlignment(JTextField.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(lettersPanel, BorderLayout.CENTER);
        topPanel.add(selectedTextField, BorderLayout.SOUTH);

        String[] words = {
            "JAVA", "CODE", "SWING", "PANEL", "BUTTON",
            "FRAME", "ARRAY", "LOOP", "STRING", "EVENT",
            "THREAD"
        };

        Map<String, JLabel> wordLabelsMap = new HashMap<>();
        Set<String> foundWords = new HashSet<>();

        JPanel wordsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        wordsPanel.setBackground(new Color(230, 230, 250));
        wordsPanel.setPreferredSize(new Dimension(0, 80));

        for (String word : words) {
            JLabel wordLabel = new JLabel(word);
            wordLabel.setFont(new Font("Arial", Font.BOLD, 20));
            wordLabel.setForeground(Color.DARK_GRAY);
            wordLabel.setOpaque(true);
            wordLabel.setBackground(Color.WHITE);
            wordLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            wordsPanel.add(wordLabel);
            wordLabelsMap.put(word, wordLabel);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 22));
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.setBorder(BorderFactory.createEmptyBorder());
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(e -> {
            String submitted = selectedTextField.getText().toUpperCase().trim();

            if (submitted.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please select some letters before submitting.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                selectedTextField.setText("");
                return;
            }

            boolean isValid = false;
            for (String word : words) {
                if (word.equals(submitted)) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                if (foundWords.contains(submitted)) {
                    JOptionPane.showMessageDialog(frame,
                            "You already found the word: " + submitted,
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    foundWords.add(submitted);
                    JLabel foundLabel = wordLabelsMap.get(submitted);
                    if (foundLabel != null) {
                        foundLabel.setBackground(new Color(144, 238, 144));
                    }
                    JOptionPane.showMessageDialog(frame,
                            "Correct! You found: " + submitted,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    if (foundWords.size() == words.length) {
                        ImageIcon originalIcon = new ImageIcon("C:\\Users\\User\\OneDrive\\Documents\\ALEC\\moodeng.jpg");
                        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        ImageIcon winIcon = new ImageIcon(scaledImage);

                        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
                        JLabel imageLabel = new JLabel(winIcon);
                        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        JLabel textLabel = new JLabel("Congratulations! You found all the words and won the game!");
                        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        textLabel.setFont(new Font("Arial", Font.BOLD, 16));

                        messagePanel.add(imageLabel, BorderLayout.CENTER);
                        messagePanel.add(textLabel, BorderLayout.SOUTH);

                        Object[] winOptions = {"Restart Game", "Exit"};
                        int result = JOptionPane.showOptionDialog(frame,
                                messagePanel,
                                "You Win!",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                winOptions,
                                winOptions[0]);

                        if (result == JOptionPane.YES_OPTION) {
                            frame.dispose();
                            main(null);
                        } else {
                            frame.dispose();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Wrong word! Try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            selectedTextField.setText("");
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(wordsPanel, BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        submitPanel.add(submitButton);

        bottomPanel.add(submitPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(0, 130));

        Random random = new Random();
        for (int i = 0; i < ROWS * COLS; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            JButton letterButton = new JButton(String.valueOf(letter));
            letterButton.setFont(new Font("Arial", Font.BOLD, 18));
            letterButton.setFocusable(false);
            letterButton.setBackground(new Color(135, 206, 235));
            letterButton.setForeground(Color.BLACK);
            letterButton.setOpaque(true);

            letterButton.addActionListener(e -> {
                selectedTextField.setText(selectedTextField.getText() + letterButton.getText());
            });

            lettersPanel.add(letterButton);
        }

        container.add(topPanel, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(container, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // ================= COUNTDOWN TIMER START =================
        Timer countdownTimer = new Timer(1000, null);
        countdownTimer.addActionListener(e -> {
            timeInSeconds[0]--;

            int mins = timeInSeconds[0] / 60;
            int secs = timeInSeconds[0] % 60;
            frame.setTitle(String.format("Word Search Game - Time Left: %02d:%02d", mins, secs));

            if (timeInSeconds[0] <= 0) {
                countdownTimer.stop();

                Object[] timeOptions = {"Restart Game", "Exit"};
                int result = JOptionPane.showOptionDialog(frame,
                        "Time's up! You did not complete the game.",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        timeOptions,
                        timeOptions[0]);

                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    main(null);
                } else {
                    frame.dispose();
                }
            }
        });
        countdownTimer.start();
        // ================= COUNTDOWN TIMER END =================
    }
}
