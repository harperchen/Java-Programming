package dClient.ui;


import dClient.Main;
import dClient.json.JsonToObject;
import dClient.json.ObjectToJsonUtil;
import dClient.user.MsgType;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import dClient.user.YS;
import dClient.user.KS;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

public class DoctorMain {
    private Stage stage = new Stage();

    private Label mainText = new Label("欢 迎 使 用 医 生 系 统");
    private Label numLabel = new Label("医生编号：");
    private Label xmLabel = new Label("医生姓名：");
    private Label ksLabel = new Label("科室名称：");
    private Label typeLabel = new Label("医生类别：");
    private Label lastlogin = new Label("最近登陆时间：");

    private Button patientButton = new Button("病人列表");
    private Button salaryButton = new Button("收入列表");
    private Button exitButton = new Button("退出系统");

    private HBox btHBox = new HBox(30);
    private VBox infoVBox = new VBox(10);
    private HBox infoHBox = new HBox();
    private HBox mainHBox = new HBox();
    private VBox vBox = new VBox();

    public DoctorMain() {
        setButton();
        setLabel();

        vBox.getChildren().addAll(mainHBox, infoHBox, btHBox);

        Scene scene = new Scene(vBox, 400, 300, Color.WHITE);
        mainText.setMinSize(scene.getWidth(), scene.getHeight()/4);
        stage.setTitle("医生系统");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.ys), MsgType.DoctorLogout);
                System.out.print("监听到窗口关闭, 用户" + Main.ys.YSBH + "下线");
                if(patientList != null){
                    patientList.getStage().close();
                }
                if(salaryList != null){
                    salaryList.getStage().close();
                }
            }
        });
    }


    private void setLabel(){
        mainText.setFont(Font.font("宋体", FontWeight.NORMAL, 20));
        mainText.setAlignment(Pos.CENTER);
        mainText.setPadding(new Insets(10, 0, 0, 0));
        mainHBox.getChildren().add(mainText);
        mainHBox.setAlignment(Pos.CENTER);

        infoVBox.getChildren().addAll(numLabel, xmLabel, ksLabel, typeLabel, lastlogin);
        infoVBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: gray;");
        infoHBox.getChildren().add(infoVBox);
        infoHBox.setAlignment(Pos.CENTER);

        List<String> object = (List<String>) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.ys), MsgType.QueryDoctor);
        Main.ys = (YS) JsonToObject.jsonToObject(object.get(0), YS.class);
        KS ks = (KS) JsonToObject.jsonToObject(object.get(1), KS.class);

        numLabel.setText(numLabel.getText().concat(Main.ys.YSBH));
        xmLabel.setText(xmLabel.getText().concat(Main.ys.YSMC));
        ksLabel.setText(ksLabel.getText().concat(ks.KSMC));
        typeLabel.setText(typeLabel.getText().concat(Main.ys.SFZJ? "专家医生":"普通医生"));

//        Instant instant = Main.ys.DLRQ.toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
        DateFormat longDateFormat = DateFormat.getDateTimeInstance
                (DateFormat.LONG,DateFormat.LONG);
        lastlogin.setText(lastlogin.getText().concat(longDateFormat.format(Main.ys.DLRQ)));
    }
    private PatientList patientList = null;
    private SalaryList salaryList = null;

    private void setButton() {
        btHBox.getChildren().addAll(patientButton, salaryButton, exitButton);
        btHBox.setAlignment(Pos.CENTER);
        btHBox.setPadding(new Insets(20, 0, 0, 0));
        patientButton.setOnAction((ActionEvent listEvent) -> {
            patientList = new PatientList();
        });

        salaryButton.setOnAction((ActionEvent listEvent) -> {
            salaryList = new SalaryList();
        });

        exitButton.setOnAction((ActionEvent clearEvent) -> {
            MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.ys), MsgType.DoctorLogout);
            stage.close();
            System.out.print("监听到窗口关闭, 用户" + Main.ys.YSBH + "下线");
            if(patientList != null){
                patientList.getStage().close();
            }
            if(salaryList != null){
                salaryList.getStage().close();
            }

        });
    }
}
