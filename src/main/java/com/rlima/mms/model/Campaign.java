package com.rlima.mms.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "campaign")
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String name;

	@NotBlank
	private int senderShortNumber;

	private boolean isEnableDLR;

	private boolean isEnabledReadReceipt;

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "campaign_client", joinColumns = { @JoinColumn(name = "campaign_id") }, inverseJoinColumns = {
			@JoinColumn(name = "client_id") })
	private Set<Client> clients;

	@ManyToOne
	private Template template;
	
	@ManyToOne
	private Operator operator;

	public Campaign() {
		super();
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
	
}
