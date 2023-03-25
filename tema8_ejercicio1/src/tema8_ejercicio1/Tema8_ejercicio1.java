package tema8_ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Tema8_ejercicio1 {

	public static void mostrarMenu() {
		System.out.println("1- Mostrar todo");
		System.out.println("2- Mostrar nombres mayores de edad");
		System.out.println("3- Mostrar edad según id");
		System.out.println("4- Insertar nueva persona");
		System.out.println("5- Borrar persona según id");
		System.out.println("6- Actualizar edad según id");
		System.out.println("7- Salir");
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int opcion = 0;
		int id = 0;

		String url = "jdbc:mysql://192.168.1.141:3306/prueba";
		String user = "alumno";
		String password = "alumno";

		try {
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement();

			do {
				mostrarMenu();
				opcion = scan.nextInt();
				switch (opcion) {
				case 1:
					ResultSet rs = stmt.executeQuery("SELECT * FROM persona");
					while (rs.next()) {
						int idper = rs.getInt("id");
						String nomper = rs.getString("nombre");
						int edadper = rs.getInt("edad");
						System.out.println("ID: " + idper + ", Nombre: " + nomper + ", Edad: " + edadper);
					}
					rs.close();
					break;
				case 2:

					PreparedStatement sel_pstmtMayorEdad = con.prepareStatement("SELECT * FROM persona WHERE edad>?");
					sel_pstmtMayorEdad.setInt(1, 17);
					ResultSet rs2 = sel_pstmtMayorEdad.executeQuery();

					while (rs2.next()) {
						int idper = rs2.getInt("id");
						String nomper = rs2.getString("nombre");
						int edadper = rs2.getInt("edad");
						System.out.println("ID: " + idper + ", Nombre: " + nomper + ", Edad: " + edadper);
					}
					rs2.close();
					sel_pstmtMayorEdad.close();

					break;
				case 3:
					PreparedStatement sel_pstmt = con.prepareStatement("SELECT * FROM persona WHERE id=?");
					System.out.print("Indique el id: ");
					id = scan.nextInt();
					sel_pstmt.setInt(1, id);
					ResultSet rs_sel = sel_pstmt.executeQuery();
					while (rs_sel.next()) {
						int idp = rs_sel.getInt("id");
						String nomp = rs_sel.getString("nombre");
						int edad = rs_sel.getInt("edad");
						System.out.println("ID: " + idp + ", Nombre: " + nomp + ", Precio: " + edad);
					}
					sel_pstmt.close();
					rs_sel.close();
					break;
				case 4:
					PreparedStatement ins_pstmt = con
							.prepareStatement("INSERT INTO persona (id, nombre, edad) VALUES (?, ?, ?)");
					String nombrep = "";
					int edadp = 0;
					System.out.print("Indique el id: ");
					id = scan.nextInt();
					ins_pstmt.setInt(1, id);
					System.out.print("Indique el nombre: ");
					nombrep = scan.next();
					ins_pstmt.setString(2, nombrep);
					System.out.print("Indique la edad: ");
					edadp = scan.nextInt();
					ins_pstmt.setInt(3, edadp);
					ins_pstmt.executeUpdate();
					ins_pstmt.close();
					break;
				case 5:
					PreparedStatement dele_pstmt = con.prepareStatement("DELETE FROM persona WHERE id = ?");
					System.out.print("Indique el id: ");
					id = scan.nextInt();
					dele_pstmt.setInt(1, id);
					dele_pstmt.executeUpdate();
					dele_pstmt.close();
					break;
				case 6:
					PreparedStatement upd_pstmt = con.prepareStatement("UPDATE persona SET edad = ? WHERE id = ?");
					System.out.print("Indique el id: ");
					id = scan.nextInt();
					upd_pstmt.setInt(2, id);
					System.out.print("Indique la edad: ");
					edadp = scan.nextInt();
					upd_pstmt.setInt(1, edadp);
					upd_pstmt.executeUpdate();
					upd_pstmt.close();
					break;
				case 7:
					System.out.println("Has salido.");
					break;
				default:
					System.out.println("Escoja una opción de las indicadas.");
				}
			} while (opcion != 7);

			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
