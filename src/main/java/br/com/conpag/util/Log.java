package br.com.conpag.util;

import javax.inject.Singleton;

@Singleton
public class Log {

	// tipos
	public final static int INFO = 0;
	public final static int AVISO = 1;
	public final static int ERRO = 2;

	// usuarios
	public final static int SISTEMA = -1;

	public final static String NENHUM_MODULO = "";
	public final static String CONPAG = "conpag";
	
//	/***********************************************
//	 * 
//	 * SALVAR O LOG NO BD
//	 * 
//	 * **********************************************/
//	private static void saveLogDB(String modulo, int user, int tipo,
//			String message) {
//		Thread logt = new LogThread(modulo, user, tipo, message);
//		logt.start();
//	}
//
//	/***********************************************
//	 * metodos estaticos para serem utilizados no sistema
//	 * **********************************************/
//	public static void saveLog(String modulo, int user, int tipo, String message) {
//		saveLogDB(modulo, user, tipo, message);
//	}
//
//	public static void saveLog(String modulo, int tipo, String message) {
//		saveLogDB(modulo, SISTEMA, tipo, message);
//	}
//
//	public static void saveLog(String modulo, String message) {
//		saveLogDB(modulo, SISTEMA, INFO, message);
//	}
//
//	public static void saveLog(String modulo, String message, Exception e) {
//		saveLogDB(modulo, SISTEMA, ERRO, message + "-> "
//				+ e.getClass().toString() + ": " + e.getMessage());
//	}
//
//	public static void saveAndPrintLog(String modulo, int user, int tipo,
//			String message) {
//		printLog(message, tipo);
//		saveLogDB(modulo, user, tipo, message);
//	}
//
//	public static void saveAndPrintLog(String modulo, String message) {
//		printLog(message, INFO);
//		saveLog(modulo, message);
//	}
//
//	public static void saveAndPrintLog(String modulo, int user, String message) {
//		printLog(message, INFO);
//		saveLog(modulo, user, message);
//	}

}
