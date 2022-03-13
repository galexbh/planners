package classes;

import javafx.collections.ObservableList;

public class Process {

	private String name;
	private Integer quantun;
	private String estado;
    private Integer timeArrival;
    private Integer timeService;
    private Integer timeStay;
    private Integer timeWaiting;
    private Integer timeStart;
    private Integer timeFinalize;
    private Float timeNormalized;
    
    public Process(String name,
    				Integer quantun,
    				Integer timeArrival,
    				Integer timeService
    			){
                this.name = name;
                this.quantun = quantun;
                this.timeArrival = timeArrival;
                this.timeService = timeService;
            }
    
//    public float timeFinalize() {return this.timeFinalize = this.timeStart + this.timeService;}
    
//    public Integer timeStay() {return this.timeStay = this.timeWaiting+ this.timeService;}

    public void timeStayFF() {this.timeStay = (this.timeFinalize-this.timeArrival);}
    
	public void timeWaitingFF() {this.timeWaiting = ((this.timeStart==0)?this.timeArrival:this.timeStart-this.timeArrival);		}
	
	public void timeFinalizeFF() {this.timeFinalize = (this.timeStart + this.timeService);}
	
	public void timeNormalizedFF() {this.timeNormalized = ((float)(this.timeWaiting)/(float)(this.timeService));}
	
	public void timeStartFF(ObservableList<Process> obs) {
		int[] acumulador = new int[1];
		acumulador[0] = 0;
		for(Process proceso: obs) {
			if(proceso.name == this.name) {
				break;
			}
			if(proceso.name == obs.get(obs.size()-1).name) {
				break;
			}
			acumulador[0] += proceso.timeService;		
		}
		this.timeStart = acumulador[0];

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantun() {
		return quantun;
	}

	public void setQuantun(Integer quantun) {
		this.quantun = quantun;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getTimeArrival() {
		return timeArrival;
	}

	public void setTimeArrival(Integer timeArrival) {
		this.timeArrival = timeArrival;
	}

	public Integer getTimeService() {
		return timeService;
	}

	public void setTimeService(Integer timeService) {
		this.timeService = timeService;
	}

	public Integer getTimeStay() {
		return timeStay;
	}

	public void setTimeStay(Integer timeStay) {
		this.timeStay = timeStay;
	}

	public Integer getTimeWaiting() {
		return timeWaiting;
	}

	public void setTimeWaiting(Integer timeWaiting) {
		this.timeWaiting = timeWaiting;
	}

	public Integer getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Integer timeStart) {
		this.timeStart = timeStart;
	}

	public Integer getTimeFinalize() {
		return timeFinalize;
	}

	public void setTimeFinalize(Integer timeFinalize) {
		this.timeFinalize = timeFinalize;
	}

	public Float getTimeNormalized() {
		return timeNormalized;
	}

	public void setTimeNormalized(Float timeNormalized) {
		this.timeNormalized = timeNormalized;
	}
	
	


}
