package com.kf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Samuel
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "login")
public class Login implements Serializable {

	@Id
	@Column(name = "mail")
	private String mail;

	@Column(name = "password")
	private String password;

	@Column(name = "player")
	private int player;

	public Login() {
	}

	public Login(String mail, String password, int player) {
		super();
		this.mail = mail;
		this.password = password;
		this.player = player;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
}
