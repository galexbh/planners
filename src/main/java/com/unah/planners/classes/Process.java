package com.unah.planners.classes;


import javafx.collections.FXCollections;
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
    
	public void timeWaitingFF() {
		System.out.println(this.timeStart);
		this.timeWaiting = ((this.timeStart==0)?this.timeArrival:this.timeStart-this.timeArrival);		}
	
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
	
	public void timeStartJSF(ObservableList<Process> obs) {

		ObservableList<Process> obs2 = FXCollections.observableArrayList();
		for( int i = 0 ; i < obs.size() ; i++ ) {
			obs2.add(new Process(obs.get(i).name,obs.get(i).quantun,obs.get(i).timeArrival,obs.get(i).timeService));
//			obs2.get(i).timeStart = 250;
		} 
		obs.clear();

		int[] timeCurrent = new int[1];
		int[] timeShorter = new int[1];
		int[] index = new int[1];
		timeCurrent[0] = 0;
		timeShorter[0] = 0;
		index[0] = 0;
		
		while (obs2.size() != 0){
			System.out.println("tama�o del observable : "+obs2.size());
			for( int i1 = 0 ; i1 < obs2.size() ; i1++ ) {
				if (obs2.get(i1).timeArrival == 0) {
					obs2.get(i1).timeStart = 0;
					System.out.println("primer if entro");
					System.out.println("Proceso \""+obs2.get(i1).name+ "\", se ejecuto en el tiempo : "+timeCurrent[0]);
					timeCurrent[0] = obs2.get(i1).timeService;	
					obs.add(obs2.get(i1));
//					obs2.remove(i1);
					continue;
				}
				System.out.println("probando si pasa o no");
				if (obs2.get(i1).timeArrival <= timeCurrent[0]) {
					
					if (obs2.get(i1).timeService < timeShorter[0]) {
						timeShorter[0] = obs2.get(i1).timeService; 
						index[0] = i1;
						continue;
					}
					System.out.println("su tiempo no es menor que el de tiempo m�s corto actualmente ");
					if (timeShorter[0] == 0 && obs2.get(i1).timeService >= 0 ) {
						timeShorter[0] = obs2.get(i1).timeService; 
						index[0] = i1;
						continue;
					}
					System.out.println("su ya llego pero asaber que pedos");
					
				} else {
					System.out.println("su tiempo aun no ha llegado");
				}
			}
			
			System.out.println("Proceso \""+obs2.get(index[0]).name+ "\", se ejecuto en el tiempo : "+timeCurrent[0]+"al final xd");
			obs2.get(index[0]).timeStart = timeCurrent[0];
			timeCurrent[0] += obs2.get(index[0]).timeService; 
			obs.add(obs2.get(index[0]));
			obs2.remove(index[0]);
			timeShorter[0] = 0; 
			index[0] = 0;
		}
		

	}


	@Override
	public String toString() {
		return "Process [name=" + name + ", quantun=" + quantun + ", estado=" + estado + ", timeArrival=" + timeArrival
				+ ", timeService=" + timeService + ", timeStay=" + timeStay + ", timeWaiting=" + timeWaiting
				+ ", timeStart=" + timeStart + ", timeFinalize=" + timeFinalize + ", timeNormalized=" + timeNormalized
				+ "]";
	}
	


}
