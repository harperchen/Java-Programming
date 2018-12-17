package dClient.ui;

import dClient.Main;
import dClient.json.ObjectToJsonUtil;
import dClient.user.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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

import java.time.LocalDateTime;
import java.util.Date;

public class PatientList {
    private VBox vBox = new VBox();
    private Stage stage = new Stage();
    private HBox mainHBox = new HBox();
    private TableView<GH_Table> patientTable = new TableView<>();
    private Button fresh = new Button("刷 新");
    private Label mainText = new Label(Main.ys.YSMC + "医生的病人列表");
    private ObservableList<GH_Table> patients = null;
    private HBox hBox = new HBox();
    public Service<String> service;

    public PatientList() {
        patientTable.setEditable(false);
        Scene scene = new Scene(vBox, 600, 430, Color.WHITE);



        TableColumn<GH_Table, String> bhColumn = new TableColumn("挂号编号");
        bhColumn.setPrefWidth(90);
        bhColumn.setCellValueFactory(new PropertyValueFactory<GH_Table, String>("GHBH"));
        bhColumn.setCellFactory(new Callback<TableColumn<GH_Table, String>, TableCell<GH_Table, String>>() {
            public TableCell call(TableColumn<GH_Table, String> p) {
                TableCell<GH_Table, String> cell = new TableCell<GH_Table, String>() {
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

        TableColumn<GH_Table, String> brColumn = new TableColumn("病人名称");
        brColumn.setPrefWidth(70);
        brColumn.setCellValueFactory(new PropertyValueFactory<GH_Table, String>("BRMC"));
        brColumn.setCellFactory(new Callback<TableColumn<GH_Table, String>, TableCell<GH_Table, String>>() {
            public TableCell call(TableColumn<GH_Table, String> p) {
                TableCell<GH_Table, String> cell = new TableCell<GH_Table, String>() {
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

        TableColumn<GH_Table, Date> rqColumn = new TableColumn("挂号日期时间");
        rqColumn.setPrefWidth(200);
        rqColumn.setCellValueFactory(new PropertyValueFactory<GH_Table, Date>("GHRQ"));
        rqColumn.setCellFactory(new Callback<TableColumn<GH_Table, Date>, TableCell<GH_Table, Date>>() {
            public TableCell call(TableColumn<GH_Table, Date> p) {
                TableCell<GH_Table, Date> cell = new TableCell<GH_Table, Date>() {
                    @Override
                    public void updateItem(Date item, boolean empty) {
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

        TableColumn<GH_Table, String> hzmcColumn = new TableColumn("号种名称");
        hzmcColumn.setPrefWidth(100);
        hzmcColumn.setCellValueFactory(new PropertyValueFactory<GH_Table, String>("HZMC"));
        hzmcColumn.setCellFactory(new Callback<TableColumn<GH_Table, String>, TableCell<GH_Table, String>>() {
            public TableCell call(TableColumn<GH_Table, String> p) {
                TableCell<GH_Table, String> cell = new TableCell<GH_Table, String>() {
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

        TableColumn<GH_Table, String> hzlbColumn = new TableColumn("号种类别");
        hzlbColumn.setPrefWidth(100);
        hzlbColumn.setCellValueFactory(new PropertyValueFactory<GH_Table, String>("SFZJ"));
        hzlbColumn.setCellFactory(new Callback<TableColumn<GH_Table, String>, TableCell<GH_Table, String>>() {
            public TableCell call(TableColumn<GH_Table, String> p) {
                TableCell<GH_Table, String> cell = new TableCell<GH_Table, String>() {
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

        YS ys = new YS(Main.ys.YSBH, null, null, null, null, null, null);
        patients = (ObservableList<GH_Table>) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(ys), MsgType.PatientList);
        patientTable.setItems(patients);
        patientTable.getColumns().addAll(bhColumn, brColumn, rqColumn, hzmcColumn, hzlbColumn);
        mainText.setFont(Font.font("宋体", FontWeight.NORMAL, 17));
        mainText.setAlignment(Pos.CENTER);
        mainText.setPadding(new Insets(10, 0, 0, 0));
        mainHBox.getChildren().add(mainText);
        mainHBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().add(fresh);
        vBox.setSpacing(5);

        fresh.setAlignment(Pos.CENTER_RIGHT);
        fresh.setOnAction((ActionEvent listEvent) -> {
            patients = (ObservableList<GH_Table>) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(ys), MsgType.PatientList);
            patientTable.setItems(patients);
        });
        vBox.getChildren().addAll(mainHBox, hBox, patientTable);
        hBox.setPadding(new Insets(0, 0, 10, 0));
        vBox.setPadding(new Insets(20, 20, 20, 20));
        stage.setTitle("病人列表");
        stage.setScene(scene);
        stage.show();

//        service = new Service<String>() {
//            @Override
//            protected Task<String> createTask() {
//                return new Task<String>() {
//                    @Override
//                    protected String call() throws Exception {
//                        while(true){
//                            LocalDateTime localDateTime = LocalDateTime.now();
//                            if(localDateTime.getHour() == 0 &&
//                                    localDateTime.getMinute() == 0 &&
//                                    localDateTime.getSecond() == 0 &&
//                                    localDateTime.getNano() == 0){
//                                patientTable.setItems(null);
//                            }
//                        }
//                    }
//                };
//            }
//        };
//        //启动任务start()一定是最后才调用的
//        service.start();

    }

    public Stage getStage() {
        return stage;
    }
}
