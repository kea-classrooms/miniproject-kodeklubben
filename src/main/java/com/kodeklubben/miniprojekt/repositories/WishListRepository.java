package com.kodeklubben.miniprojekt.repositories;
import com.kodeklubben.miniprojekt.services.DatabaseConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private DatabaseConnectionManager dcm = new DatabaseConnectionManager("jdbc:mysql://eu-west.connect.psdb.cloud/miniprojekt?sslMode=VERIFY_IDENTITY", "miniprojekt", "crxhth3n6nls3djd77if", "pscale_pw_Vz1sM3sSRwnOrc72waM3ahyvHEfHDmKnJCL2DUSKF2S");


}
