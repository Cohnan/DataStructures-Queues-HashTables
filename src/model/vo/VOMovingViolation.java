package model.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import com.google.gson.Gson;

public class VOMovingViolation {
	/**
	 * Atributos de la infracci�n
	 */
/*
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
*/
	
	private String OBJECTID;
	private String ROW_;
	private String LOCATION;
	private String ADDRESS_ID;
	private String STREETSEGID;
	private String XCOORD;
	private String YCOORD;
	private String TICKETTYPE;
	private String FINEAMT;
	private String TOTALPAID;
	private String PENALTY1;
	private String PENALTY2;
	private String ACCIDENTINDICATOR;
	private String AGENCYID;
	private String TICKETISSUEDATE;
	private String VIOLATIONCODE;
	private String VIOLATIONDESC;	
	private String ROW_ID;
	
	
	/*
	 * Constructor
	 */
/*	public VOMovingViolation(String hello, String LOCATION) {
		this.OBJECTID = hello;
		this.LOCATION = LOCATION;
	}
*/	
	
	/*
	 * Metodos
	 */
	/**
	 * @return id - Identificador único de la infracción
	 */
	public String objectId() {
		return OBJECTID;
	}	

	/**
	 * @return location - Direcci�n en formato de texto.
	 */
	public String getLocation() {
		return LOCATION;
	}
	
	/**
	 * @return addressID
	 */
	public int getAddressID() {
		return Integer.parseInt(ADDRESS_ID);
	}
	
	/**
	 * @return streetsgeID
	 */
	public int getStreetsegID() {
		return Integer.parseInt(STREETSEGID);
	}

	/**
	 * @return date - Fecha cuando se puso la infracción .
	 */
	public LocalDateTime getTicketIssueDate() {
		return LocalDateTime.parse(TICKETISSUEDATE, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.000Z'"));
	}

	/**
	 * @return totalPaid - Cuanto dinero efectivamente pag� el que recibi� la infracci�n en USD.
	 */
	public double getTotalPaid() {
		return Double.parseDouble(TOTALPAID);
	}
	
	/**
	 * @return penalty1 - 
	 */
	public double getPenalty1() {
		return Double.parseDouble(PENALTY1);
	}
	
	/**
	 * @return penalty2 - 
	 */
	public double getPenalty2() {
		return Double.parseDouble(PENALTY2);
	}

	/**
	 * @return accidentIndicator - Si hubo un accidente o no.
	 */
	public boolean getAccidentIndicator() {
		return ACCIDENTINDICATOR.equalsIgnoreCase("Yes");
	}

	/**
	 * @return description - Descripci�n textual de la infracción.
	 */
	public String  getViolationDescription() {
		return VIOLATIONDESC;
	}

	public String  getViolationCode() {
		return VIOLATIONCODE;
	}

	public int getFineAmount() {
		return Integer.parseInt(FINEAMT);
	}
	
	
	/*
	 * Comparadores
	 */
	public static class AddressIDOrder implements Comparator<VOMovingViolation> {

		@Override
		public int compare(VOMovingViolation inf1, VOMovingViolation inf2) {
			return inf1.getAddressID() - inf2.getAddressID();
		}
	}
	
	public static class TicketIssueOrder implements Comparator<VOMovingViolation> {

		@Override
		public int compare(VOMovingViolation inf1, VOMovingViolation inf2) {
			
			return inf1.getTicketIssueDate().compareTo(inf2.getTicketIssueDate());
		}
	}
	
	
	
	
	public int hashCode(){
		return getAddressID();
	}
	
	public static void main(String[] args) {
		String json = "      {\n" + 
				"		\"OBJECTID\": 14476381,\n" + 
				"		\"ROW_\": null,\n" + 
				"		\"LOCATION\": \"1400 BLK S CAPITOL ST SE N/B\",\n" + 
				"		\"ADDRESS_ID\": 277954,\n" + 
				"		\"STREETSEGID\": null,\n" + 
				"		\"XCOORD\": 399257.7,\n" + 
				"		\"YCOORD\": 133847.29,\n" + 
				"		\"TICKETTYPE\": \"Moving\",\n" + 
				"		\"FINEAMT\": 300,\n" + 
				"		\"TOTALPAID\": 0,\n" + 
				"		\"PENALTY1\": 300,\n" + 
				"		\"PENALTY2\": null,\n" + 
				"		\"ACCIDENTINDICATOR\": \"Yes\",\n" + 
				"		\"TICKETISSUEDATE\": \"2018-04-01T11:59:00.000Z\",\n" + 
				"		\"VIOLATIONCODE\": \"T122\",\n" + 
				"		\"VIOLATIONDESC\": \"SPEED 26-30 MPH OVER THE SPEED LIMIT\",\n" + 
				"		\"ROW_ID\": null\n" + 
				"	}";

		Gson gson = new Gson();

		VOMovingViolation car = gson.fromJson(json, VOMovingViolation.class);
		
		System.out.println(car.OBJECTID);
		System.out.println(car.LOCATION);
		if(car.getAccidentIndicator()) {
			System.out.println("Detecta el accidente");
		} else {
			System.out.println("NO detecta el accidente");
		}
	}
}
