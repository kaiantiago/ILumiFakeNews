package business.Log;

import comum.Auditoria;
import dao.basis.DAO;
import dao.enums.EntidadeDAO;

import java.sql.SQLException;

public class ThreadArmazenaAuditorias extends Thread {

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean ativo;

    @Override
    public void run(){
        setAtivo(true);
        DAO dados = EntidadeDAO.AUDITORIA.getEntidadeDAO();
        Auditoria a = new Auditoria();

        while (isAtivo()){
           String msg = ControleAuditoria.getInstance().removeProxAuditoria();

           try {
                if(msg!=null){
                    a.setDescricao(msg);
                    a.setIdTipo(0);
                    dados.Insere(a);
                }
                Thread.sleep(1);

            } catch (SQLException | InterruptedException erro) {
                erro.printStackTrace();
            }


        }
    }

}
