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
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<Double, Double> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonRemove;

    @FXML
    private TextField textField;

    @FXML
    private TableView table;

    @FXML
    private TableColumn<Point, String> columX;

    @FXML
    private TableColumn<Point, String> columY;

    private ObservableList<Point> pointList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        update();
        table.setEditable(true);
        columX.setCellFactory(TextFieldTableCell.forTableColumn());
        columX.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(Double.toString(cellData.getValue().getX())));
        columY.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(Double.toString(cellData.getValue().getY())));

        buttonRemove.setOnAction(event -> {
            removeCell();
        });
        buttonAdd.setOnAction(event -> {
            if (textField.getText().equals("")) {
                error("добавлении");
            } else {
                try {
                    double value = Double.parseDouble(textField.getText());
                    Point point = new Point(value);
                    Model.addPoint(point);

                    //textField.clear();
                    update();
                    refreshGraph();
                } catch (Exception e) {
                    error("добавлении");
                }
            }
        });
        columX.setOnEditCommit((TableColumn.CellEditEvent<Point, String> event) -> {
            try {
                TablePosition<Point, String> pos = event.getTablePosition();

                String newValue = event.getNewValue();

                int row = pos.getRow();
              Point point = event.getTableView().getItems().get(row);
                Model.setPoint(point.getX(), Double.parseDouble(newValue));
                point.setX(Double.parseDouble(newValue));
                table.refresh();
                refreshGraph();

            } catch (Exception e){
                error("изменении");
            }
        });
    }

    public void error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка при " + msg + " точки");
        alert.setContentText("Это не число");
        alert.show();
    }

    public void removeCell() {
        Point tt = (Point) table.getSelectionModel().getSelectedItem();
        Model.removePoint(tt);
        table.getItems().removeAll(tt);
        refreshGraph();
    }

    public void update() {

        table.getItems().removeAll();
        pointList.clear();

        getDataFromModel();

        table.setItems(pointList);

    }

    public void getDataFromModel(){
        for (Point p:  Model.getArrayList()) {
            pointList.add(p);
        }
    }

    public void refreshGraph(){
        lineChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < pointList.size(); i++) {
            series.getData().add(new XYChart.Data<String,Double>(Double.toString(pointList.get(i).getX()), pointList.get(i).getY()));
        }
        lineChart.getData().addAll(series);
    }
}
