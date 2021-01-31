package jp.co.miraino_katachi.todo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="todo_items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="item_name")
	private String name;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;

	@Column(name="expire_date")
	private Date expireDate;

	@Column(name="finished_date")
	private Date finishedDate;

	public Item() {
		this.id = 0;
		this.name = null;
		this.user = null;
		this.expireDate = null;
		this.finishedDate = null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date date) {
		this.expireDate = date;
	}

	public boolean isExpired() {
		if (this.expireDate.compareTo(new Date()) < 0) {
			return true;
		} else {
			return false;
		}
	}

	public Date getFinishedDate() {
		return this.finishedDate;
	}

	public void setFinishedDate(Date date) {
		this.finishedDate = date;
	}

	public boolean isFinished() {
		if (finishedDate != null) {
			return true;
		} else {
			return false;
		}
	}

	public void setFinished(boolean finished) {
		if (finished) {
			this.finishedDate = new Date();
		} else {
			this.finishedDate = null;
		}
	}
}
