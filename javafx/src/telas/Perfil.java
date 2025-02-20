package telas;

import business.DadosDaSecao;
import business.Acesso;
import comp.CustomControlPerfil;
import comum.Postagem;
import comum.Usuario;
import helper.HelperTelas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Perfil {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtSenha;

    @FXML
    private TextField txtSenhaConf;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblSenha;

    @FXML
    private Label lblSenhaConf;

    @FXML
    private TextField txtBio;

    @FXML
    private ImageView ivProfile;

    @FXML
    private Button btnEditarPerfil;

    @FXML
    public ListView<CustomControlPerfil> pnPostsUser;

    @FXML
    public Label lbNome;

    @FXML
    public Button btnTrocar;

    private byte[] imgBytes;

    @FXML
    private void initialize() throws SQLException {
        Usuario user;
        boolean ehEditavel = false;
        if(HelperTelas.getInstance().getIdPerfilNavega() == -1){
             user = DadosDaSecao.getInstance().getUsuarioLogado();
             ehEditavel=true;
        }
        else{
            user = Acesso.localizaUsuarioPorId(HelperTelas.getInstance().getIdPerfilNavega());
        }

        txtBio.setEditable(ehEditavel);
        txtSenha.setEditable(ehEditavel);
        txtSenhaConf.setEditable(ehEditavel);
        txtEmail.setEditable(ehEditavel);
        btnEditarPerfil.setVisible(ehEditavel);

        txtSenha.setVisible(ehEditavel);
        txtSenhaConf.setVisible(ehEditavel);
        txtEmail.setVisible(ehEditavel);
        lblEmail.setVisible(ehEditavel);
        lblSenha.setVisible(ehEditavel);
        lblSenhaConf.setVisible(ehEditavel);
        btnTrocar.setVisible(ehEditavel);

        txtEmail.setText(user.getEmail());
        txtBio.setText(user.getBio());
        txtSenha.setText(user.getSenha());
        txtSenhaConf.setText(user.getSenha());
        lbNome.setText(user.getNome() + (user.getIdTipoDeUsuario()==1?"✓":""));

        if(user.getImagem() != null && user.getImagem().length!=0){
            ivProfile.setImage(Acesso.bytesToImg(user.getImagem()));
        }

        criaListViewPostagemUser(Acesso.obtemListPostsPorUser(user.getId()));
    }

    public void btnVoltarAction(ActionEvent actionEvent) {
        HelperTelas.getInstance().IrParaTela(rootPane, "Feed.fxml");
    }

    public void editarCadastro (ActionEvent actionEvent) {

        Usuario user = DadosDaSecao.getInstance().getUsuarioLogado();
        try {
            user.setBio(txtBio.getText());
            user.setEmail(txtEmail.getText());
            user.setSenha(txtSenha.getText());
            user.setImagem(imgBytes);
            Acesso.alterarDadosUsuario(user);
            HelperTelas.getInstance().VoltarTela(rootPane);

        } catch (SQLException erro) {
            new Alert(Alert.AlertType.ERROR, "Algo de errado ao salvar!").showAndWait();
        } catch (Exception erro) {
            new Alert(Alert.AlertType.ERROR, "Há valores incorretos!").showAndWait();
        }
    }

    EventHandler<ActionEvent> metodoEditar = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent actionEvent){
            CustomControlPerfil c = (CustomControlPerfil) ((Button) actionEvent.getSource()).getParent().getParent();
            HelperTelas.getInstance().setIdPostNavega(c.getIdPostNavega());
            HelperTelas.getInstance().IrParaTela(rootPane, "VisualizaPost.fxml");
        }
    };

    EventHandler<ActionEvent> metodoApagar = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent actionEvent) {
            CustomControlPerfil c = (CustomControlPerfil) ((Button) actionEvent.getSource()).getParent().getParent();
            try {
                Postagem p = Acesso.obtemPost(c.getIdPostNavega());
                Alert alert = new Alert(Alert.AlertType.WARNING, "Você realmente deseja apagar este comentário?", ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait();
                if(alert.getResult()==ButtonType.OK) {
                    Acesso.apagaPostagem(p);
                    criaListViewPostagemUser(Acesso.obtemListPostsPorUser(DadosDaSecao.getInstance().getUsuarioLogado().getId()));
                }
                if(alert.getResult()==ButtonType.CANCEL)
                {
                    alert.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };

    EventHandler<ActionEvent> metodoPost = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent actionEvent){
            CustomControlPerfil c = (CustomControlPerfil) ((Button) actionEvent.getSource()).getParent().getParent();
            HelperTelas.getInstance().setIdPostNavega(c.getIdPostNavega());
            HelperTelas.getInstance().IrParaTela(rootPane, "VisualizaPost.fxml");

        }
    };

    public void criaListViewPostagemUser (ArrayList posts) throws SQLException {

        List<CustomControlPerfil> list = new ArrayList<CustomControlPerfil>();

        for (var p : posts) {

            if(true) {
                Postagem post = (Postagem) p;
                list.add(new CustomControlPerfil(post,metodoPost,metodoApagar,metodoEditar));
            }
        }

        ObservableList<CustomControlPerfil> myObservableList = FXCollections.observableList(list);
        pnPostsUser.setItems(myObservableList);
    }

    public void trocarFoto(ActionEvent actionEvent) {
        try {
            Stage primaryStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            Image image = new Image(selectedFile.toURI().toString());
            ivProfile.setImage(image);
            imgBytes = Acesso.imgToBytes(selectedFile);
        } catch (Exception ex) {
            //nothing
        }
    }
}