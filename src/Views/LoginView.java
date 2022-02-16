package Views;

import javax.swing.*;

public class LoginView {
    private JPanel LoginPanel;
    private JPasswordField pswEnter;
    private JButton btnNewUser;
    private JTextField txtUserName;
    private JButton btnLogin;


    // Getters and setters
    public JPanel getLoginPanel() {
        return LoginPanel;
    }

    public void setLoginPanel(JPanel loginPanel) {
        LoginPanel = loginPanel;
    }

    public JPasswordField getPswEnter() {
        return pswEnter;
    }

    public void setPswEnter(JPasswordField pswEnter) {
        this.pswEnter = pswEnter;
    }

    public JButton getBtnNewUser() {
        return btnNewUser;
    }

    public void setBtnNewUser(JButton btnNewUser) {
        this.btnNewUser = btnNewUser;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JTextField txtUserName) {
        this.txtUserName = txtUserName;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

}
