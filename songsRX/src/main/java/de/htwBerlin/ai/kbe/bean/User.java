package de.htwBerlin.ai.kbe.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")  //die Verknüpfung mit der entsprechenden DB-Tabelle
public class User {

	@Id 				// kennzeichnet das Identitätsattribut entspricht dem PK (primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // bedeutet, dass der PK automatisch durch die DB vergeben wird
	private int id;

	@Column(name = "user_id") // die Verknüpfung mit der entsprechenden Spalte
	private String userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	// hier brauchen wir kein @Column, da das Attribut den gleichen Namen wie die Spalte hat
	private String email;

	public User() {
	}

	public User(String userId, String firstName, String lastName, String email) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String setFirstName(String firstName) {
		return this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}
}
