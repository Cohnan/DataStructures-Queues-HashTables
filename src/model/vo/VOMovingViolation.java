package model.vo;

import java.time.LocalDateTime;
import java.util.Comparator;

public class VOMovingViolation {
	/**
	 * Atributos de la infracci�n
	 */
	private String iD; 
	private String location;
	private String addressID;
	private int streetsegID;
	private double totalPaid;
	private double penalty1;
	private double penalty2;
	private boolean accidentIndicator;
	private LocalDateTime ticketIssueDate;
	private String violationCode;
	private String violationDesc;
	private int fineAmount;
	
	/*
	 * Constructor
	 */
	
	
	/*
	 * Metodos
	 */
	/**
	 * @return id - Identificador único de la infracción
	 */
	public String objectId() {
		return iD;
	}	

	/**
	 * @return location - Direcci�n en formato de texto.
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * @return addressID
	 */
	public String getAddressID() {
		return addressID;
	}
	
	/**
	 * @return streetsgeID
	 */
	public int getStreetsegID() {
		return streetsegID;
	}

	/**
	 * @return date - Fecha cuando se puso la infracción .
	 */
	public LocalDateTime getTicketIssueDate() {
		return ticketIssueDate;
	}

	/**
	 * @return totalPaid - Cuanto dinero efectivamente pag� el que recibi� la infracci�n en USD.
	 */
	public double getTotalPaid() {
		return totalPaid;
	}
	
	/**
	 * @return penalty1 - 
	 */
	public double getPenalty1() {
		return penalty1;
	}
	
	/**
	 * @return penalty2 - 
	 */
	public double getPenalty2() {
		return penalty2;
	}

	/**
	 * @return accidentIndicator - Si hubo un accidente o no.
	 */
	public boolean getAccidentIndicator() {
		return accidentIndicator;
	}

	/**
	 * @return description - Descripci�n textual de la infracción.
	 */
	public String  getViolationDescription() {
		return violationDesc;
	}

	public String  getViolationCode() {
		return violationCode;
	}

	public int getFineAmount() {
		return fineAmount;
	}
	
	
	/*
	 * Comparadores
	 */
	public static class AddressIDOrder implements Comparator<VOMovingViolation> {

		@Override
		public int compare(VOMovingViolation inf1, VOMovingViolation inf2) {
			return inf1.getAddressID().compareTo(inf2.getAddressID());
		}
	}
	
	public static class TicketIssueOrder implements Comparator<VOMovingViolation> {

		@Override
		public int compare(VOMovingViolation inf1, VOMovingViolation inf2) {
			
			return inf1.getTicketIssueDate().compareTo(inf2.getTicketIssueDate());
		}
	}
}
