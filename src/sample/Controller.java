package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField textField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn<Point, String> columnX;

    @FXML
    private TableColumn<Point, String> columnY;

    @FXML
    private LineChart<Double, Double> lineChart;

    private ObservableList<Point> points = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        update();
        table.setEditable(true);
        columnX.setCellFactory(TextFieldTableCell.forTableColumn());
        columnX.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(Double.toString(cellData.getValue().getX())));
        columnY.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(Double.toString(cellData.getValue().getY())));
        btnAdd.setOnAction(event -> {
            if (textField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Внимание, введите число");
                alert.show();
            } else {
                try {
                    double value = Double.parseDouble(textField.getText());
                    Point point = new Point(value);
                    Model.addPoint(point);
                    //textField.clear();
                    update();
                    recount();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Внимание, введите число");
                    alert.show();
                }
            }
        });
        btnDelete.setOnAction(event -> {
            deleteCell();
        });
        columnX.setOnEditCommit((TableColumn.CellEditEvent<Point, String> event) -> {
            try {
                TablePosition<Point, String> pos = event.getTablePosition();
                String newValue = event.getNewValue();
                int row = pos.getRow();
                Point point = event.getTableView().getItems().get(row);
                Model.setPoint(point.getX(), Double.parseDouble(newValue));
                point.setX(Double.parseDouble(newValue));
                table.refresh();
                recount();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Внимание, введите число");
                alert.show();
            }
        });
    }


    public void deleteCell() {
        Point point = (Point) table.getSelectionModel().getSelectedItem();
        Model.removePoint(point);
        table.getItems().removeAll(point);
        recount();
    }

    public void update() {
        table.getItems().removeAll();
        points.clear();
        for (Point p : Model.getArrayList()) {
            points.add(p);
        }
        table.setItems(points);
    }


    public void recount() {
        lineChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < points.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(Double.toString(points.get(i).getX()), points.get(i).getY()));
        }
        lineChart.getData().addAll(series);
    }
}
