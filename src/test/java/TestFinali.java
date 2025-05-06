import laptop.utilities.ConnToDb;
import org.junit.jupiter.api.AfterAll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TestFinali {

    @AfterAll
    static void init() throws SQLException {
        String query="drop schema ISPW;";
        int i;
        try(Connection conn= ConnToDb.connectionToDB();
            PreparedStatement prepQ= conn.prepareStatement(query))
        {
            i= prepQ.executeUpdate();
        }
        assertEquals(8,i);

    }
}
