package com.mms.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

	private LocalDate startDate;

	private LocalDate endDate;

	private int numberOfUsers;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "campaign_client", joinColumns = { @JoinColumn(name = "campaign_id") }, inverseJoinColumns = {
			@JoinColumn(name = "client_id") })
	private Set<Client> clients;

	@ManyToOne
	@NotNull
	private Template template;

	@ManyToOne
	@NotNull
	private Operator operator;

	public Campaign() {
		super();
	}

	public Campaign(Long id, String name, int senderShortNumber, boolean isEnableDLR, boolean isEnabledReadReceipt,
			LocalDate startDate, LocalDate endDate, int numberOfUsers) {
		super();
		this.id = id;
		this.name = name;
		this.senderShortNumber = senderShortNumber;
		this.isEnableDLR = isEnableDLR;
		this.isEnabledReadReceipt = isEnabledReadReceipt;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

}
