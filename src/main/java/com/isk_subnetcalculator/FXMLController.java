package com.isk_subnetcalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {
    
    @FXML
    private TableView<Subnet> tableView; 
    @FXML
    private TableColumn<Subnet, String> nameColumn;
    @FXML
    private TableColumn<Subnet, String> neededSizeColumn;
    @FXML
    private TableColumn<Subnet, String> allocatedSizeColumn;
    @FXML
    private TableColumn<Subnet, String> addressColumn;
    @FXML
    private TableColumn<Subnet, String> maskColumn;
    @FXML
    private TableColumn<Subnet, String> decMaskColumn;
    @FXML
    private TableColumn<Subnet, String> rangeColumn;
    @FXML
    private TableColumn<Subnet, String> broadcastColumn;
    @FXML
    private TableColumn<Subnet, String> capacityColumn;
    
    @FXML
    private TextField nameText;
       
    @FXML
    private TextField sizeText;
    
    @FXML
    private TextField capacityGlobalText;
    
    @FXML
    private TextField capacityLocalText;
    
    @FXML
    private TextField majorIpText;
             
    private final Calculator calculator = new Calculator();    
    
    private void calculate(){
        tableView.setItems(FXCollections.observableArrayList(
                calculator.calculate(majorIpText.getText())));
        tableView.refresh();
    }
            
    @FXML
    private void changeCapacity(ActionEvent event) {
        calculator.setGlobalCapacity(Integer.parseInt(
                                                capacityGlobalText.getText()));
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
            calculate();
    }
    @FXML
    private void buttonGeneratorClick(ActionEvent event) {
        
        calculator.clearSubnets();
        tableView.getItems().clear();
        
        int randomNum = 3 + (int)(Math.random() * 10);
        String[] name = {"A", "B", "C", "D", "E", "F", "G", "H", "I","J"};
        
        for(int i = 0; i < randomNum; i++){
            int randomSubnetSize = 3 + (int)(Math.random() * 10);
            Subnet subnet = new Subnet(name[i], randomSubnetSize);
            subnet.setCapacity(capacityLocalText.getText());
            calculator.addSubnet(subnet);
            tableView.getItems().add(subnet);
        }        
        tableView.refresh();
    }
    
    @FXML
    private void clearSubnetsButtonClick(ActionEvent event) {
        calculator.clearSubnets();
        tableView.getItems().clear(); 
    }
        
    @FXML
    private void handleAddSubnetButtonClick(ActionEvent event) {
        
        String name = nameText.getText();
        String size = sizeText.getText();
        
        if(!name.equals("") && !size.equals("") ){
            
            Subnet subnet = new Subnet(name, Integer.parseInt(size));
            subnet.setCapacity(capacityLocalText.getText());
            ObservableList<Subnet> viewlist = tableView.getItems();

            if(!viewlist.contains(subnet)){
               viewlist.add(subnet);           
              calculator.addSubnet(subnet);  
            }          
        }
    }
          
    @FXML
    private void handleRemoveSubnetButtonClick(ActionEvent event) {
        
        String name = nameText.getText();
        
        if(!name.equals("")){          
            calculator.removeSubnet(new Subnet(name, Integer.SIZE));
            
            tableView.getItems().remove(name);
            
                calculate();
        }
    }
    
    @FXML
    private void handleChangeSubnetButtonClick(ActionEvent event) {
        
        String name = nameText.getText();
        String size = sizeText.getText();
        
        if(!name.equals("") && !size.equals("") ){ 
            
            Subnet subnet = new Subnet(name, Integer.parseInt(size));
            subnet.setCapacity(capacityLocalText.getText());
            
            calculator.removeSubnet(subnet);
            calculator.addSubnet(subnet);
            
            ObservableList<Subnet> viewlist = tableView.getItems();
            
            viewlist.remove(subnet); 
            viewlist.add(subnet);  
            
            calculate();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setEditable(true);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        neededSizeColumn.setCellValueFactory(
                new PropertyValueFactory<>("neededSize"));
        allocatedSizeColumn.setCellValueFactory(
                new PropertyValueFactory<>("allocatedSize"));
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        maskColumn.setCellValueFactory(
                new PropertyValueFactory<>("mask"));
        decMaskColumn.setCellValueFactory(
                new PropertyValueFactory<>("decMask"));
        rangeColumn.setCellValueFactory(
                new PropertyValueFactory<>("range"));
        broadcastColumn.setCellValueFactory(
                new PropertyValueFactory<>("broadcast")); 
        capacityColumn.setCellValueFactory(
                new PropertyValueFactory<>("capacity"));
    }    
}
