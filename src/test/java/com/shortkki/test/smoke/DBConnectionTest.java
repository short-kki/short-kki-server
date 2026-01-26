package com.shortkki.test.smoke;

import com.shortkki.test.support.IntegrationTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

public class DBConnectionTest extends IntegrationTestBase {

    @Autowired
    private DataSource dataSource;

    @DisplayName("[DB] DataSource 연결 확인")
    @Test
    void datasource_works() throws Exception {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT 1");
                ResultSet rs = ps.executeQuery()
        ) {
            assertThat(rs.next()).isTrue();
        }
    }
}
