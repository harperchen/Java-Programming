package dClient.ui;


import dClient.json.ObjectToJsonUtil;
import dClient.user.YS_Table;
import dClient.user.MsgType;
import javafx.collections.ObservableList;
import DateTimerPickers.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalaryList {
    private VBox vBox = new VBox();
    private Stage stage = new Stage();
    private HBox mainHBox = new HBox();
    private TableView<YS_Table> doctorTable = new TableView<>();
    private Label mainText = new Label( "医生的收入列表");
    private ObservableList<YS_Table> doctors = null;

    private HBox starthBox = new HBox();
    private HBox endhBox = new HBox();
    private HBox hBox = new HBox();

    private DateTimePicker startDatePicker = new DateTimePicker();
    private DateTimePicker endDatePicker = new DateTimePicker();

    private Label start = new Label("起始时间：");
    private Label end = new Label("结束时间：");
    private Button button = new Button("查询");

    public SalaryList() {
        doctorTable.setEditable(false);
        Scene scene = new Scene(vBox, 700, 500, Color.WHITE);
        setdoctorTable();
        setButton();
        setDateTimePicker();
        
        mainText.setFont(Font.font("宋体", FontWeight.NORMAL, 17));
        mainText.setAlignment(Pos.CENTER);
        mainText.setPadding(new Insets(10, 0, 10, 0));
        mainHBox.getChildren().add(mainText);
        mainHBox.setAlignment(Pos.CENTER);

        starthBox.setAlignment(Pos.CENTER);
        starthBox.setSpacing(5);
        starthBox.getChildren().addAll(start, startDatePicker);

        endhBox.setAlignment(Pos.CENTER);
        endhBox.setSpacing(5);
        endhBox.getChildren().addAll(end, endDatePicker);

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(starthBox, endhBox, button);
        hBox.setPadding(new Insets(0, 0, 10, 0));

        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(mainHBox, hBox, doctorTable);
        vBox.setSpacing(5);

        stage.setTitle("病人列表");
        stage.setScene(scene);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    private void setDateTimePicker(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime startDatetTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth(), 0, 0, 0, 0);
        startDatePicker.dateTimeProperty().setValue(startDatetTime);
        endDatePicker.dateTimeProperty().setValue(localDateTime);
    }
    private void setdoctorTable(){
        TableColumn<YS_Table, String> ksmcColumn = new TableColumn("科室名称");
        ksmcColumn.setPrefWidth(90);
        ksmcColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, String>("KSMC"));
        ksmcColumn.setCellFactory(new Callback<TableColumn<YS_Table, String>, TableCell<YS_Table, String>>() {
            public TableCell call(TableColumn<YS_Table, String> p) {
                TableCell<YS_Table, String> cell = new TableCell<YS_Table, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });

        TableColumn<YS_Table, String> ysbhColumn = new TableColumn("医生编号");
        ysbhColumn.setPrefWidth(90);
        ysbhColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, String>("YSBH"));
        ysbhColumn.setCellFactory(new Callback<TableColumn<YS_Table, String>, TableCell<YS_Table, String>>() {
            public TableCell call(TableColumn<YS_Table, String> p) {
                TableCell<YS_Table, String> cell = new TableCell<YS_Table, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });

        TableColumn<YS_Table, String> ysmcColumn = new TableColumn("医生名称");
        ysmcColumn.setPrefWidth(90);
        ysmcColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, String>("YSMC"));
        ysmcColumn.setCellFactory(new Callback<TableColumn<YS_Table, String>, TableCell<YS_Table, String>>() {
            public TableCell call(TableColumn<YS_Table, String> p) {
                TableCell<YS_Table, String> cell = new TableCell<YS_Table, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });

        TableColumn<YS_Table, String> hzmcColumn = new TableColumn("号种名称");
        hzmcColumn.setPrefWidth(100);
        hzmcColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, String>("HZMC"));
        hzmcColumn.setCellFactory(new Callback<TableColumn<YS_Table, String>, TableCell<YS_Table, String>>() {
            public TableCell call(TableColumn<YS_Table, String> p) {
                TableCell<YS_Table, String> cell = new TableCell<YS_Table, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });

        TableColumn<YS_Table, String> hzlbColumn = new TableColumn("号种类别");
        hzlbColumn.setPrefWidth(90);
        hzlbColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, String>("HZLB"));
        hzlbColumn.setCellFactory(new Callback<TableColumn<YS_Table, String>, TableCell<YS_Table, String>>() {
            public TableCell call(TableColumn<YS_Table, String> p) {
                TableCell<YS_Table, String> cell = new TableCell<YS_Table, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });


        TableColumn<YS_Table, Integer> ghrcbColumn = new TableColumn("挂号人次");
        ghrcbColumn.setPrefWidth(100);
        ghrcbColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, Integer>("GHRC"));
        ghrcbColumn.setCellFactory(new Callback<TableColumn<YS_Table, Integer>, TableCell<YS_Table, Integer>>() {
            public TableCell call(TableColumn<YS_Table, Integer> p) {
                TableCell<YS_Table, Integer> cell = new TableCell<YS_Table, Integer>() {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });

        TableColumn<YS_Table, Integer> srhjbColumn = new TableColumn("收入合计");
        srhjbColumn.setPrefWidth(100);
        srhjbColumn.setCellValueFactory(new PropertyValueFactory<YS_Table, Integer>("SRHJ"));
        srhjbColumn.setCellFactory(new Callback<TableColumn<YS_Table, Integer>, TableCell<YS_Table, Integer>>() {
            public TableCell call(TableColumn<YS_Table, Integer> p) {
                TableCell<YS_Table, Integer> cell = new TableCell<YS_Table, Integer>() {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });
        doctorTable.getColumns().addAll(ksmcColumn, ysbhColumn, ysmcColumn, hzmcColumn, hzlbColumn, ghrcbColumn, srhjbColumn);
    }

    private void setButton(){
        button.setOnAction((javafx.event.ActionEvent listEvent) -> {
            LocalDateTime end = endDatePicker.dateTimeProperty().getValue();
            LocalDateTime now = LocalDateTime.now();
            if(Duration.between(end, now).toMillis() < 0){
                endDatePicker.dateTimeProperty().setValue(now);
            }

            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime start = startDatePicker.dateTimeProperty().getValue();
            System.out.println(start.toString());
            ZonedDateTime zonedDateTime = start.atZone(zoneId);
            Date startdate = Date.from(zonedDateTime.toInstant());


            System.out.println(end.toString());
            zonedDateTime = end.atZone(zoneId);
            Date enddate = Date.from(zonedDateTime.toInstant());

            doctors = (ObservableList<YS_Table>) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(startdate) + "_" +
                    ObjectToJsonUtil.objectToJson(enddate), MsgType.SalaryList);
            doctorTable.setItems(doctors);

        });

    }

}
