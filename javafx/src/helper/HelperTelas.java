package helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelperTelas {

    private static HelperTelas instance;

    private HelperTelas(){
        telasAcessadas = new Stack<String>();
    }

    public static HelperTelas getInstance(){
        if(instance == null){
            instance = new HelperTelas();
        }
        return instance;
    }

    private int idComNavega;

    public int getIdComNavega() {
        return idComNavega;
    }

    public void setIdComNavega(int idComNavega) {
        this.idComNavega = idComNavega;
    }

    public byte[] docFotoByte;

    public byte[] getDocFotoByte() {
        return docFotoByte;
    }

    public void setDocFotoByte(byte[] docFotoByte) {
        this.docFotoByte = docFotoByte;
    }

    public byte[] comprovantePesquisadorByte;

    public byte[] getComprovantePesquisadorByte() {
        return comprovantePesquisadorByte;
    }

    public void setComprovantePesquisadorByte(byte[] comprovantePesquisadorByte) {
        this.comprovantePesquisadorByte = comprovantePesquisadorByte;
    }

    public int getIdPostNavega() {
        return idPostNavega;
    }

    public void setIdPostNavega(int idPostNavega) {
        this.idPostNavega = idPostNavega;
    }

    private int idPostNavega;

    public int getIdPerfilNavega() {
        return idPerfilNavega;
    }

    public void setIdPerfilNavega(int idPerfilNavega) {
        this.idPerfilNavega = idPerfilNavega;
    }

    private int idPerfilNavega;

    private Stack<String> telasAcessadas;

    private String telaAtual;

    public boolean isCkvalida() {
        return ckvalida;
    }

    public void setCkvalida(boolean ckvalida) {
        this.ckvalida = ckvalida;
    }

    private boolean ckvalida = false;

    public void setTelaInicial(String tela){
        telaAtual = tela;
    }

    public void VoltarTela(AnchorPane painelOrigem){

        if(telasAcessadas.isEmpty())
            return;

        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/telas/"+telasAcessadas.peek()));
            painelOrigem.getStylesheets().clear();
            painelOrigem.getChildren().setAll(pane);
            telaAtual = telasAcessadas.pop();
        }
        catch (Exception erro){
            //log
            System.out.print(erro.getMessage());
        }

    }

    public void IrParaTela(AnchorPane painelOrigem, String destino){

        try{
            //if(!destino.substring(destino.length() - 4).equals(".fxml"))
              //  destino = destino + ".fxml";
            URL s = getClass().getResource("/telas/"+destino);
            AnchorPane pane = FXMLLoader.load(s);
            painelOrigem.getChildren().setAll(pane);
            telasAcessadas.add(telaAtual);
            telaAtual = destino;
        }
        catch (Exception erro){
            //log
            Logger.getLogger(HelperTelas.class.getName()).log(Level.SEVERE, null, erro);
        }
    }


}
