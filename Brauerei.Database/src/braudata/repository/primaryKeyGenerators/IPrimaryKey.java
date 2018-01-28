/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braudata.repository.primaryKeyGenerators;

/**
 *
 * @author marian
 */
public interface IPrimaryKey<TDatenTyp> 
{
    TDatenTyp GetNewPrimaryKey() throws Exception;
}
