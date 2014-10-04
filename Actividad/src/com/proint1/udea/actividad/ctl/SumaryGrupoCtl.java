package com.proint1.udea.actividad.ctl;
/**
Hola esta es una prueba de un commit con git =S...
*/
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.proint1.udea.actividad.ngc.OperacionesSumaryGruposInterfaceDAO;
import com.proint1.udea.administracion.dto.SumaryGruposDTO;
import com.proint1.udea.administracion.entidades.terceros.Docente;



/**
 * Sumario de grupos asignados a un {@link Docente} logueado
 * @author Juan Cardona
 * @since 23/06/2014
 */
public class SumaryGrupoCtl extends GenericForwardComposer implements ListitemRenderer<Object> {

	/**serialVersionUID **/
	private static final long serialVersionUID = -7024470034429612586L;
	private static Logger logger=Logger.getLogger(SumaryGrupoCtl.class);
	
	/** Docente activo*/
	private Docente docenteActivo;
	
	
	/**
	 * dao de operaciones
	 */
	@WireVariable
	OperacionesSumaryGruposInterfaceDAO sumaryGrupoInt;
	@Wire
	Listbox listaGrupos;
	
	/** Nro de registros*/
	private int nroRegistros = 0;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		definirModelo();
	}
	
	
	/**
	 * Constructor
	 */
	public SumaryGrupoCtl() {
		super();
		//capturamos los par�metros enviados
		docenteActivo = (Docente) Executions.getCurrent().getArg().get("docente");
	}


	/**
	 * @param separator
	 */
	public SumaryGrupoCtl(char separator) {
		super(separator);
	}

	public void onCreate() {
		definirModelo();
	}


	/**
	 * Define los datos de la lista
	 */
	private void definirModelo() {
		List<SumaryGruposDTO> listaSumaryGrupo = sumaryGrupoInt.getSumariGrupoDTOPorDocenteIdn(getDocenteActivo().getIdn());
		ListModel<SumaryGruposDTO> model = new ListModelList<SumaryGruposDTO>(listaSumaryGrupo);
		listaGrupos.setModel(model);
		nroRegistros=0;
		listaGrupos.setItemRenderer(this);
	}

	@Override
	/**
	 * Evento que alimenta la lsista
	 */
	public void render(Listitem lista, Object arg1, int arg2) throws Exception {
		SumaryGruposDTO dto = (SumaryGruposDTO)arg1;
		nroRegistros++;
		Listcell lc0 = new Listcell(String.valueOf(nroRegistros));
		Listcell lcDependencia = new Listcell(dto.getNombreDependencia());
		Listcell lcSemestre = new Listcell(dto.getSemestre());
		Listcell lcCodCurso = new Listcell(dto.getCodigoCurso());
		Listcell lcNomCurso = new Listcell(dto.getNombreCurso());
		Listcell lcModCurso = new Listcell(dto.getModalidadCurso());
		Listcell lcnGruponro = new Listcell(dto.getGrupoNumero());
		Listcell lcnHorario = new Listcell(dto.getHorario());
		Listcell lcnTotalTiempo = new Listcell(dto.getTotalTiempoHoras().toString());		
		//Se llena la lista con las celdas anteriores
		lista.appendChild(lc0);
		lista.appendChild(lcDependencia);
		lista.appendChild(lcSemestre);
		lista.appendChild(lcCodCurso);
		lista.appendChild(lcNomCurso);
		lista.appendChild(lcModCurso);
		lista.appendChild(lcnGruponro);
		lista.appendChild(lcnHorario);
		lista.appendChild(lcnTotalTiempo);
	}

	public void onClick$btnCrear(Event ev) {
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/administracion/vista/crearPersona.zul") ;
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		try {
			Component c = Executions.createComponentsDirectly(zulReader,"zul",null,new HashMap<String, Object>()) ;
			Window win = (Window)c;
			win.doModal();
			System.out.println("despues del do");
			definirModelo();
		} catch (IOException e) {
			logger.error("ERROR",e);
		}		
	}

	
	@Command
	public void  actualizarLink(){
		Messagebox.show("Autenticaci�n exitosa, favor seleccionar nuevamente la opci�n requerida","Validaci�n exitosa",Messagebox.OK,Messagebox.INFORMATION);
		definirModelo();
	}
	
	@Command
	public void  registrarTiemposLink(){
		Messagebox.show("Autenticaci�n exitosa, favor seleccionar nuevamente la opci�n requerida","Validaci�n exitosa",Messagebox.OK,Messagebox.INFORMATION);
	}

	/**
	 * @return the sumaryGrupoInt
	 */
	public OperacionesSumaryGruposInterfaceDAO getSumaryGrupoInt() {
		return sumaryGrupoInt;
	}

	/**
	 * @param sumaryGrupoInt the sumaryGrupoInt to set
	 */
	public void setSumaryGrupoInt(OperacionesSumaryGruposInterfaceDAO sumaryGrupoInt) {
		this.sumaryGrupoInt = sumaryGrupoInt;
	}

	/**
	 * @return the docenteActivo
	 */
	public Docente getDocenteActivo() {
		return docenteActivo;
	}

	/**
	 * @param docenteActivo the docenteActivo to set
	 */
	public void setDocenteActivo(Docente docenteActivo) {
		this.docenteActivo = docenteActivo;
	}

}
