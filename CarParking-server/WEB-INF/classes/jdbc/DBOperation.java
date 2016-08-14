package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//import org.json.JSONObject;
import org.json.simple.JSONObject;

import pojo.CarOwner;

import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DBOperation {

	private static Connection con = null;
	private static String name, carno, contact, emailid;
	// Statement st =null;
	// ResultSet rs=null ;
	static {
		con = (Connection) DBConnection.GetConnection();
	}

	// @SuppressWarnings("unchecked")
	public void select(String s) {

		try {
			Statement st = (Statement) con.createStatement();
			String s1 = "select * from  " + s;
			ResultSet rs = st.executeQuery(s1);
			JSONObject obj = new JSONObject();
			while (rs.next()) {
				obj.put("name", rs.getString(1));
				obj.put("carno", rs.getString(2));
				obj.put("contact", rs.getString(3));
				obj.put("emailid", rs.getString(4));
			}

			/*
			 * ResultSetMetaData rsdm = rs.getMetaData(); int
			 * col=rsdm.getColumnCount(); for(int i=0;i<=col;i++) {
			 * System.out.println(rsdm.getColumnName(i)); }
			 */

			/*
			 * while(rs.next()) { System.out.print(rs.getString(1)+"  ");
			 * System.out.print(rs.getString(2)+"  ");
			 * System.out.print(rs.getString(3)+"  ");
			 * System.out.print(rs.getString(4)+"  "); System.out.println(" ");
			 * }
			 */

			System.out.print(obj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void update(CarOwner carowner)
	{
		
		Connection con=null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		Statement st =null;
		con=(Connection) DBConnection.GetConnection();
		//pst.addBatch();

			try {
				PreparedStatement ps = con.prepareStatement(
					      "update owner_details set  name = ?, contact = ? , emailid = ?  where carno = ?");
					 
					    // set the preparedstatement parameters
					    ps.setString(1,carowner.getName());
					    ps.setString(4,carowner.getCarno());
					    ps.setString(2,carowner.getContact());
					    ps.setString(3,carowner.getEmailid());
					 
					    // call executeUpdate to execute our sql update statement
					    ps.executeUpdate();
					    ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public static boolean checkAvil(String carno) throws SQLException {
		Statement stmt = (Statement) con.createStatement();
		String qry = "SELECT carno FROM owner_details WHERE carno='" + carno
				+ "';";
		System.out.println("Query: " + qry);
		ResultSet results = stmt.executeQuery(qry);
		System.out.println("ResultSet: " + results);
		// System.out.println("Result: " + results.getString(1));
		boolean available = false;
		boolean r = results.next();
		if (r) {
			available = true;
		}
		System.out.println("r: " + r);
		System.out.println("available: " + available);
		return available;

	}

	public static CarOwner searchByCarNo(String s) {

		Statement st = null;
		ResultSet rs = null;
		CarOwner carowner;

		con = (Connection) DBConnection.GetConnection();
		try {
			st = (Statement) con.createStatement();
			String s1 = "select * from  owner_details where carno=" + '"' + s
					+ '"';
			// System.out.println(s1);
			rs = st.executeQuery(s1);

			/*
			 * ResultSetMetaData rsdm = rs.getMetaData(); int
			 * col=rsdm.getColumnCount(); for(int i=0;i<=col;i++) {
			 * System.out.println(rsdm.getColumnName(i)); }
			 */
			while (rs.next()) {

				name = rs.getString(1);
				carno = rs.getString(2);
				contact = rs.getString(3);
				emailid = rs.getString(4);

				System.out.print(rs.getString(1) + "  ");
				System.out.print(rs.getString(2) + "  ");
				System.out.print(rs.getString(3) + "  ");
				System.out.print(rs.getString(4) + "  ");
				System.out.println(" ");

				/*
				 * carowner.setName(rs.getString(1));
				 * carowner.setCarno(rs.getString(2));
				 * carowner.setContact(rs.getString(3));
				 * carowner.setEmailid(rs.getString(4));
				 */
				// jsonObject.accumulate("contact", carowner.getContact());
				// jsonObject.accumulate("emailid",carowner.getEmailid());

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		carowner = new CarOwner();
		carowner.setName(name);
		carowner.setCarno(carno);
		carowner.setContact(contact);
		carowner.setEmailid(emailid);
		return carowner;

	}

	public static void insert(CarOwner carowner) {
		// Connection con=null;
		PreparedStatement pst = null;
		con = (Connection) DBConnection.GetConnection();
		// pst.addBatch();

		try {
			// Scanner sc = new Scanner(System.in);
			// pst.addBatch();
			pst = con
					.prepareStatement(
							"insert into owner_details values(?,?,?,?)",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// System.out.println("OWNER_NAME");
			// String s1 = sc.next();
			// System.out.println("CARNO");
			// String s2 = sc.next();
			// System.out.println("CONTACT NO");
			// String s3 = sc.next();
			// System.out.println("EMAILID");
			// String s4 = sc.next();
			// pst.setLong(1,s1);
			pst.setString(1, carowner.getName());
			pst.setString(2, carowner.getCarno());
			pst.setString(3, carowner.getContact());
			pst.setString(4, carowner.getEmailid());
			pst.executeUpdate();
			pst.addBatch();
			// pst.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// int row = pst.executeUpdate();
	}

	public static int carin() {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst;
		int x = 0;
		int y = 0;
		con = (Connection) DBConnection.GetConnection();
		try {
			st = (Statement) con.createStatement();
			String s1 = "select * from  car_availability";
			rs = st.executeQuery(s1);
			while (rs.next()) {
				// System.out.println("x="+x+"y="+y);
				x = rs.getInt(1);
				y = rs.getInt(2);
				if (y < x)
					y = y + 1;
				else
					System.out.println("SORRY! NO SPACE");
			}
			try {
				s1 = "delete from car_availability LIMIT 1";
				st.executeUpdate(s1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if(rs!=null)
			// rs.close();
			pst = con
					.prepareStatement(
							"insert into car_availability values(?,?)",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			JSONObject obj = new JSONObject();
			// System.out.println("OWNER_NAME");
			// String total_avals1 = sc.next();
			// System.out.println("x="+x+"y="+y);
			obj.put("total_aval", x);
			// System.out.println("CARNO");
			obj.put("parked_car", y);
			pst.setInt(1, (int) obj.get("total_aval"));
			pst.setInt(2, (int) obj.get("parked_car"));
			pst.executeUpdate();
			pst.addBatch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (x-y);

	}

	public static int carout() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst;
		int x = 0;
		int y = 0;
		con = (Connection) DBConnection.GetConnection();
		try {
			st = (Statement) con.createStatement();
			String s1 = "select * from  car_availability";
			rs = st.executeQuery(s1);
			while (rs.next()) {
				// System.out.println("x="+x+"y="+y);
				x = rs.getInt(1);
				y = rs.getInt(2);
				if (y >= 1)
					y = y - 1;
				else
					System.out.println("SORRY!.. NO CAR FOR EXIT..");
			}
			try {
				s1 = "delete from car_availability LIMIT 1";
				st.executeUpdate(s1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if(rs!=null)
			// rs.close();
			pst = con
					.prepareStatement(
							"insert into car_availability values(?,?)",
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			JSONObject obj = new JSONObject();
			// System.out.println("OWNER_NAME");
			// String total_avals1 = sc.next();
			// System.out.println("x="+x+"y="+y);
			obj.put("total_aval", x);
			// System.out.println("CARNO");
			obj.put("parked_car", y);
			pst.setInt(1, (int) obj.get("total_aval"));
			pst.setInt(2, (int) obj.get("parked_car"));
			pst.executeUpdate();
			pst.addBatch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (x-y);

	}

	public static int caravailability() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst;
		int x = 0;
		int y = 0;
		int p = 0;
		con = (Connection) DBConnection.GetConnection();
		try {
			st = (Statement) con.createStatement();
			String s1 = "select * from  car_availability";
			rs = st.executeQuery(s1);
			while (rs.next()) {
				// System.out.println("x="+x+"y="+y);
				// x=rs.getInt(1);
				x = rs.getInt(1);
				y = rs.getInt(2);
			    p = x - y;
				System.out.println("total availability=" + p);
				// y=y-1;
			}

			// if(rs!=null)
			// rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;

	}
}