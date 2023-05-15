package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.SQLException;

public class DatabaseTest {
    @Test
    public void testInstance() {
        assertNotNull("[TEST] Database connection is null", Database.getInstance().cnx);
    }
    @Test
    public void testConnection() throws SQLException{
        assertTrue("[TEST] Database connection timed out", Database.getInstance().cnx.isValid(5));
    }
}
