package pClient.ui;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import pClient.user.MsgType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pClient.json.ObjectToJsonUtil;
import pClient.user.*;



public class PatientConfirm extends Stage{
    private String number;

    private Label label = new Label();
    private Label mainText = new Label();

    public Service<String> service;
    public Button sureButton = new Button("确定");
    public Button exitButton = new Button("退出");

    private HBox btHBox = new HBox(10);
    private VBox vbox = new VBox();

    public PatientConfirm(String number) {
        super();
        this.number = number;
        setButton();
        setLabel();
        vbox.getChildren().addAll(label, mainText, btHBox);
        Scene scene = new Scene(vbox, 300, 170, Color.WHITE);
        super.setTitle("挂号确认");
        super.setScene(scene);
        super.show();
    }


    public void setLabel() {
        mainText.setText("系统已为您抢到号码：" + number + "\n请在10s内确认缴费，否则号码失效\n" +
                "若放弃该号码，请点击退出，该号码自动作废");
        mainText.setLineSpacing(8);
        mainText.setMaxSize(300, 200);
        mainText.setAlignment(Pos.CENTER);
        mainText.setPadding(new Insets(10, 0, 20, 0));
        label.setMaxSize(300, 200);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setPadding(new Insets(10, 38, 0, 0));
        service=new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        for(int a = 9; a >= 0;a--){
                            updateValue("倒计时："+a+"s");
                            Thread.sleep(1000);
                        }
                        return "Failed!";
                    }
                };
            }
        };
        //绑定process3的text属性为service的text属性
        label.textProperty().bind(service.valueProperty());
        //启动任务start()一定是最后才调用的
        service.start();
    }

    private void setButton(){
        btHBox.setAlignment(Pos.BOTTOM_CENTER);
        btHBox.getChildren().add(sureButton);
        btHBox.getChildren().add(exitButton);
        btHBox.setSpacing(40);

    }

}
