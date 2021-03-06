package edu.cmu.cs.sasylf.util;


public class ErrorReport {
	public ErrorReport(Errors errorType, String msg, Span loc, String debugInfo, boolean isError) {
		this.errorType = errorType;
		this.customMessage = msg;
		this.loc = loc;
		this.debugInfo = debugInfo;
		this.isError = isError;
	}

	/**
	 * @return The detailed error message including file, line, and actual error text message
	 */
	public String getMessage() {
		String msg = loc == null ? "unknown file: " : loc.getLocation().toString() + ": ";
		if (!isError)
			msg = msg + "warning: ";
		msg = msg + getShortMessage();
		return msg;
	}

	/**
	 * @return The actual error text message
	 */
	public String getShortMessage() {
		String msg = "";
		if (errorType != null)
			msg = msg + errorType.getText();
		msg = msg + customMessage;
		return msg;
	}




	public final Errors errorType;
	public final String customMessage;
	public final Span loc;
	public final String debugInfo;
	public final boolean isError;
}
