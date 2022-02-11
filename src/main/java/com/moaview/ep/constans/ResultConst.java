package com.moaview.ep.constans;

public abstract interface ResultConst {
	public static enum CODE {
		SUCCESS(200),
		NOT_FOUND(404), 
		FORBIDDEN(403), 
		DUPLICATE(409), 
		LIMIT_EXCEEDED(509), 
		ERROR(500);

		private int val;

		private CODE(int code) {
			this.val = code;
		}

		public int toInt() {
			return this.val;
		}
	}

	public static enum ERROR_MESSAGE {
		VALID("valid"),
		EXCEPTION("exception");

		String code = "";

		private ERROR_MESSAGE(String code) {
			this.code = code;
		}

		public String toString() {
			return this.code;
		}
	}
}