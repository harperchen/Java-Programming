package pClient.ui;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pClient.Main;
import pClient.json.ObjectToJsonUtil;
import pClient.user.*;
import pClient.user.MsgType;

import java.text.DecimalFormat;
import java.util.List;


public class PatientRegister {
    Stage stage = new Stage();

    private Label deptLabel = new Label("科室名称");
    private Label doctLabel = new Label("医生名称");
    private Label regTypeLabel = new Label("号码类别");
    private Label regNameLabel = new Label("号码名称");
    private Label payAmountLabel = new Label("交款金额");
    private Label amountDueLabel = new Label("应缴金额");
    private Label extraLabel = new Label("找零金额");
    private Label regNumLabel = new Label("挂号号码");

    ComboBox deptField = new ComboBox();
    ComboBox doctField = new ComboBox();
    ComboBox regTypeField = new ComboBox();
    ComboBox regNameField = new ComboBox();
    TextField payAmountField = new TextField();
    TextField amountDueField = new TextField();
    TextField extraField = new TextField();
    TextField regNumField = new TextField();

    private Button sureButton = new Button("确定");
    private Button clearButton = new Button("清除");
    private Button exitButton = new Button("退出");

    private VBox vbox = new VBox();
    private GridPane grid = new GridPane();

    private Control control;

    private String ghbh = "";
    private Boolean thbz = false;
    private Boolean flag = true;


    private PatientConfirm patientConfirm;

    private List<KS> ksList;
    ObservableList<String> ksString = FXCollections.observableArrayList();

    private List<YS> ysList;
    ObservableList<String> ysString = FXCollections.observableArrayList();

    ObservableList<String> liststr = FXCollections.observableArrayList();

    private List<HZ> hzList;
    ObservableList<String> hzString = FXCollections.observableArrayList();


    public PatientRegister() {
        stage.setTitle("病人挂号");
        Scene scene = new Scene(vbox, 600, 400, Color.WHITE);
        //获取科室信息
        ksList = (List<KS>) MsgType.MsgHandler(null, MsgType.QueryKSXX);
        for (KS ks : ksList) {
            String content = ks.KSBH + " " + ks.KSMC + " " + ks.PYZS;
            ksString.add(content);
        }

        deptField.setItems(ksString);
        deptField.setVisibleRowCount(ksString.size());

        setComboBox();
        setButton();
        setTextField();
        setDeptContent();
        setDoctContent();
        setRegTypeContent();
        setRegNameContent();

        setLayout(scene);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.br), MsgType.PatientLogout);
                System.out.print("监听到窗口关闭, 用户" + Main.br.BRBH + "下线");
            }
        });
    }


    private void setDeptContent() {
        FilteredList<String> filteredItems = new FilteredList<String>(ksString, p -> true);
        deptField.getEditor().textProperty().addListener((ov, oldvalue, newValue) -> {
            final TextField editor = deptField.getEditor();
            final String selected = (String) deptField.getSelectionModel().getSelectedItem();

            Platform.runLater(()-> {
                boolean select_flag = false;
                deptField.hide();
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        if (item.contains(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                    if(filteredItems.size() == 1){
                        if(selected != null) {
                            editor.setText(newValue);
                            editor.positionCaret(newValue.length());
                            return;
                        }
                    }
                }
                else {
                    for (String str : ksString) {
                        if (str.equals(newValue)) {
                            select_flag = true;
                            ysList = (List<YS>) MsgType.MsgHandler(deptField.getEditor().getText(), MsgType.QueryYSXX);
                            ysString.clear();
                            for (YS ys : ysList) {
                                String content = ys.YSMC + " " + ys.PYZS;
                                ysString.add(content);
                            }
                            doctField.setItems(ysString);
                            doctField.setVisibleRowCount(ysString.size());
                            filteredItems.setPredicate(item -> {
                                if (item.equals(newValue.toUpperCase())) {
                                    return true;
                                }
                                return true;
                            });
                            editor.setText(str);
                        }
                    }
                    if(select_flag) {
                        deptField.getSelectionModel().select(newValue);
                        editor.positionCaret(newValue.length());
                        return;
                    }
                }

                deptField.show();

            });
            deptField.setItems(filteredItems);
        });
    }

    public void setDoctContent() {
        FilteredList<String> filteredItems = new FilteredList<String>(ysString, p -> true);
        doctField.getEditor().textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                final TextField editor = doctField.getEditor();
                final String selected = (String) doctField.getSelectionModel().getSelectedItem();

                Platform.runLater(()-> {
                    doctField.hide();
                    boolean select_flag = false;
                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredItems.setPredicate(item -> {
                            if (item.contains(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                        if(filteredItems.size() == 1){
                            if(selected != null) {
                                editor.setText(newValue);
                                editor.positionCaret(newValue.length());
                                return;
                            }
                        }
                    }
                    else {
                        int i = 0;
                        for (String str : ysString) {
                            if (str.equals(newValue)) {
                                select_flag = true;
                                liststr.clear();
                                liststr.add("普通号 PTH");
                                if (ysList.get(i).SFZJ) {
                                    liststr.add("专家号 ZJH");
                                }
                                regTypeField.setItems(liststr);
                                regTypeField.setVisibleRowCount(liststr.size());
                                filteredItems.setPredicate(item -> {
                                    if (item.equals(newValue.toUpperCase())) {
                                        return true;
                                    }
                                    return true;
                                });
                                editor.setText(str);
                            }
                            i++;
                        }
                        if(select_flag) {
                            doctField.getSelectionModel().select(newValue);
                            editor.positionCaret(newValue.length());
                            return;
                        }
                    }
                    doctField.show();

                });
                doctField.setItems(filteredItems);
            }
        });

    }

    public void setRegTypeContent() {
        FilteredList<String> filteredItems = new FilteredList<String>(liststr, p -> true);
        regTypeField.getEditor().textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                final TextField editor = regTypeField.getEditor();
                final String selected = (String) regTypeField.getSelectionModel().getSelectedItem();

                Platform.runLater(()-> {
                    boolean select_flag = false;
                    regTypeField.hide();
                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredItems.setPredicate(item -> {
                            if (item.contains(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                        if(filteredItems.size() == 1){
                            if(selected != null) {
                                editor.setText(newValue);
                                editor.positionCaret(newValue.length());
                                return;
                            }
                        }
                    }
                    else {
                        for (String str : liststr) {
                            if (str.equals(newValue)) {
                                select_flag = true;
                                Boolean sfzj = false;
                                if (newValue.contains("专家号")) {
                                    sfzj = true;
                                }
                                HZ hzsample = new HZ("", "", "", deptField.getEditor().getText().substring(0, 6), sfzj, null);
                                hzList = (List<HZ>) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(hzsample), MsgType.QueryHZXX);
                                hzString.clear();
                                for (HZ hz : hzList) {
                                    String content = hz.HZMC + " " + hz.PYZS;
                                    hzString.add(content);
                                }
                                regNameField.setItems(hzString);
                                regNameField.setVisibleRowCount(hzString.size());
                                filteredItems.setPredicate(item -> {
                                    if (item.equals(newValue.toUpperCase())) {
                                        return true;
                                    }
                                    return true;
                                });
                                editor.setText(str);
                                if(select_flag) {
                                    regTypeField.getSelectionModel().select(newValue);
                                    editor.positionCaret(newValue.length());
                                    return;
                                }
                            }
                        }
                    }
                    regTypeField.show();

                });
                regTypeField.setItems(filteredItems);
            }
        });
    }

    private void setRegNameContent() {
        FilteredList<String> filteredItems = new FilteredList<String>(hzString, p -> true);
        regNameField.getEditor().textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                final TextField editor = regNameField.getEditor();
                final String selected = (String) regNameField.getSelectionModel().getSelectedItem();

                Platform.runLater(()-> {
                    boolean select_flag = false;
                    if (selected == null || !selected.equals(editor.getText())) {
                        filteredItems.setPredicate(item -> {
                            if (item.contains(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        });
                        if(filteredItems.size() == 1){
                            if(selected != null) {
                                editor.setText(newValue);
                                editor.positionCaret(newValue.length());
                                return;
                            }
                        }
                        regNameField.hide();
                        regNameField.show();
                    }
                    else {
                        int i = 0;
                        regNameField.hide();
                        for (String str : hzString) {
                            if (str.equals(newValue)) {
                                select_flag = true;
                                amountDueField.setText(hzList.get(i).GHFY.toString());
                                Main.br.YCJE = (double) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.br), MsgType.QueryYCJE);
                                Double dueamount = Double.valueOf(amountDueField.getText());
                                if (Main.br.YCJE >= dueamount) {
                                    payAmountField.setEditable(false);
                                    extraField.setEditable(false);
                                    payAmountField.setText("余额充足 无需充值");
                                    extraField.setText("余额充足 无找零");
                                }else{
                                    payAmountField.setEditable(true);
                                    extraField.setEditable(true);
                                }
                                filteredItems.setPredicate(item -> {
                                    if (item.equals(newValue.toUpperCase())) {
                                        return true;
                                    }
                                    return true;
                                });
                                editor.setText(str);
                            }
                            i++;
                        }
                        if(select_flag) {
                            regNameField.getSelectionModel().select(newValue);
                            editor.positionCaret(newValue.length());
                            return;
                        }
                        regNameField.show();
                    }

                });
                regNameField.setItems(filteredItems);
            }
        });
    }

    private void setComboBox(){
        deptField.setPromptText("科室编号 科室名称 拼音字首");
        ComboBoxShow(deptField);

        doctField.setPromptText("医生名称          ");
        ComboBoxShow(doctField);

        regTypeField.setPromptText("号种类别          ");
        ComboBoxShow(regTypeField);

        regNameField.setPromptText("号种名称           ");
        ComboBoxShow(regNameField);

    }


    private void setTextField(){
        amountDueField.setEditable(false);

        //若获取焦点 更新control
        amountDueField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                control = amountDueField;
            }
        });

        //鼠标点击 获取预存金额
        payAmountField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                control = payAmountField;
                return;
            }
            else {
                String content = payAmountField.getText();
                if (content.equals("")) {
                    content = "0.0";
                } else if (content.endsWith(".")) {
                    content += "0";
                } else if (!content.contains(".")) {
                    content += ".0";
                }
                payAmountField.setText(content);
                Double dueamount = Double.valueOf(amountDueField.getText());
                Double pay = Double.valueOf(payAmountField.getText());
                Double extra = Main.br.YCJE + pay - dueamount;
                if (extra < 0) {
                    extraField.setText("金额不足 请重新输入");
                    flag = false;
                }

            }
        });

        //设置输入字符的格式
        payAmountField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (payAmountField.isEditable()) {
                    if (!newValue.matches("^\\d+(\\.\\d+)?")) {
                        String s = newValue.replaceAll("[^(^\\d+(\\.\\d+)?)]", "");
                        if (s.endsWith(".") && (appearNumber(s, ".") > 1)) {
                            int index = s.indexOf(".");
                            String sub = s.substring(index + 1);
                            s = s.substring(0, index + 1);
                            sub = sub.replace(".", "");
                            s += sub;
                        }
                        payAmountField.setText(s);
                    }
                }
            }
        });

        extraField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                control = extraField;
                return;
            }
            else{
                if(extraField.isEditable()) {
                    Main.br.YCJE = (double) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.br), MsgType.QueryYCJE);
                    Double dueamount = Double.valueOf(amountDueField.getText());
                    //扣钱逻辑
                    DecimalFormat df = new DecimalFormat("0.0");
                    Double pay = Double.valueOf(payAmountField.getText());
                    Double extra = Main.br.YCJE + pay - dueamount;
                    if(flag) {
                        extraField.setText(df.format(extra));
                    }
                    flag = true;

                }
            }
        });

        extraField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                control = extraField;
            }
        });

    }

    private void setLayout(Scene scene) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(30);
        grid.setPadding(new Insets(25, 25, 35, 25));

        Label label = new Label("门诊挂号");
        label.setMaxSize(scene.getWidth(), 100);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Arial", 25));


        grid.addRow(0, deptLabel, deptField, doctLabel, doctField);
        grid.addRow(1, regTypeLabel, regTypeField, regNameLabel, regNameField);
        grid.addRow(2, payAmountLabel, payAmountField, amountDueLabel, amountDueField);
        grid.addRow(3, extraLabel, extraField, regNumLabel, regNumField);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(sureButton, clearButton, exitButton);

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        vbox.getChildren().addAll(label, grid, hBox);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.setSpacing(10);
    }

    private void setButton() {
        //点击退出用户下线
        exitButton.setOnAction((ActionEvent exitEvent) -> {
            Boolean ret = (Boolean) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.br), MsgType.PatientLogout);
            if (ret) {
                stage.close();
            }
        });

        //点击确定 减去预存金额 创建挂号号码
        sureButton.setOnAction((ActionEvent sureEvent) -> {
            String hzbh = "";
            String ysbh = "";
            String content = regNameField.getEditor().getText();
            for (HZ hz: hzList) {
                if(content.contains(hz.HZMC)){
                    hzbh = hz.HZBH;
                }
            }
            content = doctField.getEditor().getText();
            for (YS ys:ysList) {
                if(content.contains(ys.YSMC)){
                    ysbh = ys.YSBH;
                }
            }

            GH gh = new GH("", hzbh, ysbh, Main.br.BRBH, null, false,
                    Double.valueOf(amountDueField.getText()), null);
            ghbh = (String)MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(gh), MsgType.NumberQuerry);
            if(ghbh.equals("Numberisfull")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("该号种今日已满，请改日再预约！");
                alert.show();
                regNumField.setText("该号种今日已满");
            }
            else {
                patientConfirm = new PatientConfirm(ghbh);

                patientConfirm.sureButton.setOnAction((ActionEvent confirmsureEvent) -> {
                    patientConfirm.close();
                    regNumField.setText(ghbh);
                    if(!payAmountField.isEditable()) {
                        MsgType.MsgHandler(amountDueField.getText(), MsgType.CutPatientMonty);
                    }else{
                        Double dueamount = Double.valueOf(amountDueField.getText());
                        if((Main.br.YCJE < dueamount) && (Main.br.YCJE > 0)){
                            MsgType.MsgHandler(Main.br.YCJE.toString(), MsgType.CutPatientMonty);
                        }
                    }
                    patientConfirm.service.cancel();
                    Main.br.YCJE = (double) MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(Main.br), MsgType.QueryYCJE);
                    Double dueamount = Double.valueOf(amountDueField.getText());
                    if (Main.br.YCJE >= dueamount) {
                        payAmountField.setEditable(false);
                        extraField.setEditable(false);
                        payAmountField.setText("余额充足 无需充值");
                        extraField.setText("余额充足 无找零");
                    }else{
                        payAmountField.setText("");
                        extraField.setText("");
                        payAmountField.setEditable(true);
                        extraField.setEditable(true);
                    }
                });

                patientConfirm.exitButton.setOnAction((ActionEvent confirmclearEvent) -> {
                    GH ghxx = new GH(ghbh, "", "", "", null, true, null, null);
                    MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(ghxx), MsgType.NumberGiveup);
                    thbz = true;
                    patientConfirm.close();
                    regNumField.setText(ghbh + "已失效");
                });

                patientConfirm.service.setOnSucceeded((WorkerStateEvent event) -> {
                    GH ghxx = new GH(ghbh, "", "", "", null, true, null, null);
                    MsgType.MsgHandler(ObjectToJsonUtil.objectToJson(ghxx), MsgType.NumberGiveup);
                    patientConfirm.close();
                    regNumField.setText(ghbh + "已失效");
                });
            }

        });

        //点击清除 删除特定框中的信息
        clearButton.setOnAction((ActionEvent clearEvent) -> {
            if(control instanceof ComboBox){
                ((ComboBox) control).getEditor().clear();
            }
            if(control instanceof TextField){
                ((TextField) control).clear();
            }
        });
    }

    public void ComboBoxShow(ComboBox showcontrol) {
        showcontrol.setEditable(true);
        showcontrol.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                control = showcontrol;
                if (!showcontrol.isShowing()) {
                    showcontrol.show();
                }
            }
        });
    }

    private int appearNumber(String srcText, String findText) {
        int count = 0;
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            count++;
        }
        return count;
    }

}


