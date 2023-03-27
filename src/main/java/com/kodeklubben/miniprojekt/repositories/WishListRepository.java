package com.kodeklubben.miniprojekt.repositories;
import com.kodeklubben.miniprojekt.services.DatabaseConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class WishListRepository {

    private DatabaseConnectionManager dcm = new DatabaseConnectionManager("eu-west.connect.psdb.cloud", "n9yvymfj507ekkjlhtkd", "pscale_pw_iJs7WNJ0mxHfCdADkqSRM1GGcSUK5GgNiKGcgzgSz5m");

    private static final String GET_WISH_LISTS = "SELECT id, name, userId " +
            "FROM wishLists WHERE userId=?";

    public ArrayList<String> getWishLists(long userId) {
        try(PreparedStatement statement = dcm.getConnection().prepareStatement(GET_WISH_LISTS)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> wishLists = new ArrayList<>();
            while (resultSet.next()) {
                wishLists.add(resultSet.getString("name"));
            }
            return wishLists;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
