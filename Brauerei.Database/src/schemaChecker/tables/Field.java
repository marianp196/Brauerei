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
       commonConstructor(name, 500, type);
    }   
        
    public Field(String name, EDataType type, int laenge) throws Exception {
        commonConstructor(name, laenge, type);
    }

    public String getName() {
        return name;
    }

    public EDataType getType() {
        return type;
    }    
    
    public int getLaenge() {
        return laenge;
    }
    
    private void commonConstructor(String name1, int laenge1, EDataType type1) throws Exception, NullPointerException {
        if (name1 == null) {
            throw new NullPointerException("name");
        }
        if (name1.isEmpty()) {
            throw new Exception("name muss Text enthalten");
        }
        if (laenge1 <= 0) {
            throw new Exception("laenge muss groeßer 0 sein");
        }
        this.name = name1;
        this.type = type1;
        this.laenge = laenge1;
    }  
    
    private String name;
    private EDataType type;  
    private int laenge;    
}
