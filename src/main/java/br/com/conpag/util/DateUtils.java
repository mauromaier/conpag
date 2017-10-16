package br.com.conpag.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.conpag.controller.sistema.LogController;


@Singleton
public class DateUtils {
	
	private SimpleDateFormat sdfData;
	private SimpleDateFormat sdfDataSoNumeros;
	private SimpleDateFormat sdfHora;
	
	private Calendar cal = Calendar.getInstance();
	
	private DateUtils() {
		this.sdfData = new SimpleDateFormat("dd/MM/yyyy");
		this.sdfHora = new SimpleDateFormat("HH:mm");
		this.sdfDataSoNumeros = new SimpleDateFormat ("ddMMyyyy");
	}
	
	@Inject
	private LogController logController;
	
	//--Métodos para DATA----------------------------------------------//
	public String getPeriodo( Date inicio, Date fim ){
		if ( inicio == null || fim == null)
			return "";
		String inicioStr = new SimpleDateFormat("dd/MM/yyyy").format( inicio );
		String fimStr = new SimpleDateFormat("dd/MM/yyyy").format( fim );
		String periodo = "Período: ";
		
		if ( inicioStr.equalsIgnoreCase(fimStr) ){
			periodo = periodo + inicioStr;
		}
		else {
			periodo = periodo + "de " + inicioStr + " a " + fimStr;
		}
		return periodo;
	}
	
	public Date parseToDate( String data ){
		if ( data == null ){
			return null;
		}
		try{
			data = data.replace("/", "");
			Date retorno = sdfDataSoNumeros.parse( data );
			return retorno;
		}
		catch ( Exception e ){
			logController.printLog("[DateUtils] Impossivel o parse da data: " + data, Log.AVISO );
		}
		return null;
	}
	
	public Calendar parseToCalendar( String data ){
		if ( data == null ){
			return null;
		}
		try{
			data = data.replace("/", "");
			Date retorno = sdfDataSoNumeros.parse( data );
			Calendar c = Calendar.getInstance();
			c.setTime(retorno);
			return c;
		}
		catch ( Exception e ){
			logController.printLog("[DateUtils] Impossivel o parse da data: " + data, Log.AVISO );
		}
		return null;
	}
	
	
		
	/**
	 * Retorna o Dia do mês
	 * 
	 * @param
	 * data = Data em "Date", para extrair os dados
	 * @return
	 * dia = Dia do mês
	 */
	public int getDia(Date data) {
		cal.setTime(data);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * Retorna o Mês do ano
	 * 
	 * @param
	 * data = Data em "Date", para extrair os dados
	 * @return
	 * mes = Mês do ano
	 */
	public int getMes(Date data) {
		cal.setTime(data);
		return cal.get(Calendar.MONTH)+1;
	}
	/**
	 * Retorna o Ano
	 * 
	 * @param
	 * dataInicio = Data em "Date", para extrair os dados
	 * @return
	 * ano = Ano
	 */
	public int getAno(Date dataInicio) {
		cal.setTime(dataInicio);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Retorna o Dia do mês
	 * 
	 * @param
	 * data = Data em "String" (dd/MM/yyyy), para extrair os dados
	 * @return
	 * dia = Dia do mês
	 */
	public int getDia(String data) {
		return this.getDia(this.stringToDate(data));
	}
	/**
	 * Retorna o Mês do ano
	 * 
	 * @param
	 * data = Data em "String" (dd/MM/yyyy), para extrair os dados
	 * @return
	 * mes = Mês do ano
	 */
	public int getMes(String data) {
		return this.getMes(this.stringToDate(data));
	}
	/**
	 * Retorna o Ano
	 * 
	 * @param
	 * data = Data em "String" (dd/MM/yyyy), para extrair os dados
	 * @return
	 * ano = Ano
	 */
	public int getAno(String data) {
		return this.getAno(this.stringToDate(data));
	}

	/**
	 * Retorna o Dia Atual do mês
	 * 
	 * @return
	 * diaAtual = Dia do mês
	 */
	public int getDiaAtual() {
		return this.getDia(new Date());
	}
	/**
	 * Retorna o Mês Atual do ano
	 * 
	 * @return
	 * mesAtual = Mês do ano
	 */
	public int getMesAtual() {
		return this.getMes(new Date());
	}
	/**
	 * Retorna o Ano Atual
	 * 
	 * @return
	 * anoAtual = Ano atual
	 */
	public int getAnoAtual() {
		return this.getAno(new Date());
	}	
	
	/**
	 * Retorna a data completa (dd/MM/yyyy) do Dia Seguinte ao atual
	 * 
	 * @return
	 * dataDiaSeguinte = Data com o dia seguinte à data atual no formato dd/MM/yyyy
	 */
	public Date getDateDiaSeguinte() {
		int dia = getDiaSeguinte();
		int mes = getMesAtual();
		int ano = getAnoAtual();
		if (dia==1) {
			mes = getMesSeguinte();
			if (mes==1)
				ano = getAnoSeguinte();
		}
		cal.set(ano,mes-1,dia);
		return cal.getTime();
	}
	/**
	 * Retorna a data completa (dd/MM/yyyy) do Mês Seguinte ao atual
	 * 
	 * @return
	 * dataMesSeguinte = Data com o Mês seguinte à data atual no formato dd/MM/yyyy
	 */
	public Date getDateMesSeguinte() {
		int ano = getAnoAtual();
		int mes = getMesSeguinte();
		if (mes==1)
			ano = getAnoSeguinte();
		cal.set(Calendar.MONTH, mes-1);
		cal.set(Calendar.YEAR,ano);
		return cal.getTime();
	}
	/**
	 * Retorna a data completa (dd/MM/yyyy) do Ano Seguinte ao atual
	 * 
	 * @return
	 * dataAnoSeguinte = Data com o Ano seguinte à data atual no formato dd/MM/yyyy
	 */
	public Date getDateAnoSeguinte() {
		int ano = getAnoSeguinte();
		cal.set(Calendar.YEAR,ano);
		return cal.getTime();
	}
	
	/**
	 * Retorna o Dia Seguinte ao atual
	 * 
	 * @return
	 * diaSeguinte = Dia do mês seguinte ao dia atual  (se o dia atual for
	 * o último do mês, retorna 1)
	 */
	public int getDiaSeguinte() {
		cal.setTime(new Date());
		cal.roll(Calendar.DAY_OF_MONTH, true);
		return this.getDia(cal.getTime());
	}

	/**
	 * Retorna o Mês Seguinte ao atual
	 * 
	 * @return
	 * mesSeguinte = Mês seguinte ao mês atual (se o mês atual for
	 * o último do ano, retorna 1)
	 */
	public int getMesSeguinte() {
		return getMesSeguinte(new Date());
	}
	/**
	 * Retorna o Mês Seguinte à data informada
	 * 
	 * @return
	 * mesSeguinte = Mês seguinte ao mês informado (se o mês informado for
	 * o último do ano, retorna 1)
	 */
	public int getMesSeguinte(Date data) {
		cal.setTime(data);
		cal.roll(Calendar.MONTH, true);
		return this.getMes(cal.getTime());
	}

	/**
	 * Retorna o Ano Seguinte ao atual
	 * 
	 * @return
	 * anoSeguinte = Ano seguinte ao ano atual
	 */
	public int getAnoSeguinte() {
		cal.setTime(new Date());
		cal.roll(Calendar.YEAR, true);
		return this.getAno(cal.getTime());
	}
	
	/**
	 * Retorna a Data Atual em "Date"
	 * 
	 * @return
	 * dataAtual = Data atual
	 */
	public Date getDateAtual() {
		return new Date();
	}
	
	/** Retorna uma data completa qualquer a partir de dia e mes para o ano corrente.
	 * @param dia
	 * @param mes
	 * @return
	 * dataQualquer = Data qualquer
	 */
	public Date getDateQualquer(int dia, int mes) {
		cal.set(getAnoAtual(), mes, dia);
		return cal.getTime();
	}
	
	/**
	 * Retorna a Data Atual em "String"
	 * 
	 * @return
	 * dataAtual = Data atual no formato "dd/MM/yyyy"
	 */
	public String getDataAtual() {
		return this.dateToString(new Date());
	}
	
	/**
	 * Recupera um número (dia ou mês) "XX".
	 * 
	 * Ex: Entrada: 7 -> Saída: 07
	 * 
	 * @param
	 * numero = Número do dia/mês
	 * @return
	 * numeroXX = Número no formato "XX"
	 */
	public String getNumeroXX(int numero) {
		if (numero<10)
			return "0"+numero;
		else
			return String.valueOf(numero);
	}
	
	/**
	 * Recupera o Dia de uma data em "Date" no formado "dd" 
	 * 
	 * @param
	 * data = Data em "Date"
	 * @return
	 * diaDD = Dia no formato "dd"
	 */
	public String getDiaDD(Date data) {
		int dia = this.getDia(data);
		return getNumeroXX(dia);
	}
	/**
	 * Recupera o Mês de uma data em "Date" no formado "MM" 
	 * 
	 * @param
	 * data = Data em "Date"
	 * @return
	 * mesMM = Mês no formato "MM"
	 */
	public String getMesMM(Date data) {
		int mes = this.getMes(data);
		return getNumeroXX(mes);
	}

	/**
	 * Recupera o Dia Atual no formado "dd" 
	 * 
	 * @return
	 * diaDD = Dia no formato "dd"
	 */
	public String getDiaAtualDD() {
		return this.getDiaDD(new Date());
	}
	/**
	 * Recupera o Mês Atual no formado "MM" 
	 * 
	 * @return
	 * mesMM = Mês no formato "MM"
	 */
	public String getMesAtualMM() {
		return this.getMesMM(new Date());
	}
	
	/**
	 * Recupera o Dia de uma data em "String" no formado "dd" 
	 * 
	 * @param
	 * data = Data em "String" (dd/MM/yyyy)
	 * @return
	 * diaDD = Dia no formato "dd"
	 */
	public String getDiaDD(String data) {
		return this.getDiaDD(this.stringToDate(data));
	}
	/**
	 * Recupera o Mês de uma data em "String" no formado "MM" 
	 * 
	 * @param
	 * data = Data em "String" (dd/MM/yyyy)
	 * @return
	 * mesMM = Mês no formato "MM"
	 */
	public String getMesMM(String data) {
		return this.getMesMM(this.stringToDate(data));
	}
	
	/**
	 * Converte uma data "String" no formado "dd/MM/yyyy" para "Date" 
	 * 
	 * @param
	 * data = Data em "String" no formato "dd/MM/yyyy"
	 * @return
	 * dataDate = Data convertida em "Date"
	 */
	public Date stringToDate(String data) {
		try {
			return this.sdfData.parse( data );
		} catch (ParseException e) {
			logController.printLog("Erro ao converter " + data + "para Date");
		}
		return null;
	}
	

	
	/**
	 * Converte uma data "Date" para "String" no formado "dd/MM/yyyy" 
	 *
	 * @param
	 * dataDate = Data em "Date"
	 * @return
	 * dataString = Data convertida em "String" (dd/MM/yyyy)
	 */
	public String dateToString(Date data) {
		return this.sdfData.format(data);
	}
	public String dateToString(Calendar data) {
		return this.sdfData.format( data.getTime() );
	}
	
	
	/**
	 * Retorna o nome do mês por extenso 
	 * 
	 * @param
	 * mes = Mês do ano
	 * @return
	 * mesExtenso = Nome do mês por extenso
	 */
	public String getMesExtenso(int mes) {
		switch (mes) {
			case 1: return "Janeiro";
			case 2: return "Fevereiro";
			case 3: return "Março";
			case 4: return "Abril";
			case 5: return "Maio";
			case 6: return "Junho";
			case 7: return "Julho";
			case 8: return "Agosto";
			case 9: return "Setembro";
			case 10: return "Outubro";
			case 11: return "Novembro";
			case 12: return "Dezembro";
			default: return "Mês Inválido: "+mes;
		}
	}
	
	/**
	 * Retorna o nome da semana por extenso 
	 * 
	 * @param
	 * semana = Dia da semana
	 * @return
	 * semanaExtenso = Nome do semana por extenso
	 */
	public String getSemanaExtenso( int semana ) {
		switch (semana) {
			case 1: return "Domingo";
			case 2: return "Segunda";
			case 3: return "Terça";
			case 4: return "Quarta";
			case 5: return "Quinta";
			case 6: return "Sexta";
			case 7: return "Sábado";
			default: return "Semana Inválida: "+semana;
		}
	}
	
	/**
	 * Retorna o nome do mês por extenso 
	 * 
	 * @param
	 * mes = Mês do ano
	 * @return
	 * mesExtenso = Nome do mês por extenso
	 */
	public String getMesExtensoComNumero(int mes) {
		switch (mes) {
			case 1: return "01\nJaneiro";
			case 2: return "02\nFevereiro";
			case 3: return "03\nMarço";
			case 4: return "04\nAbril";
			case 5: return "05\nMaio";
			case 6: return "06\nJunho";
			case 7: return "07\nJulho";
			case 8: return "08\nAgosto";
			case 9: return "09\nSetembro";
			case 10: return "10\nOutubro";
			case 11: return "11\nNovembro";
			case 12: return "12\nDezembro";
			default: return "Mês Inválido: "+mes;
		}
	}
	
	
	/**
	 * Retorna o nome do mês por extenso 
	 * 
	 * @param
	 * mes = Mês do ano
	 * @return
	 * mesExtenso = Nome do mês por extenso
	 */
	public String getMesExtensoComNumeroSemQuebra(int mes) {
		switch (mes) {
			case 1: return "01 Janeiro";
			case 2: return "02 Fevereiro";
			case 3: return "03 Março";
			case 4: return "04 Abril";
			case 5: return "05 Maio";
			case 6: return "06 Junho";
			case 7: return "07 Julho";
			case 8: return "08 Agosto";
			case 9: return "09 Setembro";
			case 10: return "10 Outubro";
			case 11: return "11 Novembro";
			case 12: return "12 Dezembro";
			default: return "Mês Inválido: "+mes;
		}
	}
	
	
	/**
	 * Retorna uma abreviação do nome do mês mais o numero referente a este mês
	 * 
	 * @param
	 * mes = Mês do ano
	 * @return
	 * mesExtenso = Nome do mês por extenso
	 */
	public String getMesShortExtensoComNumero(int mes) {
		switch (mes) {
			case 1: return "01\nJAN";
			case 2: return "02\nFEV";
			case 3: return "03\nMAR";
			case 4: return "04\nABR";
			case 5: return "05\nMAI";
			case 6: return "06\nJUN";
			case 7: return "07\nJUL";
			case 8: return "08\nAGO";
			case 9: return "09\nSET";
			case 10: return "10\nOUT";
			case 11: return "11\nNOV";
			case 12: return "12\nDEZ";
			default: return "Mês Inválido: "+mes;
		}
	}
	
	/**
	 * Retorna uma abreviação do nome do mês 
	 * 
	 * @param
	 * mes = Mês do ano
	 * @return
	 * mesShort = Nome do mês por extenso
	 */
	public String getMesShort(int mes) {
		switch (mes) {
			case 1: return "JAN";
			case 2: return "FEV";
			case 3: return "MAR";
			case 4: return "ABR";
			case 5: return "MAI";
			case 6: return "JUN";
			case 7: return "JUL";
			case 8: return "AGO";
			case 9: return "SET";
			case 10: return "OUT";
			case 11: return "NOV";
			case 12: return "DEZ";
			default: return "Mês Inválido: "+mes;
		}
	}
	
	
	/**
	 * Retorna o número do mês no ano 
	 * 
	 * @param
	 * mesExtenso = Mês do ano por extenso
	 * @return
	 * mes = Número do mês no ano
	 */
	public int getMesNumero(String mesExtenso) {
		String[] meses = {"janeiro","fevereiro","março","abril","maio","junho",
				"julho","agosto","setembro","outubro","novembro","dezembro"};
		boolean igual;
		int i = 0;
		do {
			igual = meses[i].equals(mesExtenso.toLowerCase());
			i++;
		} while(i<meses.length && igual==false);
		if (igual)
			return i;
		else
			return -1;
	}
	
	public String anoToString(Date data) {
		String ano = String.valueOf(this.getAno(data));
		return ano;
	}

	/**
	 * Verifica se a Data está no formato "dd/MM/yyyy" 
	 * 
	 * @param
	 * data = Data em "String"
	 * @return
	 * true = Se a data estiver no formato "dd/MM/yyyy"
	 * @return
	 * false = Se a data estiver em outro formato
	 */
	public boolean isFormatoValido(String data) {
		try {
			stringToDate(data);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Retorna a diferença em Dias entre duas Datas 
	 * 
	 * @param
	 * dataInicio = Data inicial do período de comparação
	 * @param
	 * dataFim = Data final do período de comparação
	 * 
	 * @return
	 * dias = Diferença em dias entre as duas Datas 
	 */
	public int getDiferencaDatas(Date dataInicio, Date dataFim) {
		long dias = dataFim.getTime()-dataInicio.getTime();
		dias /= (24*60*60*1000);
		return (int)dias;
	}
	
	
	/**
	 * Retorna uma data X dias depois de uma determinada Data 
	 * 
	 * @param
	 * dataInicio = Data de partida
	 * @param
	 * dias = Dias a serem somados a data de partida
	 * 
	 * @return
	 * dataFinal = Data final após o número de dias 
	 */
	public Date getDataFinal(Date dataInicio, int dias) {
		long dataFinal = dataInicio.getTime()+(dias*(24*60*60*1000));
		return new Date(dataFinal);
	}
	
	/**
	 * Retorna a quantidade de semanas do mes atual
	 * 
	 * @return
	 * qtdeSemana = Quantidade de dias do mes atual
	 */
	public int getQuantidadeSemanas(){
		
		Calendar c = Calendar.getInstance();  
		int mesAtual = c.get(Calendar.MONDAY);
		
		c.set(Calendar.MONTH,  mesAtual ); 
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
		
		int qtdeSemana = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);  
		return qtdeSemana;
	}
	

	//--Métodos para HORA----------------------------------------------//

	/* Retorna a Hora do dia
	 * 
	 * @param
	 * data = Data em "Date", para extrair os dados
	 * @return
	 * hora = Hora do dia
	 */
	public int getHora(Date data) {
		cal.setTime(data);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	/* Retorna a Hora atual
	 * 
	 * @return
	 * hora = Hora atual
	 */
	public int getHora() {
		return getHora(new Date());
	}

	
	public String getHoraMinuto( Date data ){
		return this.sdfHora.format( data );
	}
	
}
