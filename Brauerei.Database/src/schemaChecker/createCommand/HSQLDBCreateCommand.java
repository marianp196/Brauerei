/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schemaChecker.createCommand;

import schemaChecker.tables.ITable;

/**
 *
 * @author marian
 */
public class HSQLDBCreateCommand implements ISqlCreateBuilder
{

    @Override
    public String CreateCommand(ITable table) {
        String SQL = "";
        
        SQL = "CREATE";
        
        return SQL;
    }
        
}
/*
CREATE TABLE Bier (
	id INT NOT NULL,
	name VARCHAR(50),
	erstellungsZeitpunkt BIGINT,
	beschreibung VARCHAR(1000),
        XML_Brauelemnte VARCHAR(8000),
	PRIMARY KEY(id)   
);


*/
