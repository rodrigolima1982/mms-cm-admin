package com.mms.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "campaign")
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false)
	private int senderShortNumber;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isEnableDLR;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isEnabledReadReceipt;

	private int numberOfUsers;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "campaign_client", joinColumns = { @JoinColumn(name = "campaign_id") }, inverseJoinColumns = {
			@JoinColumn(name = "client_id") })
	private Set<Client> clients;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Template template;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Operator operator;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(10) default 'ENABLED'")
	private EStatus status = EStatus.ENABLED;

	public Campaign() {
		super();
	}

	public Campaign(Long id, String name, int senderShortNumber, boolean isEnableDLR, boolean isEnabledReadReceipt, int numberOfUsers) {
		super();
		this.id = id;
		this.name = name;
		this.senderShortNumber = senderShortNumber;
		this.isEnableDLR = isEnableDLR;
		this.isEnabledReadReceipt = isEnabledReadReceipt;
		this.numberOfUsers = numberOfUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSenderShortNumber() {
		return senderShortNumber;
	}

	public void setSenderShortNumber(int sender) {
		this.senderShortNumber = sender;
	}

	public boolean isEnableDLR() {
		return isEnableDLR;
	}

	public void setEnableDLR(boolean isEnableDLR) {
		this.isEnableDLR = isEnableDLR;
	}

	public boolean isEnabledReadReceipt() {
		return isEnabledReadReceipt;
	}

	public void setEnabledReadReceipt(boolean isEnabledReadReceipt) {
		this.isEnabledReadReceipt = isEnabledReadReceipt;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

}
