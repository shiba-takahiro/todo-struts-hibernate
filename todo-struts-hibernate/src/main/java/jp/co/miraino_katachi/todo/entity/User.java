package jp.co.miraino_katachi.todo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="FAMILY_NAME")
	private String familyName;

	@Column(name="FIRST_NAME")
	private String firstName;

	public User() {
		this.id = 0;
		this.familyName = null;
		this.firstName = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Transient
	public String getName() {
		return this.familyName + " " + this.firstName;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String name) {
		this.familyName = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
