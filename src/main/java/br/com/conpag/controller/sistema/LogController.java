package br.com.conpag.controller.sistema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.conpag.dao.sistema.LogDAO;
import br.com.conpag.entity.dto.sistema.LogDTO;
import br.com.conpag.entity.dto.sistema.UsuarioDTO;
import br.com.conpag.entity.sistema.Modulo;
import br.com.conpag.entity.sistema.Usuario;
import br.com.conpag.util.Log;

@Stateless
public class LogController {

	@Inject
	private LogDAO logDao;

	private SimpleDateFormat f;

	@PostConstruct
	private void init() {
		this.f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	public void registerLogSistema(String mensagem, int tipo) {
		logDao.saveLog(Log.NENHUM_MODULO, Log.SISTEMA, mensagem, tipo);
	}

	public void registerLogSistema(Usuario user, String mensagem, int tipo) {
		logDao.saveLog(Log.NENHUM_MODULO, user.getId(), mensagem, tipo);
	}



	public void registerLogConpag(Usuario user, String mensagem, int tipo) {
		if (user != null) {
			logDao.saveLog(Log.CONPAG, user.getId(), mensagem, tipo);
		} else {
			logDao.saveLog(Log.CONPAG, 0, mensagem, tipo);
		}
	}

	public List<LogDTO> getAll(Date inicio, Date fim) {
		return logDao.selectAll(inicio, fim);
	}

	public List<LogDTO> getByUser(UsuarioDTO user, Date inicio, Date fim) {
		int iduser = 0;
		if (user != null)
			iduser = user.getId();
		return logDao.selectByUser(iduser, inicio, fim);
	}

	public List<LogDTO> getByModulo(Modulo modulo, Date inicio, Date fim) {
		if (modulo.getId() < 1)
			modulo = null;
		return logDao.selectByModulo(modulo, inicio, fim);
	}

	public List<LogDTO> getByUserModulo(UsuarioDTO user, Modulo modulo, Date inicio, Date fim) {
		int iduser = 0;
		if (user != null)
			iduser = user.getId();
		if (modulo.getId() < 1)
			modulo = null;
		return logDao.selectByUserModulo(iduser, modulo, inicio, fim);
	}

	public int getIdModulo(String modulo) {
		return logDao.getIdModulo(modulo);
	}

	public void printLog(boolean b) {
		printLog(String.valueOf(b), Log.INFO);
	}

	public void printLog(int i) {
		printLog(String.valueOf(i), Log.INFO);
	}

	public void printLog(double d) {
		printLog(String.valueOf(d), Log.INFO);
	}

	public void printLog(char c) {
		printLog(String.valueOf(c), Log.INFO);
	}

	public void printLog(long l) {
		printLog(String.valueOf(l), Log.INFO);
	}

	public void printLog(String message) {
		printLog(message, Log.INFO);
	}

	public void printLog(String message, int tipo) {
		System.out.println(f.format(new Date()) + " - " + getType(tipo) + ": " + message);
	}

	public void printLog(Exception e) {
		printLog(e, Log.ERRO, true);
	}

	public void printLog(Exception e, boolean stackTrace) {
		printLog(e, Log.ERRO, stackTrace);
	}

	public void printLog(Exception e, int tipo, boolean stackTrace) {
		System.out.println(f.format(new Date()) + " - " + getType(tipo) + "-> Exception: " + e.getClass().toString()
				+ ": " + e.getMessage());
		System.out.println(f.format(new Date()) + " - " + getType(tipo) + "-> Causa: " + e.getCause());
		if (stackTrace) {
			e.printStackTrace();
		}
	}

	public void printLog(String message, Exception e) {
		printLog(message, e, Log.ERRO, true);
	}

	public void printLog(String message, Exception e, boolean stackTrace) {
		printLog(message, e, Log.ERRO, stackTrace);
	}

	public void printLog(String message, Exception e, int tipo, boolean stackTrace) {
		System.out.println(f.format(new Date()) + " - " + getType(tipo) + "-> " + message + "-> "
				+ e.getClass().toString() + ": " + e.getMessage());
		System.out.println(f.format(new Date()) + " - " + getType(tipo) + "-> Causa -> " + e.getCause());
		if (stackTrace) {
			e.printStackTrace();
		}
	}

	private String getType(int tipo) {
		switch (tipo) {
		case Log.INFO:
			return "INFO";
		case Log.AVISO:
			return "AVISO";
		case Log.ERRO:
			return "ERRO";
		default:
			return "INFO";
		}
	}

}
