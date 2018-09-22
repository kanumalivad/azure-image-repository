package com.kanu.azureimgrepo.service;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.kanu.azureimgrepo.config.DatabaseConfig;

@Service
public class DatabaseService {
	
	public static void insertLink(String link)  {
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(DatabaseConfig.DB_CONNECTION_STRING);
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO img_links (link) VALUES (?);");
			preparedStatement.setString(1, link);
			preparedStatement.executeUpdate();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("error : "+e);
		}
	}
	public static ArrayList<String> getLinks(String keyword){
		ArrayList<String> list=new ArrayList();
		try
		{
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(DatabaseConfig.DB_CONNECTION_STRING);
			Statement st = con.createStatement();
			String sql = "SELECT * FROM img_links WHERE link LIKE '%"+keyword+"%';";
		    ResultSet rs = st.executeQuery(sql);
		    while(rs.next()){
		    	list.add(rs.getString("link"));
		    }
			
			con.close();
			
		}
		catch(Exception e)
		{
			System.out.println("error : "+e);
		}
		return list;
	}
}
