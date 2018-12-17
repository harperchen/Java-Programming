package pClient.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import pClient.Main;
import pClient.user.MsgType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import pClient.json.ObjectToJsonUtil;
import pClient.user.*;


public class PatientLogin {
    private Stage stage = new Stage();

    private Label mainText = new Label("病人登陆");
    private Label numLabel = new Label("病人编号:");
    private Label pswLabel = new Label("密码:");

    private Label numWarn = new Label();
    private Label pswWarn = new Label();

    private TextField control;
    private TextField numTextField = new TextField();
    private PasswordField pswTextField = new PasswordField();

    private Button submitButton = new Button("登录");
    private Button clearButton = new Button("清除");


    private GridPane gridPane = new GridPane();
    private HBox btHBox = new HBox(10);
    private VBox vbox = new VBox();

    public PatientLogin() {
        setButton();
        setTextField();

        numWarn.setTextFill(Color.RED);
        pswWarn.setTextFill(Color.RED);

        mainText.setFont(Font.font("宋体", FontWeight.NORMAL, 18));
        mainText.setMaxSize(300, 200);
        mainText.setAlignment(Pos.CENTER);
        mainText.setPadding(new Insets(10, 0, 0, 0));

        gridPane.setPadding(new Insets(20, 10, 20, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(numLabel, 0, 0);
        gridPane.add(numTextField, 1, 0);
        gridPane.add(numWarn, 2, 0);
        gridPane.add(pswLabel, 0, 1);
        gridPane.add(pswTextField, 1, 1);
        gridPane.add(pswWarn, 2, 1);
        gridPane.setStyle("-fx-background-color: BEIGE;");

        vbox.getChildren().addAll(mainText, gridPane, btHBox);

        Scene scene = new Scene(vbox, 300, 180, Color.WHITE);
        stage.setTitle("病人登录");
        stage.setScene(scene);
        stage.show();
    }

    private void setButton(){
        btHBox.setAlignment(Pos.BOTTOM_CENTER);
        btHBox.getChildren().add(submitButton);
        btHBox.getChildren().add(clearButton);
        btHBox.setSpacing(40);
        numTextField.setText("000001");
        pswTextField.setText("BR1");
        submitButton.setOnAction((ActionEvent submitEvent) -> {
            if(numTextField.getText().isEmpty()){
                numWarn.setText("编号为空");
            }
            if(pswTextField.getText().isEmpty()){
                pswWarn.setText("密码为空");
            }

            if(!numTextField.getText().isEmpty() && !pswTextField.getText().isEmpty()) {
                numWarn.setText("");
                pswWarn.setText("");
                BR br = new BR(numTextField.getText(), null, pswTextField.getText(), null, null);
                Object result = (Object) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(br), MsgType.PatientLogin);
                if(result.getClass().toString().equals("class java.lang.Boolean")) {
                    stage.close();
                    Main.br = new BR(numTextField.getText(), null,  pswTextField.getText(), null, null);
                    PatientRegister patientRegister = new PatientRegister();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if((int)result == MsgType.WrongBRBH){
                        alert.setContentText("账号不存在！");
                        numTextField.clear();
                    }
                    else if((int)result == MsgType.WrongDLKL){
                        alert.setContentText("密码错误，请重新输入！");
                    }
                    else if((int)result == MsgType.PatientAlreadyOn){
                        alert.setContentText("编号为" + numTextField.getText() + "已上线");
                        numTextField.clear();
                    }
                    pswTextField.clear();
                    alert.show();
                }
            }
        });


        clearButton.setOnAction((ActionEvent clearEvent) -> {
            control.clear();
        });
    }


    private void setTextField(){
        numTextField.requestFocus();
        numTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                String s = newValue.replaceAll("\\D*","");
                numTextField.setText(s);
                System.out.println(newValue);
                System.out.println(s);
            }
        });

        numTextField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                control = numTextField;
            }
        });
        numTextField.setPromptText("请输入您的编号");
        pswTextField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                control = pswTextField;
                System.out.println("pswTextField Having the Focus");
            }
        });
    }

}
