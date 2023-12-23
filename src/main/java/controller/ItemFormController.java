package controller;

import bo.Custom.ItemBo;
import bo.Custom.impl.ItemBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.ItemDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dto.tm.ItemTm;



import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {

    @FXML
    private TreeTableColumn colCode;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtUnitPrice;

    private ItemBo<ItemDto> itemBo= new ItemBoImpl();

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        tblItem.setOnMouseClicked(event -> {
            if(event.getClickCount()==1&& (!tblItem.getSelectionModel().isEmpty())){
                TreeItem<ItemTm> item = tblItem.getSelectionModel().getSelectedItem();
                txtCode.setText(item.getValue().getCode());
                txtDesc.setText(item.getValue().getDescription());
                txtUnitPrice.setText(String.valueOf(item.getValue().getUnitPrice()));
                txtQty.setText(String.valueOf(item.getValue().getQty()));
            }
        });
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getDescription().contains(newValue);
                    }
                });
            }
        });
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();


        try {
            List<ItemDto> dtoList = itemBo.allItem();


            for(ItemDto itemDto:dtoList){
                JFXButton btn = new JFXButton("Delete");

               tmList.add(
                       new ItemTm(
                               itemDto.getCode(),
                               itemDto.getDescription(),
                               itemDto.getUnitPrice(),
                               itemDto.getQty(),
                               btn


                               )
               );

                btn.setOnAction(actionEvent -> {
                    deleteItem(itemDto.getCode());
                });


            }

            TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {

        try
        {
            if (itemBo.deleteItem(code)){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if(!isEmpty()){
        ItemDto dto = new ItemDto(txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );


        try {
          if ( itemBo.saveItem(dto)){
           new Alert(Alert.AlertType.INFORMATION,"delete Scussesful").show();
          }else {
              new Alert(Alert.AlertType.ERROR,"delete Unscussesful").show();
          }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        }else{
            new Alert(Alert.AlertType.ERROR,"Empty field found").show();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!isEmpty()) {
            ItemDto itemDto = new ItemDto(txtCode.getText(), txtDesc.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQty.getText()));
            boolean isUpdated = itemBo.updateItem(itemDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Update Success").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Empty field found").show();
        }
    }
        private boolean isEmpty(){
            if(txtQty.getText().isEmpty()||txtDesc.getText().isEmpty()||txtCode.getText().isEmpty()||txtUnitPrice.getText().isEmpty()){
                return true;
            }else {
                return false;
            }
        }

}
