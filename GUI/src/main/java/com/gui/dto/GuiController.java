package com.gui.dto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GuiController {

    @FXML
    private Label community_pool;

    @FXML
    private Label grid_portion;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;

    @FXML
    private Label community_produced;

    @FXML
    private Label community_used;

    @FXML
    private Label grid_used;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Methode für den Refresh-Button, wie im FXML definiert
    @FXML
    private void onRefreshButtonClick() {
        loadCurrentData();
    }

    // Methode für den Show Data-Button, wie im FXML definiert
    @FXML
    private void onShowDataButtonClick() {
        loadHistoricalData();
    }

    private void loadCurrentData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/current"))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    try {
                        JsonNode json = objectMapper.readTree(responseBody);
                        String pool = json.get("communityDepleted").asText();
                        String grid = json.get("gridPortion").asText();

                        Platform.runLater(() -> {
                            community_pool.setText("Community Pool: " + pool + "%");
                            grid_portion.setText("Grid Portion: " + grid + "%");
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }

    private void loadHistoricalData() {
        LocalDate start = start_date.getValue();
        LocalDate end = end_date.getValue();

        if (start == null || end == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte Start- und Enddatum auswählen.");
                alert.showAndWait();
            });
            return;
        }

        String url = String.format("http://localhost:8080/energy/historical?start=%s&end=%s",
                start.toString(), end.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    try {
                        JsonNode jsonArray = objectMapper.readTree(responseBody);

                        if (jsonArray.isArray() && jsonArray.size() > 0) {
                            JsonNode firstEntry = jsonArray.get(0);

                            String produced = firstEntry.get("community_produced").asText();
                            String used = firstEntry.get("community_used").asText();
                            String gridUsed = firstEntry.get("grid_used").asText();

                            Platform.runLater(() -> {
                                community_produced.setText("Community Produced: " + produced);
                                community_used.setText("Community Used: " + used);
                                grid_used.setText("Grid Used: " + gridUsed);
                            });
                        } else {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Keine Daten für den Zeitraum gefunden.");
                                alert.showAndWait();
                            });
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }
}
