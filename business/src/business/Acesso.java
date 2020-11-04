
package business;

import business.Log.ControleAuditoria;
import comum.Postagem;
import comum.Usuario;
import comum.enums.TipoUsuario;
import dao.basis.DAO;
import dao.enums.EntidadeDAO;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;


public class  Acesso {

    public static Usuario validaLogin(String email, String senha) throws SQLException {

        DAO dao = EntidadeDAO.USUARIO.getEntidadeDAO();

        Usuario encontrado = (Usuario) dao.localiza(email);
        if(encontrado == null || !senha.equals(encontrado.getSenha())){
            return null;
        }
        return encontrado;
    }

    public static void validaNovaSenha (String senha, String senhaconf) throws Exception
    {
        if (senha == null || senha.trim().isEmpty()) {
            throw new Exception("Preencha os campos de senha");
        }
        else if (senhaconf == null || senhaconf.trim().isEmpty())
        {
            throw new Exception("Preencha os campos de senha");
        }
        else if (senha.equals(senhaconf))
        {
            throw new Exception("Senha Confirmada Inválida");
        }

    }

    public static void validaDataNasc (LocalDate data) throws Exception {
        LocalDate antes;
        antes = LocalDate.now().plusYears(-13);

        if (!data.isBefore(antes)){
            throw new Exception("Data de Nascimento inválida");
        }

    }

    public static Object[] listaDadosUsuario() throws SQLException {
        DAO dados = EntidadeDAO.USUARIO.getEntidadeDAO();
        return dados.listaTodos().toArray();
    }


    public static void enviaDadosUsuario(Usuario u) throws SQLException{
        DAO dao = EntidadeDAO.USUARIO.getEntidadeDAO();
        dao.Insere(u);
        ControleAuditoria.getInstance().AddAuditoria("Usuario salvo: " + u.getEmail());
    }

    public static void alterarDadosUsuario(Usuario u)  throws SQLException{
        DAO dao = EntidadeDAO.USUARIO.getEntidadeDAO();
        dao.Alter(u);
    }


    public static void enviaPost (Postagem p) throws SQLException{
        DAO dao = EntidadeDAO.POSTAGEM.getEntidadeDAO();
        dao.Insere(p);
    }

    public static Postagem obtemPost (int id) throws SQLException{
        DAO dao = EntidadeDAO.POSTAGEM.getEntidadeDAO();
        return (Postagem) dao.localizaPorId(id);
    }

    public static void enviaPostCientifico (Postagem pc) throws SQLException {
        DAO dao = EntidadeDAO.POSTAGEM.getEntidadeDAO();
        dao.Insere(pc);
        ControleAuditoria.getInstance().AddAuditoria("Postagem salva: " + pc.getTitulo());
    }
    
    public static boolean ehModeradorLogado(){
        return DefinicoesPadrao.getInstance().getTipoUsuario() == TipoUsuario.MODERADOR;
    }
    public static boolean ehComumLogado(){
        return DefinicoesPadrao.getInstance().getTipoUsuario() == TipoUsuario.COMUM;
    }
    public static boolean ehPesquisadorLogado(){
        return DefinicoesPadrao.getInstance().getTipoUsuario() == TipoUsuario.PESQUISADOR;
    }
    public static boolean ehLogado(){
        return DefinicoesPadrao.getInstance().getTipoUsuario() != null;
    }

    public static byte[] imgToBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10241];
        for (int readNum; (readNum = fis.read(buf)) != -1; ) {
            bos.write(buf, 0, readNum);
        }
        return bos.toByteArray();
    }

    public static byte[] ConvertImage(Image image) throws IOException {
        FileInputStream fis = new FileInputStream(String.valueOf(image));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10241];
        for (int readNum; (readNum = fis.read(buf)) != -1; ) {
            bos.write(buf, 0, readNum);
        }
        return bos.toByteArray();
    }

    public static Image bytesToImg(byte[] img)  {
        return new Image(new ByteArrayInputStream(img));
    }
}
