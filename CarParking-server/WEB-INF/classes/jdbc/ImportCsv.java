package jdbc;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import com.opencsv.CSVReader;
public class ImportCsv

{

    public static void main(String[] args)

    {

            readCsv();

            readCsvUsingLoad();

    }

 
    private static void readCsv()

    {

 

        try (CSVReader reader = new CSVReader(new FileReader("D:\\upload.csv"), ','); 

                     Connection connection = DBConnection.GetConnection();)

        {

                String insertQuery = "insert into owner_details (name, carno, contact, emailid) values(?,?,?,?)";

                PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                String[] rowData = null;

                int i = 0;

                while((rowData = reader.readNext()) != null)

                {

                    for (String data : rowData)

                    {

                            pstmt.setString((i % 3) + 1, data);

                            if (++i % 3 == 0)

                                    pstmt.addBatch();// add batch

 

                            if (i % 30 == 0)// insert when the batch size is 10

                                    pstmt.executeBatch();

                    }

                }

                System.out.println("Data Successfully Uploaded");

        }

        catch (Exception e)

        {

                e.printStackTrace();

        }

    }

    private static void readCsvUsingLoad()

    {

        try (Connection connection = DBConnection.GetConnection())

        {
             String loadQuery = "LOAD DATA LOCAL INFILE '" + "D:\\upload.csv" + "' INTO TABLE owner_details FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' (name, carno, contact, emailid) ";

                System.out.println(loadQuery);

                Statement stmt = connection.createStatement();

                stmt.execute(loadQuery);

        }

        catch (Exception e)

        {

                e.printStackTrace();

        }

    }

}
