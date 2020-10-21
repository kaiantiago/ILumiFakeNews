/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.basis;

import comum.Entidade;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;


public abstract class DAO <E extends Entidade> {
    
    protected Class<E> entityClass;

    public DAO(Class<E> entityClass){
        this.entityClass = entityClass;
    }
    
    public abstract E localizaPorId(int id);
    public abstract E localiza(String codigo) throws SQLException;
    public abstract ArrayList<E> lista() throws SQLException;
    public abstract void Insere(E entidade) throws SQLException;
    
    protected E getInstanceOfE()
    {
        try
        {
            return entityClass.getDeclaredConstructor().newInstance();
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }
}
