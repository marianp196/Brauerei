/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.tables;

/**
 *
 * @author marian
 */
public class Field {
    
    public Field(String name, EDataType type) throws Exception {
        if(name == null)
            throw new NullPointerException("name");
        if(name.isEmpty())
            throw new Exception("name muss Text enthalten");
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EDataType getType() {
        return type;
    }    
    
    private String name;
    private EDataType type;    
}
