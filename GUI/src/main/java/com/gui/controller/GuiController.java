package com.gui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gui.model.EnergyData;
import com.gui.model.UsageData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GuiController {

    @FXML
    private Label labelCommunityDepleted;

    @FXML
    private Label labelGridPortion;

    @FXML
    private Label labelCommunityProduced;

    @FXML
    private Label labelCommunityUsed;

    @FXML
    private Label labelGridUsed;

    @FXML
    private TextField textStart;

    @FXML
    private TextField textEnd;

    @FXML
    public void loadCurrentData() {
        HttpClient client = HttpClient.newHttpClient();

        try {
            URI url = URI.create("http://localhost:8080/energy/current");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper om = new ObjectMapper();
            EnergyData data = om.readValue(response.body(), EnergyData.class);

            labelCommunityDepleted.setText(String.format("%.2f%% used", data.getCommunityDepleted()));
            labelGridPortion.setText(String.format("%.2f%%", data.getGridPortion()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadHistoricalData() {
        HttpClient client = HttpClient.newHttpClient();

        try {
            String start = textStart.getText();
            String end = textEnd.getText();

            URI url = URI.create("http://localhost:8080/energy/historical?start=" +
                    URLEncoder.encode(start, StandardCharsets.UTF_8) +
                    "&end=" +
                    URLEncoder.encode(end, StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper om = new ObjectMapper();
            List<UsageData> dataList = om.readValue(response.body(), new TypeReference<>() {});


            double producedSum = 0;
            double usedSum = 0;
            double gridSum = 0;

            for (UsageData d : dataList) {
                producedSum += d.getCommunityProduced();
                usedSum += d.getCommunityUsed();
                gridSum += d.getGridUsed();
            }

            labelCommunityProduced.setText(String.format("%.3f kWh", producedSum));
            labelCommunityUsed.setText(String.format("%.3f kWh", usedSum));
            labelGridUsed.setText(String.format("%.3f kWh", gridSum));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
