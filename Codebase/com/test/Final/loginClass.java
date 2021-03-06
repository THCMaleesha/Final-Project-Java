package com.test.Final;

//customer login

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//extends the class from frameClass

public class loginClass extends frameClass{

    private JFrame frame = null;

    //create static variables
    static String id,firstName,lastName,emailgot;

    //method to authenticate customer login
    public void userLogin(String email, char[] password,JFrame msgFrame) {

        //check the email and password with the database
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `customers_table` WHERE email_Address = ? and Password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, String.valueOf(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while (resultSet.next()){
                count += 1;
            }

            //if login details matched, give the access to customers page with customer details
            if (count == 1){
                JOptionPane.showMessageDialog(msgFrame,"Login Successful !!!");
                userDetails(email,password,msgFrame);
                frame.dispose();
                new userpageClass(id,firstName,lastName,emailgot);

            }else{
                JOptionPane.showMessageDialog(msgFrame,"ERROR !!!");
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    //method to get customer details
    public void userDetails(String email, char[] password,JFrame msgFrame) {

        //get details from customer table where given password & details
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `customers_table` WHERE email_Address = ? and Password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, String.valueOf(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getString(1);
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
                emailgot = resultSet.getString(5);
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    private JPanel loginPanel;
    private JLabel loginLabel;
    private JButton signupButton;
    private JButton signinButton;
    private JTextField emailTxt;
    private JButton cancelButton;
    private JButton adminButton;
    private JPasswordField passwordPasswordField;
    private JButton setting;
    private JButton buyButton;

    public loginClass() {

        //set frame
        frame = setFrame(loginPanel,frame);

        //sign up
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new userregClass();
            }
        });

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //admin login
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new adminLoginClass();
            }
        });

        // check login
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = emailTxt.getText();
                char[] password = passwordPasswordField.getPassword();
                JFrame msgframe = new JFrame();

                try {
                    userLogin(email,password,msgframe);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        //order creations
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buynowClass();
            }
        });

        //update customer details
        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new usetSettingsClass();
            }
        });
    }
}