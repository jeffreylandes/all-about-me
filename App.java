import javax.swing.*;

import user.WeatherFetch;

import java.awt.*;
import java.awt.event.*;

public class App {

    private static final int year = 2021;
    private static final int month = 1;
    private static final int day = 20;

    private static final WeatherFetch weather = new WeatherFetch(52.5f, 13.5f);

    static protected class FetchWeatherAction implements ActionListener {
        JTextField latitudeTf;
        JTextField longitudeTf;
        public FetchWeatherAction(JTextField latitudeTf, JTextField longitudeTf) {
            this.latitudeTf = latitudeTf;
            this.longitudeTf = longitudeTf;
        }
        public void actionPerformed(ActionEvent event) {
            weather.setLatitude(Float.parseFloat(latitudeTf.getText()));
            weather.setLongitude(Float.parseFloat(longitudeTf.getText()));
            weather.fetchWeatherFromDay(year, month, day);
        }
    }

    public static void main(String... args) {
        JFrame frame = new JFrame("All about me.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        JPanel latitudePanel = new JPanel();
        JLabel latitudeLabel = new JLabel("My Latitude");
        JTextField latitudeTextField = new JTextField(10);
        latitudePanel.add(latitudeLabel);
        latitudePanel.add(latitudeTextField);

        JPanel longitudePanel = new JPanel();
        JLabel longitudeLabel = new JLabel("My Longitude");
        JTextField longitudeTextField = new JTextField(10);
        longitudePanel.add(longitudeLabel);
        longitudePanel.add(longitudeTextField);

        JButton button = new JButton("What is the weather today?");
        button.addActionListener(new App.FetchWeatherAction(latitudeTextField, longitudeTextField));
        frame.getContentPane().add(BorderLayout.NORTH, latitudePanel);
        frame.getContentPane().add(BorderLayout.CENTER, longitudePanel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.setVisible(true);
    }
}
